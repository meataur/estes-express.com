/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.customer.dao.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.dto.ProfileDTO;
import com.estes.services.myestes.exception.ServiceException;

public interface CustomerDAO extends BaseDAO
{
	/**
	 * Call SPROC to get customer account type code
	 * 
	 * @param session Session ID
	 * @return Account type code
	 */
	public String getAccountType(String session) throws ServiceException;
	
	/**
	 * Call SPROC to get customer sub accounts
	 * 
	 * @param the pageable object of which accounts to retrieve
	 * @param string of account code
	 * @return Page of sub accounts
	 */
	public Page<AccountDTO> getSubAccounts(Pageable pageable, String session) throws ServiceException;
	
	/**
	 * Call SPROC to validate that the selectedAccount is a sub account of the account code
	 * 
	 * @param the parent account
	 * @param the sub account
	 * @param the accountType
	 * @return boolean of whether valid sub account or not
	 */
	public boolean isSubAccountOf(String accountCode, String selectedAccount, String accountType) throws ServiceException;

	/**
	 * Get the profile information associated with the username
	 * 
	 * @param the username to get info for
	 * @return the profile information
	 */
	public ProfileDTO getUserProfile(String username) throws ServiceException;
	
	/**
	 * Get the Account information associated with the acount number
	 * @param accountNumber
	 * @return AccountDTO
	 * @throws ServiceException
	 */
	public AccountDTO getAccountInfo(String accountNumber) throws ServiceException;
}
