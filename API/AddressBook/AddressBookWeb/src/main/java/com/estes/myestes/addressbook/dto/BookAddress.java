/**
 * @author: Todd Allen
 *
 * Creation date: 05/18/2018
 */

package com.estes.myestes.addressbook.dto;

import com.estes.dto.common.Address;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes customer address book address
 */
public class BookAddress
{
	@ApiModelProperty(notes="My Estes user name - leave blank")
	String user;
	@ApiModelProperty(notes="Unique sequence number of user address; ignored on add")
	int seq;
	@ApiModelProperty(notes="Shipper address?")
	boolean shipper;
	@ApiModelProperty(notes="Consignee address?")
	boolean consignee;
	@ApiModelProperty(notes="3rd party address?")
	boolean thirdParty;
	@ApiModelProperty(notes="Cash on delivery?")
	boolean cod;
	@ApiModelProperty(notes="Company name")
	String company;
	@ApiModelProperty(notes="Company location number")
	String locationNumber;
	@ApiModelProperty(notes="Company contact 1st name")
	String firstName;
	@ApiModelProperty(notes="Company contact last name")
	String lastName;
	@ApiModelProperty(notes="Company address")
	Address address;
	@ApiModelProperty(notes="Company phone number (xxx) xxx-xxxx")
	String phone;
	@ApiModelProperty(notes="Company phone number extension")
	String phoneExt;
	@ApiModelProperty(notes="Company fax number (xxx) xxx-xxxx")
	String fax;
	@ApiModelProperty(notes="Company e-mail address")
	String email;

	public BookAddress()
	{
		this.address = new Address();
	} // constructor

	/**
	 * Get the company address
	 * 
	 * @return Address
	 */
	public Address getAddress()
	{
		return this.address;
	} // getAddress

	/**
	 * Get the company name
	 * 
	 * @return Company name
	 */
	public String getCompany()
	{
		return this.company;
	} // getCompany

	/**
	 * Get the requester e-mail address
	 * 
	 * @return E-mail address
	 */
	public String getEmail()
	{
		return this.email;
	} // getEmail

	/**
	 * Get the company location #
	 * 
	 * @return Company location #
	 */
	public String getLocationNumber()
	{
		return this.locationNumber;
	} // getLocationNumber

	/**
	 * Get the fax number
	 * 
	 * @return Fax number
	 */
	public String getFax()
	{
		return this.fax;
	} // getFax
	/**
	 * Get company contact first name
	 * 
	 * @return Company contact first name
	 */
	public String getFirstName()
	{
		return this.firstName;
	} // getFirstName

	/**
	 * Get company contact last name
	 * 
	 * @return Company contact last name
	 */
	public String getLastName()
	{
		return this.lastName;
	} // getLastName

	/**
	 * Get the phone number
	 * 
	 * @return Phone number
	 */
	public String getPhone()
	{
		return this.phone;
	} // getPhone

	/**
	 * Get the phone number extension
	 * 
	 * @return Phone number ext
	 */
	public String getPhoneExt()
	{
		return this.phoneExt;
	} // getPhoneExt

	/**
	 * Get the unique address sequence number
	 * 
	 * @return Address sequence #
	 */
	public int getSeq()
	{
		return this.seq;
	} // getSeq

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
	 * Get the COD indicator
	 * 
	 * @return COD
	 */
	public boolean isCod()
	{
		return this.cod;
	} // isCod

	/**
	 * Get the consignee indicator
	 * 
	 * @return Consignee
	 */
	public boolean isConsignee()
	{
		return this.consignee;
	} // isConsignee

	/**
	 * Get the shipper indicator
	 * 
	 * @return Shipper
	 */
	public boolean isShipper()
	{
		return this.shipper;
	} // isShipper

	/**
	 * Get the 3rd party indicator
	 * 
	 * @return 3rd party
	 */
	public boolean isThirdParty()
	{
		return this.thirdParty;
	} // isThirdParty

	/**
	 * Set the company address
	 * 
	 * @param val Address
	 */
	public void setAddress(Address val)
	{
		this.address = val;
	} // setAddress

	/**
	 * Set the COD indicator
	 * 
	 * @param val COD
	 */
	public void setCod(boolean val)
	{
		this.cod = val;
	} // setCod

	/**
	 * Set the company name
	 * 
	 * @param val Company name
	 */
	public void setCompany(String val)
	{
		this.company = val.trim();
	} // setCompany

	/**
	 * Set the consignee indicator
	 * 
	 * @param val Consignee
	 */
	public void setConsignee(boolean val)
	{
		this.consignee = val;
	} // setConsignee

	/**
	 * Set the requester e-mail address
	 * 
	 * @param val E-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val.trim();
	} // setEmail

	/**
	 * Set the company fax number
	 * 
	 * @param val Company fax number
	 */
	public void setFax(String val)
	{
		this.fax = val.trim();
	} // setFax

	/**
	 * Set the company contact first name
	 * 
	 * @param val Company contact first name
	 */
	public void setFirstName(String val)
	{
		this.firstName = val.trim();
	} // setFirstName

	/**
	 * Set the company contact last name
	 * 
	 * @param val Company contact last name
	 */
	public void setLastName(String val)
	{
		this.lastName = val.trim();
	} // setLastName

	/**
	 * Set the company location number
	 * 
	 * @param val Company location number
	 */
	public void setLocationNumber(String val)
	{
		this.locationNumber = val.trim();
	} // setLocationNumber

	/**
	 * Set the company phone number
	 * 
	 * @param val Company phone number
	 */
	public void setPhone(String val)
	{
		this.phone = val.trim();
	} // setPhone

	/**
	 * Set the company phone number extension
	 * 
	 * @param val Company phone number ext
	 */
	public void setPhoneExt(String val)
	{
		this.phoneExt = val.trim();
	} // setPhoneExt

	/**
	 * Set the unique address sequence number
	 * 
	 * @param val Address sequence #
	 */
	public void setSeq(int val)
	{
		this.seq = val;
	} // setSeq

	/**
	 * Set the shipper indicator
	 * 
	 * @param val Shipper
	 */
	public void setShipper(boolean val)
	{
		this.shipper = val;
	} // setShipper

	/**
	 * Set the 3rd party indicator
	 * 
	 * @param val 3rd party
	 */
	public void setThirdParty(boolean val)
	{
		this.thirdParty = val;
	} // setThirdParty

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
