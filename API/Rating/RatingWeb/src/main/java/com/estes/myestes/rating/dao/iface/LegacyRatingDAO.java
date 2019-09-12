/**
 * @author: Todd Allen
 *
 * Creation date: 03/29/2019
 */

package com.estes.myestes.rating.dao.iface;

import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for Net.Data rating data
 */
public interface LegacyRatingDAO extends BaseDAO
{
	/**
	 * Get rate quote data for given ID
	 * 
	 * @return {@link RateQuote}
	 */
	public RateQuote getQuoteData(int num) throws RatingException;
}
