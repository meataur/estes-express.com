/**
 * @author: Todd Allen
 *
 * Creation date: 08/20/2018
 */

package com.estes.myestes.claims.util;

/**
 * Phone number utilities based on format of (xxx) yyy-zzzz
 * 
 * @author allento
 */
public class PhoneUtil
{
	public PhoneUtil() {	}

	/**
	 * Get area code from formatted phone number
	 * 
	 * @param phone Phone number
	 * @return  3-digit area code
	 */
	public static String getAreaCode(String phone)
	{
		if (phone==null || phone.length() < 4) {
			return "";
		}
		else {
			return phone.substring(1, 4);
		}
	} // getAreaCode

	/**
	 * Get exchange from formatted phone number
	 * 
	 * @param phone Phone number
	 * @return  3-digit exchange
	 */
	public static String getExchange(String phone)
	{
		if (phone==null || phone.length() < 9) {
			return "";
		}
		else {
			return phone.substring(6, 9);
		}
	} // getExchange

	/**
	 * Get last 4 digits of formatted phone number
	 * 
	 * @param phone Phone number
	 * @return  Last 4 digits
	 */
	public static String getLast4(String phone)
	{
		if (phone==null || phone.length() < 14) {
			return "";
		}
		else return phone.substring(10, 14);
	} // getLast4
	
	/**
	 * convert the phone number from (xxx) xxx-xxxx to xxxxxxxxxx
	 * 
	 * @param phone Phone number
	 * @return  new formatted phone number
	 */
	public static String convertToDigits(String phone)
	{
		return getAreaCode(phone)+getExchange(phone)+getLast4(phone);
	} // convertToDigits
	
	/**
	 * convert the phone number from xxxxxxxxxx to (xxx) xxx-xxxx
	 * 
	 * @param phone Phone number
	 * @return  new formatted phone number
	 */
	public static String convertToFormatted(String phone)
	{
		if (phone==null || phone.length() < 10) {
			return "";
		}
		else return "(" + phone.substring(0, 3) + ") " + phone.substring(3, 6) + "-"+phone.substring(6, 10);
	} // convertToFormatted
}
