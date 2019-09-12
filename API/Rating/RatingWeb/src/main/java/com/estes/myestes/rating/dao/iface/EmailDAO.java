/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.Map;

import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rating e-mail messages
 */
public interface EmailDAO extends BaseDAO
{
	/**
	 * Get e-mail properties
	 * 
	 * @return Map of mail properties/values
	 */
	public Map<String, String> getMailProps() throws RatingException;

	/**
	 * Update contact flag
	 * 
	 * @param id Quote ID
	 */
	public void updateContact(String id);
}
