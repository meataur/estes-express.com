/**
 * @author: Todd Allen
 *
 * Creation date: 02/19/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.myestes.rating.dto.Accessorial;
import com.estes.myestes.rating.dto.AccessorialPricing;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rate quote accessorial data
 */
public interface AccessorialDAO extends BaseDAO
{
	/**
	 * Get accessorial data
	 * 
	 * @return List of {@link Accessorial}
	 */
	public List<Accessorial> getAccessorials() throws RatingException;

	/**
	 * Get accessorial data for quote
	 * 
	 * @param id Unique quote ID
	 * @return List of {@link AccessorialPricing}
	 */
	public List<AccessorialPricing> getQuoteAccessorials(String id) throws RatingException;

	/**
	 * Stage accessorial data received in request
	 * 
	 * @param id Unique quote ID
	 * @return List of {@link Accessorial} received in rating request
	 */
	public void stageAccessorials(String id, List<Accessorial> accessorials) throws RatingException;
}
