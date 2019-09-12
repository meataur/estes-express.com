package com.estes.myestes.PickupRequest.web.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.PickupRequest.util.EstesUtil;
import com.estes.myestes.PickupRequest.web.dto.Commodity;
import com.estes.myestes.PickupRequest.web.dto.Party;
import com.estes.myestes.PickupRequest.web.dto.PickupCalendar;
import com.estes.myestes.PickupRequest.web.dto.User;
import com.estes.myestes.PickupRequest.web.service.iface.PickupRequestFormService;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
@Api(description="Pickup Request Form Utility Services")
@RestController

public class PickupRequestFormUtitilyController {
	
	@Autowired
	private PickupRequestFormService pickupRequestFormService;
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get the list of previous users",notes="To get the list of previous users")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=User.class, responseContainer="List", message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/users")
	public ResponseEntity<?> getUsers(@RequestParam(name="size",defaultValue="10", required=false) int size){
		List<User> response = pickupRequestFormService.getUsers(size);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get the list of previous shippers",notes="To get the list of previous shippers")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Party.class, responseContainer="List", message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/shippers")
	public ResponseEntity<?> getShippers(@RequestParam(name="size",defaultValue="10", required=false) int size){
		List<Party> response = pickupRequestFormService.getShippers(size);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get the list of previous commodities",notes="To get the list of previous commodities")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Commodity.class, responseContainer="List", message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/commodities")
	public ResponseEntity<?> getCommodities(@RequestParam(name="size",defaultValue="10", required=false) int size){
		List<Commodity> commodities = pickupRequestFormService.getCommodities(size);
		return new ResponseEntity<>(commodities,HttpStatus.OK);
	}
	
	

	@ApiOperation(value = "To get the available pickup dates",notes="To get the available pickup dates")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=PickupCalendar.class, responseContainer="List", message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/available_pickup_dates")
	public ResponseEntity<?> getAvailablePickupDates(){
		List<PickupCalendar> response = pickupRequestFormService.getAvailablePickupDates();
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

	@ApiOperation(value = "To validate pickup date",notes="To validate pickup date. pickupDate format should be - MM/DD/YYYY")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Boolean.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/validate/pickup_date")
	public ResponseEntity<?> validatePickupDate( 
			@JsonFormat(shape = JsonFormat.Shape.STRING, pattern="MM/dd/YYYY",timezone="EST") 
			@RequestParam("pickupDate") Date pickupDate){
		
		int date = Integer.parseInt(EstesUtil.formatDate(pickupDate,EstesUtil.DATE_YYYYMMDD));
		
		boolean response = pickupRequestFormService.validatePickupDate(date);

		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

}
