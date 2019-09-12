/**
 * @author: Todd Allen
 *
 * Creation date: 11/19/2018
 */

package com.estes.services.common.dao.iface;

import com.estes.services.common.exception.ServiceException;

public interface CustomerDAO extends BaseDAO
{
	/**
	 * Call SPROC to get customer account type code
	 * 
	 * @param account Account code/number
	 * @return Account type code
	 */
	public String getAccountTypeByAccount(String account) throws ServiceException;

	/**
	 * Call SPROC to determine if account is payor on PRO
	 * 
	 * @param account Account code/number
	 * @param pro PRO
	 * @return Indicator as to whether account is payor on shipment
	 */
	public boolean isAccountPayor(String acc, String pro) throws ServiceException;
}
