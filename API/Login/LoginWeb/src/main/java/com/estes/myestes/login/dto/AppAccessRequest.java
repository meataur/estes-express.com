/**
 * @author: Todd Allen
 *
 * Creation date: 06/26/2018
 */

package com.estes.myestes.login.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * My Estes application access request
 */
@ApiModel(description="Request for access to My Estes application")
public class AppAccessRequest
{
	@ApiModelProperty(notes="Generated session ID")
	String session;
	@ApiModelProperty(notes="Generated hash")
	String hash;
	@ApiModelProperty(notes="Application name")
	String appName;
	@ApiModelProperty(notes="Other input")
	String other;

	/**
	 * Get the requested application name
	 * 
	 * @return Application name
	 */
	public String getAppName()
	{
		return this.appName;
	} // getAppName

	/**
	 * Get the hash value
	 * 
	 * @return Hash
	 */
	public String getHash()
	{
		return this.hash;
	} // getHash

	/**
	 * Get the other input
	 * 
	 * @return Other input
	 */
	public String getOther()
	{
		return this.other;
	} // getOther

	/**
	 * Get the session ID
	 * 
	 * @return Session ID
	 */
	public String getSession()
	{
		return this.session;
	} // getSession

	/**
	 * Set the application name
	 * 
	 * @param val Application name
	 */
	public void setAppName(String val)
	{
		this.appName = val.trim();
	} // setAppName

	/**
	 * Set the hash value
	 * 
	 * @param val Hash value
	 */
	public void setHash(String val)
	{
		this.hash = val.trim();
	} // setHash

	/**
	 * Set the other input
	 * 
	 * @param val Other input
	 */
	public void setOther(String val)
	{
		this.other = val;
	} // setOther

	/**
	 * Set the session ID
	 * 
	 * @param val Session
	 */
	public void setSession(String val)
	{
		this.session = val;
	} // setSession
}
