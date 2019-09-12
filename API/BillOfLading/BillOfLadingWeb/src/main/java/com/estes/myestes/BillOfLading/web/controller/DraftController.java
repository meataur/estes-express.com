package com.estes.myestes.BillOfLading.web.controller;

import javax.validation.Valid;

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
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.Draft;
import com.estes.myestes.BillOfLading.web.service.iface.DraftService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api(description="## - To create, edit, retrieve, delete and list services for Draft")
@RestController
public class DraftController {
	
	@Autowired
	private DraftService draftService;
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get draft Listing",notes="To get draft listing, Pagination, Sorting & Filtering.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successful Response with empty message & code"),
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
		@ApiImplicitParam(name="sort", dataType="String",allowableValues="bolNumber,bolDate,proNumber,shipper,consignee")
	})
	@GetMapping(value="/draft")
	public ResponseEntity<Page<Draft>> getDraftList(@ApiIgnore AuthenticationDetails auth,
			Pageable pageable,
			BolFilter filter
			){
		String username = auth.getUsername();
		Page<Draft> drafts = draftService.getDraftList(username,pageable,filter);
		return new ResponseEntity<>(drafts, HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To save as draft",notes="To save as draft. Here a BOL Number with username is unique defined in Table")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & empty errorCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/draft")
	public ResponseEntity<?> createBillOfLading(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody BillOfLading billOfLading){
		
		String username = auth.getUsername();
		draftService.createDraft(username, auth, billOfLading);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get a specific Draft details",notes="To get a specific Draft details.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BillOfLading.class, message = "Successful Response with empty message & empty errorCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/draft/{bolNumber}")
	public ResponseEntity<?> getBillOfLadingById(@ApiIgnore AuthenticationDetails auth,@PathVariable("bolNumber") String bolNumber){
		String username = auth.getUsername();
		BillOfLading billOfLading = draftService.getDraftByBolNumber(username, auth, bolNumber);
		return new ResponseEntity<>(billOfLading, HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To delete a Draft", notes="To delete a Draft")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & empty errorCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@DeleteMapping(value="/draft/{bolNumber}")
	public ResponseEntity<?> deleteDraft(@ApiIgnore AuthenticationDetails auth, @PathVariable("bolNumber") String bolNumber){
		String username = auth.getUsername();
		String accessToken = auth.getAccessToken();
		draftService.deleteDraft(username,accessToken,bolNumber);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
