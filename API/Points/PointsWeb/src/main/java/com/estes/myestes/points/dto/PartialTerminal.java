/**
 * @author: Todd Allen
 *
 * Creation date: 09/20/2018
 */

package com.estes.myestes.points.dto;

import com.estes.dto.common.ServiceResponse;

/**
 * Partial terminal information
 */
public class PartialTerminal
{
	ServiceResponse errorInfo;
	String id;
	String city;
	String state;
	String zip;

	/**
	 * Create new partial terminal
	 */
	public PartialTerminal()
	{
		this.errorInfo = new ServiceResponse();
	} // Constructor

	/**
	 * Get the city
	 * 
	 * @return City
	 */
	public String getCity()
	{
		return this.city;
	} // getCity

	/**
	 * Get the service response
	 * 
	 * @return Authentication {@link ServiceResponse}
	 */
	public ServiceResponse getErrorInfo()
	{
		return this.errorInfo;
	} // getErrorInfo

	/**
	 * Get the terminal ID
	 * 
	 * @return Terminal ID
	 */
	public String getId()
	{
		return this.id;
	} // getId

	/**
	 * Get the state
	 * 
	 * @return State
	 */
	public String getState()
	{
		return this.state;
	} // getState

	/**
	 * Get the postal/zip code
	 * 
	 * @return Zip code
	 */
	public String getZip()
	{
		return this.zip;
	} // getZip

	/**
	 * Set the city
	 * 
	 * @param val City
	 */
	public void setCity(String val)
	{
		this.city = val.trim();
	} // setCity

	/**
	 * Set the service response
	 * 
	 * @param val Service response
	 */
	public void setErrorInfo(ServiceResponse val)
	{
		this.errorInfo = val;
	} // setErrorInfo

	/**
	 * Set the terminal ID
	 * 
	 * @param val Terminal ID
	 */
	public void setId(String val)
	{
		this.id = val.trim();
	} // setId

	/**
	 * Set the state
	 * 
	 * @param val State
	 */
	public void setState(String val)
	{
		this.state = val;
	} // setState

	/**
	 * Set the postal/zip code
	 * 
	 * @param val Zip code
	 */
	public void setZip(String val)
	{
		this.zip = val;
	} // setZip
}
