/**
 * @author: Todd Allen
*
* Creation date: 04/10/2018
*/

package com.estes.myestes.accountrequest.utils;

/**
 * Constants
 */
public interface AppConstants
{
	public static final String APP_CONFIG = "accountrequest.config";
	public static final String LOGGER = "logger";
	public static final String LOCATION = "location";
	public static final String MAIL = "mail";
	public static final String MAIL_TO = "to";

	public static final String APP_MAIL_JNDI = "mail/msaccountnoreqemail";

	/*
	 * Default error code for ReST service calls
	 */
	public static String DEFAULT_ERROR_CODE = "ERROR";

	/*
	 * Error message for bad/blocked e-mail address
	 */
	public static String DEFAULT_APP_ERROR = "We're sorry, but there is a problem with your request. Please contact Estes Customer Care at 804-353-1900, Ext. 2500 for more information.";
	/*
	 * Error message for bad/Blocked e-mail address
	 */
	public static String DEFAULT_SYS_ERROR = "An unexpected error occurred.";

	/*
	 * Success message
	 */
	public static String SUCCESS_MESSAGE = "Your message has been sent. A customer care representative will respond as soon as possible.";

	public static String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
}