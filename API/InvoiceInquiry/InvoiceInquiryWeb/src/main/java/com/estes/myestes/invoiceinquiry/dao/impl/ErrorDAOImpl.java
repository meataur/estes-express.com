/**
 * @author: Todd Allen
 *
 * Creation date: 01/30/2019
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.invoiceinquiry.dao.iface.ErrorDAO;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	public String getErrorMessage(String errCode)
	{
		String sql = "SELECT ndcomm " +
				"FROM estesrtgy2.ndp950 " +
				"WHERE nder = ? ";

		try {
			return jdbcMyEstes.queryForObject(sql,  String.class, new Object[] {errCode});
		}
		catch (Exception e) {
			return "";
		}
	} // getErrorMessage
}
