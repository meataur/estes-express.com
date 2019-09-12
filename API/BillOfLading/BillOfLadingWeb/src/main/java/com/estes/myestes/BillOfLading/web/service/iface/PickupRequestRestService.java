package com.estes.myestes.BillOfLading.web.service.iface;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.PickupDetailInfo;
import com.estes.myestes.BillOfLading.web.dto.Role;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;

public interface PickupRequestRestService {
	
	/**
	 * To get pickup information  for DEFAULT, HISTORY, DRAFT and TEMPLATE 
	 * @param accessToken - authenticated user access token
	 * @param boltype - [DEFAULT, HISTORY, DRAFT, TEMPLATE]
	 * @param param - 
	 * 		** if bolType is default, param is null. 
	 * 		** if bolType is HISTORY, param is bolId. 
	 * 		** if bolType is DRAFT, param is bolNo.
	 * 		** if bolType is TEMPLATE, param is templateName
	 * @return pickupDetailInfo
	 */
	PickupDetailInfo getPickupInformation(String accessToken, BolType boltype, String param);
	
	/**
	 * To save Pickup Information for DEFAULT, HISTORY, DRAFT and TEMPLATE 
	 * @param pickupDetailInfo
	 * @param accessToken - authenticated user access token
	 * @param boltype - [DEFAULT, HISTORY, DRAFT, TEMPLATE]
	 * @param param - 
	 * 		** if bolType is default, param is null. 
	 * 		** if bolType is HISTORY, param is bolId. 
	 * 		** if bolType is DRAFT, param is bolNo.
	 * 		** if bolType is TEMPLATE, param is templateName
	 */
	void savePickupInformation(PickupDetailInfo pickupDetailInfo, String accessToken, BolType boltype, String param);
	
	/**
	 * 
	 * @param accessToken - authenticated user access token
	 * @param boltype - [DEFAULT, HISTORY, DRAFT, TEMPLATE]
	 * @param param - 
	 * 		** if bolType is default, param is null. 
	 * 		** if bolType is HISTORY, param is bolId. 
	 * 		** if bolType is DRAFT, param is bolNo.
	 * 		** if bolType is TEMPLATE, param is templateName
	 */
	void deletePickupInformation(String accessToken, BolType boltype, String param);
	
	/**
	 * To get Pickup Notification List for different party like SHIPPER, CONSIGNEE, THIRD_PARTY and OTHER
	 * @param accessToken - authenticated user access token
	 * @param boltype - [DEFAULT, HISTORY, DRAFT, TEMPLATE]
	 * @param param - 
	 * 		** if bolType is default, param is null. 
	 * 		** if bolType is HISTORY, param is bolId. 
	 * 		** if bolType is DRAFT, param is bolNo.
	 * 		** if bolType is TEMPLATE, param is templateName
	 * @return List<Role>
	 */
	List<Role> getNotification(String accessToken, BolType boltype, String param);
	
	
	/**
	 * To save Pickup Notification List for different party like SHIPPER, CONSIGNEE, THIRD_PARTY and OTHER 
	 * @param roles - List<Role> ROLE -[SHIPPER, CONSIGNEE, THIRD_PARTY,  OTHER]
	 * @param accepted [true, false]
	 * @param completed [true, false]
	 * @param accessToken - authenticated user access token
	 * @param boltype - [DEFAULT, HISTORY, DRAFT, TEMPLATE]
	 * @param param - 
	 * 		** if bolType is default, param is null. 
	 * 		** if bolType is HISTORY, param is bolId. 
	 * 		** if bolType is DRAFT, param is bolNo.
	 * 		** if bolType is TEMPLATE, param is templateName
	 */
	void saveNotification(List<Role> roles, boolean accepted, boolean completed, String accessToken, BolType boltype,
			String param);
	/**
	 * Create PickupRequest From BOL
	 * @param accessToken
	 * @param pickupRequest
	 * @return
	 */
	ResponseEntity<PickupRequest> savePickupRequest(String accessToken, PickupRequest pickupRequest);

}
