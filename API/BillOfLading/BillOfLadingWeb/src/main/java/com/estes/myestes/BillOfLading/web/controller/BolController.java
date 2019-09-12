package com.estes.myestes.BillOfLading.web.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Bol;
import com.estes.myestes.BillOfLading.web.dto.BolDocument;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.BolResponse;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;
import com.estes.myestes.BillOfLading.web.service.iface.BolService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api(description="## - To create, edit, retrieve, delete and list services for BOL History")
@RestController
public class BolController {
	
	@Autowired
	private BolService bolService;
	

	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get BOL History Listing",notes="To get BOL listing, Pagination, Sorting & Filtering.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name="filterBy", dataType="String",defaultValue="SHOW_ALL",allowableValues="SHOW_ALL, BOL_NUMBER, BOL_DATE_RANGE, PRO_NUMBER, SHIPPER, CONSIGNEE"),
		@ApiImplicitParam(name="bolNumber", value="__BOL Number is required if filterBy=BOL_NUMBER__", dataType="String"),
		@ApiImplicitParam(name="bolStartDate",  value="__BOL start Date (MM/DD/YYYY) is required if filterBy=BOL_DATE_RANGE__", dataType="String"),
		@ApiImplicitParam(name="bolEndDate",  value="__BOL end Date (MM/DD/YYYY) is required if filterBy=BOL_DATE_RANGE__", dataType="String"),
		@ApiImplicitParam(name="proNumber",  value="__PRO# is required if filterBy=PRO_NUMBER__", dataType="String"),
		@ApiImplicitParam(name="shipper",  value="__Shipper Company Name. It is required if filterBy=SHIPPER__", dataType="String"),
		@ApiImplicitParam(name="consignee",  value="__Consignee Company Name. It is required if filterBy=CONSIGNEE__", dataType="String"),
		@ApiImplicitParam(name="order", dataType="String",defaultValue="asc",allowableValues="asc,desc"),
		@ApiImplicitParam(name="page", dataType="Integer",defaultValue="1"),
		@ApiImplicitParam(name="size", dataType="Integer",defaultValue="25",value="Maximum value : 100"),
		@ApiImplicitParam(name="sort", dataType="String",allowableValues="bolNumber,bolDate,proNumber,shipper,consignee,hasShippingLabel")
	})
	@GetMapping(value="/bol")
	public ResponseEntity<Page<Bol>> getBolHistory(@ApiIgnore AuthenticationDetails auth,
			Pageable pageable,
			 BolFilter filter
			){
		String username = auth.getUsername();
		Page<Bol> billOfLading = bolService.getBolHistory(username,pageable,filter);
		return new ResponseEntity<>(billOfLading, HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To create new BOL",notes="To create new BOL")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BolResponse.class, message = "After Creating new BOL, the resultant object"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/bol")
	public ResponseEntity<?> createBol(@ApiIgnore AuthenticationDetails auth,
			@RequestBody BillOfLading billOfLading){

		String updateType   = "W";
		String templateName = "";
		
		
		BolResponse bolResponse = bolService.createBol(auth, billOfLading, updateType, templateName);

		Bol bol = bolService.getBolById(auth.getUsername(),billOfLading.getBolId());
		bolResponse.setBol(bol);
		
		return new ResponseEntity<>(bolResponse, HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get a specific BOL details", notes="To get a specific BOL details.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BillOfLading.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/bol/{id}")
	public ResponseEntity<?> getBillOfLadingById(@ApiIgnore AuthenticationDetails auth,@PathVariable("id") int id){
		String username = auth.getUsername();
		BillOfLading billOfLading = bolService.getBillOfLadingById(username,auth, id);
		return new ResponseEntity<>(billOfLading, HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To cancel a BOL",notes="To cancel a created BOL")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & empty errorCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@DeleteMapping(value="/bol/{id}")
	public ResponseEntity<?> cancelBillOfLading(@ApiIgnore AuthenticationDetails auth,@PathVariable("id") int id){
		String username = auth.getUsername();
		String accessToken = auth.getAccessToken();
		bolService.cancelBol(username, accessToken, id);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To view & download a BOL",notes="To view & download a BOL")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BolDocument.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/bol/view/{id}")
	public ResponseEntity<?> viewBolLabel(@ApiIgnore AuthenticationDetails auth,
			@PathVariable("id") int id){
		String username = auth.getUsername();
		BolDocument bolDocument = bolService.getBolDocument(username,id);
		return new ResponseEntity<>(bolDocument, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To create a BOL Shipping Label",notes="To create a BOL Shipping Label")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BolDocument.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/bol/{id}/shipping_label")
	public ResponseEntity<?> createBolLabel(@ApiIgnore AuthenticationDetails auth,
			@PathVariable("id") int id,
			@RequestBody ShippingLabel shippingLabel){
		String username = auth.getUsername();
		BolDocument bolDocument = bolService.createBolLabelDocument(username,id,shippingLabel);
		return new ResponseEntity<>(bolDocument, HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To download a BOL Shipping Label",notes="To create a BOL Shipping Label")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BolDocument.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/bol/{id}/shipping_label")
	public ResponseEntity<?> downloadBolLabel(@ApiIgnore AuthenticationDetails auth,
			@PathVariable("id") int id){
		String username = auth.getUsername();
		BolDocument bolDocument = bolService.getBolLabelDocument(username,id);
		return new ResponseEntity<>(bolDocument, HttpStatus.OK);
	}

}
