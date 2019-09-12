package com.estes.myestes.profile.web.controller;


import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.profile.config.AuthenticationDetails;
import com.estes.myestes.profile.event.PasswordChangeEvent;
import com.estes.myestes.profile.exception.AppException;
import com.estes.myestes.profile.web.dto.App;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.User;
import com.estes.myestes.profile.web.dto.UserStatus;
import com.estes.myestes.profile.web.service.iface.ProfileService;
import com.estes.myestes.profile.web.service.iface.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class UserController {
	
	@Autowired
    private ApplicationEventPublisher eventPublisher;
	
	@Autowired 
	ProfileService profileService;
	
	@Autowired
	private UserService userService;
	
	@ApiOperation(value = "Admin User can create new user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/users", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> createUser(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@Valid  @RequestBody User user) {
		
		Profile creator = profileService.getUserProfile(authDetails.getUsername());
		
		user.setAccountCode(authDetails.getAccountCode());
		user.setAccountType(authDetails.getAccountType());
		user.setCompanyName(creator.getCompanyName());
		
		
		userService.createUser(user,authDetails.getUsername());
		
		ServiceResponse response = new ServiceResponse("","User "+user.getUsername()+" has been created successfully");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }
	
	
	
	@ApiOperation(value = "List of Users")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/users", produces = {"application/json"})
    public ResponseEntity<Page<Profile>> createUser(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="q",required=false, value="Search Term. q will be matched to firstName, lastName, username & email")
    		@RequestParam(name="q",required=false) String search,
    		@ApiParam(name="firstName",required=false, value="To Search By First Name")
    		@RequestParam(name="firstName",required=false) String qFirstName,
    		@ApiParam(name="lastName",required=false, value="To Search By Last Name")
    		@RequestParam(name="lastName",required=false) String qLastName,
    		@ApiParam(name="username",required=false, value="To Search By Username")
    		@RequestParam(name="username",required=false) String qUsername,
    		@ApiParam(name="sort",required=false, value="value can be any of [firstName,lastName,username,email,phone,companyName,accountCode,createdDate,status,preference]")
    		@RequestParam(name="sort",required=false) String sort,
    		@ApiParam(name="page",defaultValue="1",required=false, value="Page Number")
    		@RequestParam(name="page",defaultValue="1",required=false) int page,
    		@ApiParam(name="size",defaultValue="25",required=false, value="The number of results per page")
    		@RequestParam(name="size",defaultValue="25",required=false) int size,
    		Pageable pageable
    		
    		) {
		Page<Profile> profileData = userService.getUsers(authDetails.getUsername(), pageable, search, qFirstName, qLastName, qUsername);
		return new ResponseEntity<>(profileData,HttpStatus.OK);
    }
	
	
	
	@ApiOperation(value = "List of Authorized Applications for a user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=App.class, responseContainer="List", message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/users/authorized_apps", produces = {"application/json"})
    public ResponseEntity<?> getAuthorizedApps(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true, value="Username")
    		@RequestParam(name="username")  String username
    		) {
		List<App> apps = userService.getAuthorizedApps(authDetails.getUsername(),username);

		return new ResponseEntity<>(apps, HttpStatus.OK);
    }
	
	
	
	@ApiOperation(value = "List of Blocked Applications for a user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=App.class, responseContainer="List", message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/users/blocked_apps", produces = {"application/json"})
    public ResponseEntity<?> getBlockedApps(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true, value="Username")
    		@RequestParam(name="username")  String username
    		) {
		List<App> apps = userService.getBlockedApps(authDetails.getUsername(),username);
		
		return new ResponseEntity<>(apps, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Add List of Apps to Blocked Applications for a user")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/users/blocked_apps", produces = {"application/json"})
    public ResponseEntity<?> addBlockedApps(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true, value="Username")
    		@RequestParam(name="username")  String username,
    		@ApiParam(name="appNames", required=true, value="List of App Names to be blocked")
    		@Valid @RequestBody List<String> appNames
    		) {
		
		/**
		 * Check the given apps are in user authorized app list
		 * In token, scopes are list of authorized apps of a authenticated user 
		 */
		
		if(! authDetails.getScope().containsAll(appNames) ){
			throw new AppException(HttpStatus.NOT_FOUND, "Given apps are not found in your authorized app list");
		}
		
		userService.addToBlockedApps(appNames, authDetails.getUsername(), username);
		
		ServiceResponse response = new ServiceResponse("","Given apps have been added to blocked apps successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	
	@ApiOperation(value = "Delete List of Apps from Blocked Applications for a user & are automatically added to authorized list")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@DeleteMapping(value = "/users/blocked_apps", produces = {"application/json"})
    public ResponseEntity<?> deleteBlockedApps(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true,value="Username")
    		@RequestParam(name="username")  String username,
    		@ApiParam(name="appNames",required=true, value="List of App Names to be deleted from blocked list")
    		@Valid @RequestBody List<String> appNames
    		) {

		/**
		 * Check the given apps are in user authorized app list
		 * In token, scopes are list of authorized apps of a authenticated user 
		 */

		if(! authDetails.getScope().containsAll(appNames)){
			
			throw new AppException(HttpStatus.NOT_FOUND, "Given apps are not found in your authorized app list");
		}
		
		userService.deleteBlockedApps(appNames, authDetails.getUsername(), username);
		
		ServiceResponse response = new ServiceResponse("","Given apps have been removed from blocked list successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Delete All Apps from Blocked Applications  & are automatically added to authorized list")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@DeleteMapping(value = "/users/blocked_apps/all", produces = {"application/json"})
    public ResponseEntity<?> deleteAllBlockedApps(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true,value="Username")
    		@RequestParam(name="username")  String username
    		) {

		userService.deleteAllBlockedApps(authDetails.getUsername(), username);
		
		ServiceResponse response = new ServiceResponse("","All the blocked apps have been removed from blocked list successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Change user status: ENABLED/DISABLED")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/users/change_status", produces = {"application/json"})
    public ResponseEntity<?> changeStatus(@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true,value="Username")
    		@RequestParam(name="username")  String username,
    		@ApiParam(name="status",required=true,value="User New Status: ENABLED/DISABLED")
    		@Valid @RequestParam(name="status") UserStatus status
    		) {

		userService.updateUserStatus(authDetails.getUsername(), username, status);
		
		ServiceResponse response = new ServiceResponse("","User status updated successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
    }
	
	@ApiOperation(value = "Change user Password")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class,message = "Success response"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@PostMapping(value = "/users/change_password", consumes={"application/json"},produces = {"application/json"})
    public ResponseEntity<?> changePassword(
    		@ApiIgnore AuthenticationDetails authDetails,
    		@ApiParam(name="username",required=true,value="Child Username")
    		@RequestParam(name="username") String username,
    		@Valid @RequestBody Password password) {
		/**
		 * Change Child User Password
		 */
		userService.changeChildUserPassword(authDetails.getUsername(), username, password);
		
		/**
		 * Trigger PasswordChangeEvent 
		 */
		eventPublisher.publishEvent(new PasswordChangeEvent(username));
		
		/**
		 * Send Successful Response
		 */
		ServiceResponse response = new ServiceResponse("","Password changed successfully");
		
		return new ResponseEntity<>(response,HttpStatus.OK);
    }

	
}
