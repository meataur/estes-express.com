/**
 * @author: Todd Allen
 *
 * Creation date: 11/20/2018
 */

package com.estes.services.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.CustomerDAO;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.CustomerService;

/**
 * Estes common customer services implementation
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
	 * @see com.estes.services.common.customer.service.iface.CustomerService#getCustomerAccountType
	 */
	@Override
	public String getCustomerAccountType(String account) throws ServiceException
	{
		return customerDAO.getAccountTypeByAccount(account);
	} // getCustomerAccountType

	/**
	 * (non-Javadoc)
	 * @see com.estes.services.common.customer.service.iface.CustomerService#isPayor
	 */
	//@Override
	public boolean isPayor(String account, String pro) throws ServiceException
	{
		// Remove dash from PRO
		pro.replace("-", "");

		return customerDAO.isAccountPayor(account, pro);
	} // getCustomerAccountType
}
