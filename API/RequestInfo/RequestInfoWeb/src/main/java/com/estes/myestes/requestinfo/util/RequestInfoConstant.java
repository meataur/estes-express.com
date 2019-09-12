/**
 *
 */

package com.estes.myestes.requestinfo.util;

/**
 * Constants
 */
public interface RequestInfoConstant
{
	/**
	 * Catch-all error code
	 */
	public static final String ERROR_CODE = "ERROR";
	
	/**
	 * JNDI Variables
	 */
	public static final String APP_JNDI = "jdbc/msrequestinfods";
	public static final String APP_MAIL_JNDI = "mail/msrequestinfoemail";
	
	/**
	 * Config Info
	 */
	String CONFIG = "requestinfo.config";
	String LOGGER = "logger";
	String LOCATION = "location";	
	String ADDRESS = "address";
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}