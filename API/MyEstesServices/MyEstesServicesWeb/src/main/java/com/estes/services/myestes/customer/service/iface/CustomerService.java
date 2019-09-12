/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.customer.service.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.dto.ProfileDTO;
import com.estes.services.myestes.exception.ServiceException;

/**
 * Estes customer services
 */
public interface CustomerService
{
	/**
	 * Get Estes customer account type code
	 * 
	 * @param session Session ID
	 * @return Account type code
	 */
	public String getCustomerAccountType(String session) throws ServiceException;
	
	/**
	 * Get Estes customer sub accounts
	 * 
	 * @param the pageable object of which accounts to retrieve
	 * @param string of account code
	 * @return Page of sub accounts
	 */
	public Page<AccountDTO> getSubAccounts(Pageable pageable, String accountCode) throws ServiceException;
	
	/**
	 * Validate that the selectedAccount is a sub account of the account code
	 * 
	 * @param the parent account
	 * @param the sub account
	 * @param the accountType
	 * @return boolean of whether valid sub account or not
	 */
	public boolean validateSubAccount(String accountCode, String selectedAccount, String accountType) throws ServiceException;
	
	/**
	 * Get the profile information associated with the username
	 * 
	 * @param the username to get info for
	 * @return the profile information
	 */
	public ProfileDTO getUserProfile(String username) throws ServiceException;
	
	/**
	 * Get the Account information associated with the account Number
	 * 
	 * @param the accountCode
	 * @return the Account information
	 */
	public AccountDTO getAccountInfo(String accountCode) throws ServiceException;
}
