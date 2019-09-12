package com.estes.myestes.PickupRequest.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estes.myestes.PickupRequest.web.dao.iface.PickupRequestDAO;
import com.estes.myestes.PickupRequest.web.dto.PickupRequest;
import com.estes.myestes.PickupRequest.web.service.iface.PickupRequestService;

@Service("pickupRequestService")
public class PickupRequestServiceImpl implements PickupRequestService{

	@Autowired
	private PickupRequestDAO pickupRequestDAO;

	@Override
	public void createPickupRequest(PickupRequest pickupRequest) {
		pickupRequestDAO.savePickupRequest(pickupRequest);
	}

}
