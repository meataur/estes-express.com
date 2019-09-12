/**
 * @author: Todd Allen
 *
 * Creation date: 11/28/2018
 */

package com.estes.myestes.invoiceinquiry.controller;

import java.util.List;
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
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchRequest;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchResult;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.SearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for invoice inquiry ReST search services
 */
@RestController
@Api(value="Estes customer invoice/statement search")
public class SearchController
{
	@Autowired
	SearchService srchService;

	@ApiOperation(value = "Search customer invoices")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=InvoiceSearchResult.class, responseContainer="List", message="Shipment invoice amount details"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> searchInvoices(
			@ApiIgnore(value="Security token") Authentication auth,
			@RequestBody InvoiceSearchRequest req)
	{
		String session;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			session = (String) details.get("session");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<InvoiceSearchResult> results = srchService.searchInvoices(session, req);
			return new ResponseEntity<List<InvoiceSearchResult>>(results, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "There are no invoice details available at this time.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // searchInvoices
}
