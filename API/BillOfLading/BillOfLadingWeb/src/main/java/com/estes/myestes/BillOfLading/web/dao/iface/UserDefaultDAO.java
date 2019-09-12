package com.estes.myestes.BillOfLading.web.dao.iface;

import java.util.List;

import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.AddressInformation;
import com.estes.myestes.BillOfLading.web.dto.BillTo;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.CommodityInformation;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;

public interface UserDefaultDAO{
	
	public AddressInformation getUserShipperInformation(String username);

	AddressInformation getUserConsigneeInformation(String username);

	public BillTo getUserBillingInformation(String username);

	public CommodityInformation getUserCommodityInformation(String username);

	List<Commodity> getUserCommodityList(String username);

	public List<Accessorial> getUserAccessorialInformation(String username);

	public ShippingLabel getUserShippingLabelInformation(String username);

	public EmailAndFaxNotification getUserNotificationInformation(String username);

	public void updateUserShipperInformation(String username, AddressInformation shipper);

	public void updateUserConsigneeInformation(String username, AddressInformation consignee);

	public void updateUserCommodityInformation(String username, CommodityInformation commodityInformation);

	public void updateUserBillingInformation(String username, BillTo billingInformation);

	public void updateUserAccessorialInformation(String username, List<Accessorial> accessorials);

	public void updateUserShippingLabelInformation(String username, ShippingLabel shippingLabel);

	public void updateUserNotificationInformation(String username, EmailAndFaxNotification emailAndFaxNotification);
}
