/**
 * @author: Todd Allen
 *
 * Creation date: 06/28/2018
 */

package com.estes.security.model;




/**
 * My Estes authentication response
 */
public class AuthenticationResponse
{
	/*
	 * Default authentication error code
	 */
	public static String DEFAULT_ERROR_CODE = "GEN0005";


	ServiceResponse error;

	String session;

	String hash;

	String accountCode;

	String accountType;

	String[] appNames;

	/**
	 * Constructor
	 */
	public AuthenticationResponse()
	{
		this.error = new ServiceResponse();
	}

	/**
	 * Constructor
	 * 
	 * @param err Error code
	 * @param msg Error message
	 */
	public AuthenticationResponse(String err, String msg)
	{
		this.error = new ServiceResponse(err, msg);
	} // Constructor

	/**
	 * Get the account code/number
	 * 
	 * @return Account Code
	 */
	public String getAccountCode()
	{
		return accountCode;
	} // getAccountCode

	/**
	 * Get the account type
	 * 
	 * @return Account type code
	 */
	public String getAccountType()
	{
		return this.accountType;
	} // getAccountType

	/**
	 * Get the allowed application names
	 * 
	 * @return Application names
	 */
	public String[] getAppNames()
	{
		return this.appNames;
	} // getAppNames

	/**
	 * Get the service response
	 * 
	 * @return Authentication {@link ServiceResponse}
	 */
	public ServiceResponse getError()
	{
		return this.error;
	} // getError

	/**
	 * Get the hash value
	 * 
	 * @return Generated hash
	 */
	public String getHash()
	{
		return this.hash;
	} // getHash

	/**
	 * Get the session ID
	 * 
	 * @return Session
	 */
	public String getSession()
	{
		return this.session;
	} // getSession

	/**
	 * Set the account code/number
	 * 
	 * @param accountCode
	 */
	public void setAccountCode(String accountCode)
	{
		this.accountCode = accountCode;
	} // setAccountCode

	/**
	 * Get the account type
	 * 
	 * @return Account type code
	 */
	public void setAccountType(String val)
	{
		this.accountType = val;
	} // setAccountType

	/**
	 * Set the allowed application names
	 * 
	 * @param val Application names
	 */
	public void setAppNames(String[] val)
	{
		this.appNames = val;
	} // setAppNames

	/**
	 * Set the service response
	 * 
	 * @param val Service response
	 */
	public void setError(ServiceResponse val)
	{
		this.error = val;
	} // setError

	/**
	 * Set the hash value
	 * 
	 * @param val Hash
	 */
	public void setHash(String val)
	{
		this.hash = null;
		if(! val.isEmpty()){
			this.hash = val;
		}
	} // setHash

	/**
	 * Set the session ID
	 * 
	 * @param val Session
	 */
	public void setSession(String val)
	{
		this.session = null;
		if(! val.isEmpty()){
			this.session = val;
		}
	} // setSession
}
