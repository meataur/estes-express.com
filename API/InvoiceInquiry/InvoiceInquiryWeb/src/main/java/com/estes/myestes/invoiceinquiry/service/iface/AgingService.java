/**
 * @author: Todd Allen
 *
 * Creation date: 01/31/2018
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dto.AgingBuildProgress;
import com.estes.myestes.invoiceinquiry.dto.AgingDetailRequest;
import com.estes.myestes.invoiceinquiry.dto.AgingDetails;
import com.estes.myestes.invoiceinquiry.dto.AgingSummary;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Service to retrieve customer aging information
 */
public interface AgingService
{
	/**
	 * Get customer A/R details for an age break
	 * 
	 * @param acct Customer account info
	 * @return {@link AgingDetails} data for customer age break
	 */
	public AgingDetails getAgingDetail(AgingDetailRequest detailReq, Account acct) throws InvoiceException;

	/**
	 * Get customer A/R build progress
	 * 
	 * @return {@link AgingBuildProgress} info
	 */
	public AgingBuildProgress getAgingBuildProgress();

	/**
	 * Get customer A/R totals by age break
	 * 
	 * @param acct Customer account info
	 * @return {@link AgingSummary} info for customer
	 */
	public AgingSummary getAgingTotals(Account acct) throws InvoiceException;
}
