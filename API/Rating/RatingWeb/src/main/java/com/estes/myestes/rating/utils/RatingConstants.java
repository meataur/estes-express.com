/**
 * @author: Todd Allen
 *
 * Creation date: 02/04/2019
 */

package com.estes.myestes.rating.utils;

/**
 * Constants
 */
public interface RatingConstants
{
	public static final String APP_CONFIG = "rating.config";
	public static final String LOGGER = "logger";
	public static final String LOCATION = "location";
	public static final String REDIRECT = "redirect";
	public static final String BILL_OF_LADING_APP = "bol";
	public static final String PICKUP_REQUEST_APP = "pickup";
	
	public static final String PICKUP_START_TIME ="08:00 AM";
	public static final String PICKUP_CLOSE_TIME = "05:30 PM";
	
	public static final String MASTER_QUOTE_FAILED_ERROR = "QUE0066";
	/**
     * LTL rate quote
     */
	public static final String LTL_QUOTE = "LTL";
	/**
     * Time critical (TC) rate quote
     */
	public static final String TIME_CRITICAL_QUOTE = "TC";
	/**
     * Volume truckload (VTL) rate quote
     */
	public static final String VTL_QUOTE = "VTL";

	public static String SHOW_PRICE = "S";

	/**
	 * Default error code for ReST service calls
	 */
	public static final String DEFAULT_ERROR_CODE = "error";
	/**
	 * Default error message for ReST service calls
	 */
	public static final String DEFAULT_ERROR_MSG = "An unexpected error occurred.";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
