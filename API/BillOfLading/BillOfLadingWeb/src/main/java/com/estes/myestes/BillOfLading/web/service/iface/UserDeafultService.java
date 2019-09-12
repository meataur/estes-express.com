package com.estes.myestes.BillOfLading.web.service.iface;

import java.util.List;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.AddressInformation;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BillTo;
import com.estes.myestes.BillOfLading.web.dto.CommodityInformation;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.PickupDetailInfo;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;
import com.estes.myestes.BillOfLading.web.dto.common.Notification;

public interface UserDeafultService {

	public PickupDetailInfo getUserPickupRequestInformation(String accessToken);

	public void updateUserPickupRequestInformation(String accessToken, PickupDetailInfo pickupRequest);

	public AddressInformation getUserShipperInformation(String username);

	public void updateUserShipperInformation(String username, AddressInformation shipper);

	public AddressInformation getUserConsigneeInformation(String username);

	public void updateUserConsigneeInformation(String username, AddressInformation consignee);

	public CommodityInformation getUserCommodityInformation(String username);

	public void updateUserCommodityInformation(String username, CommodityInformation commodityInformation);

	public BillTo getUserBillingInformation(String username);

	public void updateUserBillingInformation(String username, BillTo billingInformation);

	public List<Accessorial> getUserAccessorialInformation(String username);

	public void updateUserAccessorialInformation(String username, List<Accessorial> accessorials);

	public ShippingLabel getUserShippingLabelInformation(String username);

	public void updateUserShippingLabelInformation(String username, ShippingLabel shippingLabel);

	public EmailAndFaxNotification getUserNotificationInformation(AuthenticationDetails auth);

	public void updateUserNotificationInformation(AuthenticationDetails auth, 
			EmailAndFaxNotification emailAndFaxNotifications, Notification notification);

	public BillOfLading getUserDefaultBol(AuthenticationDetails auth);


}
