/**
 * @author: Todd Allen
 *
 * Creation date: 10/16/2018
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import java.util.List;

import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dto.AgingBuildStatus;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to check aging build status for account
 */
public interface AgingBuildStatusDAO extends BaseDAO
{
	/**
	 * Check aging data build status for customer account
	 * 
	 * @param acc {@link Account} customer account info
	 */
	public List<AgingBuildStatus> getBuildStatus(Account acc) throws InvoiceException;

	/**
	 * Set the aging build status method
	 * 
	 * @param acc {@link Account} customer account info
	 */
	public List<AgingBuildStatus> startBuildProcess(Account acc) throws InvoiceException;
}
