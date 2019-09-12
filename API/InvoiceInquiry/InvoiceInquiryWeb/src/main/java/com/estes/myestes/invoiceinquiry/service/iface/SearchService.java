/**
 * @author: Todd Allen
 *
 * Creation date: 11/30/2018
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import java.util.List;
import java.util.Set;

import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchRequest;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchResult;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Service to search customer invoice data
 */
public interface SearchService
{
	/**
	 * PRO number search type
	 */
	public static final String PRO_SEARCH = "F";
	/**
	 * Statement number search type
	 */
	public static final String STATEMENT_SEARCH = "S";
	/**
	 * PRO number search type
	 */
	public static final String PO_SEARCH = "P";
	/**
	 * PRO number search type
	 */
	public static final String BOL_SEARCH = "B";

	/**
     * Statement validation criteria delimeter
     */
	public static final String SEARCH_DELIM = "^~";

	public static final String INVALID_PRO_LENGTH = "GTE1538";

	/**
	 * Create delimited string for search and statement validation
	 * 
	 * @param arr Array of search criteria
	 * @return Search string for SPROC input
	 */
	public static String createSearchString(Set<String> arr)
	{
		StringBuilder bld = new StringBuilder();

		for (String val : arr) {
			bld.append(val);
			bld.append(SEARCH_DELIM);
		}

		return bld.toString();
	} // createSearchString

	/**
	 * Search customer A/R invoices
	 * 
	 * @param session Session ID
	 * @param search {@link InvoiceSearchRequest} search criteria
	 * @return {@link InvoiceSearchResult} search results, including errors
	 */
	public List<InvoiceSearchResult> searchInvoices(String session, InvoiceSearchRequest search) throws InvoiceException;
}
