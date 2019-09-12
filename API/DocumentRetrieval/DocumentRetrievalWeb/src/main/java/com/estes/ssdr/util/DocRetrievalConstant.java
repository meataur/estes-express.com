/**
 * @author: Todd Allen
 *
 * Creation date: 04/05/2016
 *
 */

package com.estes.ssdr.util;

/**
 * Constants
 */
public interface DocRetrievalConstant
{
	/**
	 * Service call source
	 */
	public static final String WEB_SOURCE = "W";

	/**
	 * Catch-all error code
	 */
	public static final String ERROR_CODE = "ERROR";

	String LOGGING_CONFIG = "documentretrieval.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}