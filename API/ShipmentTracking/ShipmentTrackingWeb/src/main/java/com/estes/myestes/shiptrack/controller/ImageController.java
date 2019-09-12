/**
 * @author: Todd Allen
 *
 * Creation date: 07/23/2018
 */

package com.estes.myestes.shiptrack.controller;

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
import com.estes.myestes.shiptrack.dto.Image;
import com.estes.myestes.shiptrack.dto.ImageRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.service.iface.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes shipment tracking image retrieval")
public class ImageController
{
	@Autowired
	ImageService imageService;

	@ApiOperation(value = "Get images associated with a PRO", response = ServiceResponse[].class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Image.class, responseContainer="List", message="Image information for PRO"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/images", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getImages(@RequestBody ImageRequest req, Authentication auth)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			req.setUser((String) details.get("username"));
			req.setSession((String) details.get("session"));
		} catch (Exception e) {
			Image[] resp = new Image[] {new Image()};
			return new ResponseEntity<Image[]>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			Image[] images = imageService.getImages(req);
			return new ResponseEntity<Image[]>(images, HttpStatus.OK);
		}
		catch (ShipTrackException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getImages
}
