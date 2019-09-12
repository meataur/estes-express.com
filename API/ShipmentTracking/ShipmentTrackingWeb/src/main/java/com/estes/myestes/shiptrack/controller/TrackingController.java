/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.shiptrack.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shiptrack.dto.Shipment;
import com.estes.myestes.shiptrack.dto.TrackingRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.service.iface.TrackingService;
import com.estes.myestes.shiptrack.utils.AppConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes shipment tracking")
public class TrackingController
{
	@Autowired
	TrackingService trackService;

	@ApiOperation(value = "Search shipment for requested criteria")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Shipment.class, responseContainer="List", message="Shipment information"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Error Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> trackShipments(@RequestBody TrackingRequest req, Authentication auth)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			@SuppressWarnings("unchecked")
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			req.setSession((String) details.get("session"));
		} catch (Exception e) {
			req.setSession("");
		}
		String searchCriteria = req.getSearch();
		
		if(null==searchCriteria || searchCriteria.trim().length()<2) {
			List<Shipment> response = new ArrayList<>();
			Shipment shipment = new Shipment();
			shipment.setErrorInfo(new ServiceResponse("error","Invalid PRO number"));
			shipment.setPro(searchCriteria);
			shipment.setError("Invalid PRO number,");
			response.add(shipment);
			return new ResponseEntity<>(response, HttpStatus.OK);
		}
		
		req.setSearch(trackService.getExtractedSearch(searchCriteria));
		try {		
			List<Shipment> resp = trackService.searchShipments(req);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		catch (ShipTrackException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // trackShipments
}
