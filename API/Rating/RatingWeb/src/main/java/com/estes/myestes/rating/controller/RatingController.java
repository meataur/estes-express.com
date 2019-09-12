/**
 * @author: Todd Allen
 *
 * Creation date: 01/03/2019
 */

package com.estes.myestes.rating.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.config.AuthenticationDetails;
import com.estes.myestes.rating.dto.Accessorial;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.dto.ContactRequest;
import com.estes.myestes.rating.dto.FoodWarehouse;
import com.estes.myestes.rating.dto.QuoteKeys;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.dto.ServiceLevel;
import com.estes.myestes.rating.exception.RatingErrorsException;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.BookingService;
import com.estes.myestes.rating.service.iface.EmailService;
import com.estes.myestes.rating.service.iface.RatingService;
import com.estes.myestes.rating.service.iface.ServiceAdjustmentService;
import com.estes.myestes.rating.utils.RatingConstants;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value = "Estes shipment rating services")
public class RatingController {
	
	@Autowired
	RatingService rateService;
	
	@Autowired
	ServiceAdjustmentService adjustmentService;
	
	@Autowired
	BookingService bookService;

	@Autowired
	EmailService emailService;
	
	@ApiOperation(value = "Adjust rate quote service level")
	@ApiResponses(value = { @ApiResponse(code = 200, response = ServiceResponse.class, message = "Success message"),
			@ApiResponse(code = 400, response = ServiceResponse.class, message = "Business error information"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "System error information") })
	@RequestMapping(value = "/adjust", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> adjustQuote(@RequestBody ContactRequest req) {
		
		try {
			ServiceResponse msg = adjustmentService.sendAdjustedQuoteInfo(req);
			// Send errors in response
			if (ServiceResponse.isError(msg.getErrorCode())) {
				return new ResponseEntity<ServiceResponse>(msg, HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<ServiceResponse>(msg, HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // adjustQuote

	@ApiOperation(value = "Book rate quote")
	@ApiResponses(value = { @ApiResponse(code = 200, response = ServiceResponse.class, message = "Success message"),
			@ApiResponse(code = 400, response = ServiceResponse.class, message = "Business error information"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "System error information") })
	@RequestMapping(value = "/book", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> bookQuote(@RequestBody BookingRequest req) {

		try {
			ServiceResponse msg = bookService.bookShipment(req);
			// Send errors in response
			if (ServiceResponse.isError(msg.getErrorCode())) {
				return new ResponseEntity<ServiceResponse>(msg, HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<ServiceResponse>(msg, HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // bookQuote

	@ApiOperation(value = "Generate rate quotes for all service levels")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = RateQuote.class, responseContainer = "List", message = "Rate quote"),
			@ApiResponse(code = 400, response = ServiceResponse.class, responseContainer = "List", message = "Business error information"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "System error information") })
	@RequestMapping(value = "/requestQuote", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> generateQuotes(@ApiIgnore(value = "Security token") AuthenticationDetails auth,
			@RequestBody RatingRequest req) throws RatingErrorsException {

		if(auth.getUsername()!=null){
			req.setUser(auth.getUsername());
			req.setSession(auth.getSession());
			if (StringUtils.isEmpty(req.getAccountCode())) {
				req.setAccountCode(auth.getAccountCode());
			}
		}else{
			req.setUser("");
			req.setSession("");
			req.setAccountCode(RatingRequest.BASE_ACCOUNT);
			req.setRole(RatingRequest.DEFAULT_ROLE);
			req.setTerms(RatingRequest.DEFAULT_TERMS);
		}

		/**
		 * 
		 */
		
		List<ServiceResponse> errorResponses =  validateRateRequest(req);
		
		if (errorResponses != null && !errorResponses.isEmpty()) {
			return new ResponseEntity<List<ServiceResponse>>(errorResponses, HttpStatus.BAD_REQUEST);
		}

		
		
		try {
			QuoteKeys keys = rateService.processRateRequest(req);

			List<RateQuote> quotes = rateService.retrieveNewQuotes(req.getApps(),keys);
			
			return new ResponseEntity<List<RateQuote>>(quotes, HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // generateQuotes

	private List<ServiceResponse> validateRateRequest(RatingRequest req) {
		
		List<ServiceResponse> errors = new ArrayList<>();
		if(req.getAccountCode()!=null 
				&& req.getAccountCode().trim().length() >7){
			
			ServiceResponse error = new ServiceResponse("accountCode","Invalid account number");
			errors.add(error);
		}
		
		if(req.getContactPhone()!=null 
				&& req.getContactPhone().trim().length()>0 
				&& req.getContactPhoneExt() !=null && req.getContactPhoneExt().length() >7){
			
			ServiceResponse error = new ServiceResponse("contactPhoneExt","Phone extension can have maximum 7 digits.");
			errors.add(error);
		}
		return errors;
	}

	@ApiOperation(value = "Get rating accessorial information")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = Accessorial.class, responseContainer = "List", message = "Accessorial info"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "Error information") })
	@RequestMapping(value = "/accessorials", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getAccessorials() {
		try {
			List<Accessorial> accs = rateService.retrieveAccessorials();
			return new ResponseEntity<List<Accessorial>>(accs, HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getAccessorials

	@ApiOperation(value = "Get food warehouse information")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = FoodWarehouse.class, responseContainer = "List", message = "Accessorial info"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "Error information") })
	@RequestMapping(value = "/foodWarehouses", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getFoodWarehouses() {
		try {
			List<FoodWarehouse> accs = rateService.retrieveFoodWarehouses();
			return new ResponseEntity<List<FoodWarehouse>>(accs, HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getFoodWarehouses

	@ApiOperation(value = "Get quote information")
	@ApiResponses(value = { @ApiResponse(code = 200, response = RateQuote.class, message = "Rate quote information"),
			@ApiResponse(code = 400, response = RateQuote.class, message = "Error information"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "Error information") })
	@RequestMapping(value = "/quote/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getQuote(@ApiParam(value = "Unique quote ID") @PathVariable String id) {

		try {
			RateQuote quote = rateService.retrieveQuote(id);
			if (quote != null) {
				return new ResponseEntity<RateQuote>(quote, HttpStatus.OK);
			} else {
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("Error", "Quote not found."),
						HttpStatus.BAD_REQUEST);
			}
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getQuote

	
	@ApiOperation(value = "Get all service level information")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = ServiceLevel.class, responseContainer = "List", message = "Accessorial info"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "Error information") })
	@RequestMapping(value = "/serviceLevels", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getServiceLevels() {
		try {
			List<ServiceLevel> services = rateService.retrieveServiceLevels();
			return new ResponseEntity<List<ServiceLevel>>(services, HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getServiceLevels

	@ApiOperation(value = "Select rate quote")
	@ApiResponses(value = {
			@ApiResponse(code = 200, response = ServiceLevel.class, message = "Successful confirmation"),
			@ApiResponse(code = 500, response = ServiceResponse.class, message = "Error information") })
	@RequestMapping(value = "/select/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> selectQuote(@ApiParam(value = "Unique quote ID") @PathVariable String id) {
		try {
			rateService.loadGMSData(id);
			return new ResponseEntity<ServiceResponse>(new ServiceResponse("", id + " selected."), HttpStatus.OK);
		} catch (RatingException e) {
			ServiceResponse error = new ServiceResponse(RatingConstants.DEFAULT_ERROR_CODE,
					RatingConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // selectQuote
}
