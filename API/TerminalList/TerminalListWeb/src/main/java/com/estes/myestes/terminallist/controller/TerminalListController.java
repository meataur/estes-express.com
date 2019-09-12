/**
 * @author: Shelender singh
 *
 * Creation date: 09/12/2018
 */

package com.estes.myestes.terminallist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.terminallist.dto.EmailRequestDTO;
import com.estes.myestes.terminallist.dto.StateCoverage;
import com.estes.myestes.terminallist.exception.TerminalException;
import com.estes.myestes.terminallist.service.TerminalListService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value = "Estes Terminal Listing")
public class TerminalListController
{
	@Autowired
	TerminalListService termListService;

	/**
	 * This Method help for searching Terminals
	 * 
	 * @param country
	 * @return
	 */
	@ApiOperation(value = "Search terminals for requested criteria")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=StateCoverage.class, responseContainer="List", message="Terminal coverage information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/searchTerminal", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> searchTerminals(@RequestBody EmailRequestDTO dto)
	{
		try {
			List<StateCoverage> stateCoverage = termListService.searchTerminals(dto);
			return new ResponseEntity<List<StateCoverage>>(stateCoverage, HttpStatus.OK);
		}
		catch (Exception ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // searchTerminals

	/**
	 * This method send list of terminals in mail.
	 * 
	 * @param dto
	 * @return
	 */
	@ApiOperation(value = "Send email of terminal list")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success message"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> sendEmail(@RequestBody EmailRequestDTO dto)
	{
		try {
			ServiceResponse response = new ServiceResponse();
			response.setMessage(termListService.emailTerminalList(dto));
			if (!termListService.isError()) {
				return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
			}
			else {
				response.setErrorCode("error");
				return new ResponseEntity<ServiceResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
		catch (TerminalException ex) {
			ServiceResponse err = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(err, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // sendEmail
}
