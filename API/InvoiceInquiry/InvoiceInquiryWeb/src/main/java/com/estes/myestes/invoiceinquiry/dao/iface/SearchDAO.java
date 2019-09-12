/**
 * @author: Todd Allen
 *
 * Creation date: 11/29/2018
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchRequest;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchResult;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to get perform customer invoice info search
 */
public interface SearchDAO extends BaseDAO
{
	/**
	 * Starting row; There is no paging of search results
	 */
	public static final String START_SHOW = "1";
	/**
	 * Number of rows to retrieve
	 */
	public static final String ROWS_TO_SHOW = "25";

	/**
     * Good PRO error code 
     */
	public static final String GOOD_PRO = "ARE0000";

	/**
	 * Retrieve invoice info. for A/R statement.
	 * 
	 * @param session Session ID
	 * @return Table containing result set
	 */
	public List<InvoiceSearchResult> getInvoiceInfo(String session, InvoiceSearchRequest search,Set<String> searchTerm) throws InvoiceException;

	/**
	 * Validate statements
	 * 
	 * @param session Session ID
	 * @param criteria Search criteria
	 * @return List of error codes
	 */
	public Map<String,String> validateStatements(String session, String criteria) throws InvoiceException;
}
