/**
 *  * @author: Lakshman K
 *
 * Creation date: 1/4/2019
 */
package com.estes.myestes.edirequest.utils;

public class EdiRequestUtil {

	public EdiRequestUtil() {	}

	public static String getStringFromBool(boolean tf)
	{
		return tf ? "Y":"N";
	} // getStringFromBool
	
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
	
	public static boolean isNullOrEmpty(String fieldName) {
		if(fieldName == null || fieldName.equals("")) {
			return true;	
		}
		return false;	
	}
}
