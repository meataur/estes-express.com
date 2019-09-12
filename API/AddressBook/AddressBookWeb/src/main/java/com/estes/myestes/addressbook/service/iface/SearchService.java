/**
 * @author: Todd Allen
 *
 * Creation date: 09/04/2018
 */

package com.estes.myestes.addressbook.service.iface;

import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.dto.BookAddressSearch;
import com.estes.myestes.addressbook.exception.AddressBookException;

/**
 * Address book maintenance service
 */
public interface SearchService
{
	/**
	 * Invalid address error code
	 */
	public static String INVALID_ADDRESS = "BOL0108";

	/**
	 * Default error code for ReST service calls
	 */
	public static String DEFAULT_ERROR_CODE = "GEN0005";
	/**
	 * Default error message for ReST service calls
	 */
	public static String DEFAULT_ERROR_MSG = "An unexpected error occurred.";

	/**
	 * Search customer addresses
	 * 
	 * @param crit {@link BookAddress} search criteria
	 * @return array of {@link BookAddress} objects
	 */
	public BookAddress[] searchAddresses(BookAddressSearch crit) throws AddressBookException;
}
