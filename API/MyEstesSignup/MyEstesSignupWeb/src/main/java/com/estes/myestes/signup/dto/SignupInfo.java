/**
 * @author: Todd Allen
 *
 * Creation date: 08/06/2018
 */

package com.estes.myestes.signup.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes profile signup DTO
 */
public class SignupInfo
{
	@ApiModelProperty(notes="Customer contact first name")
	String firstName;
	@ApiModelProperty(notes="Customer contact last name")
	String lastName;
	@ApiModelProperty(notes="Customer company name")
	String company;
	@ApiModelProperty(notes="Customer account code/number")
	String accountCode;
	@ApiModelProperty(notes="Customer e-mail address")
	String email;
	@ApiModelProperty(notes="Customer phone number (xxx) xxx-xxxx")
	String phone;
	@ApiModelProperty(notes="Requested user name")
	String userName;
	@ApiModelProperty(notes="Requested password")
	String password;
	@ApiModelProperty(notes="Send web site notifications")
	boolean notify;

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
	 * Get the company name
	 * 
	 * @return Company name
	 */
	public String getCompany()
	{
		return this.firstName;
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
	 * Get the contact first name
	 * 
	 * @return Contact 1st name
	 */
	public String getFirstName()
	{
		return this.firstName;
	} // getFirstName

	/**
	 * Get the contact last name
	 * 
	 * @return Contact last name
	 */
	public String getLastName()
	{
		return this.lastName;
	} // getLastName

	/**
	 * Get the My Estes password
	 * 
	 * @return Password
	 */
	public String getPassword()
	{
		return this.password;
	} // getPassword

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
	 * Get the My Estes user name
	 * 
	 * @return User name
	 */
	public String getUserName()
	{
		return this.userName;
	} // getUserName

	/**
	 * Get the notifications flag
	 * 
	 * @return Notifications boolean indicator
	 */
	public boolean isNotify()
	{
		return this.notify;
	} // isNotify

	/**
	 * Set the account code/number
	 * 
	 * @param accountCode
	 */
	public void setAccountCode(String accountCode)
	{
		this.accountCode = accountCode.trim();
	} // setAccountCode

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
	 * Set the requester e-mail address
	 * 
	 * @param val E-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val.trim();
	} // setEmail

	/**
	 * Set the contact first name
	 * 
	 * @param val Contact 1st name
	 */
	public void setFirstName(String val)
	{
		this.firstName = val.trim();
	} // setFirstName

	/**
	 * Set the contact last name
	 * 
	 * @param val Contact last name
	 */
	public void setLastName(String val)
	{
		this.lastName = val.trim();
	} // setLastName

	/**
	 * Set the notifications flag
	 * 
	 * @param val Notifications boolean indicator
	 */
	public void setNotify(boolean val)
	{
		this.notify = val;
	} // setNotify

	/**
	 * Set the My Estes password
	 * 
	 * @param val Password
	 */
	public void setPassword(String val)
	{
		this.password = val.trim();
	} // setPassword

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
	 * Set the My Estes user name
	 * 
	 * @param val User name
	 */
	public void setUserName(String val)
	{
		this.userName = val.trim();
	} // setUserName
}
