/**
 * @author: Todd Allen
 *
 * Creation date: 05/24/2018
 */

package com.estes.myestes.addressbook.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.service.iface.AddressService;
import com.estes.myestes.addressbook.service.iface.UploadService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@Controller
//@MultipartConfig
@Api(value="Estes customer address book file upload")
public class UploadController
{
	@Autowired
	UploadService uploader;

	/**
	 * Process CSV uploads
	 */
	@ApiOperation(value = "Perform append/overwrite actions on CSV uploads of customer address book data")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Success message"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/upload/{oper}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> performUpload(
			@ApiIgnore(value="Security token") Authentication aut,
			@ApiParam(value="Operation - append/overwrite") @PathVariable String oper,
			@RequestParam("file") MultipartFile csv)
	{
		String user;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse(AddressService.DEFAULT_ERROR_CODE, "");
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			// Perform simple validation on CSV data
			List<ServiceResponse> errors = uploader.validateAddresses(csv);
			if (uploader.isError()) {
				return new ResponseEntity<List<ServiceResponse>>(errors, HttpStatus.BAD_REQUEST);
			}
			// Process CSV data
			errors = uploader.processFile(csv, user, oper);
			// Return list of all messages - errors + # of uploads
			return new ResponseEntity<List<ServiceResponse>>(errors, HttpStatus.OK);
		}
		catch (AddressBookException abe) {
			ServiceResponse resp = new ServiceResponse(AddressService.DEFAULT_ERROR_CODE, AddressService.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // performUpload
}
