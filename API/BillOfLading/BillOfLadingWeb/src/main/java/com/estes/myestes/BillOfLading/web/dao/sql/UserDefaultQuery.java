package com.estes.myestes.BillOfLading.web.dao.sql;

import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.DATALIB;

public interface UserDefaultQuery {

	
	/**
	 * User default shipper, consignee, billing information, notifications, shipping label information are stored in the following table
	 */
	public static final String USER_DEFAULT_HEAD = "BOLDEFT1";
	
	/**
	 * USER Default Accessorial List are stored in the following table
	 */
	public static final String USER_DEFAULT_ACCESSORIAL = "BOLDEFT2";
	
	/**
	 * User Default Commodities are stored in the following table
	 */
	public static final String USER_DEFAULT_COMMODITY = "BOLDEFT3";
	
	
	public static final String COUNT_USER_DEFAULT_HEAD = "SELECT COUNT(*) FROM " + DATALIB + "."+USER_DEFAULT_HEAD+" WHERE BOL_USER_NAME=?";
	
	/**
	 * The following query is used to retrieve user default shipper information
	 */
	
	
	public static final String GET_USER_SHIPPER_INFORMATION=
				"SELECT "
				+ " BOL_SHIPPER_CO_NAME AS COMPANY_NAME,"
				+ " BOL_SHIPPER_FIRST_NAME AS FIRST_NAME,"
				+ " BOL_SHIPPER_LAST_NAME AS LAST_NAME,"
				+ " BOL_SHIPPER_LOCATION AS LOCATION,"
				+ " BOL_SHIPPER_PHONE AS PHONE,"
				+ " BOL_SHIPPER_EXTENSION AS PHONE_EXTENSION, "
				+ " BOL_SHIPPER_FAX AS FAX,"
				+ " BOL_SHIPPER_COUNTRY AS COUNTRY,"
				+ " BOL_SHIPPER_ADDRESS1 AS ADDRESS1,"
				+ " BOL_SHIPPER_ADDRESS2 AS ADDRESS2,"
				+ " BOL_SHIPPER_CITY AS CITY,"
				+ " BOL_SHIPPER_STATE AS STATE,"
				+ " BOL_SHIPPER_ZIP AS ZIP,"
				+ " BOL_SHIPPER_EMAIL AS EMAIL_ADDRESS"
				+ " FROM " + DATALIB + "."+USER_DEFAULT_HEAD+" WHERE BOL_USER_NAME=?";
	
	public static final String GET_USER_CONSIGNEE_INFORMATION=
			"SELECT "
			+ " BOL_CONSIGNEE_CO_NAME AS COMPANY_NAME,"
			+ " BOL_CONSIGNEE_FIRST_NAME AS FIRST_NAME,"
			+ " BOL_CONSIGNEE_LAST_NAME AS LAST_NAME,"
			+ " BOL_CONSIGNEE_LOCATION AS LOCATION,"
			+ " BOL_CONSIGNEE_PHONE AS PHONE,"
			+ " BOL_CONSIGNEE_EXTENSION AS PHONE_EXTENSION, "
			+ " BOL_CONSIGNEE_FAX AS FAX,"
			+ " BOL_CONSIGNEE_COUNTRY AS COUNTRY,"
			+ " BOL_CONSIGNEE_ADDRESS1 AS ADDRESS1,"
			+ " BOL_CONSIGNEE_ADDRESS2 AS ADDRESS2,"
			+ " BOL_CONSIGNEE_CITY AS CITY,"
			+ " BOL_CONSIGNEE_STATE AS STATE,"
			+ " BOL_CONSIGNEE_ZIP AS ZIP,"
			+ " BOL_CONSIGNEE_EMAIL AS EMAIL_ADDRESS"
			+ " FROM " + DATALIB + "."+USER_DEFAULT_HEAD+" WHERE BOL_USER_NAME=?";
	
	
	
