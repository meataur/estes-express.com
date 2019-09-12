package com.estes.myestes.BillOfLading.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dao.iface.UserDefaultDAO;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.AddressInformation;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BillTo;
import com.estes.myestes.BillOfLading.web.dto.BillingInformation;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.CommodityInformation;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.GeneralInformation;
import com.estes.myestes.BillOfLading.web.dto.PickupDetailInfo;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;
import com.estes.myestes.BillOfLading.web.dto.common.Notification;
import com.estes.myestes.BillOfLading.web.service.iface.PickupNotificationService;
import com.estes.myestes.BillOfLading.web.service.iface.PickupRequestRestService;
import com.estes.myestes.BillOfLading.web.service.iface.UserDeafultService;

@Service("userDeafultService")
public class UserDefaultServiceImpl implements UserDeafultService {
	
	@Autowired
	private UserDefaultDAO userDefaultDAO;
	
	@Autowired
	private PickupRequestRestService pickupRequestRestService;
	
	@Autowired
	private PickupNotificationService pickupNotificationService;
	
	@Override
	public PickupDetailInfo getUserPickupRequestInformation(String accessToken) {
		return pickupRequestRestService.getPickupInformation(accessToken, BolType.DEFAULT, null);
	}

	
	@Override
	public void updateUserPickupRequestInformation(String accessToken, PickupDetailInfo pickuDetailInformation) {
		pickupRequestRestService.savePickupInformation(pickuDetailInformation, accessToken,BolType.DEFAULT, null);
	}

	@Override
	public AddressInformation getUserShipperInformation(String username) {
		return userDefaultDAO.getUserShipperInformation(username);
	}

	@Override
	public void updateUserShipperInformation(String username, AddressInformation shipper) {
		userDefaultDAO.updateUserShipperInformation(username,shipper);
	}

	@Override
	public AddressInformation getUserConsigneeInformation(String username) {
		return userDefaultDAO.getUserConsigneeInformation(username);
	}

	@Override
	public void updateUserConsigneeInformation(String username, AddressInformation consignee) {
		userDefaultDAO.updateUserConsigneeInformation(username,consignee);

	}

	@Override
	public CommodityInformation getUserCommodityInformation(String username) {
		return userDefaultDAO.getUserCommodityInformation(username);
	}

	@Override
	public void updateUserCommodityInformation(String username, CommodityInformation commodityInformation) {
		userDefaultDAO.updateUserCommodityInformation(username,commodityInformation);

	}

	@Override
	public BillTo getUserBillingInformation(String username) {
		return userDefaultDAO.getUserBillingInformation(username);
	}

	@Override
	public void updateUserBillingInformation(String username, BillTo billingInformation) {
		userDefaultDAO.updateUserBillingInformation(username,billingInformation);

	}

	@Override
	public List<Accessorial> getUserAccessorialInformation(String username) {
		return userDefaultDAO.getUserAccessorialInformation(username);
	}

	@Override
	public void updateUserAccessorialInformation(String username, List<Accessorial> accessorials) {
		userDefaultDAO.updateUserAccessorialInformation(username,accessorials);

	}

	@Override
	public ShippingLabel getUserShippingLabelInformation(String username) {
		return userDefaultDAO.getUserShippingLabelInformation(username);
	}

	@Override
	public void updateUserShippingLabelInformation(String username, ShippingLabel shippingLabel) {
		userDefaultDAO.updateUserShippingLabelInformation(username,shippingLabel);

	}

	@Override
	public EmailAndFaxNotification getUserNotificationInformation(AuthenticationDetails auth) {
		
		EmailAndFaxNotification emailAndFaxNotification = userDefaultDAO.getUserNotificationInformation(auth.getUsername());
		
		emailAndFaxNotification = pickupNotificationService.getPickupNotification(auth, emailAndFaxNotification, BolType.DEFAULT, null);
	
		return emailAndFaxNotification;
	}

	@Override
	public void updateUserNotificationInformation(AuthenticationDetails auth,
			EmailAndFaxNotification emailAndFaxNotification, Notification notification) {
		
		userDefaultDAO.updateUserNotificationInformation(auth.getUsername(),emailAndFaxNotification);
		
		pickupNotificationService.updatePickupNotification(auth, emailAndFaxNotification, notification.isAccepted(),notification.isCompleted(), BolType.DEFAULT, null);
	}
	


	@Override
	public BillOfLading getUserDefaultBol(AuthenticationDetails auth) {
		BillOfLading billOfLading = new BillOfLading();
		billOfLading.setGeneralInfo(new GeneralInformation());
		billOfLading.setPickupDetailInfo(getUserPickupRequestInformation(auth.getAccessToken()));
		billOfLading.setShipperInfo(getUserShipperInformation(auth.getUsername()));
		billOfLading.setConsigneeInfo(getUserConsigneeInformation(auth.getUsername()));
		billOfLading.setCommodityInfo(getUserCommodityInformation(auth.getUsername()));
		BillingInformation billingInfo = new BillingInformation();
		billingInfo.setBillTo(getUserBillingInformation(auth.getUsername()));
		billOfLading.setBillingInfo(billingInfo);
		billOfLading.setAccessorials(getUserAccessorialInformation(auth.getUsername()));
		billOfLading.setShippingLabel(getUserShippingLabelInformation(auth.getUsername()));
		billOfLading.setEmailAndFaxNotification(getUserNotificationInformation(auth));
		return billOfLading;
	}

}
