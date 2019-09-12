package com.estes.services.myestes.customer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.config.AuthenticationDetails;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.service.iface.AccountService;
import com.estes.services.myestes.exception.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@Api(description="My Estes SubAccounts")
public class AccountController {
	
	@Autowired
	private AccountService accountService;
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "Sub-accounts searching, listing, pagination")
	@ApiResponses(value = {
			@ApiResponse(code=200, message="Success Response."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Bussiness Validation Errors"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@ApiImplicitParams({
		@ApiImplicitParam(name="searchBy",value="__For empty or null value, it searches across all fields. Multiple values can be passed with comma(,) separated.__", dataType="String", allowEmptyValue=true, allowableValues="accountNumber,name,contactName,streetAddress,streetAddress2,city,state,zip,zip4,country,phone"),
		@ApiImplicitParam(name="searchTerm",value="__Search Term. It can be anything according to searchBy param. if searchBy param is null or empty, searchTerm is free to search accross all fields__"),
		@ApiImplicitParam(name="order", dataType="String",defaultValue="asc",allowableValues="asc,desc"),
		@ApiImplicitParam(name="page", dataType="Integer",defaultValue="1"),
		@ApiImplicitParam(name="size", dataType="Integer",defaultValue="25",value="Maximum value : 100"),
		@ApiImplicitParam(name="sort", dataType="String",allowableValues="accountNumber,name,contactName,streetAddress,streetAddress2,city,state,zip,zip4,country,phone")
	})
	@RequestMapping(value = "/sub_accounts", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Page<AccountDTO>> getSubAccounts(
			@ApiIgnore AuthenticationDetails auth,
			Pageable pageable,
			String searchBy,
			String searchTerm){
		
		String username = auth.getUsername();
		String accountCode = auth.getAccountCode();
		String accountType = auth.getAccountType();

		
		try {
			Page<AccountDTO> results = accountService.getSubAccounts(username, accountType, accountCode, pageable, searchBy, searchTerm);
			return new ResponseEntity<>(results, HttpStatus.OK);
		} catch (ServiceException e) {
			return new ResponseEntity<>(new Page<AccountDTO>(null, pageable, 0), HttpStatus.BAD_REQUEST);
		}
		
	} 
}
