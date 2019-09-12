package com.estes.myestes.PickupRequest.web.dao.impl;

import static com.estes.myestes.PickupRequest.web.dao.sql.PickupRequestQuery.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.PickupRequest.config.AuthenticationDetails;
import com.estes.myestes.PickupRequest.web.dao.iface.PickupRequestFormDAO;
import com.estes.myestes.PickupRequest.web.dao.mapper.CommodityMapper;
import com.estes.myestes.PickupRequest.web.dao.mapper.PickupCalendarMapper;
import com.estes.myestes.PickupRequest.web.dao.mapper.ShipperMapper;
import com.estes.myestes.PickupRequest.web.dao.mapper.UserMapper;
import com.estes.myestes.PickupRequest.web.dto.Commodity;
import com.estes.myestes.PickupRequest.web.dto.Party;
import com.estes.myestes.PickupRequest.web.dto.PickupCalendar;
import com.estes.myestes.PickupRequest.web.dto.User;



@Repository("pickupRequestFormDAO")
public class PickupRequestFormDAOImpl implements PickupRequestFormDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<User> getUsers(int size) {
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		List<User> users = jdbcTemplate.query(PREVIOUS_USER_QUERY, new Object[]{auth.getUsername(), size},new UserMapper());
		return users;
	}

	@Override
	public List<Party> getShippers(int size) {
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		List<Party> shippers = jdbcTemplate.query(PREVIOUS_SHIPPER_QUERY, new Object[]{auth.getUsername(),size}, new ShipperMapper());
		return shippers;
	}

	@Override
	public List<Commodity> getCommodities(int size) {
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		List<Commodity> commodities = jdbcTemplate.query(PREVIOUS_COMMOTITY_QUERY, new Object[]{auth.getUsername(),size}, new CommodityMapper());
		return commodities;
	}
	
	@Override
	public List<PickupCalendar> getAvailablePickupDates() {
		return jdbcTemplate.query(GET_AVAILABLE_PICKUP_DATE,new PickupCalendarMapper());
	}

	
	@Override
	public boolean validatePickupDate(int date) {
		int row = jdbcTemplate.queryForObject(VALID_PICKUP_DATE_QUERY, new Object[]{date}, Integer.class);
		return row >0;
	}

}
