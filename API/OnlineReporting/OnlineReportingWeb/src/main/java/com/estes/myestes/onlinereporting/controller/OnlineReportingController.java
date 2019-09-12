/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.onlinereporting.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.onlinereporting.dto.OnlineReport;
import com.estes.myestes.onlinereporting.dto.ReportType;
import com.estes.myestes.onlinereporting.exception.OnlineReportingException;
import com.estes.myestes.onlinereporting.service.iface.OnlineReportingService;
import com.estes.myestes.onlinereporting.utils.OnlineReportingConstant;
import com.estes.myestes.onlinereporting.utils.OnlineReportingUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes customer online reporting.")
public class OnlineReportingController implements OnlineReportingConstant
{
	@Autowired
	OnlineReportingService onlineReportingService;

	@ApiOperation(value = "Get all report types for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ReportType.class, responseContainer="List", message="Online report types."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/types", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<ReportType>> getOnlineReportTypes(Authentication auth)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			List<ReportType> resp = new ArrayList<ReportType>();
			return new ResponseEntity<List<ReportType>>(resp, HttpStatus.UNAUTHORIZED);
		}
		List<ReportType> resp = null;
		try {
			resp = onlineReportingService.getReportTypes();
		}
		catch (OnlineReportingException ex) {
			resp = new ArrayList<ReportType>();
			return new ResponseEntity<List<ReportType>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<List<ReportType>>(resp, HttpStatus.OK);
	} // getOnlineReportTypes
	
	@ApiOperation(value = "Get all reports for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=OnlineReport.class, responseContainer="List", message="MyEstes user reports."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/reports", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<OnlineReport>> getOnlineReports(Authentication auth)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			List<OnlineReport> resp = new ArrayList<OnlineReport>();
			return new ResponseEntity<List<OnlineReport>>(resp, HttpStatus.UNAUTHORIZED);
		}
		List<OnlineReport> resp = null;
		try {
			resp = onlineReportingService.getOnlineReports(user);
		}
		catch (OnlineReportingException ex) {
			resp = new ArrayList<OnlineReport>();
			return new ResponseEntity<List<OnlineReport>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<List<OnlineReport>>(resp, HttpStatus.OK);
	} // getOnlineReports

	@ApiOperation(value = "Get scheduled report data by scheduleId for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=OnlineReport.class, message="Online report information."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/report/{scheduleId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<OnlineReport> getScheduledReport(@PathVariable String scheduleId, Authentication auth)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			OnlineReport resp = new OnlineReport();
			return new ResponseEntity<OnlineReport>(resp, HttpStatus.UNAUTHORIZED);
		}
		OnlineReport report = null;

		try {
			report = onlineReportingService.getScheduledReportData(scheduleId);
			setData(report);
		}
		catch (OnlineReportingException ex) {
			report = new OnlineReport();
			return new ResponseEntity<OnlineReport>(report, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<OnlineReport>(report, HttpStatus.OK);
	} // getScheduledReport
	
	@ApiOperation(value = "Add customer online report", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Succes information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/report", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> addReport(@RequestBody OnlineReport report, Authentication aut)
	{
		String user;
		String acctNum;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
			acctNum = (String) details.get("accountCode");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse ("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}
		
		if (OnlineReportingUtil.isNullOrEmpty(report.getReportName())) {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Please enter a report name."), HttpStatus.BAD_REQUEST);
		}
		
		if(!OnlineReportingUtil.isValidEmail(report.getEmail())) {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Please enter a valid email address."), HttpStatus.BAD_REQUEST);
		}
		
		String successMsg = null;
		try {
			boolean success = false;
			if (OnlineReportingUtil.isNullOrEmpty(report.getAccountNumber())) {
				report.setAccountNumber(acctNum);
			}
			success = onlineReportingService.addOnlineReport(report, user);
			successMsg = SUCCESS_MESSAGE.replace("REPLACETEXT", "added");
			if (success) {
				// Return successful response	
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("", successMsg), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Not Successful."), HttpStatus.BAD_REQUEST);
			}
		}
		catch (OnlineReportingException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // addReport
	
	@ApiOperation(value = "Modify customer online report", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Succes information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/report", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<ServiceResponse> updateReport(@RequestBody OnlineReport report, Authentication aut)
	{
		String user;
		String acctNum;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
			acctNum = (String) details.get("accountCode");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse ("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}	
		if (OnlineReportingUtil.isNullOrEmpty(report.getReportName())) {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Please enter a report name."), HttpStatus.BAD_REQUEST);
		}		
		if(!OnlineReportingUtil.isValidEmail(report.getEmail())) {
			return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Please enter a valid email address."), HttpStatus.BAD_REQUEST);
		}		
		String successMsg = null;
		try {
			boolean success = false;
			if (OnlineReportingUtil.isNullOrEmpty(report.getAccountNumber())) {
				report.setAccountNumber(acctNum);
			}
			success = onlineReportingService.updateOnlineReport(report);
			successMsg = SUCCESS_MESSAGE.replace("REPLACETEXT", "edited");
			if (success) {
				// Return successful response	
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("", successMsg), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "The report " + report.getScheduleId() + " does not exist."), HttpStatus.BAD_REQUEST);
			}
		}
		catch (OnlineReportingException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // updateReport
	
	@ApiOperation(value = "Delete customer online report", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Succes information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/report/{scheduleId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<ServiceResponse> deleteReport(@PathVariable String scheduleId, Authentication aut)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			details.get("username");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse ("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}
		
		String successMsg = null;
		try {
			boolean success = false;
			success = onlineReportingService.deleteOnlineReport(scheduleId);	
			successMsg = DELETE_MESSAGE;
			if (success) {
				// Return successful response	
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("", successMsg), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Not Successful."), HttpStatus.BAD_REQUEST);
			}
		}
		catch (OnlineReportingException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // deleteReport
	
	@ApiOperation(value = "Unsubscribe MyEstes user email from the report")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="{UserEmail} has been unsubscribed from this report."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/unsubscribe/{email}/{scheduleId}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> unsubscribeReport(@PathVariable String email, @PathVariable String scheduleId, Authentication auth)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}
		boolean success = false;
		try {
			success = onlineReportingService.unsubscribeReport(scheduleId, email);
		}
		catch (OnlineReportingException ex) {
			ServiceResponse resp = new ServiceResponse("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		if(success) {
			// Return response objecthttp://marketplace.eclipse.org/marketplace-client-intro?mpc_install=979
			ServiceResponse resp = new ServiceResponse("", email + " has been unsubscribed from this report.");		 
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
		} else {
			ServiceResponse resp = new ServiceResponse("Error", "Error unsubscribing the email " + email +  " from this report.");		 
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
		}
	} // unsubscribeReport
	
	private void setData(OnlineReport report) {
		if(report.getStartDate().equals(report.getExpirationDate())){
			report.setExpirationDate(null);
			report.setOneTime(true);
		}
		else if(report.getExpirationDate().equals(FUTURE_END_DATE)){
			report.setExpirationDate(null);
			report.setManually(true);
		}
		else if(!report.getRequestedWeeks().equals(DEFAULT_NUMBER_VALUE)){
			report.setExpirationDate(null);
			report.setNumberOfWeeks(true);
		}
		else if(!report.getRequestedMonths().equals(DEFAULT_NUMBER_VALUE)){
			report.setExpirationDate(null);
			report.setNumberOfMonths(true);
		}
		else if(!report.getExpirationDate().equals(FUTURE_END_DATE)){
			report.setUntilDate(true);
		}
	}
}