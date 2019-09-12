package com.estes.framework.util;

import com.ibm.websphere.cache.EntryInfo;

/**
 * Constants for framework.
 * 
 * @author singhpa
 * 
 */
public interface FrameworkConstant {

	public static String COUNTRY_MX = "MX";
	public static String COUNTRY_US = "US";
	public static String COUNTRY_CN = "CN";

	public static String POINT_SUGGEST_CITY_REGEX = "^[a-zA-Z\\s\\.]+$";
	// An address containing a number is assumed to contain a zip/postal code
	public static String POINT_SUGGEST_ZIP = ".*\\d.*";

	public static String POINT_SUGGEST_RECORD_COUNT = "pointSuggestRecordsPerQuery";

	public static int POINT_SUGGEST_DEFAULT_RECORDS = 10;
	public static int POINT_SUGGEST_MAX_RECORDS = 100;

	public static String NO_POINTS_FOUND = "No points found.";

	public static String YES = "Y";
	// Start - Caching related Constants
	
	public static final String ESTES_DATA_TIMER_CACHE_AREA_NAME = "estesDataTimer";
	public static final String DATA_INVALIDATION_TTL = "timeToLive";
	public static final String ESTES_DATA_INVALIDATION_CONFIG = "estesDataInvalidationConfig";
	public static final String SYSTEM_PROPERTY_USE_EH_CACHE = "useEHCache";
	public static final String WAS_CACHE_JNDI_SUFFIX = "java:comp/env/cache";
	public static final String SEPARATOR = "/";
	public static final int DEFAULT_TTL = 14400; // 4 hrs minutes.
	public static final int DEFAULT_PRIORITY = 1;
	public static final int DEFAULT_SHARED_TYPE = EntryInfo.SHARED_PUSH;
	
	// End - Caching related Constants
}
