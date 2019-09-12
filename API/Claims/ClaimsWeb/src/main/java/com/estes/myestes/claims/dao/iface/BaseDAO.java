package com.estes.myestes.claims.dao.iface;

public interface BaseDAO {
	/**
     * Database library (schema) name
     */
	public static String DATA_SCHEMA = "FBFILES";

	/**
	 * Program object library (schema) name
	 */
	public static String PGM_SCHEMA = "fbpgms";

	/**
	 * Alternate program object library (schema) name
	 */
	public static String ALT_PGM_SCHEMA = "ESTESRTGY2";
	
	/**
	 * Is sub account of stored procedure
	 */
	public static String SP_ISUBOF = "SAQ144B";
	
	/**
	 * Is l2l shipment stored procedure
	 */
	public static String SP_ISL2L = "L2L_SQL_isTerminalL2L";
	
	/**
	 * get account type based off of code
	 */
	public static String SP_ACCOUNTTYPE = "GETACCOUNTTYPEBYACCOUNTNUMBER";
	
	/**
	 * Is shipment to party stored procedure
	 */
	public static String SP_ISHIPARTY = "SP_SHIPMENTPARTY";
	
	/**
	 * Claim filing create header stored procedure
	 */
	public static final String SP_CLAIM_HEADER = "ADD_CLAIM_HEADER_RECORDS";
	
	/**
	 * Claim detail table
	 */
	public static final String CLAIM_DETAIL_TABLE = "FBFILES.STAGING_CLAIM_DETAIL";
}