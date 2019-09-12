/**
 * @author: Todd Allen
 *
 * Creation date: 06/04/2018
 */

package com.estes.myestes.addressbook.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.addressbook.dao.iface.AddressSearchDAO;
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.dto.BookAddressSearch;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.service.iface.SearchService;

@Service("addrSearchService")
@Scope("prototype")
public class SearchServiceImpl implements SearchService
{
	@Autowired
	private AddressSearchDAO searchDAO;

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.addressbook.service.iface.SearchService#searchAddresses()
	 */
	@Override
	public BookAddress[] searchAddresses(BookAddressSearch srch) throws AddressBookException
	{
		return searchDAO.searchAddresses(srch);
	} // searchAddresses
}
