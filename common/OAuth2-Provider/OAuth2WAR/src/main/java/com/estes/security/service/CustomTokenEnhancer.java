package com.estes.security.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import com.estes.security.model.User;

//import com.estes.security.model.AdditionalInformation;



public class CustomTokenEnhancer implements TokenEnhancer {

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
		    User user = (User) authentication.getPrincipal();
		    if(user.getInfo() != null) { // if there is info to add to this token
		    	final Map<String, Object> additionalInfo = new HashMap<String, Object>();

		        additionalInfo.put("accountType", user.getInfo().getAccountType());
		        additionalInfo.put("accountCode", user.getInfo().getAccountCode());
		        additionalInfo.put("hash", user.getInfo().getHash());
		        additionalInfo.put("session", user.getInfo().getSession());
		        additionalInfo.put("authorities", user.getAuthorities());
		        additionalInfo.put("username", user.getName());
		        ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
		        
		        ((DefaultOAuth2AccessToken) accessToken).setScope(user.getInfo().getScopes());
		    }
	        
	        return accessToken;
	}

}