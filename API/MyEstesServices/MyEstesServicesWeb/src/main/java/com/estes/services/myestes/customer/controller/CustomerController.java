/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.customer.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.dto.ProfileDTO;
import com.estes.services.myestes.customer.service.iface.CustomerService;
import com.estes.services.myestes.exception.ServiceException;

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
@Api(value="My Estes customer services")
public class CustomerController
{
	@Autowired
	CustomerService custService;

	@ApiOperation(value = "Get My Estes customer account type code from session ID")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=String.class, message="My Estes customer account type code"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/getAccountType", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getCustomerAccountType(
			@ApiIgnore(value="Security token") Authentication auth,
			@ApiParam(value = "Session ID") @RequestParam(name = "session") String sess)
	{
		// If logged in return account type from security token
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			String acctType = (String) details.get("accountType");
			return new ResponseEntity<String>(acctType, HttpStatus.OK);
		}
		catch (Exception e) {	}

		// Get account type from session ID if security token not present
		try {
			String type = custService.getCustomerAccountType(sess);
			// Return successful response
			return new ResponseEntity<String>(type, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getCustomerAccountType
	
	@ApiOperation(value = "Get the sub accounts in page form - start at page=1 - size=25 - sort name/address/city/state/zip/acct defaults to acct - order not used in this service, is always asc")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Page.class, message="Page of Account"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@GetMapping(value = "/subAccounts", produces = {"application/json"})
	public ResponseEntity<?> subAccounts(final Pageable pageable, @ApiIgnore Authentication auth) {
		com.estes.dto.common.rest.Page<AccountDTO> shipmentsPage = null;
		String accountCode = "";
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    accountCode = (String) details.get("accountCode");
		} catch(Exception e) { }
		
		try {
			shipmentsPage = custService.getSubAccounts(pageable, accountCode);
			return new ResponseEntity<com.estes.dto.common.rest.Page<AccountDTO>>(shipmentsPage, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse();
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // subAccounts

	@ApiOperation(value = "Validate if an account is sub account of user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Boolean.class, message="Is or is not a subaccount"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@GetMapping(value = "/isSubAccountOf", produces = {"application/json"})
	public ResponseEntity<?> validateSubAccount(@RequestParam(name="selectedAccount") String selectedAccount, @ApiIgnore Authentication auth) {
		String accountCode = "";
		String accountType = "";
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    accountCode = (String) details.get("accountCode");
		    accountType = (String) details.get("accountType");
		} catch(Exception e) { }
		
		try {
			Boolean response = custService.validateSubAccount(accountCode, selectedAccount, accountType);
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // validateSubAccount
	
	@ApiOperation(value = "My Estes Signed In User Profile Basic Information ")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ProfileDTO.class, message = "Success response provides Profile object"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code=500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/customer/getProfileInfo",produces = {"application/json"})
    public ResponseEntity<?> getProfileInfo(@ApiIgnore Authentication auth) {
		String username;
		String acctType;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			username = (String) details.get("username");
			acctType = (String) details.get("accountType");
			
			ProfileDTO profile = custService.getUserProfile(username);
			profile.setAccountType(acctType);
			return new ResponseEntity<>(profile, HttpStatus.OK);
		}
		catch (Exception e) {
			e.printStackTrace();
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
    } // getProfileInfo
	
	@ApiOperation(value = "My Estes Account Information for the given account number")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=AccountDTO.class, message = "Success response provides Account object"), 
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Bussiness Error"),
			@ApiResponse(code = 500, response=ServiceResponse.class,message="Internal Server Error")
	})
	@GetMapping(value = "/customer/getAccountInfo/{accountCode}",produces = {"application/json"})
    public ResponseEntity<?> getAccountInfo(@PathVariable String accountCode, @ApiIgnore Authentication auth) {
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		}
		catch(Exception ex) {
			List<AccountDTO> resp = new ArrayList<AccountDTO>();
			return new ResponseEntity<List<AccountDTO>>(resp, HttpStatus.UNAUTHORIZED);
		}
		try {
			AccountDTO account = custService.getAccountInfo(accountCode);
			return new ResponseEntity<>(account, HttpStatus.OK);
		}
		catch (Exception e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}	
    } // getAccountInfo
	
	/*
	 * need this for swagger to document the return type of page<accountDTO> because it doesn't allow
	 * 'response' to be generic and 'responseContainer' only takes List, Array, and Set
	 */
	private abstract class Page extends com.estes.dto.common.rest.Page<AccountDTO> {

		public Page(List<AccountDTO> content, com.estes.dto.common.rest.Pageable pageable, int totalSize) {
			super(content, pageable, totalSize);
		}
	}
}
