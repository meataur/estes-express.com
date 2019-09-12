/**
 * @author: Todd Allen
 *
 * Creation date: 06/15/2018
 */

package com.estes.myestes.signup.utils;

/**
 * Constants
 */
public interface AppConstants
{
	public static final String APP_CONFIG = "myestessignup.config";
	public static final String LOGGER = "logger";
	public static final String LOCATION = "location";
	public static final String MAIL = "mail";
	public static final String MAIL_FROM = "from";
	public static final String MAIL_TO = "to";
	public static final String MAIL_SUBJECT = "subject";

	public static final String APP_MAIL_JNDI = "mail/msmyestessignupemail";

	/*
	 * Default error code for ReST service calls
	 */
	public static String DEFAULT_ERROR_CODE = "ERROR";

	/*
	 * Error message for bad/Blocked e-mail address
	 */
	public static String DEFAULT_ERROR_MESSAGE = "An unexpected error occurred.";
	
	public static String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
