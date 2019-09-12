/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 */

package com.estes.myestes.shiptrack.controller;

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
import com.estes.myestes.shiptrack.dto.EmailStatusRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.service.iface.EmailUpdateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes shipment tracking e-mail updates")
public class EmailController
{
	@Autowired
	EmailUpdateService emailService;

	@ApiOperation(value = "Request e-mail status updates", response = ServiceResponse[].class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, responseContainer="List", message="Error information per PRO/e-mail address"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> emailUpdates(@RequestBody EmailStatusRequest req, Authentication auth)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			req.setSession((String) details.get("session"));
		} catch (Exception e) {
			ServiceResponse[] resp = new ServiceResponse[] {new ServiceResponse()};
			return new ResponseEntity<ServiceResponse[]>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<ServiceResponse> resp = emailService.requestUpdates(req);
			return new ResponseEntity<>(resp, HttpStatus.OK);
		}
		catch (ShipTrackException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // emailUpdates
}
