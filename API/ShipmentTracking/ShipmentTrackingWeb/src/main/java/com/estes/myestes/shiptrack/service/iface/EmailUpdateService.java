/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 */

package com.estes.myestes.shiptrack.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.shiptrack.dto.EmailStatusRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

/**
 * Shipment e-mail update service
 */
public interface EmailUpdateService
{
	/**
	 * Request e-mail updates on shipments
	 * 
	 * @param  statusReq {@link EmailStatusRequest} of PROs and e-mail addresses
	 * @return  {@link ServiceResponse} object
	 */
	public List<ServiceResponse> requestUpdates(EmailStatusRequest statusReq) throws ShipTrackException;
}
