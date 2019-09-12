/**
 * @author: Todd Allen
 *
 * Creation date: 01/24/2019
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dto.AgingEmailRequest;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Service to retrieve customer statement details
 */
public interface EmailService
{
	/**
	 * Send A/R aging details to list of e-mail addresses
	 * 
	 * @param acct Customer account info
	 * @param emailRequest {@link AgingEmailRequest}
	 * @return {@link ServiceResponse}
	 */
	public ServiceResponse sendAging(Account acct, AgingEmailRequest emailRequest) throws InvoiceException;
}
