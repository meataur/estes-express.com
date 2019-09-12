package com.estes.myestes.addressbook.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.exception.AddressBookException;

/**
 * Address book maintenance service
 */
public interface AddressService
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
	 * Add address to customer address book
	 * 
	 * @param  addr {@link BookAddress} to be added
	 * @return  Indicator of success
	 */
	public boolean addAddress(BookAddress addr) throws AddressBookException;

	/**
	 * Delete address from customer address book
	 * 
	 * @param  addr {@link BookAddress} to be deleted
	 * @return  Indicator of success
	 */
	public boolean deleteAddress(BookAddress addr) throws AddressBookException;

	/**
	 * Get all addresses for customer
	 * 
	 * @param  user My Estes user name
	 * @return  array of {@link BookAddress} objects
	 */
	public BookAddress[] getAddresses(String user) throws AddressBookException;

	/**
	 * Get all service errors
	 * 
	 * @return Array of errors
	 */
	public ServiceResponse[] getErrors();
	
	/**
	 * Update address in address book
	 * 
	 * @param  addr {@link BookAddress} to be updated
	 * @return  Indicator of success
	 */
	public boolean updateAddress(BookAddress addr) throws AddressBookException;
}
