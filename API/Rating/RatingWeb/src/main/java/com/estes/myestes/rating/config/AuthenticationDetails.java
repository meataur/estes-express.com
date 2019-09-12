package com.estes.myestes.rating.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationDetails;

import com.estes.framework.logger.ESTESLogger;

/*
 * To retrieve user authentication authorized information
 * @author - Ataur Rahman
 */
public class AuthenticationDetails  {
	
	private Map<String, Object> details = new HashMap<>();
	private OAuth2AuthenticationDetails oauthDetails;

	@SuppressWarnings("unchecked")
	public AuthenticationDetails(){
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		try {
			oauthDetails = (OAuth2AuthenticationDetails) auth.getDetails();
			
			if(oauthDetails!=null){
				
				details = (Map<String, Object>) oauthDetails.getDecodedDetails();
			
			}
		}catch(Exception ex){
			ESTESLogger.log(ESTESLogger.INFO, getClass(),"AuthenticationDetails()", ex.getMessage());
			ESTESLogger.log(ESTESLogger.INFO,getClass(),"AuthenticationDetails() constructor","User is not authenticated: Authentication object is "+auth);
		}
	}
	

	public static AuthenticationDetails getAuthenticationDetails(){
		return new AuthenticationDetails();
	}
	
	/**
	 * Check if the user is authenticated or not
	 * @return boolean
	 */
	
	public boolean isAuthenticated(){
		return oauthDetails!=null;
	}
	
	/**
	 * Check if the user is authorized to any app
	 *  * @return boolean
	 */
	public boolean isAuthorized(String appName){
		return hasScope(appName);
	}
	
	/**
	 * Get username
	 * @return String
	 */
	
	public String getUsername(){
		
		return isAuthenticated()? ((String) details.get("username")).toUpperCase() : null;
	}
	
	/**
	 * Get Account Code
	 * @return String
	 */
	public String getAccountCode(){
		return isAuthenticated()? (String) details.get("accountCode") : null ;
	}
	
	/**
	 * Get Account Type
	 * @return String
	 */
	public String getAccountType(){
		return isAuthenticated()? (String) details.get("accountType") : null;
	}
	
	/**
	 * Get Role. Account Type is mapped as Role in Oauth2 Implementation. So Account Type and Role are equal.
	 * @return String
	 */
	public String getRole(){
		return getAccountType();
	}
	
	/**
	 * Get Hash Value from token
	 * @return
	 */
	public String getHash(){
		return isAuthenticated()? (String) details.get("hash") : null;
	}
	/**
	 * Get Session Value from token
	 * @return
	 */
	public String getSession(){
		return isAuthenticated()? (String) details.get("session") : null;
	}
	
	/**
	 * Session and Random String are same
	 * @return
	 */
	public String getRandom(){
		return getSession();
	}
	/**
	 * Get AccessToken from token
	 * @return
	 */
	public String getAccessToken(){
		return isAuthenticated()? oauthDetails.getTokenValue() : null;
	}
	
	/**
	 * Get Token type
	 * @return
	 */
	public String getTokenType(){
		return isAuthenticated()? oauthDetails.getTokenType() : null;
	}
	/**
	 * Get User Ip Address
	 * @return
	 */
	public String getRemoteAddress(){
		return isAuthenticated()? oauthDetails.getRemoteAddress() : null;
	}
	
	/**
	 * Get token validity
	 * @return
	 */
	
	public Integer getExpiresIn(){
		return isAuthenticated()? (Integer) details.get("expires_in") : 0;
	}
	
	/**
	 * Get the list of Applications User is authorized to
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<String> getScope(){
		return isAuthenticated()? (List<String>) details.get("scope") : null;
	}
	/**
	 * Check the user is authorized to specific app.
	 * @param scope
	 * @return
	 */
	public boolean hasScope(String scope){
		return isAuthenticated()? getScope().contains(scope) : false;
	}
}