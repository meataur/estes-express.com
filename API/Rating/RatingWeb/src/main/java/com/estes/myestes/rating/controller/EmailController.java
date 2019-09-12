/**
 * @author: Todd Allen
 *
 * Creation date: 03/28/2019
 */

package com.estes.myestes.rating.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.dto.EmailRequest;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.EmailService;
import com.estes.myestes.rating.utils.RatingConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Rate quote e-mail services")
public class EmailController
{
	@Autowired
	EmailService emailService;

	@ApiOperation(value = "Send rate quote details")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success message"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="System error information")
	})
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> sendEmail(
			@RequestBody EmailRequest req)
	{

		try {
			ServiceResponse msg = emailService.sendQuoteDetails(req, null);
			// Send errors in response
			if (ServiceResponse.isError(msg.getErrorCode())) {
				return new ResponseEntity<ServiceResponse>(msg, HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<ServiceResponse>(msg, HttpStatus.OK);
		}
		catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // sendEmail
}
