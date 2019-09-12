package com.estes.myestes.PickupRequest.web.service.impl;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.estes.myestes.PickupRequest.config.AuthenticationDetails;
import com.estes.myestes.PickupRequest.exception.AppException;
import com.estes.myestes.PickupRequest.util.EstesUtil;
import com.estes.myestes.PickupRequest.web.dto.BolPickup;
import com.estes.myestes.PickupRequest.web.dto.BolType;
import com.estes.myestes.PickupRequest.web.dto.Notification;
import com.estes.myestes.PickupRequest.web.dto.Role;
import com.estes.myestes.PickupRequest.web.jpa.entity.NotifyPartyType;
import com.estes.myestes.PickupRequest.web.jpa.entity.NotifyStatusType;
import com.estes.myestes.PickupRequest.web.jpa.entity.PickupDetailNotify;
import com.estes.myestes.PickupRequest.web.jpa.entity.PickupDetailType;
import com.estes.myestes.PickupRequest.web.jpa.entity.PickupRequestDetail;
import com.estes.myestes.PickupRequest.web.jpa.repository.NotifyPartyTypeRepository;
import com.estes.myestes.PickupRequest.web.jpa.repository.NotifyStatusTypeRepository;
import com.estes.myestes.PickupRequest.web.jpa.repository.PickupDetailNotifyRepository;
import com.estes.myestes.PickupRequest.web.jpa.repository.PickupDetailTypeRepository;
import com.estes.myestes.PickupRequest.web.jpa.repository.PickupRequestDetailRepository;
import com.estes.myestes.PickupRequest.web.service.iface.BolPickupService;

@Service("bolPickupService")
public class BolPickupServiceImpl implements BolPickupService{
	
	@Autowired
	private PickupRequestDetailRepository pickupRequestDetailRepository;
	
	@Autowired
	private NotifyPartyTypeRepository notifyPartyTypeRepository;
	
	@Autowired
	private NotifyStatusTypeRepository notifyStatusTypeRepository;
	
	@Autowired
	private PickupDetailTypeRepository pickupDetailTypeRepository;
	
	@Autowired
	private PickupDetailNotifyRepository pickupDetailNotifyRepository;
	
	

	@Override
	public BolPickup getBolPickup(
			final BolType bolType,
			final String bolId, 
			final String bolNumber,
			final String template){
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		PickupRequestDetail pickupRequestDetail = getPickupRequestDetail(bolType, bolId, bolNumber, template, auth.getUsername());

		if(pickupRequestDetail==null){
			return new BolPickup();
		}
		
		BolPickup bolPickup = new BolPickup();
		
		/**
		 * To fix  date timezone issue with @JsonFormat
		 */
		String date = EstesUtil.formatDate(pickupRequestDetail.getPickupDate(),EstesUtil.US_DATE_FORMAT);
		Date date1 = EstesUtil.formatDate(date,EstesUtil.US_DATE_FORMAT);
		bolPickup.setPickupDate(date1);
		
		if(pickupRequestDetail.getStartTime()!=null){
			bolPickup.setStartTime(pickupRequestDetail.getStartTime().toLocalTime());
		}
		
		if(pickupRequestDetail.getEndTime()!=null){
			bolPickup.setEndTime(pickupRequestDetail.getEndTime().toLocalTime());
		}
		
		bolPickup.setHookOrDrop("Y".equalsIgnoreCase(pickupRequestDetail.getPickupHookDrop()));
		bolPickup.setLiftgateRequired("Y".equalsIgnoreCase(pickupRequestDetail.getPickupLiftgateRequired()));
		bolPickup.setNoStackPallet("Y".equalsIgnoreCase(pickupRequestDetail.getPickupPalletsNoStackInd()));
		bolPickup.setSpecialInstruction(pickupRequestDetail.getPickupInstruction());
		bolPickup.setEmail(pickupRequestDetail.getRequesterEmailAddress());
		bolPickup.setName(pickupRequestDetail.getRequesterName());
		bolPickup.setPhone(pickupRequestDetail.getRequesterPhoneNo());
		bolPickup.setPhoneExt(pickupRequestDetail.getRequesterPhoneExt());
		
		if(pickupRequestDetail.getRequesterRole()!=null){
			bolPickup.setRole(Role.getRole(pickupRequestDetail.getRequesterRole()));
		}
		
		
		bolPickup.setFood("Y".equalsIgnoreCase(pickupRequestDetail.getShipmentFoodInd()));
		bolPickup.setFreeze("Y".equalsIgnoreCase(pickupRequestDetail.getShipmentFreezeInd()));
		bolPickup.setOversize("Y".equalsIgnoreCase(pickupRequestDetail.getShipmentOversizeInd()));
		bolPickup.setPoision("Y".equalsIgnoreCase(pickupRequestDetail.getShipmentPoisonInd()));
		bolPickup.setAccountCode(pickupRequestDetail.getShipperAccountNo());
		
		List<String> notifications = pickupDetailNotifyRepository.findDistinctStatusTypeByPickupRequestDetailId(pickupRequestDetail.getId());
		
		if(notifications.contains("ACCEPT")){
			bolPickup.setAccepted(true);
		}
		if(notifications.contains("COMPLETE")){
			bolPickup.setCompleted(true);
		}
		if(notifications.contains("REJECT")){
			bolPickup.setRejected(true);
		}
		return bolPickup;
		
	}
	
