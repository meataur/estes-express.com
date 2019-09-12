/**
 * @author: Todd Allen
 *
 * Creation date: 11/19/2018
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
import com.estes.services.common.dto.Terminal;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.TerminalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes terminal info")
public class TerminalController
{
	@Autowired
	TerminalService termService;

	@ApiOperation(value = "Get Estes terminal info for given ID")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Terminal.class, message="Terminal information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/terminal/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getTerminal(@ApiParam(value="Terminal number") @PathVariable int id)
	{
		try {
			Terminal term = termService.getTerminal(id);
			if(term == null) {
				ServiceResponse resp = new ServiceResponse("Error", "No terminal info available for the terminal id "+id);
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Terminal>(term, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getTerminal
}
