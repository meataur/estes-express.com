package com.estes.security.model;

public class AuthorizationResponse {
	private ServiceResponse error;
	private String[] blockedFields;
	
	
	public AuthorizationResponse() {
	}


	public AuthorizationResponse(ServiceResponse error, String[] blockedFields) {
		super();
		this.error = error;
		this.blockedFields = blockedFields;
	}


	/**
	 * @return the error
	 */
	public ServiceResponse getError() {
		return error;
	}


	/**
	 * @param error the error to set
	 */
	public void setError(ServiceResponse error) {
		this.error = error;
	}


	/**
	 * @return the blockedFields
	 */
	public String[] getBlockedFields() {
		return blockedFields;
	}


	/**
	 * @param blockedFields the blockedFields to set
	 */
	public void setBlockedFields(String[] blockedFields) {
		this.blockedFields = blockedFields;
	}
	
	
	
}
