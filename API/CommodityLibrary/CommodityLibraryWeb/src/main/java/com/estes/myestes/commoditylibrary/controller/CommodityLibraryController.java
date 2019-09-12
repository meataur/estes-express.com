/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.commoditylibrary.controller;

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
import com.estes.myestes.commoditylibrary.dto.Commodity;
import com.estes.myestes.commoditylibrary.exception.CommodityLibraryException;
import com.estes.myestes.commoditylibrary.service.iface.CommodityLibraryService;
import com.estes.myestes.commoditylibrary.utils.CommodityLibraryConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes commodity maintenance.")
public class CommodityLibraryController implements CommodityLibraryConstant
{
	@Autowired
	CommodityLibraryService commodityLibraryService;

	@ApiOperation(value = "Get all commodities for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Commodity.class, responseContainer="List", message="List of commodities."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodities", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<Commodity>> getCommodities(Authentication auth)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			List<Commodity> resp = new ArrayList<Commodity>();
			return new ResponseEntity<List<Commodity>>(resp, HttpStatus.UNAUTHORIZED);
		}
		List<Commodity> resp = null;
		try {
			resp = commodityLibraryService.getCommodities(user);
		}
		catch (CommodityLibraryException ex) {
			resp = new ArrayList<Commodity>();
			return new ResponseEntity<List<Commodity>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<List<Commodity>>(resp, HttpStatus.OK);
	} // getCommodities
	
	@ApiOperation(value = "Get commodity info by commodity id for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Commodity.class, message="Commodity info"),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodity/{commodityId}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Commodity> getCommodityById(@PathVariable String commodityId, Authentication auth)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			Commodity resp = new Commodity();
			return new ResponseEntity<Commodity>(resp, HttpStatus.UNAUTHORIZED);
		}
		Commodity commodity = null;

		try {
			commodity = commodityLibraryService.getCommodityById(commodityId, user);
		}
		catch (CommodityLibraryException ex) {
			commodity = new Commodity();
			return new ResponseEntity<Commodity>(commodity, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<Commodity>(commodity, HttpStatus.OK);
	} // getCommodityById
	
	@ApiOperation(value = "Add a commodity for MyEstes user", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Succes information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodity", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addCommodity(@RequestBody Commodity commodity, Authentication aut)
	{
		String user = null;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse ("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}			
		boolean success = false;
		try {
			success = commodityLibraryService.addCommodity(commodity, user);
			if (success) {
				// Return successful response	
				ServiceResponse resp = new ServiceResponse("","Product " + commodity.getId() +  " added.");
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
			}
			else {
				ServiceResponse[] resp = commodityLibraryService.getErrors(user);
				return new ResponseEntity<ServiceResponse[]>(resp, HttpStatus.BAD_REQUEST);			
				}
		}
		catch (CommodityLibraryException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // addCommodity
	
	@ApiOperation(value = "Update a commodity for MyEstes user", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Succes information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodity", method = RequestMethod.PUT, produces = "application/json")
	public ResponseEntity<?> updateCommodity(@RequestBody Commodity commodity, Authentication aut)
	{
		String user = null;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse ("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}			
		boolean success = false;
		try {
			success = commodityLibraryService.updateCommodity(commodity, user);
			if (success) {
				// Return successful response	
				ServiceResponse resp = new ServiceResponse("","Product " + commodity.getId() +  " updated.");
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
			}
			else {
				ServiceResponse[] resp = commodityLibraryService.getErrors(user);
				return new ResponseEntity<ServiceResponse[]>(resp, HttpStatus.BAD_REQUEST);	
				}
		}
		catch (CommodityLibraryException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // updateCommodity
	
	@ApiOperation(value = "Delete a commodity for Myestes user", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodity/{commodityId}", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseEntity<ServiceResponse> deleteCommodity(@PathVariable String commodityId, Authentication aut)
	{
		String user;
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse ("","");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}
		try {
			boolean success = false;
			success = commodityLibraryService.deleteCommodity(user, commodityId);
			if (success) {
				// Return successful response	
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("", "Product " + commodityId +  " deleted."), HttpStatus.OK);
			}
			else {
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Error deleting " + commodityId +"."), HttpStatus.BAD_REQUEST);
			}
		}
		catch (CommodityLibraryException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // deleteCommodity
}