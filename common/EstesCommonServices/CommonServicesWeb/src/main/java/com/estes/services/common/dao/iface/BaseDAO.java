/**
 * @author: Todd Allen
 *
 * Creation date: 10/10/2018
 * 
 */

package com.estes.services.common.dao.iface;

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
