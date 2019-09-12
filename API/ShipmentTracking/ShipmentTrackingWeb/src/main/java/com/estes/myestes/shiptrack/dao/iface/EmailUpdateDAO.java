/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 */

package com.estes.myestes.shiptrack.dao.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

public interface EmailUpdateDAO extends BaseDAO
{
	/**
	 * Capture e-mail status update requests
	 * 
	 * @param  sess Session ID
	 * @param  pro PRO to monitor
	 * @param email E-mail address to receive notifications
	 * @return  Error/Success response
	 */
	public ServiceResponse writeRequest(String sess, String pro, String email) throws ShipTrackException;
}