	public static final String GET_USER_BILLING_INFORMATION=
			"SELECT "
					+ " BOL_BILL_TO_THRD as BILL_TO,"
					+ " BOL_BILL_TO_TERM as FEE,"
					+ " BOL_BILL_TO_CO_NAME AS COMPANY_NAME,"
					+ " BOL_BILL_TO_FIRST_NAME AS FIRST_NAME,"
					+ " BOL_BILL_TO_LAST_NAME AS LAST_NAME,"
					+ " BOL_BILL_TO_LOCATION AS LOCATION,"
					+ " BOL_BILL_TO_PHONE AS PHONE,"
					+ " BOL_BILL_TO_EXTENSION AS PHONE_EXTENSION, "
					+ " BOL_BILL_TO_FAX AS FAX,"
					+ " BOL_BILL_TO_COUNTRY AS COUNTRY,"
					+ " BOL_BILL_TO_ADDRESS1 AS ADDRESS1,"
					+ " BOL_BILL_TO_ADDRESS2 AS ADDRESS2,"
					+ " BOL_BILL_TO_CITY AS CITY,"
					+ " BOL_BILL_TO_STATE AS STATE,"
					+ " BOL_BILL_TO_ZIP AS ZIP,"
					+ " BOL_BILL_TO_EMAIL AS EMAIL_ADDRESS"
					+ " FROM " + DATALIB + "."+USER_DEFAULT_HEAD+" WHERE BOL_USER_NAME=?";
	
	public static final String GET_USER_COMMODITIES=" SELECT "
			+ " SEQUENCE_ID AS COMMODITY_ID, "
			+ " HAZ_MAT_FLAG AS HAZMAT,"
			+ " NUMBER_OF_PACKAGES AS GOODS_UNIT, "
			+ " PACKAGE_TYPE AS GOODS_TYPE,"
			+ " SHIPMENT_WEIGHT AS GOODS_WEIGHT,"
			+ " SHIPMENT_CLASS AS SHIPMENT_CLASS,"
			+ " NAT_MOTOR_FGT_CLASS AS NMFC,"
			+ " NMFC_EXTENSION AS NMFC_EXT,"
			+ " VICS_NUMBER_OF_PACKAGES AS NUMBER_OF_PACKAGE,"
			+ " VICS_PACKAGE_TYPE AS PACKAGE_TYPE,"
			+ " COMMODITY_DESCRIPTION AS DESCRIPTION,"
			+ " '' AS HEIGHT,"
			+ " '' AS WEIGHT,"
			+ " '' AS LENGTH"
			+ " FROM " + DATALIB + "."+USER_DEFAULT_COMMODITY+" WHERE BOL_USER_NAME=?";
	
	public static final String GET_USER_COMMODITY_INFORMATION =
			"SELECT "
			+ " DISTINCT t1.BOL_USER_NAME AS USER_NAME,"
			+ " t1.HAZMAT_CONTRACT AS CONTACT_NAME,"
			+ " t1.HAZMAT_PHONE AS PHONE,"
			+ " t1.HAZMAT_PHONE_EXTENSION AS PHONE_EXT,"
			+ " t2.BOL_TOT_CUBE AS TOTAL_CUBE,"
			+ " t2.BOL_SPEC_INSTR AS SPECIAL_INS"
			+ " FROM " + DATALIB + "."+USER_DEFAULT_COMMODITY+" AS t1"
			+ " LEFT JOIN " + DATALIB + "."+USER_DEFAULT_HEAD+" AS t2"
			+ " ON t1.BOL_USER_NAME=t2.BOL_USER_NAME"
			+ " WHERE t1.BOL_USER_NAME=?";
	
	
	public static final String GET_USER_ACCESSORIALS=
			"SELECT "
			+ " ACCESSORIAL_CODE AS CODE, "
			+ " '' AS DESCRIPTION,"
			+ "	'Y' AS ON_SCR "
			+ " FROM " + DATALIB + "."+USER_DEFAULT_ACCESSORIAL+" "
			+ " WHERE BOL_USER_NAME=?";
	
	
	
	
	
