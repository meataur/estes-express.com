/**
 * @author: Todd Allen
 *
 * Creation date: 01/29/2019
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

public interface CustomerService
{
	/**
	 * Determine whether customer account has special invoicing instructions
	 * 
	 * @param acct Customer account code
	 * @param boolean indicator as to whether invoice instructions exist for this account
	 */
	public boolean hasInstructions(String account) throws InvoiceException;
}
