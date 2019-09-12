/**
 * @author: Todd Allen
 *
 * Creation date: 01/31/2018
 */

package com.estes.services.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.dto.Point;
import com.estes.services.common.dto.PointLookup;
import com.estes.services.common.exception.PointException;
import com.estes.services.common.service.iface.PointService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes address points lookup")
public class PointsController
{
	@Autowired
	PointService pointService;

	@ApiOperation(value = "Search for Estes points matching partial city/state/zip")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Point.class, responseContainer="List", message="List of Estes points"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/points/lookup", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> lookup(@RequestBody PointLookup lookup)
	{
		List<Point> points = null;

		try {
			points = pointService.search(lookup);
			// Return successful response
			return new ResponseEntity<List<Point>>(points, HttpStatus.OK);
		}
		catch (PointException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // lookup
	
	@ApiOperation(value = "Determine if a point is direct or not using city/state/zip/country")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class,message = "Successful Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/points/isDirect", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> isDirectPoint(@RequestBody Point point)
	{
		boolean isDirectResp = false;

		try {
			isDirectResp = pointService.isDirect(point);
			// Return successful response
			return new ResponseEntity<>(isDirectResp, HttpStatus.OK);
		}
		catch (PointException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // isDirectPoint
}
