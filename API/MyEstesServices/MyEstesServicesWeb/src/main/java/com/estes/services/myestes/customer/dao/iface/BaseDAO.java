/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.customer.dao.iface;

public interface BaseDAO
{
	/**
     * Database library (schema) name
     */
	public static String DATA_SCHEMA = "fbfiles";

	/**
	 * Program object library (schema) name
	 */
	public static String PGM_SCHEMA = "fbpgms";

	/**
	 * Alternate program object library (schema) name
	 */
	public static String ALT_PGM_SCHEMA = "estesrtgy2";
	
	/**
	 * Sub accounts stored procedure
	 */
	public static String SP_SUBACCOUNTS = "SAG10Q110";
	
	/**
	 * Is sub account of stored procedure
	 */
	public static String SP_ISUBOF = "SAQ144B";
}
