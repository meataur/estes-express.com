/**
 *
 */

package com.estes.myestes.claims.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.claims.dto.ClaimantResponseDTO;
import com.estes.myestes.claims.dto.OtProRequestDTO;
import com.estes.myestes.claims.dto.ProInfoResponseDTO;
import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.service.iface.ClaimsFilingService;
import com.estes.myestes.claims.util.ClaimsConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes claims filing")
public class ClaimsFilingController implements HandlerExceptionResolver {

	@Autowired
	ClaimsFilingService claimsFilingService;
	
	@ApiOperation(value = "Get infomation based on ot pro passed and if the account is party to shipment")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ProInfoResponseDTO.class, message="The Pro Information"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Buisness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@RequestMapping(value = "/getProInfo", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getProInfo(@RequestBody OtProRequestDTO request, @ApiIgnore Authentication auth) {
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
			// if they passed an account code then use that instead
			if(request.getAccountNumber() != null && !request.getAccountNumber().trim().equals("")) {
				accountCode = request.getAccountNumber();
			}
			
			if(!claimsFilingService.isPartyToShipment(accountCode, accountType, request.getOtpro())) {
				ServiceResponse errDTO = new ServiceResponse();
				errDTO.setMessage("You must be a party to the shipment to file a claim.");
				errDTO.setErrorCode(ClaimsConstant.NOT_PARTY_TO_ERROR_CODE);
				
				return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.BAD_REQUEST);
			}
			if(claimsFilingService.isL2LShipment(request.getOtpro())) {
				ServiceResponse errDTO = new ServiceResponse();
				errDTO.setMessage("'We are unable to process Estes Level2 Logistics claims online at this time. " + 
						"Please fill out a Level2 Logistics claim form " + 
						"or contact our Claim Resolution Services team toll-free at "+
						ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_CLAIMS_DEPT_PHONE, ClaimsConstant.APP_EXT_PROP_CLAIMS_DEPT_PHONE)+
						" for assistance.");
				errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
				
				return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.BAD_REQUEST);
			}
			ProInfoResponseDTO res = claimsFilingService.getProInfo(request);
			return new ResponseEntity<ProInfoResponseDTO>(res, HttpStatus.OK);
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsFilingController.class, "getProInfo()", "An error occurred getting the pro info", e);
			
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest
	
	@ApiOperation(value = "Get infomation based on username in oauth token")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ClaimantResponseDTO.class, message="The Claimant Information"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Buisness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@RequestMapping(value = "/getClaimantInfo", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getClaimantInfo(@ApiIgnore Authentication auth) {
		String username = null;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		
		try {
			ClaimantResponseDTO res = claimsFilingService.getClaimantInfo(username);
			return new ResponseEntity<ClaimantResponseDTO>(res, HttpStatus.OK);
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsFilingController.class, "getClaimantInfo()", "An error occurred getting the claimant info", e);
			
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest
	
	@ApiOperation(value = "Whether a claim has already been entered for this ot pro")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="The message of entered or not"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Buisness Error and entered before"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@RequestMapping(value = "/hasEnteredClaim", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> hasEnteredClaim(@RequestBody OtProRequestDTO request, @ApiIgnore Authentication auth) {
		String username = null;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		
		try {
			boolean hasEntered = claimsFilingService.hasEnteredClaim(request.getOtpro());
			if(!hasEntered) {
				ServiceResponse res = new ServiceResponse();
				res.setMessage("Valid new claim");
				return new ResponseEntity<ServiceResponse>(res, HttpStatus.OK);
			}
			else {
				ServiceResponse res = new ServiceResponse();
				res.setErrorCode(ClaimsConstant.ERROR_CODE);
				res.setMessage("A claim has already been entered for this PRO number.  Please contact the claims department at (855) 693-7878, Ext. 2035 for more information.");
				return new ResponseEntity<ServiceResponse>(res, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsFilingController.class, "hasEnteredClaim()", "An error occurred checking if has entered claim", e);
			
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest
	
	@ApiOperation(value = "endpoint that gets swagger to generate the body submitclaimrequest for the submit endpoint, but doesn't do anything else", hidden=true)
	@ApiImplicitParams({
		@ApiImplicitParam(
	        dataType = "SubmitClaimRequestDTO",
	        name = "request",
	        paramType = "body",
	        required = true
	    )
	})
	@RequestMapping(value = "/test", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> test(@ApiIgnore @RequestBody SubmitClaimRequestDTO request, @ApiIgnore Authentication auth) {
	return null;
	}

	@ApiOperation(value = "File a claim, File types allowed are doc|docx|jpg|pdf|png|rtf|txt|xls|xlsx")
	@RequestMapping(value = "/submitClaim", method = RequestMethod.POST, produces = "application/json")
	@ApiImplicitParams({
		@ApiImplicitParam(
	        dataType = "SubmitClaimRequestDTO",
	        name = "request",
	        value = "The request for a new claim",
	        paramType = "body",
	        required = true
	    )
	})
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Claim filed"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Buisness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	public ResponseEntity<?> submitClaim(@ApiIgnore @ModelAttribute SubmitClaimRequestDTO request, @ApiIgnore Authentication auth) {
		String username = null;
		String accountCode = null;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		    accountCode = (String) details.get("accountCode");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		
		try {
			// if didn't pass subaccount then use this account code
			if(request.getAccountNumber() == null || request.getAccountNumber().equals("")) {
				request.setAccountNumber(accountCode);
			}
			ServiceResponse dto = claimsFilingService.fileClaim(username, request);
			if(dto.getErrorCode() != null) {
				return new ResponseEntity<ServiceResponse>(dto, HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<ServiceResponse>(dto, HttpStatus.OK);
			}
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ClaimsFilingController.class, "submitClaim()", "An error occurred submitting the claim", e);
		
			ServiceResponse errDTO = new ServiceResponse();
			errDTO.setMessage("Failed to process request");
			errDTO.setErrorCode(ClaimsConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(errDTO, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (ex instanceof MaxUploadSizeExceededException) {
			ModelAndView view = new ModelAndView();
			view.setView(new MappingJackson2JsonView());
			view.addObject("errorCode", ClaimsConstant.ERROR_CODE);
			view.addObject("message", "File Size exceeded");
			return view;
	      }
		ex.printStackTrace();
		return new ModelAndView("500");
	}
}