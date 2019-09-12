/**
 *
 */

package com.estes.myestes.requestinfo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.requestinfo.dto.InfoRequestDTO;
import com.estes.myestes.requestinfo.dto.ProblemTypeRequestDTO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;
import com.estes.myestes.requestinfo.service.iface.RequestInfoService;
import com.estes.myestes.requestinfo.util.ProblemType;
import com.estes.myestes.requestinfo.util.RequestInfoConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes request additional info")
public class RequestInfoController {

	@Autowired
	RequestInfoService requestInfoService;

	@ApiOperation(value = "Submit the request for more information", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Customer infomation"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@RequestMapping(value = "/submitRequest", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> makeRequest(@RequestBody InfoRequestDTO dto, Authentication auth) {
		ServiceResponse resp = null;

		// default to guest username
		String username = "GUEST_USER";
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		} catch(Exception e) {
		}
		try {
			resp = requestInfoService.submitRequest(username, dto);
		}
		catch (RequestInfoException ex) {
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(RequestInfoConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Return response object
		return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
	} // makeRequest
	
	@ApiOperation(value = "Get the list of problems available based off of ot pro and user", response = ProblemType.class)
	@RequestMapping(value = "/getProblemTypes", method = RequestMethod.POST, produces = "application/json")
	public List<ProblemType> generateProblemTypes(@RequestBody ProblemTypeRequestDTO dto, Authentication auth) {
		String accountCode = null;
		String accountType = null;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    accountCode = (String) details.get("accountCode");
		    accountType = (String) details.get("accountType");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		return requestInfoService.getProblemTypes(accountCode, accountType, dto);
	} // makeRequest
}