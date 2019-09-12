/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.rating.dto.RateSearch;
import com.estes.myestes.rating.dto.RateSummary;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rating services
 */
public interface RateHistoryDAO extends BaseDAO
{
	/**
	 * Default error code for ReST service calls
	 */
	public static int MAX_HISTORY_ROWS = 500;

	/**
	 * Get # rows found for rate quote history search
	 * 
	 * @return Number of rows found
	 */
	public int getSearchTotal(RateSearch search);

	/**
	 * Get all service levels for rate quote
	 * 
	 * @param refNum Quote reference number
	 * @return List of {@link RateSummary} objects
	 */
	public List<RateSummary> getAllServiceLevels(int refNum) throws RatingException;

	/**
	 * Search rate quote history or My Estes user
	 * 
	 * @param pageable {@link Pageable}
	 * @param search {@link RateSearch}
	 * @return List of {@link RateSummary}
	 */
	public List<RateSummary> searchQuoteHistory(Pageable pageable, RateSearch search) throws RatingException;

	public List<String> getAppsByRef(int ref);
}
