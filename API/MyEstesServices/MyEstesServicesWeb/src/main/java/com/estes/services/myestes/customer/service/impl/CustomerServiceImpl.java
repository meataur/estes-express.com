/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.customer.dao.iface.CustomerDAO;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.dto.ProfileDTO;
import com.estes.services.myestes.customer.service.iface.CustomerService;
import com.estes.services.myestes.exception.ServiceException;

/**
 * My Estes customer services implementation
 */
@Service("customerService")
@Scope("prototype")
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerDAO customerDAO;

	/**
	 * Create a new service
	 */
	public CustomerServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estes.services.myestes.customer.service.iface.CustomerService#getCustomerAccountType
	 */
	//@Override
	public String getCustomerAccountType(String session) throws ServiceException
	{
		return customerDAO.getAccountType(session);
	} // getCustomerAccountType

	@Override
	public Page<AccountDTO> getSubAccounts(Pageable pageable, String accountCode) throws ServiceException {
		return customerDAO.getSubAccounts(pageable, accountCode);
	} // getSubAccounts
	
	@Override
	public boolean validateSubAccount(String accountCode, String selectedAccount, String accountType)
			throws ServiceException {
		return customerDAO.isSubAccountOf(accountCode, selectedAccount, accountType);
	} // validateSubAccount

	@Override
	public ProfileDTO getUserProfile(String username) throws ServiceException {
		return customerDAO.getUserProfile(username);
	}

	@Override
	public AccountDTO getAccountInfo(String accountCode) throws ServiceException {
		return customerDAO.getAccountInfo(accountCode);
	}
}
