package com.estes.security.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.NoSuchClientException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Service;

import com.estes.security.dao.ClientDetailsDao;
import com.estes.security.model.OauthClientDetails;

@Service
public class ClientDetailsServiceImpl implements ClientDetailsService {

	private  ClientDetailsDao clientDetailsdao;
	
	public ClientDetailsDao getClientDetailsdao() {
		return clientDetailsdao;
	}

	public void setClientDetailsdao(ClientDetailsDao clientDetailsdao) {
		this.clientDetailsdao = clientDetailsdao;
	}

	/**
	 * 
	 */
	public ClientDetails loadClientByClientId(String clientId) throws OAuth2Exception {

		OauthClientDetails client =  clientDetailsdao.getClientDetails(clientId);
		if(null!=client){
		BaseClientDetails clientDetailsObj = new BaseClientDetails();
		clientDetailsObj.setClientId(client.getClientId());
		clientDetailsObj.setClientSecret(client.getClientSecret());
		
		clientDetailsObj.setAccessTokenValiditySeconds(client.getTokenExpiry());
		List<String> scope = new ArrayList<String>();
		scope.add("SCOPE_READ");
		scope.add("SCOPE_WRITE");
		scope.add("SCOPE_TRUST");
		
		clientDetailsObj.setScope(scope);
		Set<String> registredURI = new HashSet<String>();
		registredURI.add(client.getRedirecturi());
		clientDetailsObj.setRegisteredRedirectUri(registredURI);
		List<String> authorizedGrantTypes = new ArrayList<String>(Arrays.asList(client.getGrantTypes().split(",")));
		//authorizedGrantTypes.add("refresh_token");
		clientDetailsObj.setAuthorizedGrantTypes(authorizedGrantTypes);
		clientDetailsObj.addAdditionalInformation("clientType", client.getClientType());
		return clientDetailsObj;
		}else{
			System.err.println("no Client with client id------------>"+clientId);
			throw new NoSuchClientException("No client with requested id: "	+ clientId);
		}
	}

}
