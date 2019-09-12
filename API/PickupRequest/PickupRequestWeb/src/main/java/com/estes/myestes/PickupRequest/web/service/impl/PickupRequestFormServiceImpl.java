package com.estes.myestes.PickupRequest.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estes.myestes.PickupRequest.web.dao.iface.PickupRequestFormDAO;
import com.estes.myestes.PickupRequest.web.dto.Commodity;
import com.estes.myestes.PickupRequest.web.dto.Party;
import com.estes.myestes.PickupRequest.web.dto.PickupCalendar;
import com.estes.myestes.PickupRequest.web.dto.User;
import com.estes.myestes.PickupRequest.web.service.iface.PickupRequestFormService;

@Repository("pickupRequestFormService")
public class PickupRequestFormServiceImpl implements PickupRequestFormService {
	
	@Autowired
	private PickupRequestFormDAO pickupRequestFormDAO;
	
	
	@Override
	public List<User> getUsers(int size) {
		return pickupRequestFormDAO.getUsers(size);
	}

	@Override
	public List<Party> getShippers(int size) {
		return pickupRequestFormDAO.getShippers(size);
	}

	@Override
	public List<Commodity> getCommodities(int size) {
		return pickupRequestFormDAO.getCommodities(size);
	}

	@Override
	public List<PickupCalendar> getAvailablePickupDates() {
		 return pickupRequestFormDAO.getAvailablePickupDates();
	}

	@Override
	public boolean validatePickupDate(int date) {
		return pickupRequestFormDAO.validatePickupDate(date);
	}
}