	@Override
	public void saveBolPickup(
			BolPickup bolPickup,
			BolType bolType,
			String bolId, 
			String bolNumber,
			String template
			){
		
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		PickupRequestDetail pickupRequestDetail = getPickupRequestDetail(bolType, bolId, bolNumber, template, auth.getUsername());
		
		pickupRequestDetail = getPickupRequestDetail(pickupRequestDetail, bolPickup);
		
		if(pickupRequestDetail.getPickupDetailType()==null){
			PickupDetailType pickupDetailType = pickupDetailTypeRepository.findByName(bolType.name());
			pickupRequestDetail.setPickupDetailType(pickupDetailType);
		}
		
		pickupRequestDetail.setAccountNo(auth.getAccountCode());
		pickupRequestDetail.setBolNo(bolNumber);
		pickupRequestDetail.setBolSeqNo(bolId);
		pickupRequestDetail.setTemplateName(template);
		pickupRequestDetail.setUsername(auth.getUsername());
		pickupRequestDetail.setChangedBy(auth.getUsername());
		pickupRequestDetail.setChangedOn(new Timestamp(System.currentTimeMillis()));
		
		if(pickupRequestDetail.getId()==null){
			pickupRequestDetail.setCreatedBy(auth.getUsername());
			pickupRequestDetail.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		}
		
		pickupRequestDetailRepository.save(pickupRequestDetail);
		
		
		
		List<Role> roles = getRoles(pickupRequestDetail);
		
		if(roles.isEmpty()){
			roles.add(Role.SHIPPER);
		}
		
		pickupDetailNotifyRepository.deleteByPickupRequestDetail(pickupRequestDetail);
		
		savePickupDetailNotify(roles, 
				pickupRequestDetail,
				bolPickup.isAccepted(),
				bolPickup.isCompleted(),
				bolPickup.isRejected());
		
	}
	
	
	@Override
	public void deleteBolPickup(
			final BolType bolType,
			final String bolId, 
			final String bolNumber,
			final String template){
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		PickupRequestDetail pickupRequestDetail = getPickupRequestDetail(bolType, bolId, bolNumber, template, auth.getUsername());
		
		
		if(pickupRequestDetail!=null){
			
			pickupDetailNotifyRepository.deleteByPickupRequestDetail(pickupRequestDetail);
			
			pickupRequestDetailRepository.delete(pickupRequestDetail);
		}
		
	}
	
	@Override
	public List<Role> getBolPickupNotification(
			BolType bolType,
			String bolId, 
			String bolNumber,
			String template){
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		PickupRequestDetail pickupRequestDetail = getPickupRequestDetail(bolType, bolId, bolNumber, template, auth.getUsername());
		return getRoles(pickupRequestDetail);
	}
	
