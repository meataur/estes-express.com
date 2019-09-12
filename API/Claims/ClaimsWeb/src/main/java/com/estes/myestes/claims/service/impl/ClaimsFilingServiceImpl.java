/**
 *
 */

package com.estes.myestes.claims.service.impl;

import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.edps.email.EmailConstants;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.claims.dao.iface.FilingDAO;
import com.estes.myestes.claims.dto.ClaimantResponseDTO;
import com.estes.myestes.claims.dto.OtProRequestDTO;
import com.estes.myestes.claims.dto.ProInfoResponseDTO;
import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.service.iface.ClaimsFilingService;
import com.estes.myestes.claims.util.ClaimsConstant;
import com.estes.myestes.claims.util.ClaimsEmail;
import com.estes.myestes.claims.util.ClaimsFormFileItems;
import com.estesexpress.www.toolbox.operations.Account;

@Service("claimsFilingService")
@Scope("prototype")
@Transactional
public class ClaimsFilingServiceImpl implements ClaimsFilingService, ClaimsConstant {
	
	@Autowired
	FilingDAO filing;
	
	@Value("${maxUploadSize}")
	long maxUploadSize;
	
	/**
	 * Create a new service
	 */
	public ClaimsFilingServiceImpl() {
		super();
	} // Constructor

	@Override
	public ProInfoResponseDTO getProInfo(OtProRequestDTO request) throws ClaimsException {
		String otPro[] = request.getOtpro().split("-");
		if(otPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		
		return filing.getProInfo(otPro[0], otPro[1]);
	}
	
	@Override
	public ClaimantResponseDTO getClaimantInfo(String username) throws ClaimsException {
		return filing.getClaimantInfo(username);
	}

	@Override
	public boolean isPartyToShipment(String accountNumber, String accountType, String otProString) throws ClaimsException {
		String otPro[] = otProString.split("-");
		if(otPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		
		String type = filing.accountTypeForCode(accountNumber);
		if(type != null && !type.equals("")) {
			accountType = type;
		}
		Account account = new Account(accountNumber, accountType, ClaimsConstant.APP_JNDI);
		return account.isPartyToPro(otPro[0], otPro[1], ClaimsConstant.APP_JNDI);
		
//		return filing.isPartyToShipment(account, accountType, otpro[0], otpro[1]);
	}

	@Override
	public boolean isL2LShipment(String otProString) throws ClaimsException {
		String otPro[] = otProString.split("-");
		if(otPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		
		return filing.isL2LShipment(otPro[0]);
	}
	
	@Override
	public boolean hasEnteredClaim(String otProString) throws ClaimsException {
		String otPro[] = otProString.split("-");
		if(otPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		
		return filing.hasEnteredClaim(otPro[0], otPro[1]);
	}
	
	@Override
	public ServiceResponse fileClaim(String username, SubmitClaimRequestDTO claim) throws ClaimsException {
		ServiceResponse response = new ServiceResponse();
		
		// validate input
		String otPro[] = claim.getOtPro().split("-");
		if(otPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		
		// check file upload size
		if (claim.getMultipartFilesTotalSize() > maxUploadSize){
			response.setMessage("The total size of the documents you are attempting to upload is larger than " + FileUtils.byteCountToDisplaySize(maxUploadSize) + ". "
					+ "Please reduce the size of your documents and try resubmitting your claim.");
			response.setErrorCode(ClaimsConstant.ERROR_CODE);
			return response;
		}
		
		// upload files and translate them all and details to object
		ClaimsFormFileItems fileItems = new ClaimsFormFileItems(claim);
		
		// get claim number
		String claimTypeNumber = filing.getClaimTypeNumber(claim.getClaimType(), claim.getOtPro());
		
		// write the header
		String claimNumber = filing.writeHeader(username, claim, claimTypeNumber);
		// if the rpg program called in writeheader returns 0000000 this is a duplicate
		// and return an error to the user
		if ("0000000".equals(claimNumber)){
			response.setMessage("A claim has already been entered for this PRO number. "
					+ "Please contact the claims department at (855) 693-7878, Ext. 2035 for more information.");
			response.setErrorCode(ClaimsConstant.ERROR_CODE);
			return response;
		}
		
		// move the uploaded files into the claims directory
		try {
			fileItems.changeDirectories(claimNumber);
		} catch(IOException e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsFilingService.class, "fileClaim()", "** Error moving uploaded files.");
    		throw new ClaimsException("Error moving uploaded files.");
		}
		
		// write the details
		filing.writeDetails(claimNumber, claimTypeNumber, fileItems);
		
		// get info to email
		ProInfoResponseDTO proInfo = filing.getProInfo(otPro[0], otPro[1]);
		// email
		ClaimsEmail email = new ClaimsEmail(claim, proInfo, filing.getCompanyName(username), fileItems, claimNumber);
		if (email.sendClaim()) { 
			if (email.sendConfirmation()) {
				response.setMessage("Your claim number is "+claimNumber+". Please keep this number for your records. " +
						"We will send a confirmation e-mail to "+claim.getEmail()+", and you will also receive an " +
						"acknowledgment via the U.S. Mail. You will be able to track your claim's status online within 48 hours.");
				return response;
			} else {
				response.setMessage("Failed to send email to "+claim.getEmail());
				response.setErrorCode(ClaimsConstant.ERROR_CODE);
				return response;
			}
		} else {
			response.setMessage("Failed to send email to "+ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_CLAIMS, EmailConstants.APP_EXT_PROP_MAIL_CLAIMS));
			response.setErrorCode(ClaimsConstant.ERROR_CODE);
			return response;
		}
		
	}
	
}