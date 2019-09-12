/**
 * @author: Todd Allen
 *
 * Creation date: 05/24/2018
 */

package com.estes.myestes.addressbook.controller;

import java.lang.reflect.Field;
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

import com.estes.dto.common.Address;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.service.iface.AddressService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes customer address book maintenance operations")
public class AddressBookController
{
	@Autowired
	AddressService addrBookService;

	@ApiOperation(value = "Get all addresses for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=BookAddress.class, responseContainer="List", message="Customer addresses"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/show", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAddresses(Authentication aut)
	{
		String user;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
		} catch (Exception e) {
			BookAddress[] resp = new BookAddress[] {new BookAddress()};
			return new ResponseEntity<BookAddress[]>(resp, HttpStatus.UNAUTHORIZED);
		}

		BookAddress[] resp = null;

		try {
			resp = addrBookService.getAddresses(user);
			return new ResponseEntity<BookAddress[]>(resp, HttpStatus.OK);
		}
		catch (AddressBookException abe) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getAddresses

	@ApiOperation(value = "Perform add/delete/update actions on customer address book")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Error information - blank"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/do/{oper}", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> performAction(
			@ApiIgnore(value="Security token") Authentication aut,
			@PathVariable String oper,
			@RequestBody BookAddress req)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			req.setUser((String) details.get("username"));
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		if (oper.matches("add|update")) {
			setNullFields(req);
		}

		try {
			boolean success = false;
			String message = "";
			switch (oper) {
				case "add": 		success = addrBookService.addAddress(req);
										message = "The address has been added.";
										break;
				case "delete":	success = addrBookService.deleteAddress(req);
										message = "The address has been deleted.";
										break;
				case "update":	success = addrBookService.updateAddress(req);
										message = "The address has been updated.";
			}

			if (success) {
				ServiceResponse resp = new ServiceResponse("",message);
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
			}
			else {
				ServiceResponse[] resp = addrBookService.getErrors();
				return new ResponseEntity<ServiceResponse[]>(resp, HttpStatus.BAD_REQUEST);
			}
		}
		catch (AddressBookException abe) {
			ServiceResponse resp = new ServiceResponse(AddressService.DEFAULT_ERROR_CODE, AddressService.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // performAction

	private void setNullFields(BookAddress addr)
	{
		Field[] fields = addr.getClass().getDeclaredFields();
		//Field[] fields = clazz.getFields();
		try {
			for (Field field : fields) {
					field.setAccessible(true);
					if (field.getType() == String.class && field.get(addr) == null ) {
							field.set(addr, "");
					}
					else if (field.getType() == Address.class ) {
						Field[] addfields = addr.getAddress().getClass().getDeclaredFields();
						for (Field addfield : addfields) {
							addfield.setAccessible(true);
							if (addfield.getType() == String.class && addfield.get(addr.getAddress()) == null ) {
								addfield.set(addr.getAddress(), "");
							}
						} // for
					}
			} // for
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookController.class, "setNullFields()", e.getMessage(), e);
		}
	} // setNullFields
}