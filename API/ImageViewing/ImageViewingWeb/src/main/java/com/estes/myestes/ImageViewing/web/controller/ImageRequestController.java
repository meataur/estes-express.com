package com.estes.myestes.ImageViewing.web.controller;

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
import com.estes.myestes.ImageViewing.web.dto.ImageRequest;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.service.iface.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api
@RestController
public class ImageRequestController {

	@Autowired
	private ImageService imageService;
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "User can request image to view or to fax")
	@ApiResponses(value = { 
			@ApiResponse(code=200,response=ImageResult.class,responseContainer="List", message = "Successful Response Provides a list of ImageResult objects"),
			@ApiResponse(code=400,response=ServiceResponse.class, responseContainer="List", message="Bussiness/System Errors"),
			@ApiResponse(code=500,response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/image/request", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> requestImage(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody ImageRequest imageRequest) {
		
		String sessionId = authDetails.getSession();
		List<ImageResult> imageResults =  imageService.processImageRequest(imageRequest, sessionId);
		
		return new ResponseEntity<>(imageResults, HttpStatus.OK);
    }
	
	
}
