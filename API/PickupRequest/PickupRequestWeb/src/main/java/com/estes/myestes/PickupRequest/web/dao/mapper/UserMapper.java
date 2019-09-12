package com.estes.myestes.PickupRequest.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.PickupRequest.web.dto.User;

public class UserMapper  implements RowMapper<User> {


	@Override
	public User mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		User  user= new User();
		//int id = resultSet.getInt("ID");
		String name     = resultSet.getString("NAME").trim();
		String phone    = resultSet.getString("PHONE").trim();
		String phoneExt = resultSet.getString("PHONEEXT").trim();
		String email    = resultSet.getString("EMAIL").trim();
		
		if("0".equals(phoneExt)){
			phoneExt="";
		}
		
		//user.setId(id);
		user.setName(name);
		user.setPhone(phone);
		user.setPhoneExt(phoneExt);
		user.setEmail(email);
		return user;
	}
}