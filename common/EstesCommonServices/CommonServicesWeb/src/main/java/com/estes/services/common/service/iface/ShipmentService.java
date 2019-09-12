/**
 * @author: James Arthur
 *
 * Creation date: 11/30/2018
 */

package com.estes.services.common.service.iface;

import com.estes.services.common.exception.ServiceException;

/**
 * Estes shipment services
 */
public interface ShipmentService {
	/**
	 * check if ot pro is an l2l shipment
	 * 
	 * @param otPro the ot and pro for shipment in format xxx-xxxx
	 * @return Boolean of whether or not the ot pro shipment was l2l or not
	 */
	public Boolean isL2L(String otPro) throws ServiceException;
	
	/**
	 * check if ot pro is an efw shipment
	 * 
	 * @param otPro the ot and pro for shipment in format xxx-xxxx
	 * @return Boolean of whether or not the ot pro shipment was efw or not
	 */
	public Boolean isEFW(String otPro) throws ServiceException;
}
