package com.estes.myestes.PickupRequest.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.PickupRequest.web.dto.Party;

public class ShipperMapper  implements RowMapper<Party> {

	@Override
	public Party mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Party  shipper= new Party();
		String name = resultSet.getString("NAME").trim();
		String company = resultSet.getString("COMPANY").trim();;
		String addressLine1 = resultSet.getString("ADDRESSLINE1").trim();;
		String addressLine2 = resultSet.getString("ADDRESSLINE2").trim();;
		String city = resultSet.getString("CITY").trim();;
		String state = resultSet.getString("STATE").trim();;
		String zip = resultSet.getString("ZIP").trim();;
		String zipExt = resultSet.getString("ZIPEXT").trim();;
		String phone = resultSet.getString("PHONE").trim();;
		String phoneExt = resultSet.getString("PHONEEXT").trim();;
		String email = resultSet.getString("EMAIL").trim();;
		
		shipper.setName(name);
		shipper.setCompany(company);
		shipper.setAddressLine1(addressLine1);
		shipper.setAddressLine2(addressLine2);
		shipper.setCity(city);
		shipper.setState(state);
		shipper.setZip(zip);
		shipper.setZipExt(zipExt);
		shipper.setPhone(phone);
		shipper.setPhoneExt(phoneExt);
		shipper.setEmail(email);
		return shipper;
	}
}