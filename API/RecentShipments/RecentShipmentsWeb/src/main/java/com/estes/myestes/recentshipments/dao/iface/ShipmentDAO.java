package com.estes.myestes.recentshipments.dao.iface;

import java.util.List;

import com.estes.myestes.recentshipments.dto.ShipmentDTO;
import com.estes.myestes.recentshipments.exception.RecentShipmentsException;

public interface ShipmentDAO extends BaseDAO {
	List<ShipmentDTO> getRecentShipments(String username, String account, String party) throws RecentShipmentsException;
	String getDefaultParty(String username) throws RecentShipmentsException;
	Boolean writeDefaultParty(String username, String party) throws RecentShipmentsException;
	Boolean updateDefaultParty(String username, String party) throws RecentShipmentsException;
}