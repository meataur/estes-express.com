package com.estes.myestes.BillOfLading.web.dao.sql;

import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.DATALIB;

public interface TemplateQuery {
	
	public static final String TEMPLATE_HEADER_TABLE  = "EBG10P300";
	
	public static final String TEMPLATE_REFERENCE_TABLE="EBG10P303";
	
	public static final String TEMPLATE_COMMODITY_TABLE="EBG10P304";
	
	public static final String TEMPLATE_ACCESSORIAL_TABLE="EBG10P308";
	
	
	/**
	 * This query is for Template Listing, Pagination, Sorting & Filtering
	 * 
	 * 
	 * 
	
	
	
	SELECT  CASE WHEN  8=LENGTH(regexp_replace(t.BOL_DATE,'\D',''))
			THEN 
				SUBSTR(regexp_replace(t.BOL_DATE,'\D',''),5,4) ||
				SUBSTR(regexp_replace(t.BOL_DATE,'\D',''),1,2) ||
				SUBSTR(regexp_replace(t.BOL_DATE,'\D',''),3,2) 
			ELSE 
				null
		    END AS B_DATE,

		    t.* 
		    FROM FBFILES.EBG10P300 as t
	
	
	
	
	 */
	
	public static final String GET_TEMPlATE_LIST=""
			+ " SELECT * FROM ( "
			+ " SELECT TEMPLATENAME,"
			+ " BOLNUMBER, "
			+ " CASE WHEN LENGTH(BOLDATE)=8 "
			+ " AND SUBSTR(BOLDATE,1,2) > '00'"
			+ " AND SUBSTR(BOLDATE,1,2) < '12'"
			+ " AND SUBSTR(BOLDATE,3,2) > '00'"
			+ " AND SUBSTR(BOLDATE,3,2) < '31'"
			+ " AND SUBSTR(BOLDATE,5,4) > '0000'"
			+ " AND SUBSTR(BOLDATE,5,4) < '2050'"
			+ " THEN  DATE(TO_DATE(BOLDATE,'MMDDYYYY'))"
			+ " ELSE null "
			+ " END AS BOLDATE,"
			+ " PRONUMBER,"
			+ " SHIPPER,"
			+ " SHIP_FIRST_NAME,"
			+ " SHIP_LAST_NAME,"
			+ " CONSIGNEE,"
			+ " CONS_FIRST_NAME,"
			+ " CONS_LAST_NAME,"
			+ " USERNAME"
			+ " FROM ( "
			+ " SELECT"
			+ " TP_DESC AS TEMPLATENAME,"
			+ " BOL_NUMBER AS BOLNUMBER,"
			+ " regexp_replace(bol_date,'\\D+','') AS BOLDATE,"
			+ " CONCAT(CONCAT(PRO_OT,'-'), PRO_NUMBER) AS PRONUMBER,"
			+ " SHIP_COMPANY AS SHIPPER,"
			+ " SHIP_FIRST_NAME,"
			+ " SHIP_LAST_NAME,"
			+ " CONS_COMPANY_NAME AS CONSIGNEE,"
			+ " CONS_FIRST_NAME,"
			+ " CONS_LAST_NAME,USER_NAME AS USERNAME"
			+ " FROM "+DATALIB+"."+TEMPLATE_HEADER_TABLE
			+ " ) "
			+ " ) WHERE TRIM(TEMPLATENAME) !='' AND USERNAME = ?";
	
	
	
	public static final String COUNT_TEMPLATE = "SELECT COUNT(*) FROM "+DATALIB+"."+TEMPLATE_HEADER_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";
	
    public static final String GET_TEMPLATE_HEADER      = "SELECT * FROM "+DATALIB+"."+TEMPLATE_HEADER_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";
	
    public static final String GET_TEMPLATE_COMMODITY   = "SELECT "
			+ " SHIP_INFO_SEQUENCE AS COMMODITY_ID,"
			+ " HAZ_MAT AS HAZMAT,"
			+ " GOODS_QUANTITY AS GOODS_UNIT,"
			+ " GOODS_TYPE AS GOODS_TYPE,"
			+ " WEIGHT AS GOODS_WEIGHT,"
			+ " CLASS AS SHIPMENT_CLASS,"
			+ " NMFC AS NMFC,"
			+ " NMFC_SUB AS NMFC_EXT,"
			+ " SHIP_QUANTITY AS NUMBER_OF_PACKAGE,"
			+ " SHIP_GOODS_TYPE AS PACKAGE_TYPE,"
			+ " DESCRIPTION AS DESCRIPTION,"
			+ " DIM_HEIGHT AS HEIGHT,"
			+ " DIM_LENGTH AS WEIGHT,"
			+ " DIM_WIDTH AS LENGTH "
			+ " FROM "+DATALIB+"."+TEMPLATE_COMMODITY_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";
	
	
	public static final String GET_TEMPLATE_REFERENCE   = "SELECT "
			+ " REFERENCE_NO_SEQUENCE AS  REFERENCE_ID,"
			+ " REF_NO AS  REFERENCE_NUMBER,"
			+ " TYPE AS  REFERENCE_TYPE,"
			+ " CARTONS AS  CARTOON,"
			+ " WEIGHT AS  WEIGHT"
			+ " FROM "+DATALIB+"."+TEMPLATE_REFERENCE_TABLE
			+ " WHERE USER_NAME=? AND TEMPLATE_DESC=?";
	
	
	public static final String GET_TEMPLATE_ACCESSORIAL  = "SELECT "
			+ " ACCESSORIAL_CODE AS CODE, "
			+ " '' AS DESCRIPTION "
			+ " FROM "+DATALIB+"."+TEMPLATE_ACCESSORIAL_TABLE
			+ " WHERE USER_NAME=? AND TEMPLATE_DESC=? ";
	
	public static final String DELETE_TEMPLATE_HEADER = "DELETE FROM "+DATALIB+"."+TEMPLATE_HEADER_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";
	
	public static final String DELETE_TEMPLATE_REFERENCE = "DELETE FROM "+DATALIB+"."+TEMPLATE_REFERENCE_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";

	public static final String DELETE_TEMPLATE_COMMODITY = "DELETE FROM "+DATALIB+"."+TEMPLATE_COMMODITY_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";

	public static final String DELETE_TEMPLATE_ACCESSORIAL = "DELETE FROM "+DATALIB+"."+TEMPLATE_ACCESSORIAL_TABLE+" WHERE USER_NAME=? AND TEMPLATE_DESC=?";
}
