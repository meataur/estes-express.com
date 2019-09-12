package com.estes.myestes.BillOfLading.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.Role;
import com.estes.myestes.BillOfLading.web.service.iface.PickupNotificationService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupRequestRestService;

@Service("pickupNotificationService")
public class PickupNotificationServiceImpl implements PickupNotificationService{
	
	@Autowired
	private Environment env;
	
	@Autowired
	private PickupRequestRestService pickupRequestRestService;
	
	@Override
	public EmailAndFaxNotification getPickupNotification(
			AuthenticationDetails auth,
			EmailAndFaxNotification emailAndFaxNotification,
			BolType bolType,
			String param){
		
		if(auth.hasScope(env.getProperty("app.id.pickuprequest"))){
			List<Role> roles = pickupRequestRestService.getNotification(auth.getAccessToken(), bolType, param);
			if(roles!=null && ! roles.isEmpty()){
				for(Role role : roles){
					switch(role){
						case SHIPPER:
							emailAndFaxNotification.setShipperEmailPickupNotice(true);
							break;
						case CONSIGNEE:
							emailAndFaxNotification.setConsigneeEmailPickupNotice(true);
							break;
							
						case THIRD_PARTY:
							emailAndFaxNotification.setThirdPartyEmailPickupNotice(true);
							break;
						case OTHER:
							emailAndFaxNotification.setOtherEmailPickupNotice(true);
							break;
						default:
							emailAndFaxNotification.setShipperEmailPickupNotice(true);
							break;
					}
				}
			}
		}
		return emailAndFaxNotification;
	}
	
	@Override
	public void updatePickupNotification(
			AuthenticationDetails auth,
			EmailAndFaxNotification emailAndFaxNotification, 
			boolean accepted,
			boolean completed,
			BolType bolType,
			String param){
		
		if(auth.hasScope(env.getProperty("app.id.pickuprequest"))){
			List<Role> roles = new ArrayList<>();
			if(emailAndFaxNotification.isShipperEmailPickupNotice() || 
					emailAndFaxNotification.isConsigneeEmailPickupNotice() ||
					emailAndFaxNotification.isThirdPartyEmailPickupNotice() ||
					emailAndFaxNotification.isOtherEmailPickupNotice()){
				
				
				if(emailAndFaxNotification.isShipperEmailPickupNotice()){
					roles.add(Role.SHIPPER);
				}
				
				if(emailAndFaxNotification.isConsigneeEmailPickupNotice()){
					roles.add(Role.CONSIGNEE);
				}
				if(emailAndFaxNotification.isThirdPartyEmailPickupNotice()){
					roles.add(Role.THIRD_PARTY);
				}
				if(emailAndFaxNotification.isOtherEmailPickupNotice()){
					roles.add(Role.OTHER);
				}
			}
			if(roles.isEmpty()){
				roles.add(Role.SHIPPER);
			}
			
			pickupRequestRestService.saveNotification(roles, accepted, completed, auth.getAccessToken(), bolType, param);
		}
	}
}
