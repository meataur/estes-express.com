/**
 * @author: Todd Allen
 *
 * Creation date: 05/24/2018
 */

package com.estes.myestes.addressbook.controller;

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
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.dto.BookAddressSearch;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.service.iface.AddressService;
import com.estes.myestes.addressbook.service.iface.SearchService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes customer address book search")
public class SearchController
{
	@Autowired
	SearchService addrSearchService;

	@ApiOperation(value = "Search My Estes user address book")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=BookAddress.class, responseContainer="List", message="Customer addresses"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/search", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getAddresses(@RequestBody BookAddressSearch search, Authentication aut)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			search.setUser((String) details.get("username"));
		} catch (Exception e) {
			BookAddress[] resp = new BookAddress[] {new BookAddress()};
			return new ResponseEntity<BookAddress[]>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			BookAddress[] resp = addrSearchService.searchAddresses(search);
			return new ResponseEntity<BookAddress[]>(resp, HttpStatus.OK);
		}
		catch (AddressBookException abe) {
			ServiceResponse resp = new ServiceResponse(AddressService.DEFAULT_ERROR_CODE, AddressService.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getAddresses
}
