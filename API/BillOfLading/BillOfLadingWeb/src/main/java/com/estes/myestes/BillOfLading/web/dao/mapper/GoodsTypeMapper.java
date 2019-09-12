package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.GoodsType;

public class GoodsTypeMapper implements RowMapper<GoodsType>  {

	@Override
	public GoodsType mapRow(ResultSet rs, int rowNum) throws SQLException {
		String code = rs.getString("CODE").trim();
		String description = rs.getString("DESCRIPTION").trim();
		GoodsType goodsType = new GoodsType();
		goodsType.setCode(code);
		goodsType.setDescription(description);
		return goodsType;
	}

}
