package com.estes.myestes.profile.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.profile.config.AuthenticationDetails;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.service.iface.ProfileService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

public class MyController {

	
	@Autowired
	private ProfileService profileService; 
	/**
	 * This service goes to common services.
	 * So don't use this services. It is just a backup.
	 * 
	 * @param authDetails
	 * @return
	 */
	@ApiOperation(value = "My Estes Signed In User Profile Basic Information ")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Profile.class, message = "Success response provides Profile object"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/me",produces = {"application/json"})
    public ResponseEntity<?> getMyProfile(@ApiIgnore AuthenticationDetails authDetails) {
		
		Profile profile = profileService.getUserProfile(authDetails.getUsername());
		ESTESLogger.log(ESTESLogger.DEBUG,getClass(), profile.getFirstName(), "");
		profile.setAccountType(authDetails.getAccountType());
		return new ResponseEntity<>(profile, HttpStatus.OK);
    }
}
