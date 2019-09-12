package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.Reference;
import com.estes.myestes.BillOfLading.web.dto.ReferenceType;

public class ReferenceMapper implements RowMapper<Reference> {

	@Override
	public Reference mapRow(ResultSet rs, int rowNum) throws SQLException {
		String referenceId      = rs.getString("REFERENCE_ID").trim();
		String referenceNumber  = rs.getString("REFERENCE_NUMBER").trim();
		String referenceType    = rs.getString("REFERENCE_TYPE").trim();
		String cartoon          = rs.getString("CARTOON").trim();
		String weight           = rs.getString("WEIGHT").trim();
		
		Reference reference = new Reference();
		reference.setReferenceId(referenceId);
		reference.setReferenceNumber(referenceNumber);
		reference.setReferenceType(ReferenceType.valueOf(referenceType));
		reference.setCartoon(cartoon);
		reference.setWeight(weight);
		
	
		return reference;
	}

}
