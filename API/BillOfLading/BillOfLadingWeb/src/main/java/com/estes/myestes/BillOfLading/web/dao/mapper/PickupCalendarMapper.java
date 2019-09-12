package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.web.dto.PickupCalendar;

public class PickupCalendarMapper implements RowMapper<PickupCalendar>{
	
	@Override
	public PickupCalendar mapRow(ResultSet rs, int rowNum) throws SQLException {
		String date = rs.getString("WDDAT8");
		int day  = rs.getInt("WDDAY");
		String holiday = rs.getString("wdexcp");
		PickupCalendar pickupCalendar = new PickupCalendar();
		
		pickupCalendar.setDate(EstesUtil.formatDate(date, EstesUtil.DATE_YYYYMMDD));
		pickupCalendar.setWeekend(day>5);
		pickupCalendar.setHoliday("H".equalsIgnoreCase(holiday));
		return pickupCalendar;
	}

}
