package com.estes.myestes.requestinfo.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationDetails{
	
	private Map<String, Object> details = new HashMap<>();

	@SuppressWarnings("unchecked")
	public void setAuthentication(Authentication auth){
		OAuth2AuthenticationDetails oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
        details = (Map<String, Object>) oauthDetails.getDecodedDetails();
	}
	
	public String getUsername(){
		return (String) details.get("username");
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
		return (String) details.get("access_token");
	}
	
	public String getTokenType(){
		return (String) details.get("token_type");
	}
	
	public String getExpiresIn(){
		return (String) details.get("expires_in");
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getScope(){
		return (List<String>) details.get("expires_in");
	}
	
	public boolean hasScope(String scope){
		return getScope().contains(scope);
	}
}
