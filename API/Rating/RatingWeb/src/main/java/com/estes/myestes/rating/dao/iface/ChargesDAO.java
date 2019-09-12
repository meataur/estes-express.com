/**
 * @author: Todd Allen
 *
 * Creation date: 03/27/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.myestes.rating.dto.Charge;

/**
 * Data access for rate quote charges
 */
public interface ChargesDAO extends BaseDAO
{
	/**
	 * Get rate quote charges
	 * 
	 * @param id Quote ID
	 * @return Customer name
	 */
	public List<Charge> getQuoteCharges(String id);
}
