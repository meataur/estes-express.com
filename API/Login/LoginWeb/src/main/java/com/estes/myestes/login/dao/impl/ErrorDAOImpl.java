/**
 * @author: Todd Allen
 *
 * Creation date: 07/02/2018
 *
 */

package com.estes.myestes.login.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.login.dao.iface.ErrorDAO;
import com.estes.myestes.login.exception.LoginException;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	public String getErrorMessage(String errCode) throws LoginException
	{
		String sql = "SELECT ndcomm " +
				"FROM " + ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";
		String msg =  jdbcMyEstes.queryForObject(sql,  String.class, new Object[] {errCode});
		if (msg != null && !msg.equals("")) {
			return msg;
		}

		throw new LoginException("Error looking up error code " + errCode + ".");
	} // getAccountCode
}
