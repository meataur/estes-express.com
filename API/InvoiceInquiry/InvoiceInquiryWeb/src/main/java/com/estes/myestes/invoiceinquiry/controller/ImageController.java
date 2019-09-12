/**
 * @author: Todd Allen
 *
 * Creation date: 12/31/2018
 */

package com.estes.myestes.invoiceinquiry.controller;

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
import com.estes.myestes.invoiceinquiry.dto.ImageRequest;
import com.estes.myestes.invoiceinquiry.dto.Image;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.ImageService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST document images
 */
@RestController
@Api(value="Estes document images")
public class ImageController
{
	@Autowired
	ImageService imageService;

	@ApiOperation(value = "Show customer statement details")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=Image.class, message="Document image information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/images", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getImages(
			@ApiIgnore(value="Security token") Authentication auth,
			@RequestBody ImageRequest req)
	{
		String account;
		String session;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			account = (String) details.get("accountCode");
			session = (String) details.get("session");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			Image imageInfo = imageService.getImageInfo(account, session, req);
			return new ResponseEntity<Image>(imageInfo, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getImages
}
