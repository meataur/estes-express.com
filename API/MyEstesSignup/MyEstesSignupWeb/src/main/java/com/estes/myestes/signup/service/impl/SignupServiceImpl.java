/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.signup.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.edps.email.EMailMessage;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.signup.dao.iface.ErrorDAO;
import com.estes.myestes.signup.dao.iface.SignupDAO;
import com.estes.myestes.signup.dto.SignupInfo;
import com.estes.myestes.signup.exception.SignupException;
import com.estes.myestes.signup.service.iface.SignupService;
import com.estes.myestes.signup.utils.AppConstants;


@Service("signupService")
@Scope("prototype")
public class SignupServiceImpl implements SignupService
{
	@Autowired
	private SignupDAO signupDAO;
	@Autowired
	private ErrorDAO errorDAO;

	/**
	 * Create a new service
	 */
	public SignupServiceImpl() {	}

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.signup.service.iface.SignupService#requestProfile()
	 */
	public ServiceResponse[] requestProfile(SignupInfo profile) throws SignupException
	{
		List<String> errorCodes = signupDAO.createProfile(profile);
		if (errorCodes == null) {
			// If L2L account then send e-mail to sales support
			if (signupDAO.isL2L(profile.getAccountCode())) {
				sendEmail(profile);
			}
			return new ServiceResponse[] {new ServiceResponse("","Thank you for your submission. You should receive a reply within 48 hours.")};
		}

		// Set error info for each code
		List<ServiceResponse> errorList = new ArrayList<ServiceResponse>();
		for (String code : errorCodes) {
			ServiceResponse resp = new ServiceResponse(code, errorDAO.getErrorMessage(code));
			errorList.add(resp);
		}

		return errorList.toArray( new ServiceResponse[errorList.size()] );
	} // requestProfile

	private void sendEmail(SignupInfo info) throws SignupException
	{
		String emailBody = "" +
				"Please approve the Level2 My Estes profile request below."+
				"\n\n"+
				"First Name: "+
				info.getFirstName() +
				"\n\n"+
				"Last Name: "+
				info.getLastName() +
				"\n\n"+
				"Company Name: "+
				info.getCompany() +
				"\n\n"+
				"Customer Number: "+
				info.getAccountCode() +
				"\n\n"+
				"Phone Number: "+
				info.getPhone() +
				"\n\n"+
				"User Name: "+
				info.getUserName() +
				"\n\n";

			try {
				EMailMessage email = new EMailMessage();
				email.setMailJndi(AppConstants.APP_MAIL_JNDI);
				email.setFrom(ESTESConfigUtil.getProperty(AppConstants.MAIL, AppConstants.MAIL_FROM));
				email.addTo(ESTESConfigUtil.getProperty(AppConstants.MAIL, AppConstants.MAIL_TO));
				email.setSubject(ESTESConfigUtil.getProperty(AppConstants.MAIL, AppConstants.MAIL_SUBJECT));
				email.setMessageBody(emailBody);
				email.send();
			}
			catch (Exception e) {
				ESTESLogger.log(ESTESLogger.ERROR, SignupServiceImpl.class, "sendEmail()", "** Error occurred sending L2L MyEstes profile request e-mail.", e);
	    		throw new SignupException("L2L MyEstes profile request e-mail failed.");
			}
	} // sendEmail
}
