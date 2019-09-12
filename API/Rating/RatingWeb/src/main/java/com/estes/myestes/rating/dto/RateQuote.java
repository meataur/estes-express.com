/**
 * @author: Todd Allen
 *
 * Creation date: 02/06/2019
 */

package com.estes.myestes.rating.dto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import com.estes.myestes.rating.utils.RatingUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Estes rate quote
 */
@ApiModel(description="Estes rate quote information")
@Data
public class RateQuote
{
	@ApiModelProperty(position=1, notes="Quote ID")
	private String quoteID;
	@ApiModelProperty(position=2, notes="Quote reference number")
	private String quoteRefNum;
	@ApiModelProperty(notes="Quote Date - MM/DD/YYYY")
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy")
	private LocalDate quoteDate;
	@ApiModelProperty(position=4, notes="Requesting application - LTL=less than truckload/TC=time critical/VTL=volume truckload")
	String app = "";
	
	@ApiModelProperty(position=5, notes="List of all apps selected at the time of creating this quote")
	private List<String> selectedApps;
	@ApiModelProperty(notes="Account code/number requesting quote")
	private String accountCode;
	@ApiModelProperty(notes="Customer name")
	private String accountName;
	@ApiModelProperty(notes="Contact full name")
	String contactName;
	@ApiModelProperty(notes="Contact phone number (xxx) xxx-xxxx")
	String contactPhone = "";
	@ApiModelProperty(notes="Contact phone number extension")
	String contactPhoneExt = "";
	@ApiModelProperty(notes="Contact e-mail address")
	String contactEmail = "";
	@ApiModelProperty(notes="Role/payor - S=Shipper/C=Consignee/T=Third party")
	String role = "";
	@ApiModelProperty(notes="Payment terms - PPD=Prepaid/COL=Collect")
	String terms = "";
	@ApiModelProperty(notes="Shipping lane")
	private int lane;
	@ApiModelProperty(notes="Origin point")
	Point origin;
	@ApiModelProperty(notes="Destination point")
	Point dest;
	@ApiModelProperty(notes="Transit message")
	private String transitMessage;
	@ApiModelProperty(notes="Pickup date - MM/DD/YYYY")
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy")
	LocalDate pickupDate;
	@ApiModelProperty(notes="Pickup available time - hh:mm:ss")
	String pickupAvail = "";
	@ApiModelProperty(notes="Pickup close time - hh:mm:ss")
	String pickupClose = "";
	@ApiModelProperty(notes="Delivery information")
	private Delivery delivery;
	@ApiModelProperty(notes="Quote expiration date - MM/DD/YYYY")
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy",timezone="EDT")
	private Date expireDate;
	@ApiModelProperty(notes="Quote expiration time - hh:mm:ss")
	private String expireTime = "";
	@ApiModelProperty(notes="Service level information")
	private ServiceLevel service;
	@ApiModelProperty(notes="Is service gauranteed?")
	private boolean guaranteed;
	@ApiModelProperty(notes="Declared value")
	double declaredValue;
	@ApiModelProperty(notes="Declared value waived?")
	boolean declaredValueWaived;
	@ApiModelProperty(hidden=true, notes="Food warehouse unique ID")
	int foodWarehouseId;
	@ApiModelProperty(notes="Food warehouse name")
	String foodWarehouse;
	@ApiModelProperty(notes="Is freight stackable?")
	boolean stackable;
	@ApiModelProperty(notes="Linear feet")
	int linearFeet;
	@ApiModelProperty(notes="Comments")
	String comments = "";
	@ApiModelProperty(notes="Pricing information")
	private Pricing pricing;
	@ApiModelProperty(notes="Commodity pricing")
	private List<CommodityPricing> commodities;
	@ApiModelProperty(notes="Accessorial pricing")
	private List<AccessorialPricing> accessorials;
	@ApiModelProperty(notes="Individual charges")
	private List<Charge> charges;
	@ApiModelProperty(notes="Informational messages")
	private List<String> info;
	@ApiModelProperty(notes="Disclaimers")
	private  List<String> disclaimers;

	/**
	 * Create new rate quote
	 */
	public RateQuote()
	{
		this.origin = new Point();
		this.dest = new Point();
		this.delivery = new Delivery();
		this.service = new ServiceLevel();
		this.pricing = new Pricing();
		this.commodities = new ArrayList<CommodityPricing>();
		this.accessorials = new ArrayList<AccessorialPricing>();
		this.info = new ArrayList<String>();
		this.disclaimers = new ArrayList<String>();
		this.selectedApps = new ArrayList<>();
	} // Constructor

	/**
	 * Create rate quote
	 */
	public RateQuote(String id)
	{
		this();
		this.quoteID = id;
	} // Constructor

