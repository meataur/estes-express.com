package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.web.dto.Bol;

public class BolMapper implements RowMapper<Bol>{

	@Override
	public Bol mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Bol bol       = new Bol();
		
		int bolId        = resultSet.getInt("BOL_SEQUENCE");
		String bolNumber = resultSet.getString("BOL_NUMBER").trim();
		String bolDate   = resultSet.getString("BOL_DATE").trim();
		
		String proOt     = resultSet.getString("PRO_OT").trim();
		String proNum    = resultSet.getString("PRO_NUM").trim();
		String proNumber = resultSet.getString("PRO_NUMBER").trim();
		
		String shipperCompanyName   = resultSet.getString("SHIPPER_COMPANY_NAME").trim();
		String shipperFirstName     = resultSet.getString("SHIPPER_FIRST_NAME").trim();
		String shipperLastName      = resultSet.getString("SHIPPER_LAST_NAME").trim();
		
		String consigneeCompanyName = resultSet.getString("CONSIGNEE_COMPANY_NAME").trim();
		String consigneeFirstName   = resultSet.getString("CONSIGNEE_FIRST_NAME").trim();
		String consigneeLastName    = resultSet.getString("CONSIGNEE_LAST_NAME").trim();
		
		
		
		String shippingLabelStart   = resultSet.getString("SHIPPING_LABEL_START");
		String shippingLabelTotal   = resultSet.getString("SHIPPING_LABEL_TOTAL");
		String shippingLabelType    = resultSet.getString("SHIPPING_LABEL_TYPE");
		
		int hasShippingLabel  = resultSet.getInt("HAS_SHIPPING_LABEL");

		
		proNumber = (proNumber.equals("000-0000000"))? null : proNumber;
		
		bol.setBolId(bolId);
		bol.setBolNumber(bolNumber);
		bol.setBolDate(EstesUtil.formatDate(bolDate,"yyyyMMdd"));
		bol.setProOt(proOt.equals("000")? null : proOt);
		bol.setProNum(proNum.equals("0000000")? null : proNum);
		bol.setProNumber(proNumber);
		bol.setShipperCompanyName(shipperCompanyName);
		bol.setShipperFirstName(shipperFirstName);
		bol.setShipperLastName(shipperLastName);
		bol.setConsigneeCompanyName(consigneeCompanyName);
		bol.setConsigneeFirstName(consigneeFirstName);
		bol.setConsigneeLastName(consigneeLastName);
		bol.setShippingLabelStart(shippingLabelStart);
		bol.setShippingLabelTotal(shippingLabelTotal);
		bol.setShippingLabelType(shippingLabelType);
		
		bol.setHasShippingLabel(false);
		if(hasShippingLabel>0){
			bol.setHasShippingLabel(true);
		}
		
		return bol;
	}
	
}