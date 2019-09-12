package com.estes.myestes.wrinquiry.web.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.wrinquiry.exception.AppException;
import com.estes.myestes.wrinquiry.web.dao.iface.ErrorDAO;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getErrorMessage(String errCode) throws AppException
	{
		String sql = "SELECT LTRIM(ndcomm) " +
				"FROM " + ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";

		try {
			String errorMessage=  jdbcTemplate.queryForObject(sql,  String.class, new Object[] {errCode});
		    return errorMessage.trim();
		}
		catch (Exception e) {
			throw new AppException(e);
		}
	} // getErrorMessage
}