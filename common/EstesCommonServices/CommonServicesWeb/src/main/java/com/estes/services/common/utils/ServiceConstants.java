/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.common.utils;

/**
 * Constants
 */
public interface ServiceConstants
{
	String APP_CONFIG = "commonservices.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	String MAPS = "terminalMaps";
	String BASE_URL = "baseURL";

	/**
	 * Default error message for ReST service calls
	 */
	String DEFAULT_ERROR = "An unexpected error occurred.";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
