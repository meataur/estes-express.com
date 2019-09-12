/**
 * @author: Todd Allen
 *
 * Creation date: 08/29/2018
 */

package com.estes.myestes.addressbook.dao.iface;

import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.dto.BookAddressSearch;
import com.estes.myestes.addressbook.exception.AddressBookException;

public interface AddressSearchDAO extends BaseDAO
{
	/**
	 * Search My Estes user addresses
	 * 
	 * @param crit {@link BookAddress} search criteria
	 * @return  Array of {@link BookAddress} objects
	 */
	public BookAddress[] searchAddresses(BookAddressSearch crit) throws AddressBookException;
}
