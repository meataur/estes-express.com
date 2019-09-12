package com.estes.myestes.PickupRequest.web.dao.impl;

import static com.estes.myestes.PickupRequest.web.dao.sql.Schema.ALT_PGM_SCHEMA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.PickupRequest.exception.AppException;
import com.estes.myestes.PickupRequest.web.dao.iface.ErrorDAO;
@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getErrorMessage(String errCode) throws AppException
	{
		ESTESLogger.log(
				ESTESLogger.DEBUG,
				getClass(),
				"getErrorMessage()",
				"Error Code: "+errCode
			);
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