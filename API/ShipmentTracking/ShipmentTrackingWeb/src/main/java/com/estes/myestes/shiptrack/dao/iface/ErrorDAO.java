/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 * 
 */

package com.estes.myestes.shiptrack.dao.iface;

public interface ErrorDAO extends BaseDAO
{
	/**
	 * Valid error code
	 */
	public static String VALID_ERROR_CODE = "";

	/**
	 * Look up error message for error code
	 * 
	 * @param code Error code to look up
	 * @return  Error message associated with error code
	 */
	String getErrorMessage(String code);

	/**
	 * Determine presence of error by checking error code
	 * 
	 * @param code Error code to compare
	 * @return  Indication of error
	 */
	public static boolean isError(String code)
	{
		return !code.trim().equals(VALID_ERROR_CODE);
	} // isError
}
