/**
 * @author: James Arthur
 *
 * Creation date: 11/30/2018
 */

package com.estes.services.common.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.ShipmentDAO;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.ShipmentService;

/**
 * My Estes customer services implementation
 */
@Service("shipmentService")
@Scope("prototype")
public class ShipmentServiceImpl implements ShipmentService {
	@Autowired
	private ShipmentDAO shipmentDAO;

	/**
	 * Create a new service
	 */
	public ShipmentServiceImpl() {
		super();
	} // Constructor

	@Override
	public Boolean isL2L(String otPro) throws ServiceException {
		return shipmentDAO.isL2L(otPro);
	} // isL2L
	
	@Override
	public Boolean isEFW(String otPro) throws ServiceException {
		return shipmentDAO.isEFW(otPro);
	} // isEFW
}
