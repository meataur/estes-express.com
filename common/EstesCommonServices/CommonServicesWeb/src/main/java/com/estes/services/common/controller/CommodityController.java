/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */
package com.estes.services.common.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.dto.PackageType;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.CommodityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes Package types and classes")
public class CommodityController
{
	@Autowired
	CommodityService commodityService;

	@ApiOperation(value = "Get all class values")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=String.class, responseContainer="List", message="List of ship class values"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodity/classes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getClasses()
	{
		try {
			List<String> classes = commodityService.getClasses();
			// Return successful response
			return new ResponseEntity<List<String>>(classes, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getClasses
	
	@ApiOperation(value = "Get all package types")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=PackageType.class, responseContainer="List", message="List of Package types"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/commodity/packageTypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPackageTypes()
	{
		try {
			List<PackageType> pkgTypes = commodityService.getPackageTypes();
			// Return successful response
			return new ResponseEntity<List<PackageType>>(pkgTypes, HttpStatus.OK);
		}
		catch (ServiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getPackageTypes
}
