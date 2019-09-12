/**
 * @author: Todd Allen
 *
 * Creation date: 01/24/2019
 */

package com.estes.myestes.invoiceinquiry.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dto.AgingEmailRequest;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.EmailService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Invoice information e-mail services")
public class EmailController
{
	@Autowired
	EmailService emailService;

	@ApiOperation(value = "Send customer invoice aging detail for an age break or list of PROs")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success message"),
			@ApiResponse(code=400, response=ServiceResponse.class,responseContainer="List", message="Error Responses"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/agingEmail", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> sendAgingDetails(
			@ApiIgnore(value="Security token") Authentication auth,
			@RequestBody AgingEmailRequest request)
	{
		String accountCode;
		String accountType;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			accountCode = (String) details.get("accountCode");
			accountType = (String) details.get("accountType");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			ServiceResponse resp = emailService.sendAging(new Account(accountCode, accountType), request);
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // sendAgingDetails
}
