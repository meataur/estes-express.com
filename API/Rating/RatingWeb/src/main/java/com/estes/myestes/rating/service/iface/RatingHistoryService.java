/**
 * @author: Todd Allen
 *
 * Creation date: 03/01/2019
 */

package com.estes.myestes.rating.service.iface;

import java.util.List;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.rating.dto.RateSearch;
import com.estes.myestes.rating.dto.RateSummary;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Rate quote history services
 */
public interface RatingHistoryService
{
	/**
	 * Retrieve all historical quotes for given My Estes user
	 * 
	 * @param refNo Quote reference number
	 * @return List of {@link RateSummary}
	 */
	public List<RateSummary> retrieveQuoteSummaryData(int refNo) throws RatingException;

	/**
	 * Search quote history for given My Estes user
	 * 
	 * @param pageable {@link Pageable}
	 * @param search {@link RateSearch}
	 * @return List of {@link RateSummary}
	 */
	public Page<RateSummary> searchUserQuotes(Pageable pageable, RateSearch search) throws RatingException;
}
