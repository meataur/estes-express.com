/**
 * @author: Todd Allen
 *
 * Creation date: 01/03/2019
 */

package com.estes.myestes.invoiceinquiry.controller;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import com.estes.myestes.invoiceinquiry.dto.Payment;
import com.estes.myestes.invoiceinquiry.dto.PaymentLimits;
import com.estes.myestes.invoiceinquiry.dto.PaymentReason;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.PaymentService;
import com.estes.myestes.invoiceinquiry.utils.InvoiceInquiryConstants;

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
@Api(value="Estes customer payment services")
public class PaymentController
{
	@Autowired
	PaymentService payService;

	@ApiOperation(value = "Get customer invoice payment limits")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=PaymentLimits.class, message="Invoice payment limits"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/paymentLimits", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPaymentLimits(@ApiIgnore(value="Security token") Authentication auth)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			PaymentLimits limits = payService.getPaymentLimits();
			return new ResponseEntity<PaymentLimits>(limits, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse(InvoiceInquiryConstants.DEFAULT_ERROR_CODE,
					InvoiceInquiryConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getPaymentLimits

	@ApiOperation(value = "Get payment reason information")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, responseContainer="List", message="Payment reasons"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="System error information")
	})
	@RequestMapping(value = "/paymentReasons", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> getPaymentReasons(@ApiIgnore(value="Security token") Authentication auth)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<PaymentReason> reasons = payService.retrievePaymentReasons();
			return new ResponseEntity<List<PaymentReason>>(reasons, HttpStatus.OK);
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse(InvoiceInquiryConstants.DEFAULT_ERROR_CODE,
					InvoiceInquiryConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // getPaymentReasons

	@ApiOperation(value = "Finalize or cancel customer payment")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Redirect link to payment provider"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/pay/{action}/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> pay(
			@ApiIgnore(value="Security token") Authentication aut,
			@ApiParam(value="Action - C=cancel/F=finish") @PathVariable String action,
			@ApiParam(value="Payment ID") @PathVariable int id)
	{
		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) aut.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			String error = payService.processPayment(id, action);

			if (StringUtils.isEmpty(error)) {
				String message;
				if (action.equals(PaymentService.CANCEL_PAYMENT)) {
					message = "Payment cancelled.";
				}
				else {
					message = "Payment successfully processed.";
				}
				ServiceResponse resp = new ServiceResponse("", message);
				// TODO: Go to home screen; only the Angular UI knows the URL; this may not be needed
				resp.setRedirectUrl("Home - current app redirects to inv inq home page");
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.OK);
			}
			else {
				ServiceResponse err = new ServiceResponse(error, "");
				return new ResponseEntity<ServiceResponse>(err, HttpStatus.BAD_REQUEST);
			}
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse(InvoiceInquiryConstants.DEFAULT_ERROR_CODE,
					InvoiceInquiryConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // pay

	@ApiOperation(value = "Verify customer payment")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Payment details"),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business error information"),
			@ApiResponse(code=500, response=ServiceResponse.class, responseContainer="List", message="System error information")
	})
	@RequestMapping(value = "/verifyPayment", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> verifyPayment(
			@ApiIgnore(value="Security token") Authentication auth,
			@RequestBody List<Payment> req)
	{
		String user;
		String account;

		try {
			OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			Map<String, Object> details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			user = (String) details.get("username");
			account = (String) details.get("accountCode");
		} catch (Exception e) {
			ServiceResponse resp = new ServiceResponse();
			return new ResponseEntity<ServiceResponse>(resp, HttpStatus.UNAUTHORIZED);
		}

		try {
			List<ServiceResponse> msgs = payService.verify(user, account, req);
			if (!msgs.isEmpty() && !StringUtils.isEmpty(msgs.get(0).getErrorCode())) {
				return new ResponseEntity<List<ServiceResponse>>(msgs, HttpStatus.BAD_REQUEST);
			}
			else {
				return new ResponseEntity<ServiceResponse>(msgs.get(0), HttpStatus.OK);
			}
		}
		catch (InvoiceException e) {
			ServiceResponse error = new ServiceResponse(InvoiceInquiryConstants.DEFAULT_ERROR_CODE,
					InvoiceInquiryConstants.DEFAULT_ERROR_MSG);
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // verifyPayment
}