	@Override
	public void saveBolPickupNotification(
			List<Role> roles,
			Notification notification,
			BolType bolType,
			String bolId, 
			String bolNumber,
			String template
			){
		
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		PickupRequestDetail pickupRequestDetail = getPickupRequestDetail(bolType, bolId, bolNumber, template, auth.getUsername());
		
		
		
		if(pickupRequestDetail==null){
			pickupRequestDetail = createNewPickupRequestDetail(bolType, bolId, bolNumber, template, auth);
		}
		
	
		pickupRequestDetailRepository.save(pickupRequestDetail);
		
		pickupDetailNotifyRepository.deleteByPickupRequestDetail(pickupRequestDetail);
	
		savePickupDetailNotify(roles,
					pickupRequestDetail,
					notification.isAccepted(),
					notification.isCompleted(),
					notification.isRejected());
	}
	
	
	
	private void savePickupDetailNotify(
			List<Role> roles,
			PickupRequestDetail pickupRequestDetail,
			boolean accepted,
			boolean completed,
			boolean rejected
			){
		
		for(Role role : roles){
			
			NotifyPartyType notifyPartyType = notifyPartyTypeRepository.findByName(role.getNotifyParty());
	
			if(notifyPartyType!=null){
				if(accepted){
					NotifyStatusType notifyStatusType = notifyStatusTypeRepository.findByName("ACCEPT");
					
					PickupDetailNotify pickupDetailNotify = new PickupDetailNotify();
					
					pickupDetailNotify.setNotifyPartyType(notifyPartyType);
					
					pickupDetailNotify.setNotifyStatusType(notifyStatusType);
					pickupDetailNotify.setPickupRequestDetail(pickupRequestDetail);
					pickupDetailNotifyRepository.save(pickupDetailNotify);
				}
				
				if(completed){
					NotifyStatusType notifyStatusType = notifyStatusTypeRepository.findByName("COMPLETE");
					PickupDetailNotify pickupDetailNotify = new PickupDetailNotify();

					pickupDetailNotify.setNotifyPartyType(notifyPartyType);
					
					pickupDetailNotify.setNotifyStatusType(notifyStatusType);
					pickupDetailNotify.setPickupRequestDetail(pickupRequestDetail);
					pickupDetailNotifyRepository.save(pickupDetailNotify);
				}
				if(rejected){
					NotifyStatusType notifyStatusType = notifyStatusTypeRepository.findByName("REJECT");
					PickupDetailNotify pickupDetailNotify = new PickupDetailNotify();
					pickupDetailNotify.setNotifyPartyType(notifyPartyType);
					
					pickupDetailNotify.setNotifyStatusType(notifyStatusType);
					pickupDetailNotify.setPickupRequestDetail(pickupRequestDetail);
					pickupDetailNotifyRepository.save(pickupDetailNotify);
				}
			}
			
		}
	}
	

	
	private List<Role> getRoles(PickupRequestDetail pickupRequestDetail){
		
		List<Role> roles = new ArrayList<>();
		
		if(pickupRequestDetail==null){
			return roles;
		}
		
		List<String> partyNames = pickupDetailNotifyRepository.findDistinctPartiesByPickupRequestDetailId(pickupRequestDetail.getId());
		
		for(String partyName : partyNames){
			roles.add(Role.getRoleFromNotifyParty(partyName));
		}
		
		return roles;
	}
	

