package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.CommodityInformation;

public class CommodityInformationMapper implements RowMapper<CommodityInformation>{

	@Override
	public CommodityInformation mapRow(ResultSet rs, int rowNum) throws SQLException {

		
		String contactName = rs.getString("CONTACT_NAME").trim();
		String phone = rs.getString("PHONE").trim();
		String phoneExt = rs.getString("PHONE_EXT").trim();
		String totalCube = rs.getString("TOTAL_CUBE").trim();
		String specialIns = rs.getString("SPECIAL_INS").trim();
		
		CommodityInformation commodityInfo = new CommodityInformation();
		commodityInfo.setContactName(contactName);
		commodityInfo.setPhone(phone);
		commodityInfo.setPhoneExt(phoneExt);
		commodityInfo.setTotalCube(totalCube);
		commodityInfo.setSpecialIns(specialIns);
		
		return commodityInfo;
	}

}
