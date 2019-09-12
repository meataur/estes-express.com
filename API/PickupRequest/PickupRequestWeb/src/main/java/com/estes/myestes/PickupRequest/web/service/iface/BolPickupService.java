package com.estes.myestes.PickupRequest.web.service.iface;

import java.util.List;

import com.estes.myestes.PickupRequest.web.dto.BolPickup;
import com.estes.myestes.PickupRequest.web.dto.BolType;
import com.estes.myestes.PickupRequest.web.dto.Notification;
import com.estes.myestes.PickupRequest.web.dto.Role;

/**
 * BOL Pickup Service is responsible to save, update, retrieve and delete pickup information
 * @author rahmaat
 *
 */
public interface BolPickupService {
	/**
	 * Save BOL Pickup Notification
	 * @param roles (List of role is nothing but list of party)
	 * @param notification
	 * @param bolType
	 * @param bolId
	 * @param bolNumber
	 * @param template
	 */
	void saveBolPickupNotification(List<Role> roles, Notification notification, BolType bolType, String bolId,
			String bolNumber, String template);
	
	/**
	 * Retrieve BOL Pickup Notification
	 * @param bolType
	 * @param bolId
	 * @param bolNumber
	 * @param template
	 * @return List<Role> Each role has pickup notification for any of rejected, accepted or complete
	 */
	List<Role> getBolPickupNotification(BolType bolType, String bolId, String bolNumber, String template);

	/**
	 * Save BOL Pickup Information
	 * @param bolPickup
	 * @param bolType
	 * @param bolId
	 * @param bolNumber
	 * @param template
	 */
	void saveBolPickup(BolPickup bolPickup, BolType bolType, String bolId, String bolNumber, String template);
	
	/**
	 * Retrieve BOL Pickup Information
	 * @param bolType
	 * @param bolId
	 * @param bolNumber
	 * @param template
	 * @return BolPickup
	 */
	BolPickup getBolPickup(BolType bolType, String bolId, String bolNumber, String template);
	
	/**
	 * Delete Bol Pickup Information
	 * @param bolType
	 * @param bolId
	 * @param bolNumber
	 * @param template
	 */
	void deleteBolPickup(BolType bolType, String bolId, String bolNumber, String template);
	
}
