/**
 * @author: Todd Allen
 *
 * Creation date: 06/04/2018
 */

package com.estes.myestes.addressbook.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.Address;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.addressbook.dao.iface.AddressBookDAO;
import com.estes.myestes.addressbook.dao.iface.ErrorDAO;
import com.estes.myestes.addressbook.dao.iface.PointsDAO;
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.service.iface.AddressService;

@Service("addrBookService")
@Scope("prototype")
public class AddressServiceImpl implements AddressService
{
	@Autowired
	private AddressBookDAO addressDAO;
	@Autowired
	private PointsDAO pointsDAO;
	@Autowired
	private ErrorDAO errDAO;

	private List<String> errors;

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.addressbook.service.iface.AddressService#addAddress()
	 */
	@Override
	public boolean addAddress(BookAddress book) throws AddressBookException
	{
		errors = new ArrayList<String>();

		if (validateAddress(book.getAddress())) {
			if (addressDAO.addAddress(book)) {
				return true;
			}
			else {
				errors.add(DEFAULT_ERROR_CODE);
				return false;
			}
		}
		else {
			errors.add(INVALID_ADDRESS);
			return false;
		}
	} // addAddress

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.addressbook.service.iface.AddressService#deleteAddress()
	 */
	@Override
	public boolean deleteAddress(BookAddress addr) throws AddressBookException
	{
		// Log message if address to be deleted not found.
		if (!addressDAO.deleteAddress(addr)) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressServiceImpl.class, "deleteAddress()", "** Address not deleted for user " + addr.getUser() + ".");
		}

		return true;
	} // deleteAddress

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.addressbook.service.iface.AddressService#getAddresses()
	 */
	@Override
	public BookAddress[] getAddresses(String user) throws AddressBookException
	{
		return addressDAO.getAddresses(user);
	} // getAddresses

	@Override
	public ServiceResponse[] getErrors()
	{
		// Set error info for each code
		List<ServiceResponse> errorList = new ArrayList<ServiceResponse>();
		for (String code : errors) {
			ServiceResponse resp = new ServiceResponse(code, errDAO.getErrorMessage(code));
			errorList.add(resp);
		}

		return errorList.toArray( new ServiceResponse[errorList.size()] );
	} // getErrors

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.addressbook.service.iface.AddressService#updateAddress()
	 */
	@Override
	public boolean updateAddress(BookAddress addr) throws AddressBookException
	{
		errors = new ArrayList<String>();

		if (validateAddress(addr.getAddress())) {
			if (addressDAO.updateAddress(addr)) {
				return true;
			}
			else {
				errors.add(DEFAULT_ERROR_CODE);
				return false;
			}
		}
		else {
			errors.add(INVALID_ADDRESS);
			return false;
		}
	} // updateAddress

	/**
	 * Validate address
	 * 
	 * @param address Physical address
	 */
	private boolean validateAddress(Address addr) throws AddressBookException
	{
		return pointsDAO.getPoint(addr);
	} // validateAddress
}
