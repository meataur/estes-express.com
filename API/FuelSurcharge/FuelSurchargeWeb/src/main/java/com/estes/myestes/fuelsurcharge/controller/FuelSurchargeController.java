package com.estes.myestes.fuelsurcharge.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.fuelsurcharge.dto.Header;
import com.estes.myestes.fuelsurcharge.dto.History;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;
import com.estes.myestes.fuelsurcharge.service.iface.FuelSurchargeService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes fuel surcharge")
public class FuelSurchargeController
{
	@Autowired
	FuelSurchargeService fuelSurchargeService;

	@ApiOperation(value="Retrieve fuel surcharge header info")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Header.class, message="Header information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value="/current", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<?> getHeader()
	{
		try {
			Header response = fuelSurchargeService.getHeader();
			return new ResponseEntity<Header>(response, HttpStatus.OK);
		}
		catch (FuelSurchargeException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getHeader

	@ApiOperation(value="Retrieve list of fuel surcharges for LTL and TL history")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=History.class, responseContainer="List", message="LTL and TL history data"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value="/history", method=RequestMethod.GET, produces="application/json")
	public ResponseEntity<?> getHistoryList()
	{
		try {
			List<History> resp = fuelSurchargeService.getHistoryList();
			return new ResponseEntity<List<History>>(resp, HttpStatus.OK);
		}
		catch (FuelSurchargeException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getHistoryList
}
