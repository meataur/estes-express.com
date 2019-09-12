package com.estes.myestes.BillOfLading.web.dao.sql;

import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.DATALIB;

public interface DraftQuery {
	
	public static final String DRAFT_HEADER_TABLE       = "EBG10P400";
	
	public static final String DRAFT_REFERENCE_TABLE    = "EBG10P403";
	
	public static final String DRAFT_COMMODITY_TABLE    = "EBG10P404";
	
	public static final String DRAFT_ACCESSORIAL_TABLE  = "EBG10P408";
	
	/**
	 * This query is for Draft Listing, Pagination, sorting & filtering
	 */
	public static final String GET_DRAFT_LIST = 
			" select * from ( "
			+ " select bol_num as bolNumber,"
			+ " regexp_replace(bol_date,'\\D+','') as bolDate,"
			+ " CONCAT(CONCAT(pro_ot,'-'),pro_num) AS proNumber,"
			+ " ship_company as shipper,"
			+ " cons_company_name as consignee,"
			+ " user_name as username"
			+ " from "+DATALIB+"."+DRAFT_HEADER_TABLE
			+ " ) where trim(bolNumber) !='' AND username = ?";
	
	public static final String COUNT_DRAFT           = "SELECT count(*) FROM "+DATALIB+"."+DRAFT_HEADER_TABLE+" WHERE USER_NAME=? AND BOL_NUMBER=?";
	
	public static final String GET_DRAFT_HEADER      = "SELECT * FROM "+DATALIB+"."+DRAFT_HEADER_TABLE+" WHERE USER_NAME=? AND BOL_NUMBER=?";
	
	public static final String GET_DRAFT_COMMODITY   = "SELECT "
			+ " SHIP_INFO_SEQUENCE AS COMMODITY_ID,"
			+ " HAZ_MAT AS HAZMAT,"
			+ " GOODS_QUANTITY AS GOODS_UNIT,"
			+ " GOODS_TYPE AS GOODS_TYPE,"
			+ " WEIGHT AS GOODS_WEIGHT,"
			+ " CLASS AS SHIPMENT_CLASS,"
			+ " NMFC AS NMFC,"
			+ " NMFC_SUB AS NMFC_EXT,"
			+ " SHIP_QUANTITY AS NUMBER_OF_PACKAGE,"
			+ " SHIP_TYPE AS PACKAGE_TYPE,"
			+ " DESCRIPTION AS DESCRIPTION,"
			+ " DIM_HEIGHT AS HEIGHT,"
			+ " DIM_LENGTH AS WEIGHT,"
			+ " DIM_WIDTH AS LENGTH"
			+ " FROM "+DATALIB+"."+DRAFT_COMMODITY_TABLE
			+ " WHERE USER_NAME=? AND BOL_NUMBER=?";
	
	
	public static final String GET_DRAFT_REFERENCE   = "SELECT "
			+ " REFERENCE_NO_SEQUENCE AS  REFERENCE_ID,"
			+ " REF_NO AS  REFERENCE_NUMBER,"
			+ " TYPE AS  REFERENCE_TYPE,"
			+ " CARTONS AS  CARTOON,"
			+ " WEIGHT AS  WEIGHT"
			+ " FROM "+DATALIB+"."+DRAFT_REFERENCE_TABLE
			+ " WHERE USER_NAME=? AND BOL_NUMBER=?";
	
	
	
	public static final String GET_DRAFT_ACCESSORIAL  = "SELECT "
			+ " ACCESSORIAL_CODE AS CODE, "
			+ " '' AS DESCRIPTION "
			+ " FROM "+DATALIB+"."+DRAFT_ACCESSORIAL_TABLE
			+ " WHERE USER_NAME=? AND BOL_NUMBER=?";

	public static final String DELETE_DRAFT_HEADER = "DELETE FROM "+DATALIB+"."+DRAFT_HEADER_TABLE+" WHERE USER_NAME=? AND BOL_NUMBER=?";
	
	public static final String DELETE_DRAFT_REFERENCE = "DELETE FROM "+DATALIB+"."+DRAFT_REFERENCE_TABLE+" WHERE USER_NAME=? AND BOL_NUMBER=?";

	public static final String DELETE_DRAFT_COMMODITY = "DELETE FROM "+DATALIB+"."+DRAFT_COMMODITY_TABLE+" WHERE USER_NAME=? AND BOL_NUMBER=?";

	public static final String DELETE_DRAFT_ACCESSORIAL = "DELETE FROM "+DATALIB+"."+DRAFT_ACCESSORIAL_TABLE+" WHERE USER_NAME=? AND BOL_NUMBER=?";

}
