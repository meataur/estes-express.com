package com.estes.myestes.profile.listener;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.profile.event.UsernameChangeEvent;
import com.estes.myestes.profile.exception.AppException;

@Component
public class UsernameChangeListener implements ApplicationListener<UsernameChangeEvent>{
	/**
	 * Username change event listener.
	 * This will be used to logout the authenticated user
	 */
	@Autowired
	private Environment env;
	
	@Override
	public void onApplicationEvent(UsernameChangeEvent event) {
		final String token    = event.getInfo().get("token");
		final String username = event.getInfo().get("username");
		ESTESLogger.log(ESTESLogger.INFO,getClass(), "UsernameChangeEvent()", "Username for "+username+" has been changed!");
		
		final String tokenRemoveUrl      = env.getProperty("oauth2.tokenRemoveUrl");
		UriComponentsBuilder builder = UriComponentsBuilder
			    .fromUriString(tokenRemoveUrl)
			    .queryParam("token", token);
		/**
		 * 
		 * Logout/ Oauth Token Remove Service is called below.
		 * 
		 */
		try {
			RestTemplate restTemplate = new RestTemplate();
		    @SuppressWarnings("unchecked")
			Map<String, Object> result = restTemplate.getForObject(builder.toUriString(), Map.class);
		    ESTESLogger.log(ESTESLogger.INFO, getClass(), "User "+username+" has been logged out successfully after changing the username : ", result.get("success").toString());
		}catch(Exception ex){
			ex.printStackTrace();
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "Username has been changed for "+username+". But due to internal prtoblems, it was unsuccessful to enforce the user to logged out.","");
			throw new AppException(HttpStatus.EXPECTATION_FAILED, "Username has been changed successfully, but user needs to be logged out.");
		}
	    
	}

}
