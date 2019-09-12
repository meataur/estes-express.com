/**
 * @author: Todd Allen
 *
 * Creation date: 03/26/2019
 */

package com.estes.myestes.rating.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.rating.dao.iface.CustomerDAO;

@Repository ("customerDAO")
public class CustomerDAOImpl implements CustomerDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public CustomerDAOImpl()
	{
	} // Constructor

	@Override
	public String getName(String acct)
	{
		String sql = "SELECT cmname FROM " + DATA_SCHEMA + ".rap001 " +
				"WHERE cmcust = ?";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] { acct }, String.class);
		}
		catch (Exception e) {
			return "";
		}
	} // getName
}
