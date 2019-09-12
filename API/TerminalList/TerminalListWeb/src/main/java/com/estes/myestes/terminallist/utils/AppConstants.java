/**
 * @author: Shelender Singh
 *
 * Creation date: 09/12/2018
 */

package com.estes.myestes.terminallist.utils;

/**
 * Constants
 */
public interface AppConstants
{
	String APP_CONFIG = "terminalList.config";
	String LOGGER = "logger";
	String LOCATION = "location";

	public static final String APP_EXT_PROP_TERMINAL_TEMP_DIR = "tempDir";
	public static final String MAPS = "maps";
	public static final String BASE_URL = "baseURL";
	public static final String APP_MAIL_JNDI = "mail/msterminallistemail";
	
	public static final String APP_JNDI = "jdbc/myestes";
	
	public static final String ALL_COUNTRIES = "**";
	public static final String MEXICO="MX";
	
	/**
	 * File Info
	 */
	public static String EXCEL_FILE_EXTENSION = ".xls";
	public static String CSV_FILE_EXTENSION = ".csv";
	
	/**
	 * Catch-all error code
	 */
	public static final String ERROR_CODE = "ERROR";
	
	/**
	 * Default error message for ReST service calls
	 */
	String DEFAULT_ERROR = "An unexpected error occurred.";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
