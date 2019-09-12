/**
 * @author: Lakshman K
 *
 * Creation date: 10/10/2018
 */

package com.estes.myestes.recoverpassword.service.iface;

import com.estes.myestes.recoverpassword.dto.UserNamePassword;
import com.estes.myestes.recoverpassword.exception.RecoverPasswordException;

/**
 * Point lookup service
 */
public interface RecoverPasswordService
{
	/**
	 * get userName and Password
	 * 
	 * @param  selectionType, searchCriteria
	 * @return UserNamePassword
	 */
	public UserNamePassword getUserNamePassword(String selectionType, String searchCriteria) throws RecoverPasswordException;
	
	/**
	 * send email with username and password
	 * @param userName
	 * @param password
	 * @param toEmail
	 * @return
	 */
	public boolean sendEmail(String userName, String password, String toEmail);
}
