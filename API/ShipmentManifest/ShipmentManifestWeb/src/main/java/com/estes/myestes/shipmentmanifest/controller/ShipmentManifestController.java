/**
 *
 */

package com.estes.myestes.shipmentmanifest.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.edps.logger.ESTESLogger;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shipmentmanifest.dto.EmailRequestDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRequestDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestResponseDTO;
import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;
import com.estes.myestes.shipmentmanifest.service.iface.ShipmentManifestService;
import com.estes.myestes.shipmentmanifest.util.ShipmentManifestConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="My Estes Shipment Manifest")
public class ShipmentManifestController {

	@Autowired
	ShipmentManifestService shipmentManifestService;

	@ApiOperation(value = "Request to view manifest record page - pageable default start at page=1 - size=25")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Page.class, message="Page of Shipment Manifests"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
	})
	@RequestMapping(value = "/view", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> viewManifest(final Pageable pageable, @RequestBody ManifestRequestDTO dto, @ApiIgnore Authentication auth) {
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		Map<String, String> details = (Map<String, String>) oauthDetails.getDecodedDetails();
		    
		try {
			com.estes.dto.common.rest.Page<ManifestRecordDTO> response = shipmentManifestService.getManifest(pageable, dto, details);
			// Return response object
			return new ResponseEntity<com.estes.dto.common.rest.Page<ManifestRecordDTO>>(response, HttpStatus.OK);
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentManifestController.class, "viewManifest()", "Failed to view manifest", e);
			ServiceResponse error = new ServiceResponse();
			error.setMessage("Failed to process request");
			error.setErrorCode(ShipmentManifestConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // makeRequest
	
	@ApiOperation(value = "Email the list of records that makes up the manifest", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Successfully Sent Email"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error")
		})
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> emailManifest(@RequestBody EmailRequestDTO dto, @ApiIgnore Authentication auth) {
		ServiceResponse response = new ServiceResponse();
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
		Map<String, String> details = (Map<String, String>) oauthDetails.getDecodedDetails();
		    
		try {
			response = shipmentManifestService.emailManifest(dto, details);
		} catch (ShipmentManifestException e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentManifestController.class, "emailManifest()", "Failed to email manifest", e);
			ServiceResponse error = new ServiceResponse();
			error.setMessage("Failed to process request");
			error.setErrorCode(ShipmentManifestConstant.ERROR_CODE);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		// Return response object
		return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
	} // makeRequest
	
	/*
	 * need this for swagger to document the return type of page<ManifestRecordDTO> because it doesn't allow
	 * 'response' to be generic and 'responseContainer' only takes List, Array, and Set
	 */
	private abstract class Page extends com.estes.dto.common.rest.Page<ManifestRecordDTO> {

		public Page(List<ManifestRecordDTO> content, com.estes.dto.common.rest.Pageable pageable, int totalSize) {
			super(content, pageable, totalSize);
		}
	}
}
