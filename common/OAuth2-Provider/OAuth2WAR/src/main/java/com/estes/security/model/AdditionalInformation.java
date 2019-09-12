package com.estes.security.model;

import java.io.Serializable;
import java.util.Set;

public class AdditionalInformation implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9203671397992306644L;
	
	private String accountCode;
	private String accountType;
	private String session;
	private String hash;
	
	private Set<String> scopes;
	
	public String getAccountCode() {
		return accountCode;
	}

	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	
	
	
	/**
	 * @return the accountType
	 */
	public String getAccountType() {
		return accountType;
	}

	/**
	 * @param accountType the accountType to set
	 */
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	/**
	 * @return the session
	 */
	public String getSession() {
		return session;
	}

	/**
	 * @param session the session to set
	 */
	public void setSession(String session) {
		this.session = session;
	}

	/**
	 * @return the hash
	 */
	public String getHash() {
		return hash;
	}

	/**
	 * @param hash the hash to set
	 */
	public void setHash(String hash) {
		this.hash = hash;
	}

	public Set<String> getScopes() {
		return scopes;
	}

	public void setScopes(Set<String> scopes) {
		this.scopes = scopes;
	}

	

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}