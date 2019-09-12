package com.estes.myestes.BillOfLading.web.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestClientResponseException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.PickupDetailInfo;
import com.estes.myestes.BillOfLading.web.dto.Role;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;
import com.estes.myestes.BillOfLading.web.service.iface.PickupRequestRestService;

@Service("pickupRequestService")
public class PickupRequestRestServiceImpl implements  PickupRequestRestService{
	
	@Autowired
	private Environment env;

	
	private String buildServiceUrl(BolType boltype,
			String bolIdOrBolNoOrTemplateName,
			String servicePath,
			boolean accepted,
			boolean completed
			){
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(builServiceUrl(boltype,bolIdOrBolNoOrTemplateName,servicePath))
		.queryParam("accepted", accepted)
        .queryParam("completed", completed)
        .queryParam("rejected", true);
		return builder.toUriString();
	}
	private String builServiceUrl(
			BolType boltype,
			String bolIdOrBolNoOrTemplateName,
			String servicePath
			){
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("pickupRequest.serviceUrl"));
		
		builder.path(servicePath);

		if(boltype.equals(BolType.HISTORY)){
			builder.queryParam("bolId", Integer.parseInt(bolIdOrBolNoOrTemplateName));
		}
		
		if(boltype.equals(BolType.DRAFT)){
			builder.queryParam("bolNo", bolIdOrBolNoOrTemplateName);
		}
		if(boltype.equals(BolType.TEMPLATE)){
			builder.queryParam("template", bolIdOrBolNoOrTemplateName);
		}

		builder.queryParam("type", boltype);
		return builder.toUriString();
	}
	
	
	private RestTemplate getRestTemplate() {
		RestTemplate restTemplate = new RestTemplate(getClientHttpRequestFactory());
		restTemplate.setErrorHandler(new DefaultResponseErrorHandler());
		return restTemplate;
	}
	
	private HttpHeaders getHeaders(String token){
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.set(HttpHeaders.AUTHORIZATION, "bearer "+token);
		return headers;
	}
	
	private ClientHttpRequestFactory getClientHttpRequestFactory() {
		int timeout = 0;
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
		clientHttpRequestFactory.setConnectTimeout(timeout);
		return clientHttpRequestFactory;
	}
	
	@Override
	public ResponseEntity<PickupRequest> savePickupRequest(String accessToken,PickupRequest pickupRequest){

		RestTemplate restTemplate =  getRestTemplate();
		HttpHeaders headers       = getHeaders(accessToken);
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(env.getProperty("pickupRequest.serviceUrl"))
				.path("/pickup");
		String url = builder.toUriString();
		HttpEntity<PickupRequest> request = new HttpEntity<>( pickupRequest, headers);
		return restTemplate.exchange(url, HttpMethod.POST, request , PickupRequest.class);
	}
	
	
	@Override
	public PickupDetailInfo getPickupInformation(
			String accessToken,
			BolType boltype,
			String param			
			){
		
		RestTemplate restTemplate =  getRestTemplate();
		HttpHeaders headers       = getHeaders(accessToken);
		String url = builServiceUrl(boltype, param, "/bol/pickup");
		
		HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
		
		try{
			ResponseEntity<PickupDetailInfo> response =  restTemplate.exchange(url,HttpMethod.GET, request , PickupDetailInfo.class);
			return response.getBody();
		}catch(RestClientResponseException ex){
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "getPickupInformation() ",ex.getMessage()+" when called  "+ url+"  "+ex.getResponseBodyAsString());
			return new PickupDetailInfo();
		}
	}
	
	@Override
	public void savePickupInformation(
			PickupDetailInfo pickupDetailInfo, 
			String accessToken,
			BolType boltype,
			String param
			){
		
		RestTemplate restTemplate =  getRestTemplate();
		HttpHeaders headers       = getHeaders(accessToken);
		String url = builServiceUrl(boltype, param, "/bol/pickup");

		HttpEntity<PickupDetailInfo> request = new HttpEntity<>( pickupDetailInfo, headers);
		
		try{
			restTemplate.exchange(url, HttpMethod.POST, request , ServiceResponse.class);
		}catch(RestClientResponseException ex){
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "savePickupInformation()",ex.getMessage()+" "+ex.getResponseBodyAsString());
		}
	}
	
	@Override
	public void deletePickupInformation(String accessToken,
			BolType boltype,
			String param){
		
		RestTemplate restTemplate =  getRestTemplate();
		HttpHeaders headers       = getHeaders(accessToken);
		String url = builServiceUrl(boltype, param, "/bol/pickup");
		HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);

		try{
			restTemplate.exchange(url,HttpMethod.DELETE, request , ServiceResponse.class);
		}catch(RestClientResponseException ex){
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "deletePickupInformation()",ex.getMessage()+" "+ex.getResponseBodyAsString());
		}
		
	}
	
	@Override
	public List<Role> getNotification(String accessToken,
			BolType boltype,
			String param){
		
		RestTemplate restTemplate =  getRestTemplate();
		HttpHeaders headers       = getHeaders(accessToken);
		String url  = builServiceUrl(boltype, param, "/bol/pickup/notification");
		HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(headers);
	
		try{
			ResponseEntity<List<Role>> response =  restTemplate.exchange(url,HttpMethod.GET, request , new ParameterizedTypeReference<List<Role>>(){});
			return response.getBody();
		}catch(RestClientResponseException ex){
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "getNotification()",ex.getMessage()+" "+ex.getResponseBodyAsString());
			return new ArrayList<>();
		}
	}
	
	@Override
	public void saveNotification(
			List<Role> roles,
			boolean accepted,
			boolean completed,
			String accessToken,
			BolType boltype,
			String param){
		
		RestTemplate restTemplate =  getRestTemplate();
		HttpHeaders headers       = getHeaders(accessToken);
		String url = buildServiceUrl(boltype, param, "/bol/pickup/notification", accepted, completed);
		
		HttpEntity<List<Role>> request = new HttpEntity<>(roles, headers);
		try{
			restTemplate.exchange(url,HttpMethod.POST, request , ServiceResponse.class);
		}catch(RestClientResponseException ex){
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "saveNotification()",ex.getMessage()+" "+ex.getResponseBodyAsString());
		}
	}
	
}
