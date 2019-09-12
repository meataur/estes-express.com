/**
 * @author: Todd Allen
 *
 * Creation date: 03/30/2019
 */

package com.estes.myestes.rating.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.util.StringUtils;

import com.estes.myestes.rating.utils.RatingConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rate quote request
 */
@ApiModel(description="Time critical contact us request")
public class ContactRequest
{
	@ApiModelProperty(hidden=true, notes="My Estes user name")
	String user = "";
	@ApiModelProperty(notes="Source application type - LTL=Less than truckload/VTL=Volume truckload")
	String app = "";
	@ApiModelProperty(position=1, notes="Quote ID")
	private String quoteId;
	@ApiModelProperty(position=2, notes="Quote reference number")
	private int quoteRefNum;
	@ApiModelProperty(notes="Contact full name")
	String contactName = "";
	@ApiModelProperty(notes="Contact phone number (xxx) xxx-xxxx")
	String contactPhone = "";
	@ApiModelProperty(notes="Contact phone number extension")
	String contactPhoneExt = "";
	@ApiModelProperty(notes="Contact e-mail address")
	String contactEmail = "";
	@ApiModelProperty(notes="Comments")
	String comments = "";
	@ApiModelProperty(notes="Pickup date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	LocalDate pickupDate;
	@ApiModelProperty(notes="Pickup available time - hh:mm AM/PM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	String pickupAvail = "";
	@ApiModelProperty(notes="Pickup close time - hh:mm AM/PM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	String pickupClose = "";
	@ApiModelProperty(notes="Commodities - 1-99")
	List<CommodityPricing> commodities;
	@ApiModelProperty(notes="Is freight stackable?")
	boolean stackable;

	/**
	 * Create new rate quote request
	 */
	public ContactRequest()
	{
	} // Constructor

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
	 * Get comments
	 * 
	 * @return Comments
	 */
	public String getComments()
	{
		return this.comments;
	} // getComments

	/**
	 * Get list of commodities
	 * 
	 * @return Commodities
	 */
	public List<CommodityPricing> getCommodities()
	{
		return commodities;
	} // getCommodities

	/**
	 * Get contact e-mail address
	 * 
	 * @return Contact e-mail
	 */
	public String getContactEmail()
	{
		return this.contactEmail;
	} // getContactEmail

	/**
	 * Get contact name
	 * 
	 * @return Contact name
	 */
	public String getContactName()
	{
		return this.contactName;
	} // getContactName

	/**
	 * Get contact phone
	 * 
	 * @return Contact phone
	 */
	public String getContactPhone()
	{
		return this.contactPhone;
	} // getContactPhone

	/**
	 * Get contact phone extension
	 * 
	 * @return Contact phone ext
	 */
	public String getContactPhoneExt()
	{
		return this.contactPhoneExt;
	} // getContactPhoneExt

	/**
	 * Get pickup available time
	 * 
	 * @return Pickup available time
	 */
	public String getPickupAvail()
	{
		return !StringUtils.isEmpty(this.pickupAvail) ? this.pickupAvail : RatingConstants.PICKUP_START_TIME;
	} // getAvailableTime

	/**
	 * Get pickup close time
	 * 
	 * @return Pickup close time
	 */
	public String getPickupClose()
	{
		return !StringUtils.isEmpty(this.pickupClose) ? this.pickupClose : RatingConstants.PICKUP_CLOSE_TIME;
	} // getPickupClose

	/**
	 * Get pickup date
	 * 
	 * @return Pickup date
	 */
	public LocalDate getPickupDate()
	{
		return this.pickupDate;
	} // getPickupDate
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
	 * Get the stackable indicator
	 * 
	 * @return Stackable?
	 */
	public boolean isStackable()
	{
		return this.stackable;
	} // isStackable

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
	 * Set comments
	 * 
	 * @param val Comments
	 */
	public void setComments(String val)
	{
		this.comments = val;
	} // setComments

	/**
	 * Set list of commodities
	 * 
	 * @param comms Commodities
	 */
	public void setCommodities(List<CommodityPricing> comms)
	{
		this.commodities = comms;
	} // setCommodities

	/**
	 * Set contact e-mail address
	 * 
	 * @param val Contact e-mail
	 */
	public void setContactEmail(String val)
	{
		this.contactEmail = val;
	} // setContactEmail

	/**
	 * Set contact name
	 * 
	 * @param val Contact name
	 */
	public void setContactName(String val)
	{
		this.contactName = val;
	} // setContactName

	/**
	 * Set the contact phone
	 * 
	 * @param val Contact phone
	 */
	public void setContactPhone(String val)
	{
		this.contactPhone = val;
	} // setContactPhone

	/**
	 * Set the contact phone extension
	 * 
	 * @param val Contact phone ext
	 */
	public void setContactPhoneExt(String val)
	{
		this.contactPhoneExt = val;
	} // setContactPhoneExt

	/**
	 * Set pickup available time
	 * 
	 * @param val Pickup available time
	 */
	public void setPickupAvail(String val)
	{
		this.pickupAvail = val;
	} // setPickupAvail

	/**
	 * Set pickup close time
	 * 
	 * @param val Pickup close time
	 */
	public void setPickupClose(String val)
	{
		this.pickupClose = val;
	} // setPickupClose

	/**
	 * Set pickup date
	 * 
	 * @param val Pickup date
	 */
	public void setPickupDate(LocalDate val)
	{
		this.pickupDate = val;
	} // setPickupDate

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

	/**
	 * Set the stackable indicator
	 * 
	 * @param val Stackable
	 */
	public void setStackable(boolean val)
	{
		this.stackable = val;
	} // setStackable

	/**
	 * Set the My Estes user name
	 * 
	 * @param val User name
	 */
	public void setUser(String val)
	{
		this.user = val.trim();
	} // setUser
}