	public static final String GET_USER_SHIPPING_LABEL =
			"SELECT "
			+ " BOL_BILL_TO_LABEL_OPT AS LABEL_TYPE ,"
			+ " BOL_BILL_TO_LABEL_SW AS START_LABEL,"
			+ " BOL_BILL_TO_LABEL_NBR AS NO_OF_LABEL"
			+ " FROM " + DATALIB + "."+USER_DEFAULT_HEAD+""
			+ " WHERE BOL_USER_NAME=?";
	
	
	public static final String GET_USER_NOTIFICATIONS =
			 		 "   SELECT   "  + 
					 "   		BOL_SHIPPER_EMAIL AS SHIPPER_EMAIL,	     "  + 
					 "   	    BOL_ES_BOL AS SHIPPER_EMAIL_BOL_UPDATE,   "  + 
					 "   		BOL_ES_TRACKING_UPDATES AS SHIPPER_EMAIL_TRACKING_UPDATE,   "  + 
					 "   		BOL_ES_PICKUP_NOTICE AS SHIPPER_EMAIL_PICKUP_NOTICE,   "  + 
					 "   		BOL_ES_SHIPPING_LABELS AS SHIPPER_EMAIL_SHIPPING_LABEL,   "  + 
					 "   		BOL_FS_BOL AS SHIPPER_FAX_BOL_UPDATE,   "  + 
					 "   		BOL_SHIPPER_FAX AS SHIPPER_FAX,  "  + 

					 "   		BOL_CONSIGNEE_EMAIL  AS CONSIGNEE_EMAIL,  "  + 
					 "   		BOL_EC_BOL  AS CONSIGNEE_EMAIL_BOL_UPDATE,   "  + 
					 "   		BOL_EC_TRACKING_UPDATES AS CONSIGNEE_EMAIL_TRACKING_UPDATE,   "  + 
					 "   		BOL_EC_PICKUP_NOTICE AS CONSIGNEE_EMAIL_PICKUP_NOTICE,   "  + 
					 "   		BOL_EC_SHIPPING_LABELS AS CONSIGNEE_EMAIL_SHIPPING_LABEL,   "  + 
					 "   		BOL_FC_BOL AS CONSIGNEE_FAX_BOL_UPDATE,  "  + 
					 "   		BOL_CONSIGNEE_FAX  AS CONSIGNEE_FAX,  "  + 
					 
					 "   		BOL_BILL_TO_EMAIL  AS THIRD_PARTY_EMAIL,  "  + 
					 "   		BOL_TP_BOL  AS THIRD_PARTY_EMAIL_BOL_UPDATE,   "  + 
					 "   		BOL_TP_TRACKING_UPDATES AS THIRD_PARTY_EMAIL_TRACKING_UPDATE,   "  + 
					 "   		BOL_TP_PICKUP_NOTICE AS THIRD_PARTY_EMAIL_PICKUP_NOTICE,   "  + 
					 "   		BOL_TP_SHIPPING_LABEL AS THIRD_PARTY_EMAIL_SHIPPING_LABEL,   "  + 
					 "   		BOL_FT_BOL AS THIRD_PARTY_FAX_BOL_UPDATE,  "  + 
					 "   		BOL_BILL_TO_FAX  AS THIRD_PARTY_FAX,  "  + 
					 
					 "   		BOL_EO_EMAIL_ADDRESS  AS OTHER_EMAIL,   "  + 
					 "   		BOL_EO_BOL  AS OTHER_EMAIL_BOL_UPDATE,   "  + 
					 "   		BOL_EO_TRACKING_UPDATES AS OTHER_EMAIL_TRACKING_UPDATE,   "  + 
					 "   		BOL_EO_PICKUP_NOTICE AS OTHER_EMAIL_PICKUP_NOTICE,   "  + 
					 "   		BOL_EO_SHIPPING_LABELS AS OTHER_EMAIL_SHIPPING_LABEL,  "  + 
					 "   		BOL_FO_BOL AS OTHER_FAX_BOL_UPDATE,   "  + 
					 "   		BOL_FO_PHONE AS OTHER_FAX  "  + 
					 
					 "   	FROM " + DATALIB + "."+USER_DEFAULT_HEAD + 
					 "  	WHERE BOL_USER_NAME=? ";

}
