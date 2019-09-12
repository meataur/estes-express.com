/**
 * @author: Todd Allen
 *
 * Creation date: 11/08/2018
 */

package com.estes.services.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.StateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="States/provinces for a country")
public class StateController
{
	@Autowired
	StateService stService;

	@ApiOperation(value = "Get all states/provinces for given country abbreviation")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=String.class, responseContainer="List", message="State/province abbreviations"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/states/{country}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getStates(@ApiParam(value="2-letter country abbreviation") @PathVariable String country)
	{
		try {
			List<String> states = stService.getStates(country);
			// Return successful response
			return new ResponseEntity<List<String>>(states, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getStates
}
