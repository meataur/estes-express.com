package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

public class BolErrorMapper implements ResultSetExtractor<Map<String, String>>{

	@Override
	public Map<String, String> extractData(ResultSet rs) throws SQLException, DataAccessException {
		Map<String, String> errors = new HashMap<>();
		while(rs.next()){
			errors.put(rs.getString("BOL1ERRCD").trim(), rs.getString("BOL1COMM").trim());
		}
		return errors;
	}
}
