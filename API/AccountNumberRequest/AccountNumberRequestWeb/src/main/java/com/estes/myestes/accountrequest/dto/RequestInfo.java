/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 */

package com.estes.myestes.accountrequest.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes account code/number request DTO
 */
public class RequestInfo
{
	@ApiModelProperty(notes="Source of request - rating or elsewhere")
	String source;
	@ApiModelProperty(notes="Requestor name")
	String name;
	@ApiModelProperty(notes="Requestor company")
	String company;
	@ApiModelProperty(notes="Requestor e-mail address")
	String email;
	@ApiModelProperty(notes="Requestor phone number")
	String phone;
	@ApiModelProperty(notes="Requested company's name")
	String parentCompany;
	@ApiModelProperty(notes="Requested company's addresses")
	String addresses;

	/**
	 * Get the addresses
	 * 
	 * @return Address
	 */
	public String getAddresses()
	{
		return this.addresses;
	} // getAddress

	/**
	 * Get the company name
	 * 
	 * @return Company name
	 */
	public String getCompany()
	{
		return this.name;
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
	 * Get the parent company name
	 * 
	 * @return Parent company name
	 */
	public String getParentCompany()
	{
		return this.parentCompany;
	} // getParentCompany

	/**
	 * Get the request source
	 * 
	 * @return Request source
	 */
	public String getSource()
	{
		return this.source;
	} // getgetSourceId

	/**
	 * Get the requester name
	 * 
	 * @return Requester name
	 */
	public String getName()
	{
		return this.name;
	} // getName

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
	 * Set the addresses
	 * 
	 * @param val Addresses
	 */
	public void setAddresses(String val)
	{
		this.addresses = val;
	} // setAddresses

	/**
	 * Set the company name
	 * 
	 * @param val Company name
	 */
	public void setCompany(String val)
	{
		this.company = val;
	} // setCompany

	/**
	 * Set the requester e-mail address
	 * 
	 * @param val E-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val;
	} // setEmail

	/**
	 * Set the requester name
	 * 
	 * @param val Requester name
	 */
	public void setName(String val)
	{
		this.name = val;
	} // setName

	/**
	 * Set the parent company name
	 * 
	 * @param val Parent company name
	 */
	public void setParentCompany(String val)
	{
		this.parentCompany = val;
	} // setParentCompany

	/**
	 * Set the terminal phone number
	 * 
	 * @param val Terminal phone number
	 */
	public void setPhone(String val)
	{
		this.phone = val;
	} // setPhone

	/**
	 * Set the request source
	 * 
	 * @param val Source
	 */
	public void setSource(String val)
	{
		this.source = val;
	} // setSource
}
