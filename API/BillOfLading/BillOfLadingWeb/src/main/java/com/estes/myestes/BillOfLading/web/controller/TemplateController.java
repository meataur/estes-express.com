package com.estes.myestes.BillOfLading.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.TemeplateFilter;
import com.estes.myestes.BillOfLading.web.dto.Template;
import com.estes.myestes.BillOfLading.web.service.iface.TemplateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api(description="## - To create, edit, retrieve, delete and list services for Template")
@RestController
public class TemplateController {
	
	@Autowired
	private TemplateService templateService;
    
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get Template Listing",notes="To get Template listing, Pagination, Sorting & Filtering.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name="filterBy", dataType="String",defaultValue="SHOW_ALL",allowableValues="SHOW_ALL, TEMPLATE_NAME, BOL_NUMBER, BOL_DATE_RANGE, PRO_NUMBER, SHIPPER, CONSIGNEE"),
		@ApiImplicitParam(name="templateName", value="__Template Name is required if filterBy=TEMPLATE_NAME__", dataType="String"),
		@ApiImplicitParam(name="bolNumber", value="__BOL Number is required if filterBy=BOL_NUMBER__", dataType="String"),
		@ApiImplicitParam(name="bolStartDate",  value="__BOL start Date (MM/DD/YYYY) is required if filterBy=BOL_DATE_RANGE__", dataType="String"),
		@ApiImplicitParam(name="bolEndDate",  value="__BOL end Date (MM/DD/YYYY) is required if filterBy=BOL_DATE_RANGE__", dataType="String"),
		@ApiImplicitParam(name="proNumber",  value="__PRO# is required if filterBy=PRO_NUMBER__", dataType="String"),
		@ApiImplicitParam(name="shipper",  value="__Shipper Company Name. It is required if filterBy=SHIPPER__", dataType="String"),
		@ApiImplicitParam(name="consignee",  value="__Consignee Company Name. It is required if filterBy=CONSIGNEE__", dataType="String"),
		@ApiImplicitParam(name="order", dataType="String",defaultValue="asc",allowableValues="asc,desc"),
		@ApiImplicitParam(name="page", dataType="Integer",defaultValue="1"),
		@ApiImplicitParam(name="size", dataType="Integer",defaultValue="25",value="Maximum value : 100"),
		@ApiImplicitParam(name="sort", dataType="String",allowableValues="templateName,bolNumber,bolDate,proNumber,shipper,consignee")
	})
	@GetMapping(value="/template")
	public ResponseEntity<Page<Template>> getTemplates(@ApiIgnore AuthenticationDetails auth,
			Pageable pageable,
			TemeplateFilter filter){
		String username = auth.getUsername();
		Page<Template> templates = templateService.getTemplates(username,pageable,filter);
		return new ResponseEntity<>(templates, HttpStatus.OK);
	}
	

	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To create/update Template",notes="To create/update  Template")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & empty errprCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/template")
	public ResponseEntity<?> createTemplate(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody BillOfLading billOfLading,
			@ApiParam(value="template name with username is unique") @RequestParam("template") String template
			){
		String username = auth.getUsername();
		templateService.createTemplate(username, auth, template,billOfLading);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get a specific template details",notes="To get a specific Template details.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BillOfLading.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/template/{templateName:.+}")
	public ResponseEntity<?> getTemplateById(@ApiIgnore AuthenticationDetails auth,@PathVariable("templateName") String template){
		String username = auth.getUsername();
		
		BillOfLading billOfLading = templateService.getTemplateByTemplateName(username,auth, template);
		return new ResponseEntity<>(billOfLading, HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To delete a Template", notes="To delete a Template")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & empty errorCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@DeleteMapping(value="/template/{templateName:.+}")
	public ResponseEntity<?> deleteTemplate(@ApiIgnore AuthenticationDetails auth,@PathVariable("templateName") String template){
		String username = auth.getUsername();
		String accessToken = auth.getAccessToken();
		templateService.deleteTemplate(username,accessToken, template);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	

	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get a specific template details",notes="To get a specific Template details.")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BillOfLading.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/get_template")
	public ResponseEntity<?> getTemplateByName(@ApiIgnore AuthenticationDetails auth,@ApiParam(value="template name with username is unique") @RequestParam("templateName") String template){
		String username = auth.getUsername();
		
		BillOfLading billOfLading = templateService.getTemplateByTemplateName(username,auth, template);
		return new ResponseEntity<>(billOfLading, HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To delete a Template", notes="To delete a Template")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & empty errorCode"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@DeleteMapping(value="/delete_template")
	public ResponseEntity<?> deleteTemplateByName(@ApiIgnore AuthenticationDetails auth,@ApiParam(value="template name with username is unique") @RequestParam("templateName") String template){
		String username = auth.getUsername();
		String accessToken = auth.getAccessToken();
		templateService.deleteTemplate(username,accessToken, template);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
