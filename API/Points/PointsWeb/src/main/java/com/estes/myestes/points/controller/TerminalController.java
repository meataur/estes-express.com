/**
 * @author: Todd Allen
 *
 * Creation date: 02/20/2018
 */

package com.estes.myestes.points.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.service.iface.TerminalService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
	
	@ApiOperation(value = "Get Estes terminal info for the given point")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Terminal.class, message="Terminal information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/terminal", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getTerminal(@RequestBody Point point)
	{
		try {
			Terminal term = termService.getTerminal(point);
			if(term == null) {
				ServiceResponse resp = new ServiceResponse("Error", "No terminal info available.");
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<Terminal>(term, HttpStatus.OK);
		}
		catch (PointException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getTerminal
}
