/**
 * @author: Todd Allen
 *
 * Creation date: 02/20/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rate quote disclaimer messages
 */
public interface DisclaimerDAO extends BaseDAO
{
	/**
	 * Get disclaimer messages for quote
	 * 
	 * @return List of messages
	 */
	public List<String> getDisclaimers() throws RatingException;
}
