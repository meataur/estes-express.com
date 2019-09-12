package com.estes.myestes.PickupRequest.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.PickupRequest.web.dto.Pickup;
import com.estes.myestes.PickupRequest.web.dto.PickupRequest;
import com.estes.myestes.PickupRequest.web.service.iface.PickupHistoryService;
import com.estes.myestes.PickupRequest.web.service.iface.PickupRequestService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(description="Pickup Request with Pickup History Services")
@RestController
public class PickupRequestController {
	
	@Autowired
	private PickupRequestService pickupRequestService;
	
	@Autowired
	private PickupHistoryService pickupHistoryService;
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To save pickup request",notes="To save pickup request")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/pickup")
	public ResponseEntity<PickupRequest> createPickupRequest(
		@ApiParam(value="consignee, partyNotificationList and  bolId is not needed for PickupRequest from PickupRequest Application")
		@Valid @RequestBody PickupRequest pickupRequest
		){
		pickupRequestService.createPickupRequest(pickupRequest);
		return new ResponseEntity<>(pickupRequest,HttpStatus.OK);
	}

	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get pickup history list, pagination & filtering",notes="To get pickup history list, pagination & filtering")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@ApiImplicitParams({
			@ApiImplicitParam(name="order", dataType="String",defaultValue="asc",allowableValues="asc,desc"),
			@ApiImplicitParam(name="page", dataType="Integer",defaultValue="1"),
			@ApiImplicitParam(name="size", dataType="Integer",defaultValue="25",value="Maximum value : 100"),
			@ApiImplicitParam(name="sort", dataType="String",defaultValue="submittedDate",allowableValues="pickupDate,proNumber,requestNumber,shipperCity,shipperCompany,shipperState,status,submittedDate,terminalFax,terminalName,terminalPhone,totalPieces,totalWeight")
	})
	@GetMapping(value="/pickup")
	public ResponseEntity<Page<Pickup>> getPickup(final Pageable pageable){
		Page<Pickup> response = pickupHistoryService.getPickup(pageable);
		return new ResponseEntity<>(response,HttpStatus.OK);
	}

}
