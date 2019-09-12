/**
 * @author: Lakshman Kandaswamy
 *
 * Creation date: 10/10/2018
 *
 */

package com.estes.myestes.recoverpassword.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.edps.email.EMailMessage;
import com.estes.framework.logger.ESTESLogger;
import com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService;
import com.estes.myestes.recoverpassword.dao.iface.RecoverPasswordDAO;
import com.estes.myestes.recoverpassword.dto.RecoverPassword;
import com.estes.myestes.recoverpassword.dto.UserNamePassword;
import com.estes.myestes.recoverpassword.exception.RecoverPasswordException;
import com.estes.myestes.recoverpassword.service.iface.RecoverPasswordService;
import com.estes.myestes.recoverpassword.utils.RecoverPasswordConstants;

/**
 * Estes points lookup implementation 
 */
@Service("recoverPasswordService")
@Scope("prototype")
public class RecoverPasswordServiceImpl implements RecoverPasswordService, RecoverPasswordConstants
{
	@Autowired
	RecoverPasswordDAO recoverPasswordDAO;

	/**
	 * Create a new service
	 */
	public RecoverPasswordServiceImpl()
	{
		super();
	}

	@Override
	public UserNamePassword getUserNamePassword(String selectionType, String searchCriteria) throws RecoverPasswordException {
		UserNamePassword userNamePwd = null;
		if(selectionType.equals("userName")){
			userNamePwd = recoverPasswordDAO.getUserNamePassordWithUserName(searchCriteria);
		} else {
			userNamePwd = recoverPasswordDAO.getUserNamePasswordWithEmail(searchCriteria);
		}		
		return userNamePwd;
	} 
	
	@Override
	public boolean sendEmail(String userName, String password, String toEmail){
		String message = this.generateMessage(userName, password);
		
		EMailMessage emailMessage = new EMailMessage();
		emailMessage.setMailJndi(APP_MAIL_JNDI);
		emailMessage.setFrom(MAIL_FROM);
		emailMessage.addTo(toEmail);
		emailMessage.setSubject(MAIL_SUBJECT);
		emailMessage.setMessageBody(message);
		ESTESLogger.log(ESTESLogger.DEBUG, RecoverPassword.class, "sendEmail", "Completed.");
		
		return emailMessage.send();
	}
	
	private String generateMessage(String userName, String password){
		return "Your My Estes username is - " +
				userName +
				"\n" +
				"Your My Estes password is - " +
				password +
				"\n\n" +
				"To log in to My Estes visit http://www.estes-express.com ";
	}
}
