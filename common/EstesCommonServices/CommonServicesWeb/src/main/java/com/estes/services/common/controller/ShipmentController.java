/**
 * @author: James Arthur
 *
 * Creation date: 11/30/2018
 */

package com.estes.services.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.service.iface.ShipmentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes shipment services")
public class ShipmentController {
	@Autowired
	ShipmentService shipService;

	@ApiOperation(value = "Check if the ot pro is l2l - pass otPro request param as XXX-XXXX")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class, message="Whether or not ot pro is l2l"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@GetMapping(value = "/shipment/isL2L", produces = {"application/json"})
	public ResponseEntity<?> isL2L(@RequestParam(name="otPro") String otPro) {
		try {
			boolean isL2L = shipService.isL2L(otPro);
			return new ResponseEntity<Boolean>(isL2L, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // isL2L
	
	@ApiOperation(value = "Check if the ot pro is efw - pass otPro request param as XXX-XXXX")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class, message="Whether or not ot pro is efw"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@GetMapping(value = "/shipment/isEFW", produces = {"application/json"})
	public ResponseEntity<?> isEFW(@RequestParam(name="otPro") String otPro) {
		try {
			boolean isEFW = shipService.isEFW(otPro);
			return new ResponseEntity<Boolean>(isEFW, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // isEFW

}
