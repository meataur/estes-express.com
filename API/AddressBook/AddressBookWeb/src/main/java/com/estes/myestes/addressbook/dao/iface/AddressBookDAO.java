/**
 * @author: Todd Allen
 *
 * Creation date: 06/05/2018
 */

package com.estes.myestes.addressbook.dao.iface;

import java.util.List;

import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.exception.AddressBookException;

public interface AddressBookDAO extends BaseDAO
{
	/**
	 * Error code value indicating no errors
	 */
	public static final String NO_ERRORS = "0000000";

	/**
	 * Insert new address for My Estes user
	 * 
	 * @param address Customer address
	 * @return  Indication of success
	 */
	public boolean addAddress(BookAddress address) throws AddressBookException;

	/**
	 * Insert uploaded address for My Estes user
	 * 
	 * @param user Profile name
	 * @param address[] Array of address values
	 * @return  Indication of success
	 */
	public List<String> addAddress(String user, String[] address) throws AddressBookException;

	/**
	 * Delete existing address for My Estes user
	 * 
	 * @param address Customer address
	 * @return  Indication of success
	 */
	public boolean deleteAddress(BookAddress address) throws AddressBookException;

	/**
	 * Delete all addresses for My Estes user
	 * 
	 * @param user User profile name
	 * @return  Indication of success
	 */
	public boolean deleteAddresses(String user) throws AddressBookException;

	//public List<String> getAddressErrors(String userName, String ts) throws AddressBookException;

	/**
	 * Get addresses for My Estes user
	 * 
	 * @param user My Estes user name
	 * @return  Array of {@link BookAddress} objects
	 */
	public BookAddress[] getAddresses(String user) throws AddressBookException;

	/**
	 * Update existing address for My Estes user
	 * 
	 * @param address Customer address
	 * @return  Indication of success
	 */
	public boolean updateAddress(BookAddress address) throws AddressBookException;
}
