package com.estes.myestes.quickLinks.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.quickLinks.config.AuthenticationDetails;
import com.estes.myestes.quickLinks.web.dto.QuickLink;
import com.estes.myestes.quickLinks.web.service.iface.QuickLinkService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
public class QuickLinksController {
	
	@Autowired
	private QuickLinkService quickLinkService; 
	

	@ApiOperation(value = "My Estes Quick Links ")
	@ApiResponses(value = { 
			@ApiResponse(code=200, response=QuickLink.class, responseContainer="List", message = "List of Quick Links"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message = "Internal Server Error")
		})
	@GetMapping(value = "/links",  produces = "application/json")
	public ResponseEntity<?> getAuthenticatedLinks(@ApiIgnore(value="Access Token Needed") AuthenticationDetails auth)
	{
		String username = auth.getUsername();
		List<QuickLink> quickLinks = new ArrayList<>();
		
		if(username!=null){
			quickLinks = quickLinkService.getUserQuickLinks(username);
		}else{
			quickLinks = quickLinkService.getDefaultQuickLinks();
		}
		
		return new ResponseEntity<>(quickLinks, HttpStatus.OK);
	}
	
	@ApiOperation(value = "My Estes List of Avaliable Quick Links for Authenticated User")
	@ApiResponses(value = { 
			@ApiResponse(code=200, response=QuickLink.class, responseContainer="List", message = "List of Quick Links"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message = "Internal Server Error")
		})
	@RequestMapping(value = "/links/list", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> availableLinks(@ApiIgnore AuthenticationDetails auth)
	{
		String username = auth.getUsername();
		
		List<QuickLink> quickLinks = quickLinkService.getAvailableQuickLinks(username);
		return new ResponseEntity<>(quickLinks, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "Add Quick Link to User Quick Link", response =QuickLink.class)
	@ApiResponses(value = { 
			@ApiResponse(code=200, response=QuickLink.class, message = "List of Quick Links"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message = "Internal Server Error")
		})
	@RequestMapping(value = "/links", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> addLink(
			@ApiIgnore AuthenticationDetails auth,
			@RequestParam("appName") String appName,
			@RequestParam("appCategory") String appCategory)
	{
		String username = auth.getUsername();
		
		QuickLink quickLink = new QuickLink();
		quickLink.setAppName(appName.replace("_", ""));
		quickLink.setAppCategory(appCategory);
		
		QuickLink ql = quickLinkService.addQuickLink(quickLink, username);
		
		return new ResponseEntity<>(ql, HttpStatus.OK);
	}
	
	
	
	@ApiOperation(value = "Remove Quick Link from User Quick Link")
	@ApiResponses(value = { 
			@ApiResponse(code=200, response=ServiceResponse.class, message = "Successful Response"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message = "Internal Server Error")
    })
	@DeleteMapping(value = "/links", produces = "application/json")
	public ResponseEntity<?> deleteLink(
			@ApiIgnore AuthenticationDetails auth,
			@RequestParam("appName") String appName,
			@RequestParam("appCategory") String appCategory
			)
	{
		String username = auth.getUsername();
		
		QuickLink quickLink = new QuickLink();
		quickLink.setAppName(appName.replace("_", ""));
		quickLink.setAppCategory(appCategory);
		quickLinkService.deleteQuickLink(quickLink, username);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@ApiOperation(value = "Reset User Quick Link to Default Quick Link")
	@ApiResponses(value = { 
			@ApiResponse(code=200, response=ServiceResponse.class, message = "Successful Response"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code=500, response=ServiceResponse.class, message = "Internal Server Error")
    })
	@RequestMapping(value = "/links/reset", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> resetLink(@ApiIgnore AuthenticationDetails auth)
	{

		String username = auth.getUsername();
		/**
		 * Reset the quickLinks
		 */
		quickLinkService.setDefaultQuickLinks(username);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
