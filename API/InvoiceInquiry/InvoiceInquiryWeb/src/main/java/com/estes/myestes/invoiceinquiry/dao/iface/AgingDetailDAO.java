/**
 * @author: Todd Allen
 *
 * Creation date: 10/15/2018
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dto.AgingDetailRequest;
import com.estes.myestes.invoiceinquiry.dto.AgingDetails;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to get A/R aging details for an age break
 */
public interface AgingDetailDAO extends BaseDAO
{
	static final int DEFAULT_ROWS = 25;
	static final int MAX_ROWS = 100;

	/**
	 * Get A/R aging details
	 * 
	 * @param request {@link AgingDetailRequest} request info
	 * @param custAcct {@link Account} info
	 * @return Aging data for age break
	 */
	public AgingDetails getAgingDetail(AgingDetailRequest request, Account custAcct) throws InvoiceException;
}
