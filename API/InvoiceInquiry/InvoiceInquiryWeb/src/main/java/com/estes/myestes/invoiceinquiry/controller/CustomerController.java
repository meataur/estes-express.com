/**
 * @author: Todd Allen
 *
 * Creation date: 01/29/2019
 */

package com.estes.myestes.invoiceinquiry.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST customer invoice services
 */
@RestController
@Api(value="Estes customer services")
public class CustomerController
{
	@Autowired
	CustomerService customerService;

	@ApiOperation(value = "Does customer have special invoice instructions?")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class, message="Customer invoice instructions present"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/customer/hasInstructions", method = RequestMethod.GET)
	public ResponseEntity<?> hasInstructions(@ApiIgnore(value="Security token") Authentication auth)
	{
		String account;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			account = (String) details.get("accountCode");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			boolean instructionsExist = customerService.hasInstructions(account);
			return new ResponseEntity<Boolean>(instructionsExist, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // hasInstructions
}
