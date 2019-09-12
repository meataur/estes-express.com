package com.estes.myestes.PickupRequest.web.dao.iface;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.PickupRequest.web.dto.Pickup;

public interface PickupHistoryDAO {

	Page<Pickup> getPickup(Pageable pageable);

}
