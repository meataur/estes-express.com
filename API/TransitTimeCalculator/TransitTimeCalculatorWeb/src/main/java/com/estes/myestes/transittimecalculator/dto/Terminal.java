/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 */

package com.estes.myestes.transittimecalculator.dto;

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
	@ApiModelProperty(notes="Terminal name")
	String name;
	@ApiModelProperty(notes="Terminal address")
	Address address;
	@ApiModelProperty(notes="Terminal phone number – country code plus 10 digit number")
	String phone;
	@ApiModelProperty(notes="Terminal fax number - country code plus 10 digit number")	
	String fax;

	/**
	 * Create new terminal DTO
	 */
	public Terminal()
	{
		this.address = new Address();
	} // Constructor

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
