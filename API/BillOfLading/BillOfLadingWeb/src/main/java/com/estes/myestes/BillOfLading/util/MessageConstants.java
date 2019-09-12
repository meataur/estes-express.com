package com.estes.myestes.BillOfLading.util;

/**
 * Constants related to messages.
 * 
 */
public interface MessageConstants {

	String PICKUP_CACHE_ERROR = "Error retrieving or populating pickup cache.";
	String PICKUP_CACHE_USER_ROLES_ERROR = "Error retrieving or populating pickup cache user roles.";
	String PICKUP_CACHE_PICKUP_INSTRUCTIONS_ERROR = "Error retrieving or populating pickup cache pickup instructions.";
	String PICKUP_CACHE_SHIPMENT_OPTIONS_ERROR = "Error retrieving or populating pickup cache shipment options.";
	String PICKUP_CACHE_SERVICE_LEVELS_ERROR = "Error retrieving or populating pickup cache service levels.";
	
	String PICKUP_CACHE_REFRESH_START = "Initiating PICKUP Reference data cache REFRESH process.";
	String PICKUP_CACHE_REFRESH_END = "Successfully completed PICKUP Reference data cache REFRESH process.";
	String PICKUP_CACHE_DESTROY_START = "Initiating PICKUP Reference data cache DESTROY process.";
	String PICKUP_CACHE_DESTROY_END = "Successfully completed PICKUP Reference data cache DESTROY process.";
	String PICKUP_CACHE_MAX_DAYS = "Error retrieving maximum days allowed for pickups.";
	String PICKUP_CACHE_MAX_WEIGHT="Error retrieving maximum shipment weight";
	String PICKUP_CACHE_BUSINESS_ERRORS = "Error retrieving business error field mapping.";
	
	String PICKUP_PREVIOUS_USER_REQUESTS_ERROR = "Error retrieving previous user requests.";
	String PICKUP_PREVIOUS_SHIPPER_REQUESTS_ERROR = "Error retrieving previous shipper requests.";
	String PICKUP_PREVIOUS_SHPIMENT_REQUESTS_ERROR = "Error retrieving previous shipment requests.";
	String HAS_PREVIOUS_ADDRESSES_ERROR = "Error retrieving previous addresses.";
	String SAVE_ERROR = "Error saving data.";
	String PREFILL_SHIPPER_INFO_ERROR = "Error retrieving prefill shipper info.";
	
	String WEB_GROUP_ACCOUNTS_ERROR = "Error retrieving web group accounts";
	String GROUP91_ACCOUNTS_ERROR = "Error retrieving 91 group accounts";
	
	String ERROR_OBJ_NULL_MSG = "Errors object must not be null";
	
	// Input validation error messages are defined in \resources\messages.properties
	String PICKUP_001 = "PICKUP_001_MSG";
	String PICKUP_002 = "PICKUP_002_MSG";
	String PICKUP_003 = "PICKUP_003_MSG";
	String PICKUP_004 = "PICKUP_004_MSG";
	String PICKUP_005 = "PICKUP_005_MSG";
	String PICKUP_006 = "PICKUP_006_MSG";
	String PICKUP_007 = "PICKUP_007_MSG";
	
	Integer SPECIAL_INSTRUCTIONS_MAX_LENGTH = 256;
	Integer CONTACT_NAME_MAX_LENGTH = 30;

	String TOKEN = "Token: ";
	String PICKUP_008 = "PICKUP_008_MSG";
	String PICKUP_009 = "PICKUP_009_MSG";
	String PICKUP_010 = "PICKUP_010_MSG";
	String PICKUP_011 = "PICKUP_011_MSG";
	String PICKUP_012 = "PICKUP_012_MSG";
	String PICKUP_013 = "PICKUP_013_MSG";
	String PICKUP_014 = "PICKUP_014_MSG";
	String PICKUP_015 = "PICKUP_015_MSG";
	String PICKUP_016 = "PICKUP_016_MSG";
	String PICKUP_017 = "PICKUP_017_MSG";
	String PICKUP_018 = "PICKUP_018_MSG";
	String PICKUP_LIMIT = "PICKUP_LIMIT_MSG";	 
	String PICKUP_300 = "PICKUP_300_MSG";
	
	String PKE2014 = "PKE2014_MSG";
	String PKE2104 = "PKE2104_MSG";
	String PKE2015 = "PKE2015_MSG";
	String PKE2017 = "PKE2017_MSG";
	String PKE2018 = "PKE2018_MSG";
	String PKE2082 = "PKE2082_MSG";
	String PKE2068 = "PKE2068_MSG";
	String PKE2098 = "PKE2098_MSG";
	String PKE2048 = "PKE2048_MSG";
	String PKE2049 = "PKE2049_MSG";
	String PKE2050 = "PKE2050_MSG";
	String PKE2051 = "PKE2051_MSG";
	String PKE2052 = "PKE2052_MSG";
	String PKE2063 = "PKE2063_MSG";
	String PKE2064 = "PKE2064_MSG";
	String PKE2065 = "PKE2065_MSG";
	String PKE2081 = "PKE2081_MSG";
	String PKE2087 = "PKE2087_MSG";
	String PKE2092 = "PKE2092_MSG";
	String PKE2099 = "PKE2099_MSG";
	String PKE2097 = "PKE2097_MSG";
	String PKE2100 = "PKE2100_MSG";
	String PKE2062 = "PKE2062_MSG";
	String GTE0001 = "GTE0001_MSG";
	String BOL0003 = "BOL0003_MSG";
	String BOL0103 = "BOL0103_MSG";
	String BOL0101 = "BOL0101_MSG";
	String BOL0108 = "BOL0108_MSG";
	String BOL0104 = "BOL0104_MSG";
	String BOL0008 = "BOL0008_MSG";
	String BOL0017 = "BOL0017_MSG";
	String BOL0007 = "BOL0007_MSG";
	String BOL0018 = "BOL0018_MSG";
	String BOL0015 = "BOL0015_MSG";
	String BOL0102 = "BOL0102_MSG";
	String BOL0145 = "BOL0145_MSG";
	String BOL0153 = "BOL0153_MSG";
	String BOL0156 = "BOL0156_MSG";
	String BOL0110 = "BOL0110_MSG";
	String BOL0095 = "BOL0095_MSG";
	String BOL0096 = "BOL0096_MSG";
	String BOL0097 = "BOL0097_MSG";
	String BOL0098 = "BOL0098_MSG";
	String TLE0041 =  "TLE0041_MSG";
	String PICKUP_ACTION_FAILED_WITH_TOKEN = "An error has occurred within the LTL Pickup Request application. Please contact Customer Care for assistance. Error Token: ";
	String PICKUP_ACTION_FAILED = "An error has occurred within the Pickup Request application. Please contact Customer Care for assistance.";
	String PICKUP_APP_ERROR = "PICKUP_APP_ERROR";
	String GENERIC_ERROR =  "GENERIC_ERROR";
}