package com.estes.services.myestes.customer.dao.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.exception.ServiceException;

public interface AccountDAO {
	
	public Page<AccountDTO> getSubAccounts(String username, String accountType, String parentAccountCode, Pageable pageable, String searchBy, String searchTerm) throws ServiceException;

}
