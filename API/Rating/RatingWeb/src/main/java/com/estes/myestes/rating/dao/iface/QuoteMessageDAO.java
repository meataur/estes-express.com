/**
 * @author: Todd Allen
 *
 * Creation date: 02/19/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;
import java.util.Map;

import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rate quote informational (non-error) messages
 */
public interface QuoteMessageDAO extends BaseDAO
{
	public static final String ERROR_TYPE = "E";
	public static final String INFO_TYPE = "I";
	public static final String WARNING_TYPE = "W";

	/**
	 * Get error messages for quote request
	 * 
	 * @param idx Error index
	 * @return Map of error codes/messages
	 */
	public Map<String, String> getErrorMessages(int idx) throws RatingException;

	/**
	 * Get informational messages for quote
	 * 
	 * @param id Unique quote ID
	 * @return List of messages
	 */
	public List<String> getQuoteMessages(String id) throws RatingException;
}
