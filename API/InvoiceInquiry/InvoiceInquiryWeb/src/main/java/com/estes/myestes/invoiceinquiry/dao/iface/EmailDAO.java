/**
 * @author: Todd Allen
 *
 * Creation date: 01/23/2019
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to send A/R aging detail via e-mail
 */
public interface EmailDAO extends BaseDAO
{
	/**
	 * Send A/R aging details
	 * 
	 * @param acct Customer account info
	 * @param addr E-mail address
	 * @param format File format
	 * @param age Aging bucket
	 * @param pros String of space separated PROs
	 */
	public void sendEmail(Account acct, String addr, String format, String age, String pros) throws InvoiceException;
}
