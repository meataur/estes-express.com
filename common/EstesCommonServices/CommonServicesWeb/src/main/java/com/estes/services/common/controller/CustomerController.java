/**
 * @author: Todd Allen
 *
 * Creation date: 11/20/2018
 */

package com.estes.services.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes common customer services")
public class CustomerController
{
	@Autowired
	CustomerService custService;

	@ApiOperation(value = "Get Estes customer account type code from account code/number")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=String.class, message="Estes customer account type code"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/customer/getAccountType", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCustomerAccountType(
			@ApiParam(value = "Account code") @RequestParam(name = "account") String account)
	{
		// Get account type from given account code
		try {
			String type = custService.getCustomerAccountType(account);
			return new ResponseEntity<String>(type, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getCustomerAccountType

	@ApiOperation(value = "Determine if customer is payor on shipment")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class, message="Boolean indicator if account is payor on shipment"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/customer/isPayor", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> isPayor(
			@ApiParam(value = "Account code") @RequestParam(name = "account") String account,
			@ApiParam(value = "PRO") @RequestParam(name = "pro") String pro)
	{
		try {
			boolean ans = custService.isPayor(account, pro);
			return new ResponseEntity<Boolean>(ans, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // isPayor
}
