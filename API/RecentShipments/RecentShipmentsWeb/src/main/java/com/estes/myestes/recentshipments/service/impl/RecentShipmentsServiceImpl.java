/**
 *
 */

package com.estes.myestes.recentshipments.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.recentshipments.dao.iface.ShipmentDAO;
import com.estes.myestes.recentshipments.dto.ShipmentDTO;
import com.estes.myestes.recentshipments.exception.RecentShipmentsException;
import com.estes.myestes.recentshipments.service.iface.RecentShipmentsService;
import com.estes.myestes.recentshipments.util.RecentShipmentsConstant;

@Service("recentShipmentsService")
@Scope("prototype")
@Transactional
public class RecentShipmentsServiceImpl implements RecentShipmentsService, RecentShipmentsConstant {
	
	@Autowired
	private ShipmentDAO shipmentDAO;
	
	/**
	 * Create a new service
	 */
	public RecentShipmentsServiceImpl() {
		super();
	} // Constructor

	@Override
	public List<ShipmentDTO> getRecentShipments(String username, String account, String party) throws RecentShipmentsException {
		return shipmentDAO.getRecentShipments(username, account, party);
	}

	@Override
	public String getDefaultParty(String username) throws RecentShipmentsException {
		String party = shipmentDAO.getDefaultParty(username);
		
		// default to shipper party
		if(party == null || party.trim().length() == 0) {
			party = "S";
		}
		else {
			party = party.trim();
		}
		
		return party;
	}

	@Override
	public ServiceResponse setDefaultParty(String username, String party) throws RecentShipmentsException {
		boolean success;
		String defaultParty = shipmentDAO.getDefaultParty(username);
		
		if(defaultParty == null || defaultParty.trim().length() == 0) {
			success = shipmentDAO.writeDefaultParty(username, party);
		} else {
			success = shipmentDAO.updateDefaultParty(username, party);
		}
		
		if(success) {
			ServiceResponse response = new ServiceResponse();
			response.setMessage("Successfully updated default party to shipment.");
			return response;
		}
		else {
			throw new RecentShipmentsException("Unable to set party");
		}
	}
}