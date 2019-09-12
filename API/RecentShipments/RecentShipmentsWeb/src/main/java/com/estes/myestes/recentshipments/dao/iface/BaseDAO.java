package com.estes.myestes.recentshipments.dao.iface;

public interface BaseDAO {
	/**
     * Database library (schema) name
     */
	public static String DATA_SCHEMA = "FBPGMS";

	/**
	 * Alternate program object library (schema) name
	 */
	public static String ALT_PGM_SCHEMA = "ESTESRTGY2";
	
	/**
	 * Recent shipments stored procedure
	 */
	public static String SP_RECENTSHIPMENTS = "SAG10Q100";
	
	/**
	 * Get default party stored procedure
	 */
	public static String SP_GETDEFAULTPARTY = "SP_GETCUSTDETAIL";
	
	/**
	 * Write default party stored procedure
	 */
	public static String SP_WRITEDEFAULTPARTY = "SP_WRITECUSTDETAIL";
	
	/**
	 * Update default party stored procedure
	 */
	public static String SP_UPDATEDEFAULTPARTY = "SP_UPDATECUSTDETAIL";
}