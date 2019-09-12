/**
 * @author: Todd Allen
 *
 * Creation date: 02/11/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.myestes.rating.dto.Commodity;
import com.estes.myestes.rating.dto.CommodityPricing;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rate quote commodity data
 */
public interface CommodityDAO extends BaseDAO
{
	/**
	 * Get commodity data for quote
	 * 
	 * @param id Unique quote ID
	 * @return List of {@link CommodityPricing}
	 */
	public List<CommodityPricing> getQuoteCommodities(String id) throws RatingException;

	/**
	 * Stage commodity data received in request
	 * 
	 * @param id Unique quote ID
	 */
	public void stageCommodities(String id, List<Commodity> commodities) throws RatingException;
}
