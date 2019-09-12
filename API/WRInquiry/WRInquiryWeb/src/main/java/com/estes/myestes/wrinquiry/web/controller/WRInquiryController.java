package com.estes.myestes.wrinquiry.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.wrinquiry.config.AuthenticationDetails;
import com.estes.myestes.wrinquiry.web.dto.WRCertificate;
import com.estes.myestes.wrinquiry.web.dto.WREmailRequest;
import com.estes.myestes.wrinquiry.web.dto.WRSearchRequest;
import com.estes.myestes.wrinquiry.web.service.iface.WRInquiryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@Api
@RestController
public class WRInquiryController {

	@Autowired
	private WRInquiryService wrInquiryService;
	
	@ApiOperation(value = "WR Inquiry Serach Results")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=WRCertificate.class,responseContainer="List", message = "Successful Response Provides a list of Weight& Research Inquiry Results"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/search", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> searchWRInquiryDetails(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody WRSearchRequest wrSearchRequest) {
		
		System.out.println("UserName=="+authDetails.getUsername());
		String userName=authDetails.getUsername();
		 
		List<WRCertificate> wrCertificates =  wrInquiryService.searchWRInquiryDetails(wrSearchRequest,userName);
		
		return new ResponseEntity<>(wrCertificates, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Get Weight and Resarch Certificate Details list with document link")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=WRCertificate.class,responseContainer="List", message = "Successful Response Provides a list of Weight And Research Certificate objects"),
			@ApiResponse(code=400,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/documentDetails", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getDocmentDetails(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody List<WRCertificate> wrCertificates) {
		
		String userName=authDetails.getUsername();
		
		List<WRCertificate> documentDetails=  wrInquiryService.getDocumentDeails(wrCertificates,userName);
		return new ResponseEntity<>(documentDetails, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Get Email for Weight And Resaerch Inquiry Certificates")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=String.class, message = "Successful Response Provides a email Response message"),
			@ApiResponse(code=400,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/getEmail", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getEmail(
    		@ApiIgnore AuthenticationDetails authDetail,
    		@Valid  @RequestBody List<WREmailRequest>  wrEmailRequests) {
		String userName=authDetail.getUsername();
		String emailResponse=wrInquiryService.getEmail(wrEmailRequests,userName);
		return new ResponseEntity<>(emailResponse, HttpStatus.OK);
    }
	
	
	
}
