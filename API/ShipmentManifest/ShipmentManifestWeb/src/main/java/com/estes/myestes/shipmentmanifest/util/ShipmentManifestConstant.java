/**
 *
 */

package com.estes.myestes.shipmentmanifest.util;

/**
 * Constants
 */
public interface ShipmentManifestConstant
{

	/**
	 * Catch-all error code
	 */
	public static final String ERROR_CODE = "ERROR";
	
	/**
	 * JNDI Variables
	 */
	public static final String APP_JNDI = "jdbc/myestes";
	public static final String APP_MAIL_JNDI = "mail/msshipmentmfemail";
	
	/**
	 * Account Info
	 */
	public static final String APP_DISPLAY_TIME_DATABASE_FORMAT = "HH:mm:ss";
	public static final String APP_DISPLAY_TIME_UI_FORMAT = "hh:mm a";
	public static final String ACCOUNT_TYPE_NATIONAL = "N";
	public static final String ACCOUNT_TYPE_GROUP = "G";
	public static final String ACCOUNT_TYPE_WEB = "W";
	public static final String ACCOUNT_TYPE_LOCAL = "L";

	/**
	 * File Info
	 */
	public static String FILE_PATH = System.getProperty("file.separator") + "tmp" + System.getProperty("file.separator") + "shipmentmanifests" + System.getProperty("file.separator");
	public static String EXCEL_FILE_EXTENSION = ".xls";
	public static String TEXT_FILE_EXTENSION = ".txt";
	public static String CSV_FILE_EXTENSION = ".csv";
	
	/**
	 * Config Info
	 */
	String CONFIG = "shipmentmanifest.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	String VALUE = "value";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}