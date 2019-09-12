/**
 * @author: Todd Allen
 *
 * Creation date: 11/26/2018
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import java.util.List;

import com.estes.myestes.invoiceinquiry.dto.StatementDetail;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Service to retrieve customer statement details
 */
public interface StatementService
{
	/**
	 * Get customer A/R statement details
	 * 
	 * @param stmt Statement number
	 * @param sortby Sorted column
	 * @return {@link StatementDetail} list for statement number
	 */
	public List<StatementDetail> getStatementDetails(String stmt, String sortby) throws InvoiceException;
}
