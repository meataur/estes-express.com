/**
 * @author: Todd Allen
 *
 * Creation date: 11/20/2018
 */

package com.estes.services.common.service.iface;

import com.estes.services.common.exception.ServiceException;

/**
 * Estes common customer services
 */
public interface CustomerService
{
	/**
	 * Get Estes customer account type code
	 * 
	 * @param account Customer account code/number
	 * @return Account type code
	 */
	public String getCustomerAccountType(String account) throws ServiceException;

	/**
	 * Determine if account is payor on PRO
	 * 
	 * @param account Account code/number
	 * @param pro PRO
	 * @return Indicator as to whether account is payor on shipment
	 */
	public boolean isPayor(String account, String pro) throws ServiceException;
}
