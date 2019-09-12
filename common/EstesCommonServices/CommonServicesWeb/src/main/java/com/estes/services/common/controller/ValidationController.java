package com.estes.services.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.service.iface.ValidationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api("Validate Estes PRO")
@RestController
public class ValidationController
{
	@Autowired
	private ValidationService validationService;
	
	@ApiOperation(value = "To validate PRO Number",notes="To validate PRO Number.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Boolean.class,message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error")
	})
	@GetMapping(value="/validate/pro")
	public ResponseEntity<?> validatePro( 
			@RequestParam("proNumber") String proNumber)
	{
		boolean response = validationService.validateProNumber(proNumber);
		return new ResponseEntity<>(response,HttpStatus.OK);
	} // validatePro

	@ApiOperation(value = "Validate City, State & ZIP",notes="Validate City, State & ZIP")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Boolean.class,message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error")	})
	@GetMapping(value="/validate/city_state_zip")
	public ResponseEntity<?> validateCityStateZip( 
			@RequestParam("city") String city,
			@RequestParam("state") String state,
			@RequestParam("zip") String zip
			)
	{
		boolean response = validationService.validateCityStateZip(city, state,zip);
		return new ResponseEntity<>(response,HttpStatus.OK);
	} // validateCityStateZip
}
