package com.estes.myestes.PickupRequest.web.service.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.PickupRequest.web.dto.Pickup;
public interface PickupHistoryService {

	Page<Pickup> getPickup(Pageable pageable);
	
}
