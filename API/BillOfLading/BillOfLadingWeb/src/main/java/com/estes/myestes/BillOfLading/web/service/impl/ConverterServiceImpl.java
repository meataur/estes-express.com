package com.estes.myestes.BillOfLading.web.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.Reference;
import com.estes.myestes.BillOfLading.web.dto.Role;
import com.estes.myestes.BillOfLading.web.dto.common.Notification;
import com.estes.myestes.BillOfLading.web.dto.common.Party;
import com.estes.myestes.BillOfLading.web.dto.common.PartyNotification;
import com.estes.myestes.BillOfLading.web.dto.common.PickupCommodity;
import com.estes.myestes.BillOfLading.web.dto.common.PickupInformation;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;
import com.estes.myestes.BillOfLading.web.dto.common.Shipment;
import com.estes.myestes.BillOfLading.web.dto.common.ShipmentOption;
import com.estes.myestes.BillOfLading.web.dto.common.ShipmentService;
import com.estes.myestes.BillOfLading.web.dto.common.User;
import com.estes.myestes.BillOfLading.web.service.iface.ConverterService;

@Service("converterService")
public class ConverterServiceImpl implements ConverterService {
	
	@Override
	public PickupRequest conver(BillOfLading bol, AuthenticationDetails auth){
		
		PickupRequest pickupRequest = new PickupRequest();
		
		
		if(bol.getPickupDetailInfo()==null){
			return pickupRequest;
		}
		
		User user = new User();
		
		user.setName(bol.getPickupDetailInfo().getName());
		user.setPhone(bol.getPickupDetailInfo().getPhone());
		user.setPhoneExt(bol.getPickupDetailInfo().getPhoneExt());
		user.setEmail(bol.getPickupDetailInfo().getEmail());
		user.setRole(bol.getPickupDetailInfo().getRole());
		pickupRequest.setUser(user);
		
		
		
		Party shipper = new Party();
		
		if(bol.getPickupDetailInfo().getRole().equals(Role.SHIPPER)
				&& auth.getAccountType().equalsIgnoreCase("L")){
				bol.getPickupDetailInfo().setAccountCode(auth.getAccountCode());
		}
		
		/**
		 * Set shipper account code emty as default
		 */
		shipper.setAccountCode("");
		/**
		 * Set Account Code only if the Requester role is shipper
		 */
		
		if(bol.getPickupDetailInfo().getRole().equals(Role.SHIPPER)){
			shipper.setAccountCode(bol.getPickupDetailInfo().getAccountCode());
		}
		
		shipper.setName(bol.getShipperInfo().getFirstName() + " "+ bol.getShipperInfo().getLastName());
		shipper.setCompany(bol.getShipperInfo().getCompanyName());
		shipper.setAddressLine1(bol.getShipperInfo().getAddress1());
		shipper.setAddressLine2(bol.getShipperInfo().getAddress2());
		shipper.setCity(bol.getShipperInfo().getCity());
		shipper.setState(bol.getShipperInfo().getState());
		shipper.setZip(bol.getShipperInfo().getZip());
		shipper.setZipExt("");
		shipper.setPhone(bol.getShipperInfo().getPhone());
		shipper.setPhoneExt(bol.getShipperInfo().getPhoneExt());
		shipper.setEmail(bol.getShipperInfo().getEmail());
		
		pickupRequest.setShipper(shipper);
		
		
		Party consignee = new Party();
		
		consignee.setAccountCode("");
		consignee.setName(bol.getConsigneeInfo().getFirstName() + " "+ bol.getConsigneeInfo().getLastName());
		consignee.setCompany(bol.getConsigneeInfo().getCompanyName());
		consignee.setAddressLine1(bol.getConsigneeInfo().getAddress1());
		consignee.setAddressLine2(bol.getConsigneeInfo().getAddress2());
		consignee.setCity(bol.getConsigneeInfo().getCity());
		consignee.setState(bol.getConsigneeInfo().getState());
		consignee.setZip(bol.getConsigneeInfo().getZip());
		consignee.setZipExt("");
		consignee.setPhone(bol.getConsigneeInfo().getPhone());
		consignee.setPhoneExt(bol.getConsigneeInfo().getPhoneExt());
		consignee.setEmail(bol.getConsigneeInfo().getEmail());
		pickupRequest.setConsignee(consignee);
		
		
		PickupInformation pickupInfo = new PickupInformation();
		
		pickupInfo.setPickupDate(bol.getPickupDetailInfo().getPickupDate());
		pickupInfo.setStartTime(bol.getPickupDetailInfo().getStartTime());
		pickupInfo.setEndTime(bol.getPickupDetailInfo().getEndTime());
		pickupInfo.setHookOrDrop(bol.getPickupDetailInfo().isHookOrDrop());
		pickupInfo.setLiftgateRequired(bol.getPickupDetailInfo().isLiftgateRequired());
		
		pickupInfo.setNoStackPallet(bol.getPickupDetailInfo().isNoStackPallet());

		
		pickupRequest.setPickupInfo(pickupInfo);
		
		List<Shipment> shipmentInfo = new ArrayList<>();
		
		Shipment shipment = new Shipment();
		
		shipment.setRequestNumber("");
		
		PickupCommodity pickupCommodity = new PickupCommodity();

		
		pickupCommodity.setDestinationZipCode(bol.getConsigneeInfo().getZip());
		
		pickupCommodity.setDestinationZipCodeExt("");
		
		int totalPieces = 0;
		int totalWeight = 0;
		int totalSkids  = 0;
		boolean hazmat = false;
		if(bol.getCommodityInfo()!=null 
				&& bol.getCommodityInfo().getCommodityList()!=null 
				&& bol.getCommodityInfo().getCommodityList().isEmpty()==false){
			
			for(Commodity commodity : bol.getCommodityInfo().getCommodityList()){
				totalPieces += commodity.getGoodsUnit();
				totalWeight += commodity.getGoodsWeight();
				if("SK".equalsIgnoreCase(commodity.getGoodsType().getCode())){
					totalSkids += commodity.getGoodsUnit();
				}
				hazmat = hazmat || commodity.isHazmat(); 
			}
			pickupCommodity.setSpecialInstructions(bol.getCommodityInfo().getSpecialIns()); 
		}
		
		pickupCommodity.setTotalPieces(totalPieces);
		pickupCommodity.setTotalWeight(totalWeight);
		pickupCommodity.setTotalSkids(totalSkids);
		
		pickupCommodity.setReferenceNumber(getLastNonEmptyReferenceNumber(bol.getReferences()));
			
		shipment.setCommodity(pickupCommodity);
		
		ShipmentOption shipmentOption = new ShipmentOption();
		shipmentOption.setFood(bol.getPickupDetailInfo().isFood());
		shipmentOption.setFreeze(bol.getPickupDetailInfo().isFreeze());
		shipmentOption.setOversize(bol.getPickupDetailInfo().isOversize());
		shipmentOption.setPoision(bol.getPickupDetailInfo().isPoision());
		shipment.setShipmentOption(shipmentOption);
		
		if(bol.getGeneralInfo().isGuranteed()){
			shipment.setShipmentService(ShipmentService.GUARANTEED);
		}else{
			shipment.setShipmentService(ShipmentService.LTL);
		}
		
		/**
		 * Set Notification
		 */
		
		Notification notification = new Notification();
		notification.setAccepted(bol.getPickupDetailInfo().isAccepted());
		notification.setCompleted(bol.getPickupDetailInfo().isCompleted());
		notification.setRejected(true);
		shipment.setNotification(notification);
		
		shipmentInfo.add(shipment);
		pickupRequest.setShipmentInfo(shipmentInfo);
		
		
		List<PartyNotification> partyNotificationList = new ArrayList<>();
		
		if(bol.getEmailAndFaxNotification()!=null){
			
			if(bol.getEmailAndFaxNotification().isShipperEmailPickupNotice()){
				PartyNotification partyNotification = new PartyNotification();
				partyNotification.setRole(Role.SHIPPER);
				partyNotification.setName(bol.getShipperInfo().formatName());
				partyNotification.setEmail(bol.getEmailAndFaxNotification().getShipperEmail());
				partyNotification.setPhone(bol.getShipperInfo().getPhone());
				partyNotification.setPhoneExt(bol.getShipperInfo().getPhoneExt());
				partyNotificationList.add(partyNotification);
			}
			
			if(bol.getEmailAndFaxNotification().isConsigneeEmailPickupNotice()){
				PartyNotification partyNotification = new PartyNotification();
				partyNotification.setRole(Role.CONSIGNEE);
				partyNotification.setName(bol.getConsigneeInfo().formatName());
				partyNotification.setEmail(bol.getEmailAndFaxNotification().getConsigneeEmail());
				partyNotification.setPhone(bol.getConsigneeInfo().getPhone());
				partyNotification.setPhoneExt(bol.getConsigneeInfo().getPhoneExt());
				partyNotificationList.add(partyNotification);
			}
			
			if(bol.getEmailAndFaxNotification().isThirdPartyEmailPickupNotice()){
				PartyNotification partyNotification = new PartyNotification();
				partyNotification.setRole(Role.THIRD_PARTY);

				partyNotification.setEmail(bol.getEmailAndFaxNotification().getThirdPartyEmail());
				partyNotification.setName(bol.getEmailAndFaxNotification().getThirdPartyEmail());
				partyNotification.setPhone("");
				partyNotification.setPhoneExt("");
				if(bol.getBillingInfo()!=null 
						&& bol.getBillingInfo().getBillTo()!=null 
						&& bol.getBillingInfo().getBillTo().getBillingAddressInfo()!=null){
					partyNotification.setName(bol.getBillingInfo().getBillTo().getBillingAddressInfo().formatName());
					partyNotification.setPhone(bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhone());
					partyNotification.setPhoneExt(bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhoneExt());
				}
				/**
				 * If third party does not have phone set, consignee phone to third party as legacy is doing this.
				 */
				if(partyNotification.getPhone().equals("") || partyNotification.getPhone()==null){
					partyNotification.setPhone(bol.getConsigneeInfo().getPhone());
					partyNotification.setPhoneExt("");
				}
				
				partyNotificationList.add(partyNotification);
				
			}
			/**
			 * Set requester name & phone for other as other does not have those information as legacy is doing this.
			 */
			if(bol.getEmailAndFaxNotification().isOtherEmailPickupNotice()){
				PartyNotification partyNotification = new PartyNotification();
				partyNotification.setRole(Role.OTHER);
				partyNotification.setEmail(bol.getEmailAndFaxNotification().getOtherEmail());
				partyNotification.setName(bol.getPickupDetailInfo().getName());
				partyNotification.setPhone(bol.getPickupDetailInfo().getPhone());
				partyNotification.setPhoneExt("");
				partyNotificationList.add(partyNotification);
			}
			
		}
		
		if(partyNotificationList.isEmpty()){
			PartyNotification partyNotification = new PartyNotification();
			partyNotification.setRole(bol.getPickupDetailInfo().getRole());
			partyNotification.setName(bol.getPickupDetailInfo().getName());
			partyNotification.setEmail(bol.getPickupDetailInfo().getEmail());
			partyNotification.setPhone(bol.getPickupDetailInfo().getPhone());
			partyNotification.setPhoneExt(bol.getPickupDetailInfo().getPhoneExt());
			partyNotificationList.add(partyNotification);
		}
		
		
		pickupRequest.setPartyNotificationList(partyNotificationList);
		
		
		pickupRequest.setTos(bol.getGeneralInfo().isTosChecked());
		
		pickupRequest.setBolId(bol.getBolId());

		return pickupRequest;
		
	}
	
