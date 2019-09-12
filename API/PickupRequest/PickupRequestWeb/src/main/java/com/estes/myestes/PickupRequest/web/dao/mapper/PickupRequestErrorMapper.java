package com.estes.myestes.PickupRequest.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.PickupRequest.web.dto.PickupRequestError;

public class PickupRequestErrorMapper implements RowMapper<PickupRequestError> {
	
	@Override
	public PickupRequestError mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String field        = rs.getString("FIELD").trim();
		String code         = rs.getString("CODE").trim();
		String description  = rs.getString("DESC").trim();
		String badData      = rs.getString("BADDATA").trim();
		String defaultTo    = rs.getString("DEFAULTTO").trim();
		String flag         = rs.getString("FLAG").trim();
		String flag2        = rs.getString("FLAG2").trim();  
		
		PickupRequestError error = new PickupRequestError();
		
		error.setField(field);
		error.setCode(code);
		error.setDescription(description);
		error.setBadData(badData);
		error.setDefaultTo(defaultTo);
		error.setFlag(flag);
		error.setFlag2(flag2);
		return error;
	}

}
