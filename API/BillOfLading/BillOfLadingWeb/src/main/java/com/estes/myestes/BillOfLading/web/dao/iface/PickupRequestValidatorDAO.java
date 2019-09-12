package com.estes.myestes.BillOfLading.web.dao.iface;

import java.util.Map;

import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;

public interface PickupRequestValidatorDAO {

	void validatePickupRequest(PickupRequest pickupRequest, BillOfLading bol, AuthenticationDetails auth);

	boolean isActiveAccount(String accountCode);

	boolean isAccountExists(String query, String parentAccountCode, String subAccountCode);

	Map<String, ?> getAccounInfoByAccountCode(String accountCode);

}
