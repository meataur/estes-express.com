package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.Template;

public class TemplateMapper implements RowMapper<Template>{

	@Override
	public Template mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Template template = new Template();
		String templateName = resultSet.getString("TEMPLATENAME").trim();
		String bolNumber = resultSet.getString("BOLNUMBER").trim();
		Date bolDate   = resultSet.getDate("BOLDATE");
		String proNumber = resultSet.getString("PRONUMBER").trim();
		String shipper = resultSet.getString("SHIPPER").trim();
		String shipperFirstName = resultSet.getString("SHIP_FIRST_NAME").trim();
		String shipperLastName = resultSet.getString("SHIP_LAST_NAME").trim();
		String consignee = resultSet.getString("CONSIGNEE").trim();
		String consigneeFirstName = resultSet.getString("CONS_FIRST_NAME").trim();
		String consigneeLastName = resultSet.getString("CONS_LAST_NAME").trim();
		
		
		/**
		 * In database table, bolDate is in terrible format. No fixed date format is followed in previous application.
		 * Sometimes it is stored with slash, sometimes with no slash & sometimes it is a garbage one!
		 * I just try to remove slash & make it as MMddyyyy format. all other formats will be null value.
		 * 
		 */
		
		
		proNumber = (proNumber.trim().equals("-"))? null : proNumber;
		
		template.setTemplateName(templateName);
		template.setBolNumber(bolNumber);
		template.setBolDate(bolDate);
		template.setProNumber(proNumber);
		template.setShipper(shipper);
		template.setShipperFirstName(shipperFirstName);
		template.setShipperLastName(shipperLastName);
		template.setConsignee(consignee);
		template.setConsigneeFirstName(consigneeFirstName);
		template.setConsigneeLastName(consigneeLastName);
		return template;
	}
	
}