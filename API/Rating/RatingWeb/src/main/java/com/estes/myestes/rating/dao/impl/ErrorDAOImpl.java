package com.estes.myestes.rating.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.BaseDAO;
import com.estes.myestes.rating.dao.iface.ErrorDAO;
@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getErrorMessage(String errCode)
	{
		ESTESLogger.log(
				ESTESLogger.DEBUG,
				getClass(),
				"getErrorMessage()",
				"Error Code: "+errCode
			);
		String sql = "SELECT LTRIM(ndcomm) " +
				"FROM " + BaseDAO.ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";

		try {
			String errorMessage=  jdbcTemplate.queryForObject(sql,  String.class, new Object[] {errCode});
		    return errorMessage.trim();
		}
		catch (Exception e) {
			return null;
		}
	} // getErrorMessage
}