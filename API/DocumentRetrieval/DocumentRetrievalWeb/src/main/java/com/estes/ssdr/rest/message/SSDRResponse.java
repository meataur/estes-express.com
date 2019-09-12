/**
 * @author: Todd Allen
 *
 * Creation date: 04/08/2016
 *
 */

package com.estes.ssdr.rest.message;

/**
 * Parent class for DocumentResponse
 */
public class SSDRResponse extends DocumentResponse
{
	/**
	 * Determine whether the request was successful
	 * 
	 * @param  code Response code
	 * @return Success boolean indicator 
	 */
	public static boolean isSuccess(String code)
	{
		return code.equalsIgnoreCase("SSDR000");
	} // isSuccess
}
