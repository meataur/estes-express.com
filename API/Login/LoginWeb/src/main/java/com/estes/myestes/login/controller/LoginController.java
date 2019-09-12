/**
 * @author: Todd Allen
 *
 * Creation date: 06/25/2018
 */

package com.estes.myestes.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.estes.myestes.login.dto.AppAccessRequest;
import com.estes.myestes.login.dto.AuthenticationResponse;
import com.estes.myestes.login.dto.AuthorizationResponse;
import com.estes.myestes.login.dto.Credentials;
import com.estes.myestes.login.exception.LoginException;
import com.estes.myestes.login.service.iface.LoginService;
import com.estes.myestes.login.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes login")
public class LoginController
{
	@Autowired
	LoginService loginService;

	@ApiOperation(value = "Authenticate My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=AuthenticationResponse.class, message="Success message"),
			@ApiResponse(code=500, response=AuthenticationResponse.class, message="Error information")
	})
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody Credentials req, @ApiIgnore WebRequest webReq)
	{
		AuthenticationResponse resp = null;

		try {
			 resp = loginService.authenticate(req);
			// Return successful response
			return new ResponseEntity<AuthenticationResponse>(resp, HttpStatus.OK);
		}
		catch (LoginException e) {
            resp = new AuthenticationResponse(AuthenticationResponse.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<AuthenticationResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // authenticate

	@ApiOperation(value = "Authorize app access for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=AuthorizationResponse.class, message="Success message"),
			@ApiResponse(code=500, response=AuthorizationResponse.class, message="Error information")
	})
	@RequestMapping(value = "/authorizeApp", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<AuthorizationResponse> authorizeAppAccess(@RequestBody AppAccessRequest req, @ApiIgnore WebRequest webReq)
	{
		AuthorizationResponse resp = null;

		try {
			resp = loginService.authorizeAppUser(req);
			// Return successful response
			return new ResponseEntity<AuthorizationResponse>(resp, HttpStatus.OK);
		}
		catch (LoginException e) {
			resp = new AuthorizationResponse(AuthorizationResponse.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<AuthorizationResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // authorizeAppAccess
}
