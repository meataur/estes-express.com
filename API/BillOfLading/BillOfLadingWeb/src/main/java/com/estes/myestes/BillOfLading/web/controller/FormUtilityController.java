package com.estes.myestes.BillOfLading.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.service.iface.FormUtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(description="## - To retrieve option values for select/checkbox/radio inputs in BOL form.")
@RestController
public class FormUtilityController {
	
	@Autowired
	private FormUtilityService formUtilityService;
	

	@ApiOperation(value = "To get the list of Accessorials",notes="The service returns list of accessorials")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Accessorial.class, responseContainer="List", message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/accessorials")
	public ResponseEntity<?> getAccessorials(){
		List<Accessorial> response = formUtilityService.getAccessorials();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
