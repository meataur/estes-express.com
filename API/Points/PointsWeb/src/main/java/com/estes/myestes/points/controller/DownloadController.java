/**
 * @author: Todd Allen
 *
 * Creation date: 09/25/2018
 */

package com.estes.myestes.points.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dto.DownloadRequest;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.service.iface.DownloadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Points Download")
public class DownloadController
{
	@Autowired
	DownloadService dwService;

	@ApiOperation(value = "Process points download request")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Error information - blank"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/download", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> download(@RequestBody DownloadRequest request)
	{
		try {
			ServiceResponse resp = dwService.processDownload(request);
			// Check for error and return proper HTTP status code
			if (!ServiceResponse.isError(resp.getErrorCode())) {
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
			}
		}
		catch (PointException e) {
			ServiceResponse resp = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // download
}
