/**
 *
 */

package com.estes.myestes.recentshipments.util;

/**
 * Constants
 */
public interface RecentShipmentsConstant {
	/**
	 * Catch-all error code
	 */
	public static final String ERROR_CODE = "ERROR";
	
	/**
	 * JNDI Variables
	 */
	public static final String APP_JNDI = "jdbc/myestes";
	
	/**
	 * Logging Info
	 */
	String LOGGING_CONFIG = "recentshipments.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	
	/**
	 * Application settings
	 */
	public static final int PREVIOUS_DAYS = -14; // previous two weeks
	public static final int RECENT_SHIPMENT_RECORDS = 8 * 25; // 8 pages of 25 records each
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}