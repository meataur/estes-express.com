/**
 * @author: Todd Allen
 *
 * Creation date: 11/16/2018
 */

package com.estes.services.common.dto;

import com.estes.dto.common.Address;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes terminal DTO
 */
@ApiModel(description="Estes terminal information")
public class Terminal
{
	@ApiModelProperty(notes="3 digit terminal ID")
	String id;
	@ApiModelProperty(notes="3 letter terminal abbreviation")
	String abbr;
	@ApiModelProperty(notes="Terminal name")
	String name;
	@ApiModelProperty(notes="Terminal address")
	Address address;
	@ApiModelProperty(notes="Company phone number (xxx) xxx-xxxx")
	String phone;
	@ApiModelProperty(notes="Company fax number 1xxxxxxxxxx")
	String fax;
	@ApiModelProperty(notes="Terminal e-mail address")
	String email;
	@ApiModelProperty(notes="Terminal manager full name")
	String manager;
	@ApiModelProperty(notes="Terminal manager e-mail address")
	String managerEmail;
	@ApiModelProperty(notes="Path to terminal servicing area map gif")
	String serviceArea;
		@ApiModelProperty(notes="Path to terminal servicing area map pdf")
	String serviceMap;
	@ApiModelProperty(notes="Path to terminal national area map pdf")
	String nationalMap;
	@ApiModelProperty(notes="Display Options - 3 bytes containing any combination of 3 flags - " +
            "A = omit terminal from terminal list/" + 
            "B = display terminal as a consolidation terminal/" + 
            "C = omit link to terminal map and details")
	String displayOptions;
	

	/**
	 * Create new terminal DTO
	 */
	public Terminal()
	{
		this.address = new Address();
	} // Constructor

	/**
	 * Get the terminal abbreviation
	 * 
	 * @return Terminal abbreviation
	 */
	public String getAbbr()
	{
		return this.abbr;
	} // getAbbr

	/**
	 * Get the terminal address
	 * 
	 * @return Address
	 */
	public Address getAddress()
	{
		return this.address;
	} // getAddress

	/**
	 * Get the terminal e-mail address
	 * 
	 * @return Terminal e-mail address
	 */
	public String getEmail()
	{
		return this.email;
	} // getEmail

	/**
	 * Get the terminal fax number
	 * 
	 * @return Terminal fax number
	 */
	public String getFax()
	{
		return this.fax;
	} // getFax

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
	 * Get the terminal manager name
	 * 
	 * @return Terminal manager name
	 */
	public String getManager()
	{
		return this.manager;
	} // getgetManager

	/**
	 * Get the terminal manager e-mail address
	 * 
	 * @return Terminal manager e-mail address
	 */
	public String getManagerEmail()
	{
		return this.managerEmail;
	} // getManagerEmail

	/**
	 * Get the terminal name
	 * 
	 * @return Terminal name
	 */
	public String getName()
	{
		return this.name;
	} // getName

	/**
	 * Get the terminal phone number
	 * 
	 * @return Terminal phone number
	 */
	public String getPhone()
	{
		return this.phone;
	} // getPhone

	/**
	 * Get the servicing map path
	 * 
	 * @return Servicing map path
	 */
	public String getServiceMap()
	{
		return this.serviceMap;
	} // getServiceMap

	/**
	 * Set the terminal abbreviation
	 * 
	 * @param val Terminal abbreviation
	 */
	public void setAbbr(String val)
	{
		this.abbr = val;
	} // setAbbr

	/**
	 * Set the terminal address
	 * 
	 * @param val Terminal address
	 */
	public void setAddress(Address val)
	{
		this.address = val;
	} // setAddress

	/**
	 * Set the terminal e-mail address
	 * 
	 * @param val Terminal e-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val.trim();
	} // setEmail

	/**
	 * Set the terminal fax number
	 * 
	 * @param val Terminal fax number
	 */
	public void setFax(String val)
	{
		this.fax = val.trim();
	} // setFax

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
	 * Set the terminal manager name
	 * 
	 * @param val Terminal manager name
	 */
	public void setManager(String val)
	{
		this.manager = val.trim();
	} // setManager

	/**
	 * Set the terminal manager e-mail address
	 * 
	 * @param val Terminal manager e-mail address
	 */
	public void setManagerEmail(String val)
	{
		this.managerEmail = val.trim();
	} // setManagerEmail

	/**
	 * Set the terminal name
	 * 
	 * @param val Terminal name
	 */
	public void setName(String val)
	{
		this.name = val.trim();
	} // setName

	/**
	 * Set the terminal phone number
	 * 
	 * @param val Terminal phone number
	 */
	public void setPhone(String val)
	{
		this.phone = val.trim();
	} // setPhone

	/**
	 * Set the servicing map path
	 * 
	 * @param val Servicing map path
	 */
	public void setServiceMap(String val)
	{
		this.serviceMap = val.trim();
	} // setServiceMap
	
	/**
	 * @return the nationalMap
	 */
	public String getNationalMap() {
		return nationalMap;
	}

	/**
	 * @param nationalMap the nationalMap to set
	 */
	public void setNationalMap(String nationalMap) {
		this.nationalMap = nationalMap;
	}//setNationalMap
	/**
	 * @return the serviceArea
	 */
	public String getServiceArea() {
		return serviceArea;
	}

	/**
	 * @param serviceArea the serviceArea to set
	 */
	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	/**
	 * @return the displayOptions
	 */
	public String getDisplayOptions() {
		return displayOptions;
	}

	/**
	 * @param displayOptions the displayOptions to set
	 */
	public void setDisplayOptions(String displayOptions) {
		this.displayOptions = displayOptions;
	}


}
