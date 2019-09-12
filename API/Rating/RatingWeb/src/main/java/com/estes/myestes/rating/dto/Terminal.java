/**
 * @author: Todd Allen
 *
 * Creation date: 02/08/2018
 */

package com.estes.myestes.rating.dto;

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
}
