package com.estes.myestes.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.estes.myestes.login.dto.Credentials;
import com.estes.myestes.login.service.iface.LoginService;
import com.fasterxml.jackson.databind.JsonNode;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
@RestController
@Api(value="My Estes login")
public class MyEstesLoginController {

	@Autowired
	private Environment env;

	
	@ApiOperation(value = "MyEstes Login")
	@ApiResponses(value = {
			@ApiResponse(code=200, message="Success message"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=401, message="Unauthorized"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	@PostMapping(value = "/login", produces = "application/json")
	public ResponseEntity<?> doLogin(@RequestBody Credentials req)
	{

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("oauth2.tokenUrl"))
				.queryParam("username", req.getUserName())
		        .queryParam("password", req.getPassword())
		        .queryParam("client_id", env.getProperty("oauth2.clientId"))
		        .queryParam("grant_type","password");
		
		String url = builder.toUriString();
	
		try{
			ResponseEntity<JsonNode> response = getRestTemplate().getForEntity(url, JsonNode.class);
			return response;
		}
		catch (HttpClientErrorException ex) {
			
			return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
		}
		
	}
	
	@ApiOperation(value = "MyEstes Logout Service")
	@ApiResponses(value = {
			@ApiResponse(code=200, message="Success message"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=401, message="Unauthorized"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	@GetMapping(value = "/logout", produces = "application/json")
	public ResponseEntity<?> doLogout(@RequestParam("token") String token)
	{
		

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("oauth2.tokenRemoveUrl"))
				.queryParam("token", token);
		
		String url = builder.toUriString();
		
		try {
			 ResponseEntity<JsonNode> response = getRestTemplate().getForEntity(url, JsonNode.class);
			return response;
		}
		catch (HttpClientErrorException ex) {
			
			return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
		}
	}
	
	@ApiOperation(value = "MyEstes Check Token Service")
	@ApiResponses(value = {
			@ApiResponse(code=200, message="Success message"),
			@ApiResponse(code=400, message="Bad Request"),
			@ApiResponse(code=401, message="Unauthorized"),
			@ApiResponse(code=403, message="Forbidden"),
			@ApiResponse(code=500, message="Internal Server Error")
	})
	@GetMapping(value = "/check_token", produces = "application/json")
	public ResponseEntity<?> checkToken(@RequestParam("token") String token)
	{
		

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("oauth2.checkTokenUrl"))
				.queryParam("token", token);
		
		String url = builder.toUriString();
		
		try {
			 ResponseEntity<JsonNode> response = getRestTemplate().getForEntity(url, JsonNode.class);
			return response;
		}
		catch (HttpClientErrorException ex) {
			
			return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
		}
	}
	
	

	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
		return restTemplate;
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 0;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}

}
