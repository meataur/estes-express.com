/**
 * @author: Todd Allen
 *
 * Creation date: 03/28/2019
 */

package com.estes.myestes.rating.dto;

import java.util.ArrayList;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rate quote booking request
 */
@ApiModel(description="Estes rate quote booking request")
public class BookingRequest
{
	public static final String BOL_TARGET = "BOL";
	public static final String PICKUP_TARGET = "PU";

	@ApiModelProperty(position=1, notes="Application type - LTL=less than truckload/TC=time critical/VTL=volume truckload")
	String app = "";
	@ApiModelProperty(notes="Quote ID")
	private String quoteId;
	@ApiModelProperty(notes="Quote reference number")
	private int quoteRefNum;
	@ApiModelProperty(notes="Service level ID")
	int level;
	@ApiModelProperty(notes="E-mail addresses to receive quote details")
	private  List<String> addresses;
	@ApiModelProperty(notes="Target - BOL=Bill of lading/PU=Pickup request")
	String target = "";

	/**
	 * Create new rate quote
	 */
	public BookingRequest()
	{
		this.addresses = new ArrayList<String>();
	} // Constructor

	/**
	 * Create rate quote
	 */
	public BookingRequest(String id)
	{
		this();
		this.quoteId = id;
	} // Constructor

	/**
	 * Get e-mail action to perform
	 * 
	 * @return Action
	 */
	public String getAction()
	{
		return target;
	} // getAction

	/**
	 * Get the e-mail addresses
	 * 
	 * @return E-mail addresses
	 */
	public List<String> getAddresses()
	{
		return this.addresses;
	} // getSearch

	/**
	 * Get the requesting application
	 * 
	 * @return Request application
	 */
	public String getApp()
	{
		return app;
	} // getApp

	/**
	 * Get the service level ID
	 * 
	 * @return ID
	 */
	public int getLevel()
	{
		return level;
	} // getLevel

	/**
	 * Get the quote ID
	 * 
	 * @return Quote ID
	 */
	public String getQuoteId()
	{
		return this.quoteId;
	} // getQuoteId

	/**
	 * Get the quote reference number
	 * 
	 * @return Quote ref#
	 */
	public int getQuoteRefNum()
	{
		return this.quoteRefNum;
	} // getQuoteRefNum

	public String getTarget()
	{
		return target;
	}

	/**
	 * Set e-mail action to perform
	 * 
	 * @param val Action
	 */
	public void setAction(String val)
	{
		this.quoteId = val;
	} // setAction

	/**
	 * Set the e-mail addresses to receive quote details
	 * 
	 * @param lst E-mail addresses
	 */
	public void setAddresses(List<String> lst)
	{
		this.addresses = lst;
	} // setAddresses

	/**
	 * Set the requesting application code
	 * 
	 * @return Requesting application
	 */
	public void setApp(String val)
	{
		this.app = val;
	} // setApp

	/**
	 * Set the service level ID
	 * 
	 * @param val ID
	 */
	public void setLevel(int val)
	{
		this.level = val;
	} // setLevel

	/**
	 * Set the quote ID
	 * 
	 * @param val Quote ID
	 */
	public void setQuoteId(String val)
	{
		this.quoteId = val;
	} // setQuoteId

	/**
	 * Set the quote reference number
	 * 
	 * @param val Quote ref#
	 */
	public void setQuoteRefNum(int val)
	{
		this.quoteRefNum = val;
	} // setQuoteRefNum

	public void setTarget(String target)
	{
		this.target = target;
	}
}
