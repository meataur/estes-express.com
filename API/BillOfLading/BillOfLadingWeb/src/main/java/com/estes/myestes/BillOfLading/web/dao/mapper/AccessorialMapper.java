package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.Accessorial;

public class AccessorialMapper implements RowMapper<Accessorial> {

	@Override
	public Accessorial mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String code = rs.getString("CODE").trim();
		String description = rs.getString("DESCRIPTION").trim();
		String display = "N";
		try{
			display = rs.getString("ON_SCR").trim();
		}catch(Exception ex){
			display = "N";
		}
		Accessorial accessorial = new Accessorial();
		
		accessorial.setCode(code);
		accessorial.setDescription(description);
		accessorial.setDisplay("Y".equalsIgnoreCase(display));
	
		return accessorial;
	}

}
