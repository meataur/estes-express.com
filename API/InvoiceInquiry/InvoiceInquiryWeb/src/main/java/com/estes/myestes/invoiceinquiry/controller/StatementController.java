/**
 * @author: Todd Allen
 *
 * Creation date: 11/26/2018
 */

package com.estes.myestes.invoiceinquiry.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.invoiceinquiry.dto.StatementDetail;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.StatementService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes customer statement details")
public class StatementController
{
	@Autowired
	StatementService stmtService;

	@ApiOperation(value = "Show customer statement details")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=StatementDetail.class, responseContainer="List", message="A/R statement details"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/statement", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getStatementDetails(
			@ApiIgnore(value="Security token") Authentication auth,
			@ApiParam(value = "Statement number") @RequestParam(name = "stmt") String statement,
			@ApiParam(value = "Sort by column - PRO/BOL/PO/FBD=Freight bill date/OPN=Open amount")
					@RequestParam(name = "sort") String sort)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<StatementDetail> details = stmtService.getStatementDetails(statement, sort);
			return new ResponseEntity<List<StatementDetail>>(details, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getStatementDetails
}
