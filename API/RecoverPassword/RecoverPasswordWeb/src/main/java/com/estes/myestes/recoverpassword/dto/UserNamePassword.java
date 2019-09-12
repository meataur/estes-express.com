/**
 * @author: Lakshman Kandaswamy
 *
 * Creation date: 10/10/2018
 */

package com.estes.myestes.recoverpassword.dto;

/**
 * Estes UserNamePassword DTO
 */
public class UserNamePassword
{
	String userName;
	String password;
	String email;

	/**
	 * Create new recoverPassword DTO
	 */
	public UserNamePassword()
	{
	} // Constructor

	public UserNamePassword(String userName, String password, String email) {
		super();
		this.userName = userName;
		this.password = password;
		this.email = email;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}	
}
