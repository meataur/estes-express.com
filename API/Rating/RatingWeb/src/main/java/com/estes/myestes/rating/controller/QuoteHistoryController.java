/**
 * @author: Todd Allen
 *
 * Creation date: 03/01/2019
 */

package com.estes.myestes.rating.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.rating.config.AuthenticationDetails;
import com.estes.myestes.rating.dto.RateSearch;
import com.estes.myestes.rating.dto.RateSummary;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.RatingHistoryService;

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
@Api(value="Rate quote history services")
public class QuoteHistoryController
{
	@Autowired
	RatingHistoryService rateHistoryService;

	@ApiOperation(value = "Get all quotes for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=RateSummary.class, responseContainer="List", message="Rate quote summary information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/reference/{num}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getQuotesByRefNum(
			@ApiParam(value="Unique quote reference number") @PathVariable int num)
	{
	
		try {
			List<RateSummary> quotes = rateHistoryService.retrieveQuoteSummaryData(num);
			return new ResponseEntity<List<RateSummary>>(quotes, HttpStatus.OK);
		}
		catch (RatingException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getQuotesByRefNum

	@ApiOperation(value = "Search rate quote history - maximum 500 results; pageable default page=1, size=25, sort=TIMST, order=desc; sort=QUOTENUM/TIMST/SVCLVL/ORGZIP/DESZIP/NETCHG")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=RateSummary.class, responseContainer="List", message="Rate quote summary information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> searchQuotes(
			@ApiIgnore(value="Security token") AuthenticationDetails auth,
			final Pageable pageable, 
			@RequestBody RateSearch search)
	{
		
		search.setUser(auth.getUsername());
		
		try {
			com.estes.dto.common.rest.Page<RateSummary> response = rateHistoryService.searchUserQuotes(pageable, search);
			return new ResponseEntity<com.estes.dto.common.rest.Page<RateSummary>>(response, HttpStatus.OK);
		}
		catch (RatingException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // searchQuotes
}