	private PickupRequestDetail createNewPickupRequestDetail(
			BolType bolType,
			String bolId, 
			String bolNumber,
			String template,
			AuthenticationDetails auth){
		
		PickupRequestDetail pickupRequestDetail = new PickupRequestDetail();
		
		PickupDetailType pickupDetailType = pickupDetailTypeRepository.findByName(bolType.name());
		pickupRequestDetail.setPickupDetailType(pickupDetailType);
		
		pickupRequestDetail.setAccountNo(auth.getAccountCode());
		pickupRequestDetail.setBolNo(bolNumber);
		pickupRequestDetail.setBolSeqNo(bolId);
		pickupRequestDetail.setTemplateName(template);
		pickupRequestDetail.setUsername(auth.getUsername());
		pickupRequestDetail.setChangedBy(auth.getUsername());
		pickupRequestDetail.setChangedOn(new Timestamp(System.currentTimeMillis()));
		
		pickupRequestDetail.setCreatedBy(auth.getUsername());
		pickupRequestDetail.setCreatedOn(new Timestamp(System.currentTimeMillis()));
		
		return pickupRequestDetail;
		
	}

	
	private PickupRequestDetail getPickupRequestDetail(
			PickupRequestDetail pickupRequestDetail,
			BolPickup bolPickup
			){
		
		if(pickupRequestDetail==null){
			pickupRequestDetail = new PickupRequestDetail();
		}
		
		pickupRequestDetail.setPickupDate(bolPickup.getPickupDate());
		pickupRequestDetail.setStartTime(Time.valueOf(bolPickup.getStartTime()));
		pickupRequestDetail.setEndTime(Time.valueOf(bolPickup.getEndTime()));
		pickupRequestDetail.setPickupHookDrop(bolPickup.isHookOrDrop()? "Y":"N");
		pickupRequestDetail.setPickupLiftgateRequired(bolPickup.isLiftgateRequired()? "Y" : "N");
		pickupRequestDetail.setPickupPalletsNoStackInd(bolPickup.isNoStackPallet()? "Y" : "N");
		pickupRequestDetail.setPickupInstruction(bolPickup.getSpecialInstruction());
		pickupRequestDetail.setRequesterEmailAddress(bolPickup.getEmail());
		pickupRequestDetail.setRequesterName(bolPickup.getName());
		pickupRequestDetail.setRequesterPhoneNo(bolPickup.formatPhoneForDb());
		pickupRequestDetail.setRequesterPhoneExt(bolPickup.getPhoneExt());
		pickupRequestDetail.setRequesterRole(bolPickup.getRole().getPickupRole());
		pickupRequestDetail.setShipmentFoodInd(bolPickup.isFood()? "Y" : "N");
		pickupRequestDetail.setShipmentFreezeInd(bolPickup.isFreeze()? "Y" : "N");
		pickupRequestDetail.setShipmentOversizeInd(bolPickup.isOversize()? "Y" : "N");
		pickupRequestDetail.setShipmentPoisonInd(bolPickup.isPoision()? "Y" : "N");
		
		pickupRequestDetail.setShipperAccountNo(bolPickup.getAccountCode());
		
		return pickupRequestDetail;
		
	}
	
	private PickupRequestDetail getPickupRequestDetail(
			final BolType bolType,
			final String bolId, 
			final String bolNumber,
			final String template,
			final String username){
		
		PickupRequestDetail pickupRequestDetail;
		
		switch(bolType){
			case HISTORY:
				if(bolId==null){
					throw new AppException(HttpStatus.CONFLICT,"Required String parameter 'bolId' is not present");
				}
				pickupRequestDetail = pickupRequestDetailRepository.findByUsernameAndBolSeqNoAndPickupDetailType_Name
				(username, bolId, "HISTORY");
				break;
			case DRAFT:
				if(bolNumber==null){
					throw new AppException(HttpStatus.CONFLICT,"Required String parameter 'bolNo' is not present");
				}
				pickupRequestDetail = pickupRequestDetailRepository.findByUsernameAndBolNoAndPickupDetailType_Name
				(username, bolNumber, "DRAFT");
				break;
			case TEMPLATE:
				if(template==null){
					throw new AppException(HttpStatus.CONFLICT,"Required String parameter 'template' is not present");
				}
				pickupRequestDetail = pickupRequestDetailRepository.findByUsernameAndTemplateNameAndPickupDetailType_Name
				(username, template, "TEMPLATE");
				break;
			case DEFAULT:

			default:
				pickupRequestDetail = pickupRequestDetailRepository.findByUsernameAndPickupDetailType_Name
				(username, "DEFAULT");
				break;
		}
		
		return pickupRequestDetail;
	}
	
	
	
	
	
}
