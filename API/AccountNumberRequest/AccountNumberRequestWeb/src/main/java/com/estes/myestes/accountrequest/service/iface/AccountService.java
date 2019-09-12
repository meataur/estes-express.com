/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 */

package com.estes.myestes.accountrequest.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.accountrequest.dto.RequestInfo;
import com.estes.myestes.accountrequest.exception.AccountRequestException;

/**
 * My Estes account code/number request handler service
 */
public interface AccountService
{
	/**
	 * Request My Estes account code/number
	 * 
	 * @param  reqInfo Requesting company info
	 * @return {@link ServiceResponse} object
	 */
	public ServiceResponse requestNumber(RequestInfo reqInfo) throws AccountRequestException;
}
