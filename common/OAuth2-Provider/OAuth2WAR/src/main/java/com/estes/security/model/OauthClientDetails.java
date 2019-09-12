package com.estes.security.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;



public class OauthClientDetails implements RowMapper<OauthClientDetails> {
	
	private String clientId;
	
	private String grantTypes;
	
	private String clientType;
	
	private List<String> customFeilds = new ArrayList<String>();
	
	private String redirecturi;
	
	private Integer tokenExpiry;
	
	private Integer refreshTokenExpiry;
	
	private String clientSecret;
	
	private String authorities;

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getRedirecturi() {
		return redirecturi;
	}

	public void setRedirecturi(String redirecturi) {
		this.redirecturi = redirecturi;
	}

	public Integer getTokenExpiry() {
		return tokenExpiry;
	}

	public void setTokenExpiry(Integer tokenExpiry) {
		this.tokenExpiry = tokenExpiry;
	}

	public OauthClientDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
		// TODO Auto-generated method stub
		OauthClientDetails client = new OauthClientDetails();
		
		client.setClientId(rs.getString("appId"));
		client.setGrantTypes(rs.getString("grantTypes"));
		client.setRedirecturi(rs.getString("redirectUrl"));
		client.setTokenExpiry(rs.getInt("access_token_validity"));
		client.setClientSecret(rs.getString("appSecret"));
		client.setAuthorities(rs.getString("authorities"));
		client.setRefreshTokenExpiry(rs.getInt("refresh_token_validity"));
		
		return client;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(String grantTypes) {
		this.grantTypes = grantTypes;
	}

	public String getClientType() {
		return clientType;
	}

	public void setClientType(String clientType) {
		this.clientType = clientType;
	}

	public List<String> getCustomFeilds() {
		return customFeilds;
	}

	public void setCustomFeilds(List<String> customFeilds) {
		this.customFeilds = customFeilds;
	}

	public String getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String authorities) {
		this.authorities = authorities;
	}

	public Integer getRefreshTokenExpiry() {
		return refreshTokenExpiry;
	}

	public void setRefreshTokenExpiry(Integer refreshTokenExpiry) {
		this.refreshTokenExpiry = refreshTokenExpiry;
	}
	
	
	
}
