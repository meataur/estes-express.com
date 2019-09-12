/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 * 
 */

package com.estes.myestes.accountrequest.dao.iface;

import com.estes.myestes.accountrequest.exception.AccountRequestException;

public interface BlockedEmailDAO
{
	/**
	 * Check e-mail against blocked e-mail addresses
	 * 
	 * @param email Customer provided e-mail address
	 */
	boolean isBlocked(String email) throws AccountRequestException;
}
