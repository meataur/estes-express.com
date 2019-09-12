package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;

public class ShippingLabelMapper implements RowMapper<ShippingLabel> {

	@Override
	public ShippingLabel mapRow(ResultSet rs, int rowNum) throws SQLException {
		String labelType  = rs.getString("LABEL_TYPE").trim();
		String startLabel = rs.getString("START_LABEL").trim();
		String numberOfLabel = rs.getString("NO_OF_LABEL").trim();
	
		ShippingLabel shippingLabel = new ShippingLabel();
		shippingLabel.setLabelType(labelType);
		shippingLabel.setStartLabel(startLabel);
		shippingLabel.setNumberOfLabel(numberOfLabel);
		
		return shippingLabel;
	}

}
