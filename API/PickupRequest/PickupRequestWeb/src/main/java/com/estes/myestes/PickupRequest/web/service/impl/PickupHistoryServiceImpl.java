package com.estes.myestes.PickupRequest.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.PickupRequest.web.dao.iface.PickupHistoryDAO;
import com.estes.myestes.PickupRequest.web.dto.Pickup;
import com.estes.myestes.PickupRequest.web.service.iface.PickupHistoryService;


@Service("pickupHistoryService")
public class PickupHistoryServiceImpl implements PickupHistoryService {
	
	@Autowired
	private PickupHistoryDAO pickupHistoryDAO;
	
	@Override
	public Page<Pickup> getPickup(Pageable pageable) {
		return pickupHistoryDAO.getPickup(pageable);
	}

}
