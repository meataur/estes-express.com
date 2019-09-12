/**
 * @author: Todd Allen
 *
 * Creation date: 06/15/2018
 */

package com.estes.myestes.shiptrack.utils;

/**
 * Shipment tracking app constants
 */
public interface AppConstants
{
	String APP_CONFIG = "shipmenttracking.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	public static final String IMAGING = "imaging";
	public static final String IMAGING_SERVER = "webserver";

	public static final String APP_EXT_PROP_IMAGE_WEBSERVER = "imageWebServer";
	
	/** Search criteria delimiter. */
	public static final String DELIMITER = "£";

	/**
	 * Default error message for ReST service calls
	 */
	String DEFAULT_ERROR = "An unexpected error occurred.";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
