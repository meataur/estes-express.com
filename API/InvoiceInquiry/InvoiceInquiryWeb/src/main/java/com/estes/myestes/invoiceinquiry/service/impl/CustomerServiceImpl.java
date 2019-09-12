/**
 * @author: Todd Allen
 *
 * Creation date: 01/29/2019
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.invoiceinquiry.dao.iface.CustomerDAO;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.CustomerService;

@Service("customerService")
@Scope("prototype")
public class CustomerServiceImpl implements CustomerService
{
	@Autowired
	private CustomerDAO custDAO;

	@Override
	public boolean hasInstructions(String accountCode) throws InvoiceException
	{
		return custDAO.hasSpecialInstructions(accountCode);
	} // hasInstructions
}
