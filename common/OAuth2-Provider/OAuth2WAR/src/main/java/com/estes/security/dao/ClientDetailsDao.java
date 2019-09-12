package com.estes.security.dao;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.security.model.OauthClientDetails;

@Repository
public class ClientDetailsDao {
	
	
private JdbcTemplate jdbcTemplate;

private final String CLIENT_DETAILS = "select * from APP_SECURITY.ClientDetails where appId = ?";
	
	public ClientDetailsDao(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	public ClientDetailsDao(){
		
	}

	public OauthClientDetails  getClientDetails(String clientId){
		try{
			OauthClientDetails client = (OauthClientDetails) jdbcTemplate.queryForObject(CLIENT_DETAILS, new Object[] {clientId},new OauthClientDetails());
			return client;
		}catch(Exception exp){
			exp.printStackTrace();
			return null;
		}
			
	}

}
