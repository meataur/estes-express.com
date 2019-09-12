/**
 *
 */

package com.estes.myestes.claims.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edps.email.EMailMessage;
import com.edps.email.EmailConstants;
import com.edps.format.StringFormat;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.claims.dao.iface.ErrorDAO;
import com.estes.myestes.claims.dao.iface.InquiryDAO;
import com.estes.myestes.claims.dao.iface.ValidateDAO;
import com.estes.myestes.claims.dto.ClaimDTO;
import com.estes.myestes.claims.dto.ClaimsRequestDTO;
import com.estes.myestes.claims.dto.EmailClaimsRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.service.iface.ClaimsInquiryService;
import com.estes.myestes.claims.util.ClaimsConstant;
import com.estes.myestes.claims.util.FileUtils;

@Service("claimsInquiryService")
@Scope("prototype")
@Transactional
public class ClaimsInquiryServiceImpl implements ClaimsInquiryService, ClaimsConstant {
	
	@Autowired
	InquiryDAO inquiry;
	
	@Autowired
	ValidateDAO validate;
	
	@Autowired
	ErrorDAO error;
	
	/**
	 * Create a new service
	 */
	public ClaimsInquiryServiceImpl() {
		super();
	} // Constructor

	@Override
	public boolean validAccount(String parentAccount, String subAccount, String accountType) throws ClaimsException {
		return validate.validateAccount(parentAccount, subAccount, accountType);
	}
	
	public List<ClaimDTO> getClaims(String account, String accountType, ClaimsRequestDTO request) throws ClaimsException {
		List<ClaimDTO> claims = new ArrayList<ClaimDTO>();
		List<String> getNumbers = new ArrayList<String>();
		List<ClaimDTO> invalidClaims = new ArrayList<ClaimDTO>();
		
		if (!"Date Range".equals(request.getSearchBy())) { 
			for (String number : request.getNumbers()) { // remove string that is non numeric
				if("Your Reference Number".equals(request.getSearchBy())){
					getNumbers.add(number);
				}else if(StringUtils.isNumeric(number)) {
					getNumbers.add(number);
				}else {
					ClaimDTO claimDTO = new ClaimDTO();
					claimDTO.setClaimNumber(number);
					claimDTO.setStatus(ClaimsConstant.INVALID_CLAIMS);
					invalidClaims.add(claimDTO);
				}
			}
		}
		
		if(invalidClaims.size()>0) {
			return invalidClaims;
		}
		
		switch(request.getSearchBy()) {
			case "Date Range":
				claims = inquiry.getClaimsByDate(request);
				break;
			case "PRO Number":
				for(String number: getNumbers) {
					claims.addAll(inquiry.getClaimsByProNumber(number, request.getAccountNumber()));
				}
				
				break;
			case "Your Reference Number":
				for(String number: getNumbers) {
					claims.addAll(inquiry.getClaimsByRefNumber(number, request.getAccountNumber()));
				}
				
				break;
			case "Estes Claim Number":
				for(String number: getNumbers) {
					claims.addAll(inquiry.getClaimsByClaimNumber(number, request.getAccountNumber()));
				}
				
				break;
		}
		
		return claims;
	}

	public boolean emailClaims(String account, String accountType, EmailClaimsRequestDTO request) throws ClaimsException {
		List<ClaimDTO> claims = new ArrayList<ClaimDTO>();
		
		switch(request.getSearchBy()) {
			case "Date Range":
				claims = inquiry.getClaimsByDate(request);
				break;
			case "PRO Number":
				for(String number: request.getNumbers()) {
					claims.addAll(inquiry.getClaimsByProNumber(number, request.getAccountNumber()));
				}
				
				break;
			case "Your Reference Number":
				for(String number: request.getNumbers()) {
					claims.addAll(inquiry.getClaimsByRefNumber(number, request.getAccountNumber()));
				}
				
				break;
			case "Estes Claim Number":
				for(String number: request.getNumbers()) {
					claims.addAll(inquiry.getClaimsByClaimNumber(number, request.getAccountNumber()));
				}
				
				break;
		}
		
		return email(claims, request);
	}
	
	/**
	 * create the file from the data and send as an email attachment                
	 */
	private boolean email(List<ClaimDTO> results, EmailClaimsRequestDTO request){
		String format = request.getFormat();
		boolean sent = false;
		String fullPath = " ";
			if(format.equals("XLS")){
				fullPath = FileUtils.createExcelFile(results);
			}
			if(format.equals("TXT")){
				fullPath = FileUtils.createTabDelimitedFile(results);
			}
			if(format.equals("CSV")){
				fullPath = FileUtils.createCSVFile(results);
			}
	    	String[] emailAddresses = request.getEmailAddresses().split("\n");
	    	for(int count=0; count < emailAddresses.length; count++){
	    		String emailAddress = emailAddresses[count];
	    		if (!StringFormat.isBlank(emailAddress)){
	    			EMailMessage email = new EMailMessage();
	    			email.setMailJndi(ClaimsConstant.APP_MAIL_JNDI);
	    			email.addTo(emailAddress);
	    			email.setFrom(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_FROM, EmailConstants.APP_EXT_PROP_MAIL_FROM));
	    	
	    			email.setSubject(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_SUBJECT, EmailConstants.APP_EXT_PROP_MAIL_SUBJECT));
	    			if(results.size() > 0){
	    				email.setMessageBody(StringEscapeUtils.unescapeJava(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_BODY, EmailConstants.APP_EXT_PROP_MAIL_BODY)));
	    				email.addAttachment(fullPath);
	    			}
	    			else{
	    				email.setMessageBody(StringEscapeUtils.unescapeJava(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_NO_RESULTS_FOR_QUERY, EmailConstants.APP_EXT_PROP_MAIL_NO_RESULTS_FOR_QUERY)));
	    			}
	    			sent = email.send();
	    		}
	    	}
	    	FileUtils.cleanUpFile(fullPath);
	    	return sent;
	}

	@Override
	public String getErrorMessage(String errorCode) {
		return error.getErrorMessage(errorCode);
	}

}