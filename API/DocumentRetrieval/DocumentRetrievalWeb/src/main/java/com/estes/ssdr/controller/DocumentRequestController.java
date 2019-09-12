/**
 * @author: Todd Allen
 *
 * Creation date: 10/17/2014
 *
 */

package com.estes.ssdr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.ssdr.dto.DocumentInfo;
import com.estes.ssdr.dto.DocumentRequestDTO;
import com.estes.ssdr.dto.DocumentResponseDTO;
import com.estes.ssdr.exception.DocumentRetrievalException;
import com.estes.ssdr.rest.message.SSDRResponse;
import com.estes.ssdr.service.iface.DocumentRetrievalService;
import com.estes.ssdr.util.DocRetrievalConstant;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
public class DocumentRequestController
{
	public static final String DOC = "docRequestDTO";

	@Autowired
	DocumentRetrievalService documentRetrievalService;

	@ApiOperation(value = "Get customer invoice aging detail for an age break")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=DocumentInfo.class, message="Document information"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/doc", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> makeRequest(@RequestBody DocumentRequestDTO dto)
	{
		DocumentResponseDTO resp = null;

		try {
			resp = documentRetrievalService.submitRequest(dto);
			if (SSDRResponse.isSuccess(resp.getCode())) {
				return new ResponseEntity<DocumentInfo>(new DocumentInfo(resp), HttpStatus.OK);
			}
			else {
				ServiceResponse error = new ServiceResponse(resp.getCode(), resp.getMessage());
				return new ResponseEntity<ServiceResponse>(error, HttpStatus.BAD_REQUEST);
			}
		}
		catch (DocumentRetrievalException doce) {
			resp = new DocumentResponseDTO();
			resp.setCode(DocRetrievalConstant.ERROR_CODE);
			return new ResponseEntity<DocumentResponseDTO>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest
}
