/**
 * @author: Lakshman K
 *
 * Creation date: 11/12/2018
 */

package com.estes.myestes.commoditylibrary.utils;

/**
 * Constants
 */
public interface CommodityLibraryConstant
{
	String APP_CONFIG = "commoditylibrary.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	
	/**
     * Library (schema) name
     */
	public static String DATA_SCHEMA = "FBFILES";
	/**
     * Library (schema) name
     */
	public static String PGM_SCHEMA = "FBPGMS";

    /**
     * Commodity master table
     */
	public static String COMMODITIES = "ebg10p116";
    /**
     * Commodity work table
     */
	public static String TEMP_COMMODITIES = "ebg10p216";

	public static final String CALLER = "EBG10O121";
	
	/**
	 * Alternate program object library (schema) name
	 */
	public static String ALT_PGM_SCHEMA = "estesrtgy2";
	
	public static String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
