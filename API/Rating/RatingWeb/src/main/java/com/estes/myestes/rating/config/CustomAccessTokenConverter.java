package com.estes.myestes.rating.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;

public class CustomAccessTokenConverter extends DefaultAccessTokenConverter
{
	@Override
	public OAuth2Authentication extractAuthentication(Map<String, ?> claims)
	{
		Map<String, Object> newClaims = new HashMap<>(claims);
		String username = (String) claims.get("username");
		newClaims.put("user_name", username);
		OAuth2Authentication authentication = super.extractAuthentication(newClaims);
		
		authentication.setDetails(newClaims);
		
		return authentication;
	}
	
}
