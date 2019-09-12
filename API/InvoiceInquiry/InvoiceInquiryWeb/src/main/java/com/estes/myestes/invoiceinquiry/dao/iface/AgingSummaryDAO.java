/**
 * @author: Todd Allen
 *
 * Creation date: 10/11/2018
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import java.util.List;

import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to get A/R age break totals
 */
public interface AgingSummaryDAO extends BaseDAO
{
	/**
	 * Get A/R aging totals
	 * 
	 * @param account Customer account code
	 * @return List of total values
	 */
	public List<Double> getAgingSummary(String account) throws InvoiceException;

	/**
	 * Get A/R aging totals for web group account
	 * 
	 * @param account Customer account code
	 * @return Indication of error
	 */
	public List<Double> getAgingWebGroupSummary(String account) throws InvoiceException;
}
