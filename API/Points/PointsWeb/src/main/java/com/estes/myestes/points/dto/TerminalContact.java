/**
 * @author: Todd Allen
 *
 * Creation date: 09/14/2018
 */

package com.estes.myestes.points.dto;

/**
 * Estes terminal contact DTO
 */
public class TerminalContact
{
	String fulName;
	String email;

	/**
	 * Get the terminal manager full name
	 * 
	 * @return Terminal manager full name
	 */
	public String getFullName()
	{
		return this.fulName;
	} // getFullName

	/**
	 * Get the terminal manager e-mail address
	 * 
	 * @return Terminal manager e-mail address
	 */
	public String getEmail()
	{
		return this.email;
	} // getEmail

	/**
	 * Set the terminal manager full name
	 * 
	 * @param val Terminal manager full name
	 */
	public void setFullName(String val)
	{
		this.fulName = val.trim();
	} // setFullName

	/**
	 * Set the terminal manager e-mail address
	 * 
	 * @param val Terminal manager e-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val.trim();
	} // setEmail
}
