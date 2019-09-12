/**
 * @author: Todd Allen
 *
 * Creation date: 08/07/2018
 *
 */

package com.estes.myestes.signup.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.signup.dao.iface.ErrorDAO;
import com.estes.myestes.signup.exception.SignupException;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	public String getErrorMessage(String errCode) throws SignupException
	{
		String sql = "SELECT LTRIM(ndcomm) " +
				"FROM " + ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";

		try {
			return  jdbcMyEstes.queryForObject(sql,  String.class, new Object[] {errCode});
		}
		catch (Exception e) {
			throw new SignupException(e);
		}
	} // getErrorMessage
}
