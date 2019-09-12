/**
 * @author: Todd Allen
 *
 * Creation date: 03/26/2019
 */

package com.estes.myestes.rating.dao.iface;

/**
 * Data access for customer data
 */
public interface CustomerDAO extends BaseDAO
{
	/**
	 * Get customer name
	 * 
	 * @param acct Customer account code/number
	 * @return Customer name
	 */
	public String getName(String acct);
}
