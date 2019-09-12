/**
 * @author: Todd Allen
 *
 * Creation date: 07/02/2018
 * 
 */

package com.estes.myestes.login.dao.iface;

import com.estes.myestes.login.exception.LoginException;

public interface ErrorDAO extends BaseDAO
{

	/**
	 * Look up error message for error code
	 * 
	 * @param code Error code to look up
	 * @return  Error message associated with error code
	 */
	String getErrorMessage(String code) throws LoginException;
}
