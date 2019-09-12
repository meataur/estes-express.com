package com.estes.myestes.PickupRequest.web.service.iface;

import java.util.List;

import com.estes.myestes.PickupRequest.web.dto.Commodity;
import com.estes.myestes.PickupRequest.web.dto.Party;
import com.estes.myestes.PickupRequest.web.dto.PickupCalendar;
import com.estes.myestes.PickupRequest.web.dto.User;

public interface PickupRequestFormService {

	List<User> getUsers(int size);

	List<Party> getShippers(int size);

	List<Commodity> getCommodities(int size);

	List<PickupCalendar> getAvailablePickupDates();

	boolean validatePickupDate(int date);
	

}
