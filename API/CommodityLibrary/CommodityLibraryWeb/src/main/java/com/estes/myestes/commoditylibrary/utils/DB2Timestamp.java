/**
 * @author: Lakshman K
 *
 * Creation date: 12/17/2018
 *
 */

package com.estes.myestes.commoditylibrary.utils;

import java.sql.Timestamp;

/**
 * Timestamp in DB2 database
 *
 * @tag		  Date		Description
 * ----		  ----		-----------
 */
public class DB2Timestamp
{
	Timestamp time;

	/**
	 * Constructor
	 */
	public DB2Timestamp()
	{
		time = new Timestamp(System.currentTimeMillis());
	} // Constructor

	/**
	 * Constructor
	 * 
	 * @param millis Milliseconds since epoch of timestamp to set
	 */
	public DB2Timestamp(int millis)
	{
		time = new Timestamp(millis);
	} // Constructor

	/**
	 * Format timestamp for use with DB2 database.
	 */
	public static String formatTimestamp()
	{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		return formatTimestamp(now);
	} // formatTimestamp

	/**
	 * Format timestamp for use with DB2 database.
	 * 
	 * @param ts String representation of timestamp formatted to match database format
	 * - Replace space between date and time with a dash
	 * - Replace colons in time with periods
	 */
	public static String formatTimestamp(Timestamp ts)
	{
		StringBuffer time = new StringBuffer(ts.toString());
		time.replace(10, 11, "-");
		return time.toString().replace(':', '.');
	} // formatTimestamp

	/**
	 * Get timestamp
	 * 
	 * @return timestamp
	 */
	public Timestamp getTime()
	{
		return time;
	} // getTime

	/**
	 * Get timestamp
	 * 
	 * @return timestamp
	 */
	public String getFormattedTimestamp()
	{
		return formatTimestamp(time);
	} // getFormattedTimestamp

	/**
	 * Set timestamp
	 * 
	 * @param timestamp
	 */
	public void setTime(Timestamp tim)
	{
		this.time = tim;
	} // setTime
}