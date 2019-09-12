/**
 *
 */

package com.estes.myestes.recentshipments.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.recentshipments.dto.ShipmentDTO;
import com.estes.myestes.recentshipments.exception.RecentShipmentsException;


public interface RecentShipmentsService {
	/**
	 * Submit request to get recent shipments
	 * 
	 * @return recent shipments
	 */
	public List<ShipmentDTO> getRecentShipments(String username, String account, String party)  throws RecentShipmentsException;
	/**
	 * Get the default party for the user
	 * 
	 * @return string of the default party
	 */
	public String getDefaultParty(String username)  throws RecentShipmentsException;
	/**
	 * Set the default party for the user
	 * 
	 * @return service response of if able to set default party
	 */
	public ServiceResponse setDefaultParty(String username, String party)  throws RecentShipmentsException;
	
}