	/**
	 * Get accessorial pricing
	 * 
	 * @return Accessorials
	 */
	public List<AccessorialPricing> getAccessorials()
	{
		return this.accessorials;
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
	 * Get the customer name
	 * 
	 * @return Customer name
	 */
	public String getAccountName()
	{
		return accountName.trim();
	} // getAccountName

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
	 * Get charges
	 * 
	 * @return Charges
	 */
	public List<Charge> getCharges()
	{
		return this.charges;
	} // getCharges

	/**
	 * Get comments
	 * 
	 * @return Comments
	 */
	public String getComments()
	{
		return this.comments.trim();
	} // getComments

	/**
	 * Get commodity pricing
	 * 
	 * @return Commodities
	 */
	public List<CommodityPricing> getCommodities()
	{
		return this.commodities;
	} // getCommodities

	/**
	 * Get contact e-mail address
	 * 
	 * @return Contact e-mail
	 */
	public String getContactEmail()
	{
		return this.contactEmail.trim();
	} // getContactEmail

	/**
	 * Get contact name
	 * 
	 * @return Contact name
	 */
	public String getContactName()
	{
		return this.contactName.trim();
	} // getContactName

	/**
	 * Get contact phone
	 * 
	 * @return Contact phone
	 */
	public String getContactPhone()
	{
		if(contactPhone==null){
			return "";
		}
		
		contactPhone = contactPhone.replaceAll("\\D+", "");
		
		if(contactPhone.length() < 10){
			return "";
		}
		
		return "("+contactPhone.substring(0, 3)+") "+contactPhone.substring(3, 6)+"-" +contactPhone.substring(6,10);

	} // getContactPhone

	/**
	 * Get contact phone extension
	 * 
	 * @return Contact phone ext
	 */
	public String getContactPhoneExt()
	{
		return this.contactPhoneExt.trim();
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
	 * Get delivery info
	 * 
	 * @return Delivery info
	 */
	public Delivery getDelivery()
	{
		return this.delivery;
	} // getDelivery

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
	 * Get the disclaimer text
	 * 
	 * @return Disclaimer
	 */
	public List<String> getDisclaimers()
	{
		return this.disclaimers;
	} // getDisclaimers

	/**
	 * Get quote expiration date
	 * 
	 * @return Quote expiration date
	 */
	public Date getExpireDate()
	{
		return this.expireDate;
	} // getExpireDate

	/**
	 * Get quote expiration time
	 * 
	 * @return Quote expiration time
	 */
	public String getExpireTime()
	{
		return !StringUtils.isEmpty(this.expireTime) ? this.expireTime : "00:00:00";
	} // getExpireTime

	/**
	 * Get the food warehouse ID
	 * 
	 * @return Food warehouse
	 */
	public String getFoodWarehouse()
	{
		return this.foodWarehouse;
	} // getFoodWarehouse

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
	 * Get the informational messages
	 * 
	 * @return Info messages
	 */
	public List<String> getInfo()
	{
		return this.info;
	} // getInfo

	/**
	 * Get the shipping lane
	 * 
	 * @return Lane
	 */
	public int getLane()
	{
		return lane;
	} // getLane

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
		return !StringUtils.isEmpty(this.pickupAvail) ? this.pickupAvail : "00:00:00";
	} // getAvailableTime

	/**
	 * Get pickup close time
	 * 
	 * @return Pickup close time
	 */
	public String getPickupClose()
	{
		return !StringUtils.isEmpty(this.pickupClose) ? this.pickupClose : "00:00:00";
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
	 * Get pricing info
	 * 
	 * @return Pricing info
	 */
	public Pricing getPricing()
	{
		return this.pricing;
	} // getPricing

	public LocalDate getQuoteDate() {
		return quoteDate;
	}

	/**
	 * Get the quote ID
	 * 
	 * @return Quote ID
	 */
	public String getQuoteID()
	{
		return this.quoteID;
	} // getQuoteID

	/**
	 * Get the quote reference number
	 * 
	 * @return Quote ref#
	 */
	public String getQuoteRefNum()
	{
		return this.quoteRefNum;
	} // getQuoteRefNum

	/**
	 * Get user role description
	 * 
	 * @return Role description
	 */
	public String getRole()
	{
//		if (this.terms.equals("C")) {
//			return "Consignee";
//		}
//		if (this.terms.equals("S")) {
//			return "Shipper";
//		}
//		if (this.terms.equals("T")) {
//			return "Third party";
//		}

		return this.role;
	} // getRole

	/**
	 * Get service level info
	 * 
	 * @return Service level
	 */
	public ServiceLevel getService()
	{
		return this.service;
	} // getService

	/**
	 * Get the payment terms text
	 * 
	 * @return terms code description
	 */
	public String getTerms()
	{
		if (this.terms.equals("C")) {
			return "COL";
		}
		if (this.terms.equals("P")) {
			return "PPD";
		}

		return "";
	} // getTerms

	/**
	 * Get the transit message
	 * 
	 * @return Transit message
	 */
	public String getTransitMessage()
	{
		return this.transitMessage.trim();
	} // getTransitMessage

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
	 * Get the guaranteed service indicator
	 * 
	 * @return Guaranteed service?
	 */
	public boolean isGuaranteed()
	{
		return this.guaranteed;
	} // isGuaranteed

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
	 * Set accessorial pricing
	 * 
	 * @param val Accessorials
	 */
	public void setAccessorials(List<AccessorialPricing> lst)
	{
		this.accessorials = lst;
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
	 * Set the customer name
	 * 
	 * @param name
	 */
	public void setAccountName(String name)
	{
		this.accountName = name;
	} // setAccountName

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
	 * Set charges
	 * 
	 * @param val Charges
	 */
	public void setCharges(List<Charge> lst)
	{
		this.charges = lst;
	} // setCharges

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
	 * Set commodities
	 * 
	 * @param Commodities
	 */
	public void setCommodities(List<CommodityPricing> lst)
	{
		this.commodities = lst;
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
	 * Set the declared value waived indicator
	 * 
	 * @param val Declared value waived
	 */
	public void setDeclaredValueWaived(boolean val)
	{
		this.declaredValueWaived = val;
	} // setDeclaredValueWaived

	/**
	 * Set delivery info
	 * 
	 * @param val Delivery
	 */
	public void setDelivery(Delivery val)
	{
		this.delivery = val;
	} // setDelivery

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
	 * Set the disclaimer text
	 * 
	 * @param val Disclaimers
	 */
	public void setDisclaimers(List<String> lst)
	{
		this.disclaimers = lst;
	} // setDisclaimers

	/**
	 * Set quote expiration date
	 * 
	 * @param val Quote expiration date
	 */
	public void setExpireDate(Date val)
	{
		this.expireDate = val;
	}

	/**
	 * Set quote expiration time
	 * 
	 * @param val Quote expiration time
	 */
	public void setExpireTime(Date val)
	{
		DateFormat time = new SimpleDateFormat("HH:mm:ss");
		this.expireTime = time.format(val);
	} // setExpireTime

	/**
	 * Set the food warehouse name
	 * 
	 * @param val Food warehouse
	 */
	public void setFoodWarehouse(String val)
	{
		this.foodWarehouse = val;
	} // setFoodWarehouse

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
	 * Set the guaranteed indicator
	 * 
	 * @param val Guaranteed?
	 */
	public void setGuaranteed(boolean val)
	{
		this.guaranteed = val;
	} // setGuaranteed

	/**
	 * Get the informational messages
	 * 
	 * @param lst Info messages
	 */
	public void setInfo(List<String> lst)
	{
		this.info = lst;
	} // setInfo

	/**
	 * Set shipping lane
	 * 
	 * @param val Lane
	 */
	public void setLane(int val)
	{
		this.lane = val;
	} // setLane

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
	 * Set pricing info
	 * 
	 * @param val Pricing info
	 */
	public void setPricing(Pricing val)
	{
		this.pricing = val;
	} // setPricing

	public void setQuoteDate(LocalDate qDate)
	{
		this.quoteDate = qDate;
	}

	/**
	 * Set the quote ID
	 * 
	 * @param val Quote ID
	 */
	public void setQuoteID(String val)
	{
		this.quoteID = val;
	} // setQuoteID

	/**
	 * Set the quote reference number
	 * 
	 * @param val Quote ref#
	 */
	public void setQuoteRefNum(String val)
	{
		this.quoteRefNum = val;
	} // setQuoteRefNum

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
	 * Set service level info
	 * 
	 * @param val Service level
	 */
	public void setService(ServiceLevel val)
	{
		this.service = val;
		setGuaranteed(RatingUtil.isGuaranteed(val.getId()));
	} // setService

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
	 * Set the transit message
	 * 
	 * @param Transit message text
	 */
	public void setTransitMessage(String val)
	{
		this.transitMessage = val;
	} // setTransitMessage
	
	
	public List<String> getSelectedApps(){
		if(selectedApps.size()==0){
			selectedApps.add(app);
		}
		return selectedApps;
		
	}
	public void addLtlToSelectedApps(String flag){
		if(flag!=null && flag.equalsIgnoreCase("Y")){
			selectedApps.add("LTL");
		}
	}
	public void addVtlToSelectedApps(String flag){
		if(flag!=null && flag.equalsIgnoreCase("Y")){
			selectedApps.add("VTL");
		}
	}
	public void addTcToSelectedApps(String flag){
		if(flag!=null && flag.equalsIgnoreCase("Y")){
			selectedApps.add("TC");
		}
	}
}
