/**
 *
 */

package com.estes.myestes.claims.controller;

import java.util.ArrayList;
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

import com.edps.email.EmailConstants;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.claims.dao.impl.FilingDAOImpl;
import com.estes.myestes.claims.dto.ClaimDTO;
import com.estes.myestes.claims.dto.ClaimsRequestDTO;
import com.estes.myestes.claims.dto.EmailClaimsRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.service.iface.ClaimsInquiryService;
import com.estes.myestes.claims.util.ClaimsConstant;

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
@Api(value="My Estes claims inquiry")
public class ClaimsInquiryController {

	@Autowired
	ClaimsInquiryService claimsInquiryService;
	
	@ApiOperation(value = "Get the list of claims based off provided data and user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ClaimDTO.class, responseContainer="List", message="Error information - blank"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Buisness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/getClaims", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getClaims(@RequestBody ClaimsRequestDTO request, @ApiIgnore Authentication auth) {
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
		
		try {
			// validate user account if using grouped account
			if(request.getAccountNumber() != null && !request.getAccountNumber().equals(accountCode)) {
				if(!claimsInquiryService.validAccount(accountCode, request.getAccountNumber(), accountType)) {
					ServiceResponse errDTO = new ServiceResponse();
					errDTO.setErrorCode(ClaimsConstant.NOT_VALID_SUB_ACCOUNT_ERROR_CODE);
					errDTO.setMessage(claimsInquiryService.getErrorMessage(errDTO.getErrorCode()));
					
					return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.BAD_REQUEST);
				}
			}
			else if(request.getAccountNumber() == null) {
				request.setAccountNumber(accountCode);
			}
			
			// get claims
			List<ClaimDTO> claims = claimsInquiryService.getClaims(accountCode, accountType, request);
			List<ServiceResponse> errResponse = new ArrayList<ServiceResponse>();
			// check if there were any claims
			if(claims.size() < 1) {
				
				if(request.getSearchBy().equals("Date Range")) {
					ServiceResponse errDTO = new ServiceResponse();
					errDTO.setErrorCode(ClaimsConstant.NO_CLAIMS_FOR_DATE_ERROR_CODE);
					errDTO.setMessage(claimsInquiryService.getErrorMessage(errDTO.getErrorCode()));
					errResponse.add(errDTO);
				}
				else {
					for(String number: request.getNumbers()) {
						ServiceResponse errDTO = new ServiceResponse();
						errDTO.setMessage("Invalid Number: "+number);
						errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
						errResponse.add(errDTO);
					}
					
				}
				
				return new ResponseEntity<List<ServiceResponse>>(errResponse, HttpStatus.BAD_REQUEST);
			}
			else {
				
				for(ClaimDTO dto:claims) {
					if(dto.getStatus().equals(ClaimsConstant.INVALID_CLAIMS)) {
						ServiceResponse errDTO = new ServiceResponse();
						errDTO.setMessage("Invalid Number: "+dto.getClaimNumber());
						errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
						errResponse.add(errDTO);
					}
				}
				if(errResponse.size()>0) {
					return new ResponseEntity<List<ServiceResponse>>(errResponse, HttpStatus.BAD_REQUEST);
				}
				return new ResponseEntity<List<ClaimDTO>>(claims, HttpStatus.OK);
			}
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsInquiryController.class, "getClaims()", "An error occurred getting the claims", e);
			
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest

	@ApiOperation(value = "Email the list of claims based off provided data and user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Error information - blank"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Buisness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/emailClaims", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> emailClaims(@RequestBody EmailClaimsRequestDTO request, @ApiIgnore Authentication auth) {
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
		
		try {
			// validate user account if using grouped account
			if(request.getAccountNumber() != null && !request.getAccountNumber().equals(accountCode)) {
				if(!claimsInquiryService.validAccount(accountCode, request.getAccountNumber(), accountType)) {
					ServiceResponse errDTO = new ServiceResponse();
					errDTO.setErrorCode(ClaimsConstant.NOT_VALID_SUB_ACCOUNT_ERROR_CODE);
					errDTO.setMessage(claimsInquiryService.getErrorMessage(errDTO.getErrorCode()));
					
					return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.BAD_REQUEST);
				}
			}
			else if(request.getAccountNumber() == null) {
				request.setAccountNumber(accountCode);
			}
			
			// send email
			ServiceResponse res = new ServiceResponse();
			if(claimsInquiryService.emailClaims(accountCode, accountType, request)) {
				res.setMessage(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_SENT_MSG, EmailConstants.APP_EXT_PROP_MAIL_SENT_MSG));
				return new ResponseEntity<ServiceResponse>(res, HttpStatus.OK);
			}
			else {
				res.setErrorCode(ClaimsConstant.ERROR_CODE);
				res.setMessage("Failed to send email.");
				return new ResponseEntity<ServiceResponse>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsInquiryController.class, "emailClaims()", "An error occurred emailing the claims", e);
			
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest
}