/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 */

package com.estes.myestes.accountrequest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.accountrequest.dto.RequestInfo;
import com.estes.myestes.accountrequest.exception.AccountRequestException;
import com.estes.myestes.accountrequest.service.iface.AccountService;
import com.estes.myestes.accountrequest.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes account code/number request operations")
public class RequestController
{
	@Autowired
	AccountService acctService;

	@ApiOperation(value = "Process request for Estes account code/number")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success message"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/req", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> processAccountRequest(@RequestBody RequestInfo req, WebRequest webReq)
	{
		ServiceResponse resp = null;

		try {
			resp = acctService.requestNumber(req);
			// Check for error and return appropriate HTTP status code
			if (!resp.getErrorCode().equals(AppConstants.DEFAULT_ERROR_CODE)) {
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
			}
		}
		catch (AccountRequestException e) {
			resp = new ServiceResponse(AppConstants.DEFAULT_ERROR_CODE, AppConstants.DEFAULT_SYS_ERROR);
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // processAccountRequest
}
