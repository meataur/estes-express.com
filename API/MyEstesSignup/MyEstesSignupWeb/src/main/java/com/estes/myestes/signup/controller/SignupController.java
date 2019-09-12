/**
 * @author: Todd Allen
 *
 * Creation date: 07/30/2018
 */

package com.estes.myestes.signup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.signup.dao.iface.ErrorDAO;
import com.estes.myestes.signup.dto.SignupInfo;
import com.estes.myestes.signup.exception.SignupException;
import com.estes.myestes.signup.service.iface.SignupService;
import com.estes.myestes.signup.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes profile signup operations")
public class SignupController
{
	@Autowired
	SignupService estesService;

	@ApiOperation(value = "Process My Estes profile signup request", response = ServiceResponse[].class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success message"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> processSignup(@RequestBody SignupInfo req, WebRequest webReq)
	{
		ServiceResponse[] resp = null;

		try {
			resp = estesService.requestProfile(req);
			// Check for error and return appropriate HTTP status code
			if (!ErrorDAO.isError(resp[0].getErrorCode())) {
				return new ResponseEntity<ServiceResponse[]>(resp, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse[]>(resp, HttpStatus.BAD_REQUEST);
			}
		}
		catch (SignupException e) {
			ServiceResponse err = new ServiceResponse(AppConstants.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_ERROR_MESSAGE);
			return new ResponseEntity<ServiceResponse>(err, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // processSignup
}
