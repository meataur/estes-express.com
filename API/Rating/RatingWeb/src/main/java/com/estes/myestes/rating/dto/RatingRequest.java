/**
 * @author: Todd Allen
 *
 * Creation date: 02/05/2019
 */

package com.estes.myestes.rating.dto;

import java.time.LocalDate;
import java.util.List;

import org.springframework.util.StringUtils;

import com.estes.myestes.rating.utils.RatingConstants;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Rate quote request
 */
@ApiModel(description="Rate quote request")
@Data
public class RatingRequest
{
	/**
	 * Base account code used for rating
	 */
	public final static String BASE_ACCOUNT = "0212603";
	/**
	 * Default role = shipper
	 */
	public final static String DEFAULT_ROLE = "S";
	/**
	 * Default terms = pre-paid
	 */
	public final static String DEFAULT_TERMS = "P";

	@ApiModelProperty(hidden=true, notes="My Estes user name")
	String user = "";
	@ApiModelProperty(hidden=true, notes="Session ID")
	String session = "";
	@ApiModelProperty(position=1, notes="List of application types - LTL=less than truckload/TC=time critical/VTL=volume truckload")
	List<String> apps;
	@ApiModelProperty(position=2, notes="Account code/number - blank for local accounts")
	String accountCode = "";
	@ApiModelProperty(position=3, notes="Contact full name")
	String contactName = "";
	@ApiModelProperty(position=4, notes="Contact phone number (xxx) xxx-xxxx")
	String contactPhone = "";
	@ApiModelProperty(position=5, notes="Contact phone number extension")
	String contactPhoneExt = "";
	@ApiModelProperty(position=6, notes="Contact e-mail address")
	String contactEmail = "";
	@ApiModelProperty(position=7, notes="Role/payor - S=Shipper/C=Consignee/T=Third party")
	String role = "";
	@ApiModelProperty(position=8, notes="Payment terms - PPD=Prepaid/COL=Collect")
	String terms = "";
	@ApiModelProperty(position=9, notes="Discount percentage")
	int discount;
	@ApiModelProperty(position=10, notes="Origin point")
	Point origin;
	@ApiModelProperty(position=11, notes="Destination point")
	Point dest;
	@ApiModelProperty(position=12, notes="Pickup date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	LocalDate pickupDate;
	@ApiModelProperty(position=13, notes="Pickup available time - hh:mm AM/PM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	String pickupAvail = "";
	@ApiModelProperty(position=14, notes="Pickup close time - hh:mm AM/PM")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	String pickupClose = "";
	@ApiModelProperty(position=15, notes="Commodities - 1-99")
	List<Commodity> commodities;
	@ApiModelProperty(position=16, notes="Declared value")
	double declaredValue;
	@ApiModelProperty(position=17, notes="Declared value waived?")
	boolean declaredValueWaived;
	@ApiModelProperty(position=18, notes="Food warehouse unique ID")
	int foodWarehouseId;
	@ApiModelProperty(position=19, notes="Is freight stackable?")
	boolean stackable;
	@ApiModelProperty(position=20, notes="Linear feet")
	int linearFeet;
	@ApiModelProperty(position=21, notes="Comments")
	String comments = "";
	@ApiModelProperty(position=22, notes="Accessorials - 0-99")
	List<Accessorial> accessorials;

	/**
	 * Create new rate quote request
	 */
	public RatingRequest()
	{
	} // Constructor

	/**
	 * Get list of accessorial info
	 * 
	 * @return Accessorials
	 */
	public List<Accessorial> getAccessorials()
	{
		return accessorials;
	} // getAccessorials

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
	 * Get the requesting application
	 * 
	 * @return Request application
	 */
	public List<String> getApps()
	{
		return apps;
	} // getApps

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
	public List<Commodity> getCommodities()
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
	 * @return the declaredValue
	 */
	public double getDeclaredValue()
	{
		return declaredValue;
	}

	/**
	 * @return Declared value waived indicator
	 */
	public boolean getDeclaredValueWaived()
	{
		return declaredValueWaived;
	} // getDeclaredValueWaived

	/**
	 * Get the destination point
	 * 
	 * @return Destination point
	 */
	public Point getDest()
	{
		return this.dest;
	} // getDest

	/**
	 * Get customer discount percentage
	 * 
	 * @return Discount %
	 */
	public int getDiscount()
	{
		return this.discount;
	} // getDiscount

	/**
	 * Get the food warehouse ID
	 * 
	 * @return Food warehouse
	 */
	public int getFoodWarehouseId()
	{
		return foodWarehouseId;
	} // getFoodWarehouseId

	/**
	 * Get linear feet
	 * 
	 * @return Linear feet
	 */
	public int getLinearFeet()
	{
		return this.linearFeet;
	} // getLinearFeet

	/**
	 * Get user role
	 * 
	 * @return Role
	 */
	public String getRole()
	{
		return this.role;
	} // getRole

	/**
	 * Get the origin point
	 * 
	 * @return Origin point
	 */
	public Point getOrigin()
	{
		return this.origin;
	} // getOrigin

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
	 * Get the session ID
	 * 
	 * @return Session ID
	 */
	public String getSession()
	{
		return this.session;
	} // getSession

	/**
	 * Get the payment terms code
	 * <p>
	 * This is necessary only because the database table column
	 * was incorrectly defined with a length of 1 instead of a
	 * length of 3 for the terms code.
	 * Terms codes should be COL and PPD.
	 * </p>
	 * 
	 * @param  3-byte terms code
	 * @return 1-byte terms code
	 */
	public String getTerms()
	{
		if (this.terms == null || this.terms.length() == 0) {
			return "";
		}

		return this.terms.substring(0, 1);
	} // getTerms

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
	 * Get the declared value waived indicator
	 * 
	 * @return Declared value waived?
	 */
	public boolean isDeclaredValueWaived()
	{
		return this.declaredValueWaived;
	} // isDeclaredValueWaived

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
	 * Set list of accessorial info
	 * 
	 * @return Accessorials
	 */
	public void setAccessorials(List<Accessorial> accs)
	{
		this.accessorials = accs;
	} // setAccessorials

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
	 * Set the requesting application code
	 * 
	 * @return Requesting application
	 */
	public void setApps(List<String> val)
	{
		this.apps = val;
	} // setApps

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
	public void setCommodities(List<Commodity> comms)
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
	 * @param val Declared value
	 */
	public void setDeclaredValue(double val)
	{
		this.declaredValue = val;
	} // setDeclaredValue

	/**
	 * Set the destination point
	 * 
	 * @param pt Destination point
	 */
	public void setDest(Point pt)
	{
		this.dest = pt;
	} // setDest

	/**
	 * Set the declared value waived indicator
	 * 
	 * @param val Declared value waived
	 */
	public void setDeclaredValueWaived(boolean val)
	{
		this.declaredValueWaived = val;
	} // setDeclaredValueWaived

	/**
	 * Set customer discount percentage
	 * 
	 * @param Discount %
	 */
	public void setDiscount(int val)
	{
		this.discount = val;
	} // setDiscount

	/**
	 * Set food warehouse ID
	 * 
	 * @param val Food warehouse
	 */
	public void setFoodWarehouseId(int val)
	{
		this.foodWarehouseId = val;
	} // setFoodWarehouseId

	/**
	 * Set linear feet
	 * 
	 * @param val Linear feet
	 */
	public void setLinearFeet(int val)
	{
		this.linearFeet = val;
	} // setLinearFeet

	/**
	 * Set the origin point
	 * 
	 * @param pt Origin point
	 */
	public void setOrigin(Point pt)
	{
		this.origin = pt;
	} // setOrigin

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
	 * Set user role
	 * 
	 * @param val Role
	 */
	public void setRole(String val)
	{
		this.role = val;
	} // setRole

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
	 * Set the stackable indicator
	 * 
	 * @param val Stackable
	 */
	public void setStackable(boolean val)
	{
		this.stackable = val;
	} // setStackable

	/**
	 * Set payment terms code
	 * 
	 * @param val Payment terms
	 */
	public void setTerms(String val)
	{
		this.terms = val;
	} // setTerms

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
