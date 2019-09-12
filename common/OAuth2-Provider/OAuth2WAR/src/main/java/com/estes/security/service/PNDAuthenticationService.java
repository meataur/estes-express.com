package com.estes.security.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.estes.security.model.AuthorizationResponse;
import com.estes.security.model.User;

public class PNDAuthenticationService implements IAuthenticationService{

	@Override
	public User authenticate(String username, String password) {
		User user = new User();
		user.setName(username);
		user.setPassword(password);
		
		user.addAuthority(new SimpleGrantedAuthority("ROLE_USER"));
		return user;		
	}

	@Override
	public AuthorizationResponse authorize(String session, String hash, String appName) throws Exception {
		throw new Exception("PND does not have blocked fields");
	}
}
