/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2019
 */

package com.estes.myestes.rating.dto;

/**
 * Rate quote keys
 */
public class QuoteKeys
{
	String errorId;
	String refNum;

	public QuoteKeys()
	{
	} // Constructor

	public QuoteKeys(String error, String num)
	{
		this();
		setErrorId(error);
		setRefNum(num);
	} // Constructor

	/**
	 * Get the error ID
	 * 
	 * @return Error ID
	 */
	public String getErrorId()
	{
		return this.errorId;
	} // getErrorId

	/**
	 * Get quote reference number
	 * 
	 * @return Quote ref#
	 */
	public String getRefNum()
	{
		return this.refNum;
	} // getRefNum

	/**
	 * Set the error ID
	 * 
	 * @param val Error ID
	 */
	public void setErrorId(String val)
	{
		this.errorId = val.trim();
	} // setErrorId

	/**
	 * Set quote reference number
	 * 
	 * @param val Quote ref#
	 */
	public void setRefNum(String val)
	{
		this.refNum = val.trim();
	} // setRefNum
}
