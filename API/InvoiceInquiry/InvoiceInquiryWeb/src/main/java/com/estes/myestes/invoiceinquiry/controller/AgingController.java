/**
 * @author: Todd Allen
 *
 * Creation date: 10/01/2018
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
import com.estes.myestes.invoiceinquiry.dto.AgingBuildProgress;
import com.estes.myestes.invoiceinquiry.dto.AgingDetail;
import com.estes.myestes.invoiceinquiry.dto.AgingDetailRequest;
import com.estes.myestes.invoiceinquiry.dto.AgingDetails;
import com.estes.myestes.invoiceinquiry.dto.AgingSummary;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.AgingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes invoice inquiry summary")
public class AgingController
{
	@Autowired
	AgingService ageService;

	@ApiOperation(value = "Get customer invoice aging detail for an age break")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=AgingDetail.class, responseContainer="List", message="A/R aging details for given age break"),
			@ApiResponse(code=206, response=AgingBuildProgress.class, message="A/R aging build progress"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/agedetail", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getAgingDetails(@RequestBody AgingDetailRequest req, Authentication auth)
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
			AgingDetails details = ageService.getAgingDetail(req, new Account(accountCode, accountType));
			// Return progress if build process busy; otherwise return totals
			if (details == null) {
				return new ResponseEntity<AgingBuildProgress>(ageService.getAgingBuildProgress(), HttpStatus.PARTIAL_CONTENT);
			}
			else {
				return new ResponseEntity<AgingDetails>(details, HttpStatus.OK);
			}
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getAgingDetails

	@ApiOperation(value = "Get customer invoice aging summary")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=AgingSummary.class, message="A/R aging totals by age break"),
			@ApiResponse(code=206, response=AgingBuildProgress.class, message="A/R aging build progress"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/summary", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAgingSummary(Authentication auth)
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
			AgingSummary totals = ageService.getAgingTotals(new Account(accountCode, accountType));
			// Return progress if build process busy; otherwise return totals
			if (totals == null) {
				return new ResponseEntity<AgingBuildProgress>(ageService.getAgingBuildProgress(), HttpStatus.PARTIAL_CONTENT);
			}
			else {
				return new ResponseEntity<AgingSummary>(totals, HttpStatus.OK);
			}
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getAgingSummary
}
