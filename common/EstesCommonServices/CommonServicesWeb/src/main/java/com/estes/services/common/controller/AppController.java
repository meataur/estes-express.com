/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.AppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes web application status")
public class AppController
{
	@Autowired
	AppService appService;

	@ApiOperation(value = "Get Estes web application availability")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class, message="Boolean - Estes web application availability"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/appAvailable/{appName}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAppAvailability(@ApiParam(value="Application name") @PathVariable String appName)
	{
		try {
			boolean avail = appService.getAvailability(appName);
			// Return successful response
			return new ResponseEntity<Boolean>(avail, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getAppAvailability
}
