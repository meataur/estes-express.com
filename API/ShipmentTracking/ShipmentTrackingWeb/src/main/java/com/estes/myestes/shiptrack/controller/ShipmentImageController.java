/**
 * @author: Todd Allen
 *
 * Creation date: 07/23/2018
 */

package com.estes.myestes.shiptrack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shiptrack.config.AuthenticationDetails;
import com.estes.myestes.shiptrack.dto.ShipmentImage;
import com.estes.myestes.shiptrack.dto.ShipmentImageRequest;
import com.estes.myestes.shiptrack.service.iface.ShipmentImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes shipment tracking image retrieval")
public class ShipmentImageController
{
	@Autowired
	ShipmentImageService imageService;

	@ApiOperation(value = "Get BOL images associated with a PRO", response = ServiceResponse[].class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ShipmentImage.class, responseContainer="List", message="Image information for PRO"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/images/bol", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getBolImages(@RequestBody ShipmentImageRequest req, AuthenticationDetails auth)
	{
		String sessionId = auth.getSession();
		if(sessionId==null){
			sessionId = "";
		}
		List<ShipmentImage> images = imageService.getBolOrDRImages(req, sessionId, "BOL");
		return new ResponseEntity<>(images, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get DR images associated with a PRO", response = ServiceResponse[].class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ShipmentImage.class, responseContainer="List", message="Image information for PRO"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/images/dr", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getDeliveryRecieptImages(@RequestBody ShipmentImageRequest req, AuthenticationDetails auth)
	{
		String sessionId = auth.getSession();
		if(sessionId==null){
			sessionId = "";
		}
		List<ShipmentImage> images = imageService.getBolOrDRImages(req, sessionId, "DR");
		return new ResponseEntity<>(images, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Get W&R images associated with a PRO", response = ServiceResponse[].class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ShipmentImage.class, responseContainer="List", message="Image information for PRO"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/images/wr", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getWeightAndResearchImages(@RequestBody ShipmentImageRequest req, AuthenticationDetails auth)
	{
		String username = auth.getUsername();
		if(username==null){
			username = "";
		}
		List<ShipmentImage> images = imageService.getWeightAndResearchImages(req, username);
		return new ResponseEntity<>(images, HttpStatus.OK);
	}

}
