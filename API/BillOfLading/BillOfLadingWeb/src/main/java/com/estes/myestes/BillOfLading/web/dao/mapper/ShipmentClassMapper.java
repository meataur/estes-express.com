package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.ShipmentClass;

public class ShipmentClassMapper implements RowMapper<ShipmentClass> {

	@Override
	public ShipmentClass mapRow(ResultSet rs, int rowNum) throws SQLException {
		String code = rs.getString("CODE").trim();
		ShipmentClass shipmentClass = new ShipmentClass();
		shipmentClass.setCode(code);
		shipmentClass.setDescription(code);
		return shipmentClass;
	}

}
