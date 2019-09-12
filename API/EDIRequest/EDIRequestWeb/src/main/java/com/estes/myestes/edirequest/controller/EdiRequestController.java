/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.edirequest.controller;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.edirequest.dto.BillingHeaderType;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.estes.myestes.edirequest.email.EdiRequestMail;
import com.estes.myestes.edirequest.exception.EdiRequestException;
import com.estes.myestes.edirequest.pdf.HTML2PDF;
import com.estes.myestes.edirequest.service.iface.EdiRequestService;
import com.estes.myestes.edirequest.utils.EdiRequestConstant;
import com.estes.myestes.edirequest.utils.EdiRequestUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes EDI form transmission request.")
public class EdiRequestController implements EdiRequestConstant
{
	@Autowired
	EdiRequestService ediRequestService;

	@ApiOperation(value = "Get all billing types for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=BillingHeaderType.class, responseContainer="List", message="List of BillingType."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/billTypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<BillingHeaderType>> getBillingTypes()
	{
		String user;
		List<BillingHeaderType> resp = null;
		try {
			resp = ediRequestService.getEdiBillingTypes();
		}
		catch (EdiRequestException ex) {
			resp = new ArrayList<BillingHeaderType>();
			return new ResponseEntity<List<BillingHeaderType>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<List<BillingHeaderType>>(resp, HttpStatus.OK);
	} // getBillingTypes
	
	@ApiOperation(value = "Get all header types for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=BillingHeaderType.class, responseContainer="List", message="List of header types."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/headerTypes", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<List<BillingHeaderType>> getHeaderTypes()
	{
		String user;
		List<BillingHeaderType> resp = null;
		try {
			resp = ediRequestService.getEdiHeaderTypes();
		}
		catch (EdiRequestException ex) {
			resp = new ArrayList<BillingHeaderType>();
			return new ResponseEntity<List<BillingHeaderType>>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		// Return response object
		return new ResponseEntity<List<BillingHeaderType>>(resp, HttpStatus.OK);
	} // getHeaderTypes
	
	@ApiOperation(value = "Submit a EDI Form Transmission request for MyEstes user", response = ServiceResponse.class)
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Succes information."),
			@ApiResponse(code=400, response=ServiceResponse.class, responseContainer="List", message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> saveEdiFormSubmitRequest(@RequestBody EdiRequest ediRequest)
	{	
		List<ServiceResponse> respList = ediRequestService.validateRequest(ediRequest);	
		if(respList.size()>0) {
			return new ResponseEntity<List<ServiceResponse>>(respList, HttpStatus.BAD_REQUEST);
		}				
		String ediNewRequestNumber = null;
		boolean success = false;
		try {
			ediNewRequestNumber = ediRequestService.saveEdiFormTransmissionRequest(ediRequest);
			
			if(ediNewRequestNumber!=null){
				ediRequest.setReferenceNumber(ediNewRequestNumber);
				ByteArrayOutputStream pdfAttachmentFile = new HTML2PDF().getAttachmentFileWithHeader(ediRequest, ediNewRequestNumber);			
				EdiRequestMail email = new EdiRequestMail();
				String sendCopyToRequestorsEmail = ediRequest.getSendCopyInMail();
				if(pdfAttachmentFile!=null){						
					if(sendCopyToRequestorsEmail.equalsIgnoreCase("Y")){				
						success = email.sendEmail(ediRequest.getFillersEmail(),pdfAttachmentFile,ediNewRequestNumber);					
					}else{
						success = email.sendEmail(pdfAttachmentFile,ediNewRequestNumber);
					}									
				}else{
					ESTESLogger.log(ESTESLogger.ERROR, EdiRequestService.class, "saveEdiFormSubmitRequest()", "Pdf Attachment file could not be generated.");
				}
			}else{
				ESTESLogger.log(ESTESLogger.ERROR, EdiRequestService.class, "saveEdiFormSubmitRequest()", "Customer Could not be saved.");
			}
			
			if(success) {
				// Return successful response	
				ServiceResponse response = new ServiceResponse("","Your EDI request has been successfully submitted.  An account representative will be in touch with you soon.  Your request reference number is: "+ediNewRequestNumber);
				return new ResponseEntity<ServiceResponse>(response, HttpStatus.OK);
			} else {
				ServiceResponse response = new ServiceResponse("","Your EDI request could not be submitted. Please try later.");
				return new ResponseEntity<ServiceResponse>(response, HttpStatus.BAD_REQUEST);
			}
		}
		catch (EdiRequestException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // saveEdiFormSubmitRequest
	
}