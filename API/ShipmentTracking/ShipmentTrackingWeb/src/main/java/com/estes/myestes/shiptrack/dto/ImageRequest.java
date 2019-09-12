/**
 * @author: Todd Allen
 *
 * Creation date: 07/20/2018
 */

package com.estes.myestes.shiptrack.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment tracking images request
 */
@ApiModel(description="Request for scanned image information asscoaited with a PRO")
public class ImageRequest
{
	@ApiModelProperty(notes="My Estes user profile name - leave blank")
	String user;
	@ApiModelProperty(notes="Session ID - leave blank")
	String session;
	@ApiModelProperty(notes="PRO associated with image")
	String pro;
	@ApiModelProperty(notes="Is user party to shipment?")
	boolean party;
	@ApiModelProperty(notes="Is user the shipment payor?")
	boolean payor;
	@ApiModelProperty(notes="Bill of lading image request number for retry")
	String bolRequestNum;
	@ApiModelProperty(notes="Delivery receipt image request number for retry")
	String drRequestNum;
	@ApiModelProperty(notes="Weight & Research image request number for retry")
	String wrRequestNum;

	public ImageRequest()
	{
	} // constructor

	/**
	 * Get the BOL request number
	 * 
	 * @return BOL request number
	 */
	public String getBolRequestNum()
	{
		return this.bolRequestNum;
	} // getBolRequestNum

	/**
	 * Get the DR request number
	 * 
	 * @return DR request number
	 */
	public String getDrRequestNum()
	{
		return this.drRequestNum;
	} // getDrRequestNum

	/**
	 * Get the PRO
	 * 
	 * @return PRO
	 */
	public String getPro()
	{
		return this.pro;
	} // getPro

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
	 * Get the user name
	 * 
	 * @return My Estes user name
	 */
	public String getUser()
	{
		return this.user;
	} // getUser

	/**
	 * Get the WR request number
	 * 
	 * @return WR request number
	 */
	public String getWrRequestNum()
	{
		return this.wrRequestNum;
	} // getWrRequestNum

	/**
	 * Is user party to shipment?
	 * 
	 * @return Indicator of party to shipment
	 */
	public boolean isParty()
	{
		return this.party;
	} // isParty

	/**
	 * Is user the payor?
	 * 
	 * @return Indicator of payor
	 */
	public boolean isPayor()
	{
		return this.payor;
	} // isPayor

	/**
	 * Is image request ID empty?
	 * 
	 * @param reqNum Request ID
	 * @return Indicator of request ID empty
	 */
	public static boolean isRequestNumEmpty(String reqNum)
	{
		return reqNum == null || reqNum.isEmpty();
	} // isRequestNumEmpty

	/**
	 * Set the BOL request number
	 * 
	 * @param val BOL request number
	 */
	public void setBolRequestNum(String val)
	{
		this.bolRequestNum = val;
	} // setBolRequestNum

	/**
	 * Set the DR request number
	 * 
	 * @param val DR request number
	 */
	public void setDrRequestNum(String val)
	{
		this.drRequestNum = val;
	} // setDrRequestNum

	/**
	 * Set the party boolean indicator
	 * 
	 * @param val Party to shipment boolean
	 */
	public void setParty(boolean val)
	{
		this.party = val;
	} // setParty

	/**
	 * Set the payor boolean indicator
	 * 
	 * @param val Payor boolean
	 */
	public void setPayor(boolean val)
	{
		this.payor = val;
	} // setPayor

	/**
	 * Set the PRO
	 * 
	 * @param val PRO
	 */
	public void setPro(String val)
	{
		this.pro = val;
	} // setPro

	/**
	 * Set the session ID
	 * 
	 * @param va Session ID
	 */
	public void setSession(String val)
	{
		this.session = val.trim();
	} // setSession

	/**
	 * Set the user name
	 * 
	 * @param val My Estes user name
	 */
	public void setUser(String val)
	{
		this.user = val;
	} // setUser

	/**
	 * Set the WR request number
	 * 
	 * @param val WR request number
	 */
	public void setWrRequestNum(String val)
	{
		this.wrRequestNum = val;
	} // setWrRequestNum
}
