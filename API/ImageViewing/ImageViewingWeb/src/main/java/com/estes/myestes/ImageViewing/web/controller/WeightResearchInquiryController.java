package com.estes.myestes.ImageViewing.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.ImageViewing.config.AuthenticationDetails;
import com.estes.myestes.ImageViewing.web.dto.WRCertificate;
import com.estes.myestes.ImageViewing.web.dto.WREmailRequest;
import com.estes.myestes.ImageViewing.web.dto.WRSearchRequest;
import com.estes.myestes.ImageViewing.web.service.iface.WeightResearchInquiryService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api
@RestController
public class WeightResearchInquiryController {

	@Autowired
	private WeightResearchInquiryService weightResearchInquiryService;
	

	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "WR Inquiry search results")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=WRCertificate.class,responseContainer="List", message = "Successful response provides a list of weight & research inquiry results"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/wr/search", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> searchWRInquiryDetails(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody WRSearchRequest wrSearchRequest) {
		
		
		List<ServiceResponse> errors = validateProNumber(wrSearchRequest);
		
		if(errors.size() >0){
			return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
		}
		
		String userName=authDetails.getUsername();
		String accountNumber = authDetails.getAccountCode(); 
		
		if(null == wrSearchRequest.getAccountNumber() || wrSearchRequest.getAccountNumber().equals("")) {
			wrSearchRequest.setAccountNumber(accountNumber);
		}
		
		List<WRCertificate> wrCertificates =  weightResearchInquiryService.searchWRInquiryDetails(wrSearchRequest, userName);
		
		if(wrCertificates == null || wrCertificates.size()==0){
			return new ResponseEntity<>(new ServiceResponse("error","No results were found"), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(wrCertificates, HttpStatus.OK);
    }


	private List<ServiceResponse> validateProNumber(@Valid WRSearchRequest wrSearchRequest) {
		List<ServiceResponse> errors = new ArrayList<>();
		if(wrSearchRequest.getSearchBy().equalsIgnoreCase("PRO")){
			List<String> proNumbers = wrSearchRequest.getSearchTerm();
			int index =0;
			for(String proNumber: proNumbers){
				
				if(proNumber.replaceAll("\\D+", "").length()>10){
					
					ServiceResponse error = new ServiceResponse("error","PRO '"+proNumber+"' is invalid.","searchTerm["+index+"]");
					errors.add(error);
					
				}
				
				index++;
			}
		}
		return errors;
	}

	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "Get weight and research certificate details list with document link")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=WRCertificate.class,responseContainer="List", message = "Successful Response Provides a list of Weight And Research Certificate objects"),
			@ApiResponse(code=400,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/wr/documentDetails", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getDocmentDetails(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody List<String> proNumbers) {
		
		String userName=authDetails.getUsername();
		
		List<WRCertificate> documentDetails=  weightResearchInquiryService.getDocumentDeails(proNumbers,userName);
		return new ResponseEntity<>(documentDetails, HttpStatus.OK);
    }
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "Get email for weight and research inquiry certificates")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=ServiceResponse.class, message = "Successful Response Provides a email Response message"),
			@ApiResponse(code=400,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/wr/getEmail", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getEmail(
    		@ApiIgnore AuthenticationDetails authDetail,
    		@Valid  @RequestBody WREmailRequest  wrEmailRequest) {
		String userName=authDetail.getUsername();
		String emailResponse=weightResearchInquiryService.getEmail(wrEmailRequest,userName);
		ServiceResponse response = new ServiceResponse("",emailResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	
	
}
