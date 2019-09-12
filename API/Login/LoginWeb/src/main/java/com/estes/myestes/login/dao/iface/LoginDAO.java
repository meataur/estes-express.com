/**
 * @author: Todd Allen
 *
 * Creation date: 06/25/2018
 * 
 */

package com.estes.myestes.login.dao.iface;

import com.estes.myestes.login.dto.AppAccessRequest;
import com.estes.myestes.login.dto.AuthenticationResponse;
import com.estes.myestes.login.dto.AuthorizationResponse;
import com.estes.myestes.login.dto.Credentials;
import com.estes.myestes.login.exception.LoginException;

public interface LoginDAO extends BaseDAO
{
	/**
	 * Valid authentication code
	 */
	public static String AUTHENT_VALID_CODE = "";
	/**
	 * Valid authorization code
	 */
	public static String AUTHOR_VALID_CODE = "";
	/**
	 * Unauthorized error code
	 */
	public static String AUTHOR_ERROR_CODE = "QNE4001";

	/**
	 * Call authentication SPROC
	 * 
	 * @param creds My Estes customer credentials
	 * @return  Login {@link AuthenticationResponse}
	 */
	AuthenticationResponse authenticate(Credentials creds) throws LoginException;

	/**
	 * Determine app access for My Estes user
	 * 
	 * @param access My Estes {@link AppAccessRequest}
	 * @return  {@link AuthorizationResponse} for app user
	 */
	AuthorizationResponse authorizeApp(AppAccessRequest access) throws LoginException;

	public static boolean isAuthenticationError(String code)
	{
		return !code.trim().equalsIgnoreCase(AUTHENT_VALID_CODE);
	} // isAuthenticationError

	public static boolean isAuthorizationError(String code)
	{
		return !code.trim().equalsIgnoreCase(AUTHOR_VALID_CODE);
	} // isAuthorizationError
	
	public boolean isAdmin(String username);
}
