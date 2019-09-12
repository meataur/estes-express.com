package com.estes.security.service;

import com.estes.security.model.AuthorizationResponse;
import com.estes.security.model.User;

public class CentralDispatchAuthenticationService implements IAuthenticationService{

	@Override
	public User authenticate(String username, String password) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		return user;		
	}

	@Override
	public AuthorizationResponse authorize(String session, String hash, String appName) throws Exception {
		throw new Exception("Central Dispatch does not have blocked fields");
	}
}
