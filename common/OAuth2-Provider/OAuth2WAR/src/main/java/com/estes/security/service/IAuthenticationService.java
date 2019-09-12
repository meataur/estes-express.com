package com.estes.security.service;

import com.estes.security.model.AuthorizationResponse;
import com.estes.security.model.User;

public interface IAuthenticationService {
	
	public User authenticate(String username, String password);
	
	public AuthorizationResponse authorize(String session,String hash, String appName) throws Exception; 
	
}
