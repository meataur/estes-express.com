package com.estes.myestes.ImageViewing.web.controller;
import java.io.IOException;
import java.util.List; 

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.ImageViewing.config.AuthenticationDetails;
import com.estes.myestes.ImageViewing.web.dto.DocumentType;
import com.estes.myestes.ImageViewing.web.dto.Image;
import com.estes.myestes.ImageViewing.web.dto.ImageEmail;
import com.estes.myestes.ImageViewing.web.dto.ImageFax;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.dto.ImageSearch;
import com.estes.myestes.ImageViewing.web.dto.ImageStatusResponse;
import com.estes.myestes.ImageViewing.web.service.iface.ImageViewingEmailService;
import com.estes.myestes.ImageViewing.web.service.iface.ImageViewingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api
@RestController
public class ImageViewingController {
	
	
	@Autowired
	private ImageViewingService imageViewingService;
	
	@Autowired
	private ImageViewingEmailService imageViewingEmailService;
	
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "Get Document Types")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=DocumentType.class,responseContainer="List", message = "Successful Response Provides a list of DocumentType objects"),
			@ApiResponse(code=400,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/image/types", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getDocumentTypes(@ApiIgnore AuthenticationDetails authDetails) {
		
		List<DocumentType> documentTypeList =  imageViewingService.getDocumentTypes(authDetails.getUsername(), authDetails.getAccountCode());
		return new ResponseEntity<>(documentTypeList, HttpStatus.OK);
    }
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")},value = "Search & Request Image Document")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=ImageResult.class,responseContainer="List", message = "Successful Response Provides a list of ImageResult objects"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/image/search", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> searchImage(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody ImageSearch imageSearch) {
		
		String sessionId = authDetails.getSession();
		List<ImageResult> imageResults =  imageViewingService.processImageRequest(imageSearch, sessionId);
		
		return new ResponseEntity<>(imageResults, HttpStatus.OK);
    }
	
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")},value = "Fax Image Document to FAX")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=ServiceResponse.class, message = "Successful Response"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/image/fax", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> faxImage(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody ImageFax imageFax) {
		
		String sessionId = authDetails.getSession();
		
		List<String> faxResults =  imageViewingService.writeImageFaxRequest(imageFax, sessionId);
		
		if(faxResults.isEmpty()){
			
			ESTESLogger.log(ESTESLogger.ERROR,getClass(),"faxImage()","To send fax error in SPROC calls");
			
			return new ResponseEntity<>(
					new ServiceResponse("error", "Sorry! we can't process your request due to internal errors. Please try again after sometimes."), 
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		if (imageFax.getProNumbers().size() > faxResults.size() ){
			ESTESLogger.log(ESTESLogger.ERROR,getClass(),"faxImage()","Few documents can't be sent due to errors.");
			return new ResponseEntity<>(new ServiceResponse("", faxResults.size()+" document(s) successfully sent to your given fax number but failed to sent "+(imageFax.getProNumbers().size()-faxResults.size())+" document(s)."), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(new ServiceResponse("", "Successfully sent to your given fax number"), HttpStatus.OK);
		}
    }
	

	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")},value = "Send Image Document to Email")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=ServiceResponse.class, message = "Successful Response"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value="/image/email", consumes={"application/json"},produces = {"application/json"})
	public ResponseEntity<?> sendMessageWithAttachment(@RequestBody @Valid ImageEmail imageEmail) throws MessagingException, IOException {
			     
		
		 	boolean status = imageViewingEmailService.processEmailRequest(imageEmail);
			if(status==false){
				return new ResponseEntity<>(new ServiceResponse("error","Sorry! we could not sent email."), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return new ResponseEntity<>(new ServiceResponse("",""), HttpStatus.OK);
	}
	
	

	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "Get Image Status")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=ImageStatusResponse.class, message = "Successful Response Provides a ImageStatusResponse objects"),
			@ApiResponse(code=400,response=ServiceResponse.class,responseContainer="List",message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/image/status", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getImageStatus(
    		@ApiIgnore Authentication auth,
    		@ApiParam( required=true) @RequestParam(name="requestNumber", required=true) String requestNumber,
    		@ApiParam( required=true,value="Search Data: Formatted  PRO Number/Bill of Lading Number/Purchase Order Number/Interline PRO Number") @RequestParam(name="searchData", required=true) String searchData,
    		@ApiParam( required=true,value="Document type – BOL/DR/WR") @RequestParam(name="docType", required=true) String docType
    		) {
		
		if(requestNumber==null){
			
		}
		
		ImageStatusResponse imageStatus = imageViewingService.getImageStatus(requestNumber, searchData, docType);
		return new ResponseEntity<>(imageStatus, HttpStatus.OK);
    }
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "Get Image Details to View")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=Image.class,responseContainer="List", message = "Successful Response Provides a list of Image objects"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/image/view", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> getImageView(
    		@ApiIgnore Authentication auth,
    		@ApiParam( required=true) @RequestParam(name="key1") String key1,
    		@ApiParam( required=true) @RequestParam(name="key2") String key2,
    		@ApiParam( required=false) @RequestParam(name="key3",defaultValue="") String key3,
    		@ApiParam( required=false) @RequestParam(name="key4",defaultValue="") String key4,
    		@ApiParam( required=false) @RequestParam(name="key5",defaultValue="") String key5,
    		@ApiParam( required=true,value="Document type – BOL/DR/WR") @RequestParam(name="docType") String docType
    		) {
		List<Image> images = imageViewingService.getImagesDetails(key1, key2, key3, key4, key5, docType);
		
		return new ResponseEntity<>(images, HttpStatus.OK);
    }
}
