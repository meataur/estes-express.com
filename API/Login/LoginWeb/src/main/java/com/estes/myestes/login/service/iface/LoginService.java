/**
 * @author: Todd Allen
 *
 * Creation date: 06/25/2018
 */

package com.estes.myestes.login.service.iface;

import com.estes.myestes.login.dto.AppAccessRequest;
import com.estes.myestes.login.dto.AuthenticationResponse;
import com.estes.myestes.login.dto.AuthorizationResponse;
import com.estes.myestes.login.dto.Credentials;
import com.estes.myestes.login.exception.LoginException;

/**
 * My Estes login service
 */
public interface LoginService
{
	/**
	 * Authenticate My Estes user
	 * 
	 * @param  cred Login {@link Credentials}
	 * @return {@link AuthenticationResponse} object
	 */
	public AuthenticationResponse authenticate(Credentials cred) throws LoginException;

	/**
	 * Authorize My Estes user to app
	 * 
	 * @param  acc {@link AppAccessRequest} for My Estes user to app
	 * @return {@link AuthorizationResponse} object
	 */
	public AuthorizationResponse authorizeAppUser(AppAccessRequest acc) throws LoginException;
}
