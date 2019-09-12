/**
 *
 */

package com.estes.myestes.shipmentmanifest.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edps.email.EMailMessage;
import com.edps.email.EmailConstants;
import com.edps.format.StringFormat;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shipmentmanifest.dao.iface.ManifestDAO;
import com.estes.myestes.shipmentmanifest.dto.EmailRequestDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRequestDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestResponseDTO;
import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;
import com.estes.myestes.shipmentmanifest.service.iface.ShipmentManifestService;
import com.estes.myestes.shipmentmanifest.util.FileUtils;
import com.estes.myestes.shipmentmanifest.util.ShipmentManifestConstant;
import com.estes.myestes.shipmentmanifest.validator.ShipmentManifestValidator;

/**
 * Document retrieval service implementation 
 */
@Service("requestInfoService")
@Scope("prototype")
@Transactional
public class ShipmentManifestServiceImpl implements ShipmentManifestService, ShipmentManifestConstant {
	
	@Autowired
	ShipmentManifestValidator manifestValidator;
	
	@Autowired
	ManifestDAO manifestDAO;
	
	/**
	 * Create a new service
	 */
	public ShipmentManifestServiceImpl() {
		super();
	} // Constructor

	/**
	 * 
	 */
	public Page<ManifestRecordDTO> getManifest(Pageable pageable, ManifestRequestDTO request, Map<String, String> oauthDetails) throws ShipmentManifestException {
		Page<ManifestRecordDTO> page = null;
		List<ManifestRecordDTO> results;
		String accountType = oauthDetails.get("accountType");
		String accountNumber = oauthDetails.get("accountCode");
		String hash = oauthDetails.get("hash");
		String session = oauthDetails.get("session");
		
		// check the request is valid
		manifestValidator.performValidation(request, accountNumber, accountType);

		// get data
		results = manifestDAO.gatherData(pageable, request, accountNumber, accountType, hash, session);
		
		if(results != null && results.size() > 0) {
			int totalRecords = manifestDAO.getTotalNumberRecords(request, accountNumber, accountType, hash, session);
			page = new Page<ManifestRecordDTO>(results, pageable, totalRecords);
			
		}
		return page;
	}

	/**
	 * 
	 */
	public ServiceResponse emailManifest(EmailRequestDTO request, Map<String, String> oauthDetails) throws ShipmentManifestException {
		List<ManifestRecordDTO> results;
		String accountType = oauthDetails.get("accountType");
		String accountNumber = oauthDetails.get("accountCode");
		String hash = oauthDetails.get("hash");
		String session = oauthDetails.get("session");
		
		// check the request is valid
		manifestValidator.performValidation(request, accountNumber, accountType);
		
		// get data to email
		results = manifestDAO.gatherData(null, request, accountNumber, accountType, hash, session);
		
		// generate the file and email it
		boolean sent = email(results, request);
		
		if(sent && results != null && results.size() > 0) {
			ServiceResponse successDTO = new ServiceResponse();
			successDTO.setMessage("Successfully Sent Email");
			return successDTO;
		}
		else {
			ServiceResponse errDTO = new ServiceResponse();
			if(sent) {
				errDTO.setMessage("No results found.");
			}
			else {
				errDTO.setMessage("Unable to send Emails.");
			}
			errDTO.setErrorCode(ShipmentManifestConstant.ERROR_CODE);
			return errDTO;
		}
	}
	
	/**
	 * create the file from the data and send as an email attachment                
	 */
	private boolean email(List<ManifestRecordDTO> results, EmailRequestDTO request){
		boolean emailSent = false;
		String format = request.getFormat();
		String shipmentTypes = request.getShipmentTypes();
		String passedEmailAddresses = request.getEmail();
		Boolean charges = request.getViewCharges();
		String fullPath = " ";
			if(format.equals("XLS")){
				fullPath = FileUtils.createExcelFile(results, shipmentTypes, charges);
			}
			if(format.equals("TXT")){
				fullPath = FileUtils.createTabDelimitedFile(results, shipmentTypes, charges);
			}
			if(format.equals("CSV")){
				fullPath = FileUtils.createCSVFile(results, shipmentTypes, charges);
			}
	    	String[] emailAddresses = passedEmailAddresses.split("\n");
	    	boolean sentForAll = true;
	    	for(int count=0; count < emailAddresses.length; count++){
	    		String emailAddress = emailAddresses[count];
	    		if (!emailAddress.trim().equals("")){
	    			EMailMessage email = new EMailMessage();
	    			email.setMailJndi(ShipmentManifestConstant.APP_MAIL_JNDI);
	    			email.addTo(emailAddress);
	    			email.setFrom(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_FROM, ShipmentManifestConstant.VALUE));
	    	
	    			email.setSubject(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_SUBJECT, ShipmentManifestConstant.VALUE));
	    			if(results.size() > 0){
	    				email.setMessageBody(StringEscapeUtils.unescapeJava(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_BODY, ShipmentManifestConstant.VALUE)));
	    				email.addAttachment(fullPath);
	    			}
	    			else{
	    				email.setMessageBody(StringEscapeUtils.unescapeJava(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_NO_RESULTS_FOR_QUERY, ShipmentManifestConstant.VALUE)));
	    			}
	    			if(email.send()) {
	    				emailSent = true;
	    			}
	    			else {
	    				// failed to send all emails
	    				FileUtils.cleanUpFile(fullPath);
	    				return false;
	    			}
	    		}
	    	}
	    	FileUtils.cleanUpFile(fullPath);
	    	return emailSent;
	}
	
}