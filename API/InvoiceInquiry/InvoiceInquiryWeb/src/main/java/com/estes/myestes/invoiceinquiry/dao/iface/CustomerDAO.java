/**
 * @author: Todd Allen
 *
 * Creation date: 01/29/2019
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object for customer data
 */
public interface CustomerDAO extends BaseDAO
{
	/**
	 * Determine whether customer account has special invoicing instructions
	 * 
	 * @param acct Customer account code
	 * @param bollean indicator as to whether special instructions exist for this account
	 */
	public boolean hasSpecialInstructions(String accountCode) throws InvoiceException;
}
