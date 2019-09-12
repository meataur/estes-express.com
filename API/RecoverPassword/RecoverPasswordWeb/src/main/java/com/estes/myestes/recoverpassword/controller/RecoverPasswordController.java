/**
 * @author: Lakshman Kandaswamy
 *
 * Creation date: 10/10/2018
 */

package com.estes.myestes.recoverpassword.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.recoverpassword.dto.RecoverPassword;
import com.estes.myestes.recoverpassword.dto.UserNamePassword;
import com.estes.myestes.recoverpassword.exception.RecoverPasswordException;
import com.estes.myestes.recoverpassword.service.iface.RecoverPasswordService;
import com.estes.myestes.recoverpassword.utils.RecoverPasswordConstants;
import com.estes.myestes.recoverpassword.utils.RecoverPasswordUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for ReST service interfaces
 */
@RestController
@Api(value="Estes recover password")
public class RecoverPasswordController implements RecoverPasswordConstants
{
	@Autowired
	RecoverPasswordService recoverPasswordService;

	@ApiOperation(value = "Recover password with the username/email")
	@ApiResponses(value = {
			@ApiResponse(code=200, response=ServiceResponse.class, message="Your username and password will be sent to {UserEmailAddress}."),
			@ApiResponse(code=400, response=ServiceResponse.class, message="Business/System Errors."),
			@ApiResponse(code=500, response=ServiceResponse.class, message="An unexpected error occurred.")
	})
	@RequestMapping(value = "/email", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<ServiceResponse> emailUserNamePassword(@RequestBody RecoverPassword recoverPwd)
	{
		ServiceResponse resp = null;
		UserNamePassword userNamePwd = null;
		String selectionType =  recoverPwd.getSelectionType();
		String searchCriteria =  recoverPwd.getSearchCriteria().trim().toUpperCase();
		boolean success = false;
		String emailTo =null;
		try {
			if(searchCriteria == null || searchCriteria == "") {
				resp = new ServiceResponse("error", INVALID_CRITERIA_ERROR);
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
			}
			if(selectionType == null || selectionType == "") {
				selectionType = "userName";
			}
			if(selectionType.equals("email") && !RecoverPasswordUtils.validEmail(searchCriteria)) {
				resp = new ServiceResponse("error", INVALID_EMAIL_ERROR);
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);	
			}
			userNamePwd = recoverPasswordService.getUserNamePassword(selectionType, searchCriteria);
			if(userNamePwd != null) {
				String emailId = ESTESConfigUtil.getProperty(RecoverPasswordConstants.EMAIL_ID, RecoverPasswordConstants.MAIL_TO);
				if(emailId != null && emailId != "") {
					emailTo = emailId;
				} else {
					emailTo = userNamePwd.getEmail();
				}
				if(RecoverPasswordUtils.validEmail(emailTo)) {
					success = recoverPasswordService.sendEmail(userNamePwd.getUserName(), userNamePwd.getPassword(), emailTo);
				} else {
					resp = new ServiceResponse("error", EMAIL_ON_FILE_ERROR);
					return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
				}
			}
			else {
				resp = new ServiceResponse("error", NO_USER_PROFILE_ERROR);
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.BAD_REQUEST);
			}
			if (success) {
				// Return successful response
				String successMsg = SUCCESS_MESSAGE.replace("userEmailAddress", emailTo.trim());
				return new ResponseEntity<ServiceResponse>(new ServiceResponse("", successMsg), HttpStatus.OK);
			}
			else {
				resp = new ServiceResponse("error", EMAIL_SEND_ERROR);
				return new ResponseEntity<ServiceResponse>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
			}	
		}
		catch (RecoverPasswordException ex) {
			ServiceResponse error = new ServiceResponse("error", "An unexpected error occurred.");
			return new ResponseEntity<ServiceResponse>(error, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	} // email
}
