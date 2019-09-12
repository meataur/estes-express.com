/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 */

package com.estes.myestes.login.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * My Estes credentials DTO
 */
@ApiModel(description="My Estes login credentials")
public class Credentials
{
	@ApiModelProperty(notes="My Estes user name")
	String userName;
	@ApiModelProperty(notes="My Estes user password")
	String password;

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
	 * Get the My Estes user name
	 * 
	 * @return User name
	 */
	public String getUserName()
	{
		return this.userName;
	} // getUserName

	/**
	 * Set the My Estes password
	 * 
	 * @param val Password
	 */
	public void setPassword(String val)
	{
		this.password = val;
	} // setPassword

	/**
	 * Set the My Estes user name
	 * 
	 * @param val User name
	 */
	public void setUserName(String val)
	{
		this.userName = val;
	} // setUserName
}

