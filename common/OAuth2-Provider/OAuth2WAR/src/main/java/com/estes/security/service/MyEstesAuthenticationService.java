package com.estes.security.service;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.client.RestTemplate;

import com.estes.exception.CustomOauth2Exception;
import com.estes.security.model.AdditionalInformation;
import com.estes.security.model.AuthenticationResponse;
import com.estes.security.model.AuthorizationResponse;
import com.estes.security.model.User;
import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class MyEstesAuthenticationService implements IAuthenticationService{

	private String authenticationUrl;

	private String authorizationUrl;
	
	public MyEstesAuthenticationService(){
		try{
			Context initial_ctx = new InitialContext();
			authenticationUrl = (String) initial_ctx.lookup("authentication/url");
			authorizationUrl = (String) initial_ctx.lookup("authorization/url");
			initial_ctx.close();
		}catch(NamingException ex){
			ex.printStackTrace();
		}
	}
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 5000;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
	
	
	@Override
	public User authenticate(String username, String password) {
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();
		//System.out.println(authenticationUrl+" "+authenticationUrl);
		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpHeaders headers       = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		JsonObject loginRequest = new JsonObject();
		loginRequest.addProperty("userName", username);
		loginRequest.addProperty("password", password);
		
		
		Gson gson = new Gson();
		String jsonRequestBody = gson.toJson(loginRequest);
  
		HttpEntity<String> request = new HttpEntity<String>(jsonRequestBody, headers);
		
		ResponseEntity<AuthenticationResponse> response;
		try {
			response = restTemplate.exchange(authenticationUrl, HttpMethod.POST, request, AuthenticationResponse.class);
		} catch(Exception e) {
			throw new BadCredentialsException("Invalid Credentials.");
		}
		
		if(response.getStatusCode().equals(HttpStatus.OK)){
			AuthenticationResponse authResponse = response.getBody();
			
			if( authResponse.getError().getErrorCode().trim().isEmpty()){
				
				User user = new User();
				user.setName(username);
				user.setPassword(password);

				user.addAuthority(new SimpleGrantedAuthority("ROLE_"+authResponse.getAccountType()));
				
				AdditionalInformation info = new AdditionalInformation();
				info.setAccountType(authResponse.getAccountType());
				info.setHash(authResponse.getHash());
				info.setSession(authResponse.getSession());
				info.setAccountCode(authResponse.getAccountCode());
				
				Set<String> scopes = new HashSet<String>(Arrays.asList(authResponse.getAppNames()));
				info.setScopes(scopes);
				
				user.setInfo(info);
				//System.out.println(user.getName());
				return user;
				
			}else{
				
				throw new BadCredentialsException(authResponse.getError().getMessage());
			
			}
			
		}
		
		throw new BadCredentialsException("Invalid Credentials.");
		
	}

	@Override
	public AuthorizationResponse authorize(String session, String hash, String appName) throws Exception {
		
		ClientHttpRequestFactory requestFactory = getClientHttpRequestFactory();

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		HttpHeaders headers       = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		
		JsonObject request = new JsonObject();
		request.addProperty("session", session);
		request.addProperty("hash", hash);
		request.addProperty("appName", appName);
		request.addProperty("other", "");
		
		
		Gson gson = new Gson();
		String jsonRequestBody = gson.toJson(request);
  
		HttpEntity<String> requestEntity = new HttpEntity<String>(jsonRequestBody, headers);

		ResponseEntity<AuthorizationResponse> response = restTemplate.exchange(authorizationUrl, HttpMethod.POST, requestEntity, AuthorizationResponse.class);
		
		if(response.getStatusCode().equals(HttpStatus.OK)){
			return response.getBody();
		}
		throw new CustomOauth2Exception("Something wrong with authorization Service");
	}
}
