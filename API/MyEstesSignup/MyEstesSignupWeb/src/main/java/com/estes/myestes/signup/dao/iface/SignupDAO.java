/**
 * @author: Todd Allen
 *
 * Creation date: 08/07/2018
 */

package com.estes.myestes.signup.dao.iface;

import java.util.List;

import com.estes.myestes.signup.dto.SignupInfo;
import com.estes.myestes.signup.exception.SignupException;

public interface SignupDAO extends BaseDAO
{
	/**
	 * Validate signup info and create profile request
	 * 
	 * @param  info {@link SignupInfo} data
	 * @return  List of error codes
	 */
	public List<String> createProfile(SignupInfo info) throws SignupException;

	/**
	 * Check whether account is an L2L account
	 * 
	 * @param  acct Account code
	 * @return  Indicator whether account is L2L
	 */
	public boolean isL2L(String acct) throws SignupException;
}
