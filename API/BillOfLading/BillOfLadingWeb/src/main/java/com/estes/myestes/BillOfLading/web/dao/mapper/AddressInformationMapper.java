
package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.web.dto.AddressInformation;
import com.estes.myestes.BillOfLading.web.dto.Country;

public class AddressInformationMapper implements RowMapper<AddressInformation>{

	@Override
	public AddressInformation mapRow(ResultSet resultSet, int rowNum) throws SQLException {

		
		String companyName = resultSet.getString("COMPANY_NAME").trim();
		String firstName = resultSet.getString("FIRST_NAME").trim();
		String lastName = resultSet.getString("LAST_NAME").trim();
		String location = resultSet.getString("LOCATION").trim();
		String phone = resultSet.getString("PHONE").trim();
		String phoneExt = resultSet.getString("PHONE_EXTENSION").trim();
		String fax = resultSet.getString("FAX").trim();
		String countryVal = resultSet.getString("COUNTRY").trim();
		String address1 = resultSet.getString("ADDRESS1").trim();
		String address2 = resultSet.getString("ADDRESS2").trim();
		String city = resultSet.getString("CITY").trim();
		String state = resultSet.getString("STATE").trim();
		String zip = resultSet.getString("ZIP").trim();
		String email = resultSet.getString("EMAIL_ADDRESS").trim();
		
		Country country = null;
		if(! countryVal.trim().equals("")){
			country = Country.valueOf(countryVal);
		}
		AddressInformation addressInformation = new AddressInformation();
		addressInformation.setCompanyName(companyName);
		addressInformation.setFirstName(firstName);	
		addressInformation.setLastName(lastName);	
		addressInformation.setLocation(location);	
		addressInformation.setPhone(EstesUtil.getPhoneOrFax(phone));	
		addressInformation.setPhoneExt(phoneExt);	
		addressInformation.setFax(EstesUtil.getPhoneOrFax(fax));	
		addressInformation.setCountry(country);	
		addressInformation.setAddress1(address1);	
		addressInformation.setAddress2(address2);	
		addressInformation.setCity(city);	
		addressInformation.setState(state);	
		addressInformation.setZip(zip);
		addressInformation.setEmail(email);	
		
		return addressInformation;
	}

}