	private String getLastNonEmptyReferenceNumber(List<Reference> references){
		
		if(references!=null && references.isEmpty()==false){
			/**
			 * Reverse the List
			 */
			Collections.reverse(references); 
			
			
			for(Reference reference : references){
				if(reference.getReferenceNumber()!=null && reference.getReferenceNumber().trim().length()>0){
					return reference.getReferenceNumber().trim();
				}
			}
		}
		
		
		return "";
		
	}
	
	/**
	 * Update email and Fax information for Shipper, Consignee & Third Party from EmailAndFaxNotification object
	 * 
	 */
	
	@Override
	public void updateFaxAndEmailForShipperConsigneeThirdPartyFromEmailAndNotification(BillOfLading bol){
		
		if(bol.getEmailAndFaxNotification()!=null){
			
			if(bol.getShipperInfo()!=null){
				
				if(bol.getEmailAndFaxNotification().getShipperEmail()!=null 
						&& bol.getEmailAndFaxNotification().getShipperEmail().trim().length()>0){
					bol.getShipperInfo().setEmail(bol.getEmailAndFaxNotification().getShipperEmail().trim());
				}
				
				if(bol.getEmailAndFaxNotification().getShipperFax()!=null 
						&& bol.getEmailAndFaxNotification().getShipperFax().trim().length()>0){
					bol.getShipperInfo().setFax(bol.getEmailAndFaxNotification().getShipperFax().trim());
				}
			}
			
			if(bol.getConsigneeInfo()!=null){
				
				if(bol.getEmailAndFaxNotification().getConsigneeEmail()!=null 
						&& bol.getEmailAndFaxNotification().getConsigneeEmail().trim().length()>0){
					bol.getConsigneeInfo().setEmail(bol.getEmailAndFaxNotification().getConsigneeEmail().trim());
				}
				
				if(bol.getEmailAndFaxNotification().getConsigneeFax()!=null 
						&& bol.getEmailAndFaxNotification().getConsigneeFax().trim().length()>0){
					bol.getConsigneeInfo().setFax(bol.getEmailAndFaxNotification().getConsigneeFax().trim());
				}
			}
			
			
			if(bol.getBillingInfo()!=null 
					&& bol.getBillingInfo().getBillTo()!=null){
				
				if(bol.getEmailAndFaxNotification().getThirdPartyEmail()!=null 
						&& bol.getEmailAndFaxNotification().getThirdPartyEmail().trim().length()>0){
					bol.getBillingInfo().getBillTo().getBillingAddressInfo().setEmail(bol.getEmailAndFaxNotification().getThirdPartyEmail().trim());
				}
				
				if(bol.getEmailAndFaxNotification().getThirdPartyFax()!=null 
						&&  bol.getEmailAndFaxNotification().getThirdPartyFax().trim().length()>0){
					bol.getBillingInfo().getBillTo().getBillingAddressInfo().setFax( bol.getEmailAndFaxNotification().getThirdPartyFax().trim());
				}
			}
			
		}
		
		
	}
	
}
