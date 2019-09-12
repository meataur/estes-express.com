/**
 * @author: Todd Allen
 *
 * Creation date: 12/06/2018
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import com.estes.myestes.invoiceinquiry.dto.ImageRequest;
import com.estes.myestes.invoiceinquiry.dto.Image;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object to get images associated with PRO
 */
public interface ImageDAO extends BaseDAO
{
	/**
	 * 'P' or 'F' parameter (whatever that is)
	 */
	public static final String P_OR_F = "P";
	
	

	/**
	 * Image retrieval statuses - Y/N/W
	 */
	public static final String YES = "Y";
	public static final String NO = "N"; 
	public static final String WORKING = "W";
	
	
	public static final String QUERY_BILLTO_ACCOUNT = "SELECT RICUST FROM FBFILES.RAP002 WHERE RIOT = ? AND RIPRO = ? AND RIOPEN>0 AND RISTAT='O'";

	/**
	 * Reprint invoice to retrieve images for PRO
	 * 
	 * @param session Session ID
	 * @return Table containing result set
	 */
	public Image reprintInvoice(String acct, String session, ImageRequest view) throws InvoiceException;
	
	/**
	 * Retrieve Bill-to Account Code
	 */
	
	public String getBillToAccountCode(String pro);
}
