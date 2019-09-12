package com.estes.security.service;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.estes.security.model.User;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String username = authentication.getName().toUpperCase();
		String password = (String) authentication.getCredentials();
		String client   = getClientId();
		

		IAuthenticationService authService = AuthenticationServiceFactory.getAuthenticationService(client);
		
		User user =  authService.authenticate(username, password);
		
		
		if(user == null) {
			throw new BadCredentialsException("Client Not Found.");
		}

		/**
		 * authorities should be mapped to account type
		 */
		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

		return new UsernamePasswordAuthenticationToken(user, password, authorities);

	}
	


	public boolean supports(Class<?> authentication) {
		return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	
	private String getClientId() {
		final HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

		String client = request.getParameter("client_id");
		if(client==null){
			new Exception("client_id is required!");
		}
	    return client;
	}
}
