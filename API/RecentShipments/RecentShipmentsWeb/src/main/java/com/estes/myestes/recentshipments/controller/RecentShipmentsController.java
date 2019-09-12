/**
 *
 */

package com.estes.myestes.recentshipments.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.recentshipments.dao.impl.ShipmentDAOImpl;
import com.estes.myestes.recentshipments.dto.ShipmentDTO;
import com.estes.myestes.recentshipments.exception.RecentShipmentsException;
import com.estes.myestes.recentshipments.service.iface.RecentShipmentsService;
import com.estes.myestes.recentshipments.util.RecentShipmentsConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes recent shipments services")
public class RecentShipmentsController {

	@Autowired
	RecentShipmentsService recentShipmentsService;
	
	@ApiOperation(value = "Get the recentShipments using account number and party - S/T/C")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ShipmentDTO.class, responseContainer="List", message="The recent shipments"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@GetMapping(value = "/recentShipments", produces = {"application/json"})
	public ResponseEntity<?> recentShipments(@RequestParam(name="account") String account, 
			@RequestParam(name="party") String party, @ApiIgnore Authentication auth) {
		String username = "";
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		List<ShipmentDTO> shipments = null;
		try {
			shipments = recentShipmentsService.getRecentShipments(username, account, party);
			return new ResponseEntity<List<ShipmentDTO>>(shipments, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse();
			response.setErrorCode(RecentShipmentsConstant.ERROR_CODE);
			response.setMessage("Unable to process request.");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@ApiOperation(value = "Get the default party to shipment - Returns T/C/S")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=String.class, message="The default party"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@GetMapping(value = "/defaultParty", produces = {"application/json"})
	public ResponseEntity<?> defaultParty(@ApiIgnore Authentication auth) {
		String username = "";
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		try {
			String response = recentShipmentsService.getDefaultParty(username);
			return new ResponseEntity<String>(response, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse();
			response.setErrorCode(RecentShipmentsConstant.ERROR_CODE);
			response.setMessage("Unable to process request.");
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "defaultParty()", "An error getting the default party", e);
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@ApiOperation(value = "Set the default party to shipment - Party should be T/C/S")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Set default party or error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error in setting default party")
	})
	@PostMapping(value = "/defaultParty", produces = {"application/json"})
	public ResponseEntity<ServiceResponse> defaultParty(@RequestParam(name="party") String party, @ApiIgnore Authentication auth) {
		String username = "";
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		    username = (String) details.get("username");
		} catch(Exception e) {
			ESTESLogger.log(this.getClass(), "Not authorized.");
		}
		try {
			ServiceResponse response = recentShipmentsService.setDefaultParty(username, party);
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			ServiceResponse response = new ServiceResponse();
			response.setErrorCode(RecentShipmentsConstant.ERROR_CODE);
			response.setMessage("Unable to process request.");
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "defaultParty()", "An error setting the default party", e);
			return new ResponseEntity<ServiceResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}