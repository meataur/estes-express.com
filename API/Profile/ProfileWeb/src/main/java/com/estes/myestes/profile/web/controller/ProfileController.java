package com.estes.myestes.profile.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.profile.config.AuthenticationDetails;
import com.estes.myestes.profile.event.EmailChangeEvent;
import com.estes.myestes.profile.event.PasswordChangeEvent;
import com.estes.myestes.profile.event.UsernameChangeEvent;
import com.estes.myestes.profile.web.dto.BasicProfile;
import com.estes.myestes.profile.web.dto.EmailAddress;
import com.estes.myestes.profile.web.dto.EmailPreference;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Username;
import com.estes.myestes.profile.web.service.iface.ProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;


@Api("Profile Information Update")
@RestController
public class ProfileController {
	 
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	AuthenticationDetails authDetails;
	
	@Autowired
	private ProfileService profileService; 
	
	
	@ApiOperation(value = "change username for authenticated and authorized user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/change_username", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> changeUsername(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid @RequestBody Username username
    		) {
		
		String token = authDetails.getAccessToken();
		
		profileService.updateUsername(authDetails.getUsername(),username);
		
		Map<String, String> info = new HashMap<>();
		info.put("token", token);
		info.put("username", authDetails.getUsername());
		info.put("newUsername",username.getUsername().toUpperCase());
		
		eventPublisher.publishEvent(new UsernameChangeEvent(info));
		
		ServiceResponse response = new ServiceResponse("","Username changed successfully");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
	
	@ApiOperation(value = "chnage password for authenticated & authorized user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/change_password", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> changePassword(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid @RequestBody Password password) {
		profileService.changePassword(authDetails.getUsername(), password);
		
		ServiceResponse response = new ServiceResponse("","Password changed successfully");
		
		/**
		 * Trigger PasswordChangeEvent 
		 */
		eventPublisher.publishEvent(new PasswordChangeEvent(authDetails.getUsername()));
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
	
	@ApiOperation(value = "change email address for authenticated & authorized user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/change_email", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> updateEmailAddress(@ApiIgnore AuthenticationDetails authDetails, @Valid @RequestBody EmailAddress emailAddress){
		
		profileService.updateEmailAddress(authDetails.getUsername(),emailAddress);
		
		
		Map<String, String> info = new HashMap<>();
		info.put("token", authDetails.getAccessToken());
		info.put("username", authDetails.getUsername());
		info.put("newUsername",emailAddress.getEmail().toUpperCase());
		eventPublisher.publishEvent(new EmailChangeEvent(info));
		
		ServiceResponse response = new ServiceResponse("","Email address updated successfully");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
	@ApiOperation(value = "Chang Email Update Preference ")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/change_email_preference", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> updateEmailPreference(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid @RequestBody EmailPreference  preference) {
		profileService.updateEmailPrefence(authDetails.getUsername(), preference.getPreference());
		
		ServiceResponse response = new ServiceResponse("","Email preference updated successfully");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
	
	@ApiOperation(value = "Update Profile ")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/update_profile", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> updateUserProfile(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid @RequestBody BasicProfile  profile) {
		
		profileService.updateProfile(authDetails.getUsername(), authDetails.getAccountCode(), profile);
		
		ServiceResponse response = new ServiceResponse("","Profile  updated successfully");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
}
