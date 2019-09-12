package com.estes.myestes.BillOfLading.web.service.iface;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BolType;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;

public interface PickupNotificationService {

	EmailAndFaxNotification getPickupNotification(AuthenticationDetails auth, EmailAndFaxNotification emailAndFaxNotification,
			BolType bolType, String param);

	void updatePickupNotification(AuthenticationDetails auth, EmailAndFaxNotification emailAndFaxNotification,
			boolean accepted, boolean completed, BolType bolType, String param);

}
