/**
 * @author: Pradeep K
 *
 */

package com.estes.myestes.pcraterdownload.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.pcraterdownload.dto.PCRaterDownloadDTO;
import com.estes.myestes.pcraterdownload.exception.PCRaterDownloadException;
import com.estes.myestes.pcraterdownload.service.iface.PCRaterDownloadService;
import com.estes.myestes.pcraterdownload.utils.PCRaterDownloadConstants;
import com.estes.myestes.pcraterdownload.utils.PhoneUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes customer PC Rater Download ReST Service.")
public class PCRaterDownloadController implements PCRaterDownloadConstants
{
	@Autowired
	PCRaterDownloadService pcRaterDownloadService;

	@ApiOperation(value = "Get rater download link for My Estes user")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=String.class,  message="PC Rater Download Link."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business Error"),
			@ApiResponse(code=500, response=ServiceResponse.class, message="Error information")
	})
	@RequestMapping(value = "/pcraterlink", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<?> getPCRaterDownloadLink(@RequestBody PCRaterDownloadDTO pcRaterDownloadDTO)
	{
		String resp = PCRaterDownloadConstants.EMPTY_STRING;
		
		String phoneNum = PhoneUtil.getAreaCode(pcRaterDownloadDTO.getCustomerPhone())+
		PhoneUtil.getExchange(pcRaterDownloadDTO.getCustomerPhone())+
		PhoneUtil.getLast4(pcRaterDownloadDTO.getCustomerPhone());
		
		String message = pcRaterDownloadDTO.getCompanyName() +" (Acct.Number - "+ pcRaterDownloadDTO.getAccountCode() + ")";
		
		pcRaterDownloadDTO.setCustomerPhone(phoneNum);
		
		try {
				ESTESLogger.log(ESTESLogger.INFO, PCRaterDownloadController.class, "getPCRaterDownloadLink()", "Invoking pcRaterDownloadService.pcRaterDownloadService() :");
				resp = pcRaterDownloadService.getPCRaterDownloadLink(pcRaterDownloadDTO);	
			}catch(PCRaterDownloadException ex) {
				List<ServiceResponse> errors = ex.getServiceResponseList();
				ESTESLogger.log(ESTESLogger.ERROR, PCRaterDownloadController.class, "getPCRaterDownloadLink()", "**Invalid Input. Error retrieving link.", ex);
				return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
			}

		Map<String, String> response = new HashMap<>();
		response.put("link", resp);
		response.put("message", message);
		// Return response object
		return new ResponseEntity<>(response, HttpStatus.OK);
	} // getPCRaterDownloadLink

	
}