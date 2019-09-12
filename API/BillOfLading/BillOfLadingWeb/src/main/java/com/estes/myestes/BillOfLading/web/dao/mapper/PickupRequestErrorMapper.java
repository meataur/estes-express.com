package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.dto.common.ServiceResponse;

public class PickupRequestErrorMapper implements RowMapper<ServiceResponse> {


	@Override
	public ServiceResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
		String field        = rs.getString("FIELD").trim();
		String code         = rs.getString("CODE").trim();
		String description  = rs.getString("DESC").trim();
		String badData      = rs.getString("BADDATA").trim();
		String defaultTo    = rs.getString("DEFAULTTO").trim();
		String flag         = rs.getString("FLAG").trim();
		String flag2        = rs.getString("FLAG2").trim();  
		
		ServiceResponse serviceResponse = new ServiceResponse();

		serviceResponse.setFieldName(field);
		serviceResponse.setErrorCode(code);
		serviceResponse.setMessage(description);
		serviceResponse.setBadData(badData+" : "+ defaultTo+" : "+flag + " : "+flag2);
		return serviceResponse;
	}

}
