package com.estes.myestes.commoditylibrary.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

public class AuthenticationDetails  {
	
	
	private Map<String, Object> details = new HashMap<>();
	private OAuth2AuthenticationDetails oauthDetails;

	@SuppressWarnings("unchecked")
	public AuthenticationDetails(Authentication auth){
		oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
        details = (Map<String, Object>) oauthDetails.getDecodedDetails();
	}
	
	public String getUsername(){
		return ((String) details.get("username")).toUpperCase();
	}
	
	public String getAccountCode(){
		return (String) details.get("accountCode");
	}
	
	public String getAccountType(){
		return (String) details.get("accountType");
	}
	
	public String getHash(){
		return (String) details.get("hash");
	}
	
	public String getSession(){
		return (String) details.get("session");
	}
	
	public String getAccessToken(){
		return oauthDetails.getTokenValue();
	}
	
	public String getTokenType(){
		return oauthDetails.getTokenType();
	}
	public String getRemoteAddress(){
		return oauthDetails.getRemoteAddress();
	}
	public String getSessionId(){
		return oauthDetails.getSessionId();
	}
	public Integer getExpiresIn(){
		return (Integer) details.get("expires_in");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getScope(){
		return (List<String>) details.get("scope");
	}
	
	public boolean hasScope(String scope){
		return getScope().contains(scope);
	}
}
