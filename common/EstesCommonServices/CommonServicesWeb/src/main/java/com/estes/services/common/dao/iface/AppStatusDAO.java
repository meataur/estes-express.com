/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.common.dao.iface;

import com.estes.services.common.exception.ServiceException;

/**
 * Data access object to check running status of application
 */
public interface AppStatusDAO extends BaseDAO
{
	/**
	 * Application name in NDP100 table
	 */
	public static final String INVOICE_INQUIRY = "INVOICE REQUEST";
	/**
	 * Successful return code for application status
	 */
	final public static String ACTIVE = "0000000";

	/**
	 * Determine presence of error by checking error code
	 * 
	 * @param code Error code to compare
	 * @return Indication of error
	 */
	public static boolean isActive(String code)
	{
		return code.trim().equals(ACTIVE);
	} // isActive

	/**
	 * Check status of application.
	 */
	public boolean isAppAvailable(String app) throws ServiceException;

	/**
	 * Check status of invoice inquiry.
	 */
	public boolean isInvoiceInquiryAvailable() throws ServiceException;
}
