package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.web.dto.Draft;

public class DraftMapper implements RowMapper<Draft>{

	@Override
	public Draft mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Draft draft = new Draft();
		String bolNumber = resultSet.getString("BOLNUMBER").trim();
		String bolDate   = resultSet.getString("BOLDATE").trim();
		String proNumber = resultSet.getString("PRONUMBER").trim();
		String shipper = resultSet.getString("SHIPPER").trim();
		String consignee = resultSet.getString("CONSIGNEE").trim();
		
		proNumber = (proNumber.trim().equals("-"))? null : proNumber;
		
		draft.setBolNumber(bolNumber);
		draft.setBolDate(EstesUtil.formatDate(bolDate,"yyyyMMdd"));
		draft.setProNumber(proNumber);
		draft.setShipper(shipper);
		draft.setConsignee(consignee);
		return draft;
	}
	
}