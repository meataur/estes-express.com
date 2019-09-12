package com.estes.myestes.PickupRequest.web.dto;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Pickup Request  model. consignee, partyNotificationList and  bolId is not needed for PickupRequest from PickupRequest Application")
public class PickupRequest {
	
	@ApiModelProperty(position=1,value="User Information")
	private User user;
	
	@ApiModelProperty(position=2,value= "Shipper Information")
	@Valid
	private Party shipper;
	
	@ApiModelProperty(position=3, value= "Consignee Information. Not Required on Pickup Request")
	private Party consignee;
	
	@ApiModelProperty(position=4,value="Pickup Details Information")
	@Valid
	private PickupInformation pickupInfo;
	
	@ApiModelProperty(position=5,value="Shipment Information.")
	private List<Shipment> shipmentInfo;
	
	@ApiModelProperty(position=6, value="Party Notification List. Not Required on Pickup Request")
	private List<PartyNotification> partyNotificationList;
	
	@ApiModelProperty(position=7, value="Agree to Terms of Service. Required for Guaranteed Service")
	private boolean tos;
	
	@ApiModelProperty(position=8, value="BOL Sequence Number. It is not required in Pickup Request")
	private int bolId;
		
	public Party getConsignee(){
		if(consignee==null){
			Party party = new Party();
			if(user.getRole()==Role.CONSIGNEE){
				party.setName(user.getName());
				party.setName(user.getName());
				party.setPhone(user.getPhone());
				party.setPhoneExt(user.getPhoneExt());
				party.setEmail(user.getEmail());
			}
			return party;
		}
		return consignee;
	}
	
	public List<PartyNotification> getPartyNotificationList(){
		
//		if(partyNotificationList==null || partyNotificationList.isEmpty()){
//			
//			partyNotificationList = new ArrayList<>();			
//			PartyNotification partyNotification = new PartyNotification();
//			partyNotification.setName(user.getName());
//			partyNotification.setEmail(user.getEmail());
//			partyNotification.setPhone(user.getPhone());
//			partyNotification.setPhoneExt(user.getPhoneExt());
//			partyNotification.setRole(user.getRole());
//			partyNotificationList.add(partyNotification);
//			
//		}
		
		return partyNotificationList;
		
	}
	

}
