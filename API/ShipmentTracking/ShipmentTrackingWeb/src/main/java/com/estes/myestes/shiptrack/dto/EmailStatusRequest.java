/**
 * @author: Todd Allen
 *
 * Creation date: 07/18/2018
 */

package com.estes.myestes.shiptrack.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment tracking e-mail status request
 */
@ApiModel(description="Request for e-mail status updates on a PRO")
public class EmailStatusRequest
{
	@ApiModelProperty(notes="Session ID - leave blank")
	String session;
	@ApiModelProperty(notes="PROs on which to receive e-mail updates")
	String[] pros;
	@ApiModelProperty(notes="E-mail addresses to receive updates - separated by carriage return")
	String addresses;

	public EmailStatusRequest()
	{
	} // constructor

	/**
	 * Get the e-mail addresses to receive updates
	 * 
	 * @return E-mail addresses
	 */
	public String getAddresses()
	{
		return this.addresses;
	} // getSearch

	/**
	 * Get the array of PROs
	 * 
	 * @return Selected PROs
	 */
	public String[] getPros()
	{
		return this.pros;
	} // getPros

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
	 * Set the e-mail addresses to receive updates
	 * 
	 * @param val E-mail addresses
	 */
	public void setAddresses(String val)
	{
		this.addresses = val;
	} // setAddresses

	/**
	 * Set the array of PROs
	 * 
	 * @param val Selected PROs
	 */
	public void setPros(String[] val)
	{
		this.pros = val;
	} // setPros

	/**
	 * Set the session ID
	 * 
	 * @param val Session ID
	 */
	public void setSession(String val)
	{
		this.session = val.trim();
	} // setSession
}
