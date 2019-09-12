/**
 * @author: Todd Allen
 *
 * Creation date: 06/29/2018
 */

package com.estes.myestes.login.dto;

import com.estes.dto.common.ServiceResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * My Estes app authorization response
 */
@ApiModel(description="My Estes authorization response")
public class AuthorizationResponse
{
	/*
	 * Default authorization error code
	 */
	public static String DEFAULT_ERROR_CODE = "GEN0005";

	@ApiModelProperty(notes="Error code/message")
	ServiceResponse error;
	@ApiModelProperty(notes="Blocked field names")
	String[] blockedFields;

	/**
	 * Constructor
	 */
	public AuthorizationResponse()
	{
		this.error = new ServiceResponse();
	}

	/**
	 * Constructor
	 * 
	 * @param err Error code
	 * @param msg Error message
	 */
	public AuthorizationResponse(String err, String msg)
	{
		this.error = new ServiceResponse(err, msg);
	} // Constructor

	/**
	 * Get the blocked field names
	 * 
	 * @return Blocked field names
	 */
	public String[] getBlockedFields()
	{
		return this.blockedFields;
	} // getBlockedFields

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
	 * Set the blocked field names
	 * 
	 * @param val Blocked field names
	 */
	public void setBlockedFields(String[] val)
	{
		this.blockedFields = val;
	} // setBlockedFields

	/**
	 * Set the service response
	 * 
	 * @param val Service response
	 */
	public void setError(ServiceResponse val)
	{
		this.error = val;
	} // setError
}
