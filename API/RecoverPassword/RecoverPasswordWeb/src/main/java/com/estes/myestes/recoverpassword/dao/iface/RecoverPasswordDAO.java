/**
 * @author: Lakshman Kandaswamy
 *
 * Creation date: 10/10/2018
 *
 */
package com.estes.myestes.recoverpassword.dao.iface;

import com.estes.myestes.recoverpassword.dto.UserNamePassword;
import com.estes.myestes.recoverpassword.exception.RecoverPasswordException;

public interface RecoverPasswordDAO
{
	/**
	 * gets username and pwd details with email
	 * @param emailAddress
	 * @return
	 * @throws RecoverPasswordException
	 */
	UserNamePassword getUserNamePasswordWithEmail(String emailAddress) throws RecoverPasswordException;
	
	/**
	 * gets username and pwd details with username
	 * @param userName
	 * @return
	 * @throws RecoverPasswordException
	 */
	UserNamePassword getUserNamePassordWithUserName(String userName) throws RecoverPasswordException;
}
