/**
 * @author: Todd Allen
 *
 * Creation date: 06/25/2018
 *
 */

package com.estes.myestes.login.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estes.myestes.login.dao.iface.ErrorDAO;
import com.estes.myestes.login.dao.iface.LoginDAO;
import com.estes.myestes.login.dto.AppAccessRequest;
import com.estes.myestes.login.dto.AuthenticationResponse;
import com.estes.myestes.login.dto.AuthorizationResponse;
import com.estes.myestes.login.dto.Credentials;
import com.estes.myestes.login.exception.LoginException;
import com.estes.myestes.login.service.iface.LoginService;

/**
 * My Estes login services implementation
 */
@Service("loginService")
@Scope("prototype")
@Transactional
public class LoginServiceImpl implements LoginService
{
	@Autowired
	private LoginDAO loginDAO;
	@Autowired
	private ErrorDAO errorDAO;

	/**
	 * Create a new service
	 */
	public LoginServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.service.iface.LoginService#authenticate
	 */
	@Override
	public AuthenticationResponse authenticate(Credentials creds) throws LoginException
	{
		// Upper case user name
		creds.setUserName(creds.getUserName().toUpperCase());
		AuthenticationResponse resp = loginDAO.authenticate(creds);

		
		if (LoginDAO.isAuthenticationError(resp.getError().getErrorCode())) {
			resp.getError().setMessage(errorDAO.getErrorMessage(resp.getError().getErrorCode()));
		}

		return resp;
	} // authenticate

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.service.iface.LoginService#authorizeAppUser
	 */
	@Override
	public AuthorizationResponse authorizeAppUser(AppAccessRequest accReq) throws LoginException
	{
		AuthorizationResponse response = loginDAO.authorizeApp(accReq);
		if (LoginDAO.isAuthorizationError(response.getError().getErrorCode())) {
			response.getError().setMessage(errorDAO.getErrorMessage(response.getError().getErrorCode()));
		}

		return response;
	} // authorizeAppUser
}
