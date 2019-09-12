package com.estes.myestes.profile.web.dao.mapper;



import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.UserStatus;

public class ProfileMapper implements RowMapper<Profile>{

	@Override
	public Profile mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Profile profile = new Profile();
		String firstName      = resultSet.getString("FIRSTNAME").trim();
		String lastName       = resultSet.getString("LASTNAME").trim();
		String accountCode    = resultSet.getString("ACCOUNTCODE").trim();
		String phone          = resultSet.getString("PHONE").trim();
		String companyName    = resultSet.getString("COMPANYNAME").trim();
		String username       = resultSet.getString("USERNAME").trim();
		String email          = resultSet.getString("EMAIL").trim();
		String preference     = resultSet.getString("PREFERENCE").trim();
		String status         = resultSet.getString("STATUS").trim();
		String createdDate    = resultSet.getString("CREATEDDATE").trim();
		
		profile.setFirstName(firstName);
		profile.setLastName(lastName);
		profile.setAccountCode(accountCode);
		profile.setPhone(phone);
		profile.setCompanyName(companyName);
		profile.setUsername(username);
		profile.setEmail(email);
		profile.setPreference(preference);
		profile.setStatus(UserStatus.valueOf(status));
		profile.setCreatedDate(createdDate);
		
		if(profile.getPhone().length() < 14){
			profile.setPhone("");
		}
		return profile;
	}
	
}