package com.estes.myestes.BillOfLading.web.service.iface;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;

public interface ConverterService {
	PickupRequest conver(BillOfLading bol, AuthenticationDetails auth);

	void updateFaxAndEmailForShipperConsigneeThirdPartyFromEmailAndNotification(BillOfLading bol);

}
