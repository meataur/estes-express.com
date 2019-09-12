/**
 * @author: Todd Allen
 *
 * Creation date: 12/14/2017
 */

package com.estesexpress.currency.convert.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.estesexpress.currency.convert.dto.RateDTO;
import com.estesexpress.currency.convert.service.exception.CurrencyConversionException;
import com.estesexpress.currency.convert.service.iface.RateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Currency Converter operations")
public class RateController
{
	@Autowired
	RateService rateService;

	@ApiOperation(value = "Retrieve all US currency conversion rates for given currency")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=RateDTO.class, responseContainer="List", message="Currency conversion rates"),
			@ApiResponse(code=500, response=RateDTO.class, responseContainer="List", message="Empty list")
	})
	@RequestMapping(value = "/rates/{currency}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<RateDTO[]> getRates(@PathVariable String currency, WebRequest webReq)
	{
		RateDTO[] rates = null;

		try {
			rates = rateService.getRates(currency);
			// Return successful response
			return new ResponseEntity<RateDTO[]>(rates, HttpStatus.OK);
		}
		catch (CurrencyConversionException e) {
			rates = new RateDTO[] {new RateDTO()};
			return new ResponseEntity<RateDTO[]>(rates, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getRates
}
