/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 * 
 */

package com.estes.myestes.shiptrack.dao.iface;

public interface BaseDAO
{
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
	public static String ALT_PGM_SCHEMA = "estesrtgy2";
}
