package com.estes.myestes.PickupRequest.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.PickupRequest.web.dto.BolPickup;
import com.estes.myestes.PickupRequest.web.dto.BolType;
import com.estes.myestes.PickupRequest.web.dto.Notification;
import com.estes.myestes.PickupRequest.web.dto.Role;
import com.estes.myestes.PickupRequest.web.service.iface.BolPickupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;

@Api(description="## <span style=\"color:#F88\">These services are being used only by BOL application. There are no UIs in PickupRequest for these services. </style>")
@RestController
public class BolPickupController {
	
	@Autowired
	private BolPickupService bolPickupService;
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To save and update BOL pickup Information for HISTORY, DRAFT, TEMPLATE & DEFAULT",notes="To save and update BOL pickup Information for DRAFT, TEMPLATE & DEFAULT")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/bol/pickup")
	public ResponseEntity<?> saveBolPickup(
			@ApiParam(required=true)
			@RequestParam("type") BolType bolType,
			@ApiParam(value="Required only if bolType is HISTORY.",required=false)
			@RequestParam(name="bolId",required=false) String bolId,
			@ApiParam(value="Required only if bolType is DRAFT.",required=false)
			@RequestParam(name="bolNo",required=false) String bolNumber,
			@ApiParam(value="Required only if bolType is TEMPLATE.",required=false)
			@RequestParam(name="template",required=false) String template,
			@RequestBody BolPickup bolPickup){
		
		bolPickupService.saveBolPickup(bolPickup, bolType, bolId, bolNumber, template);
		
		
		ServiceResponse response = new ServiceResponse();
		response.setErrorCode("");
		response.setMessage("Successfully saved!");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To retrieve BOL Pickup Information for HISTORY, DRAFT, TEMPLATE & DEFAULT",notes="To retrieve BOL Pickup Information for HISTORY, DRAFT, TEMPLATE & DEFAULT")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BolPickup.class,message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/bol/pickup")
	public ResponseEntity<?> getBolPickup(
			@ApiParam(required=true)
			@RequestParam("type") BolType bolType,
			
			@ApiParam(value="Required only if bolType is HISTORY.",required=false)
			@RequestParam(name="bolId",required=false) String bolId,

			@ApiParam(value="Required only if bolType is DRAFT.",required=false)
			@RequestParam(name="bolNo",required=false) String bolNumber,
			
			@ApiParam(value="Required only if bolType is TEMPLATE.",required=false)
			@RequestParam(name="template",required=false) String template
			){
			
		BolPickup bolPickup = bolPickupService.getBolPickup(bolType, bolId, bolNumber, template);
		
		return new ResponseEntity<>(bolPickup,HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To delete BOL Pickup Information for BOL HISTORY, DRAFT, TEMPLATE & DEFAULT",notes="To delete BOL Pickup Information for BOL HISTORY, DRAFT, TEMPLATE & DEFAULT")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BolPickup.class,message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@DeleteMapping(value="/bol/pickup")
	public ResponseEntity<?> deleteBolPickup(
			@ApiParam(required=true)
			@RequestParam("type") BolType bolType,
			
			@ApiParam(value="Required only if bolType is HISTORY.",required=false)
			@RequestParam(name="bolId",required=false) String bolId,
			
			@ApiParam(value="Required only if bolType is DRAFT.",required=false)
			@RequestParam(name="bolNo",required=false) String bolNumber,
			
			@ApiParam(value="Required only if bolType is TEMPLATE.",required=false)
			@RequestParam(name="template",required=false) String template){
		
		bolPickupService.deleteBolPickup(bolType, bolId, bolNumber, template);
		
		ServiceResponse response = new ServiceResponse();
		response.setErrorCode("");
		response.setMessage("Deleted successfully!");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To save and update BOL Pickup notification information for HISTORY, DRAFT, TEMPLATE & DEFAULT",notes="To save BOL Pickup notification information for DRAFT, TEMPLATE & DEFAULT")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/bol/pickup/notification")
	public ResponseEntity<?> saveBolPickupNotification(
			@ApiParam(required=true)
			@RequestParam("type") BolType bolType,
			@ApiParam(value="Required only if bolType is HISTORY.",required=false)
			@RequestParam(name="bolId",required=false) String bolId,
			
			@ApiParam(value="Required only if bolType is DRAFT",required=false)
			@RequestParam(name="bolNo",required=false) String bolNumber,
			
			@ApiParam(value="Required only if bolType is TEMPLATE.",required=false)
			@RequestParam(name="template",required=false) String template,
			
			final Notification notification,
			@RequestBody List<Role> roles
			){
		
		bolPickupService.saveBolPickupNotification(roles, notification, bolType, bolId, bolNumber, template);
		ServiceResponse response = new ServiceResponse();
		response.setErrorCode("");
		response.setMessage("Successfully saved!");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To retrieve the list of role (party) who gets the notification for BOL HISTORY, DRAFT, TEMPLATE & DEFAULT",notes="To retrieve the list of role (party) who gets the notification for BOL HISTORY, DRAFT, TEMPLATE & DEFAULT")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Role.class, responseContainer="List",message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/bol/pickup/notification")
	public ResponseEntity<?> getBolPickupNotification(
			
			@ApiParam(required=true)
			@RequestParam("type") BolType bolType,
			
			@ApiParam(value="Required only if bolType is HISTORY.",required=false)
			@RequestParam(name="bolId",required=false) String bolId,
			
			@ApiParam(value="Required only if bolType is DRAFT.",required=false)
			@RequestParam(name="bolNo",required=false) String bolNumber,
			
			@ApiParam(value="Required only if bolType is TEMPLATE.",required=false)
			@RequestParam(name="template",required=false) String template
			){
		
		List<Role> roles = bolPickupService.getBolPickupNotification(bolType, bolId, bolNumber, template);
		
		return new ResponseEntity<>(roles,HttpStatus.OK);
	}
}
