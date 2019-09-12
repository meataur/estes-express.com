package com.estes.services.myestes.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.customer.dao.iface.AccountDAO;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.service.iface.AccountService;
import com.estes.services.myestes.exception.ServiceException;

@Service("accountService")
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDAO accountDAO;
	
	@Override
	public Page<AccountDTO> getSubAccounts(String username, String accountType, String parentAccountCode,
			Pageable pageable, String searchBy, String searchTerm) throws ServiceException {
		return accountDAO.getSubAccounts(username, accountType, parentAccountCode, pageable, searchBy, searchTerm);
	}

}
