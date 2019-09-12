/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2018
 */

package com.estes.myestes.points.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dto.CoverageRequest;
import com.estes.myestes.points.dto.CoverageResponse;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.service.iface.CoverageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Show Estes terminal coverage and service for address point")
public class CoverageController
{
	@Autowired
	CoverageService coverService;

	@ApiOperation(value = "Search for Estes terminal servicing given point")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Terminal.class, responseContainer="List", message="Terminals servicing point"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/coverage", method = RequestMethod.POST)
	public ResponseEntity<?> getCoverage(@RequestBody CoverageRequest cvr)
	{
		CoverageResponse resp = null;

		try {
			resp = coverService.getTerminals(cvr);
			// Check for error and return ServiceResponse if error present
			if (!ServiceResponse.isError(resp.getErrorInfo().getErrorCode())) {
				return new ResponseEntity<List<Terminal>>(resp.getTerminals(), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(resp.getErrorInfo(), HttpStatus.BAD_REQUEST);
			}
		}
		catch (PointException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getCoverage
}
