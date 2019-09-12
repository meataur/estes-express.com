/**
 * @author: Lakshman K
 *
 * Creation date: 12/19/2018
 * 
 */

package com.estes.myestes.commoditylibrary.dao.iface;

public interface ErrorDAO
{
	/**
	 * Valid error code
	 */
	public static String VALID_ERROR_CODE = "0000000";

	/**
	 * Look up error message for error code
	 * 
	 * @param code Error code to look up
	 * @return  Error message associated with error code
	 */
	public String getErrorMessage(String code);
}
