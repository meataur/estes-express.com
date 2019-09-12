/**
 * @author: James Arthur
 *
 * Creation date: 11/30/2018
 */

package com.estes.services.common.dao.iface;

import com.estes.services.common.exception.ServiceException;

public interface ShipmentDAO extends BaseDAO {
	/**
	 * Call SPROC to get whether shipment is l2l or not
	 * 
	 * @param otPro of the shipment
	 * @return Boolean of if l2l or not
	 */
	public Boolean isL2L(String otPro) throws ServiceException;

	/**
	 * Call SPROC to get whether shipment is efw or not
	 * 
	 * @param otPro of the shipment
	 * @return Boolean of if efw or not
	 */
	public Boolean isEFW(String otPro) throws ServiceException;
}
