/**
 * @author: Todd Allen
 *
 * Creation date: 08/06/2018
 */

package com.estes.myestes.signup.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.signup.dto.SignupInfo;
import com.estes.myestes.signup.exception.SignupException;

/**
 * My Estes profile signup service
 */
public interface SignupService
{
	/**
	 * Process My Estes profile signup
	 * 
	 * @param search {@link SignupInfo} from input form
	 * @return Array of {@link ServiceResponse} objects
	 */
	public ServiceResponse[] requestProfile(SignupInfo info) throws SignupException;
}
