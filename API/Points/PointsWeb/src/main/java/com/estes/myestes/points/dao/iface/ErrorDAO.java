/**
 * @author: Todd Allen
 *
 * Creation date: 09/27/2018
 * 
 */

package com.estes.myestes.points.dao.iface;

public interface ErrorDAO
{
	/**
	 * Valid error code
	 */
	public static String VALID_ERROR_CODE = "";
	/**
	 * Valid error code
	 */
	public static String VALID_EMAIL_ERROR_CODE = "EMR0000";

	/**
	 * Default error code for ReST service calls
	 */
	public static String DEFAULT_ERROR_CODE = "GEN0005";

	/**
	 * Look up error message for error code
	 * 
	 * @param code Error code to look up
	 * @return Error message associated with error code
	 */
	String getErrorMessage(String code);

	/**
	 * Determine presence of error on e-mail address by checking error code
	 * 
	 * @param code Error code to compare
	 * @return Indication of error
	 */
	public static boolean isEmailError(String code)
	{
		return !code.trim().equals(VALID_EMAIL_ERROR_CODE);
	} // isError

	/**
	 * Determine presence of error by checking error code
	 * 
	 * @param code Error code to compare
	 * @return Indication of error
	 */
	public static boolean isError(String code)
	{
		return !code.trim().equals(VALID_ERROR_CODE);
	} // isError
}
