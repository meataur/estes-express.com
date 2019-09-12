/**
 * @author: Todd Allen
 *
 * Creation date: 11/26/2018
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import java.util.List;

import com.estes.myestes.invoiceinquiry.dto.StatementDetail;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to get customer A/R statement details
 */
public interface StatementDAO extends BaseDAO
{
	/**
	 * Get A/R statement details
	 * 
	 * @param statementNum Statement number
	 * @param sort Sort column for results
	 * @return List of statement detail items
	 */
	public List<StatementDetail> getStatementDetails(String statementNum, String sort) throws InvoiceException;
}
