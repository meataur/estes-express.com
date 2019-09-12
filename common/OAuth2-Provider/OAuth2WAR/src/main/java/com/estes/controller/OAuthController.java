package com.estes.controller;


import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.exception.CustomOauth2Exception;
import com.estes.security.model.AuthorizationResponse;
import com.estes.security.service.AuthenticationServiceFactory;
import com.estes.security.service.IAuthenticationService;



@RestController
public class OAuthController {
	 	
	@Autowired
	private TokenStore tokenStore;
	
    @RequestMapping(value = "/oauth/remove", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> removeToken(HttpServletRequest request, @RequestParam("token") String value) {
		OAuth2AccessToken token = tokenStore.readAccessToken(value);
		
		
		if (token == null) {
			throw new CustomOauth2Exception("Token was not recognised");
		}

		if (token.isExpired()) {
			throw new CustomOauth2Exception("Token has expired");
		}

		tokenStore.removeAccessToken(token);
		Map<String,Object> response = new HashMap<String,Object>();
		
		response.put("success", true);
		
		return new ResponseEntity<Object>(response, HttpStatus.OK);
	}
    
    
    
    @RequestMapping(value = "/oauth/blocked_fields", method = RequestMethod.GET)
   	@ResponseBody
   	public ResponseEntity<?> getBlockedFields(HttpServletRequest request, 
   			@RequestParam("token") String tokenValue,
   			@RequestParam("appName") String appName,
   			@RequestParam("client_id") String client) throws Exception{
    	
   		OAuth2AccessToken token = tokenStore.readAccessToken(tokenValue);
   		 
   		if (token == null) {
			throw new CustomOauth2Exception("Token was not recognised");
		}

		if (token.isExpired()) {
			throw new CustomOauth2Exception("Token has expired");
		}


		if(token.getScope().contains(appName)){
			
			String session = (String) token.getAdditionalInformation().get("session");
			String hash = (String) token.getAdditionalInformation().get("hash");
			
			IAuthenticationService authService = AuthenticationServiceFactory.getAuthenticationService(client);
			
			AuthorizationResponse authResponse = authService.authorize(session, hash, appName);
			if(authResponse.getError().getErrorCode().trim().isEmpty() == false)
				throw new CustomOauth2Exception(authResponse.getError().getMessage());
			
			Map<String,String[]> response = new HashMap<String,String[]>();
			response.put("blocked_fields", authResponse.getBlockedFields());
			
			return new ResponseEntity<Object>(response, HttpStatus.OK);
		}
		
		throw new CustomOauth2Exception("Invalid appName");

   	}

    
    
}
