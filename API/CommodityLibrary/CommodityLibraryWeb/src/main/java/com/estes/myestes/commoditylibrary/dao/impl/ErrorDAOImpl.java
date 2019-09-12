/**
 * @author: Lakshman K
 *
 * Creation date: 12/19/2018
 *
 */

package com.estes.myestes.commoditylibrary.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.commoditylibrary.dao.iface.ErrorDAO;
import com.estes.myestes.commoditylibrary.utils.CommodityLibraryConstant;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO, CommodityLibraryConstant
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	public String getErrorMessage(String errCode)
	{
		String sql = "SELECT ndcomm " +
				"FROM " + ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";

		try {
			return jdbcMyEstes.queryForObject(sql,  String.class, new Object[] {errCode});
		}
		catch (Exception e) {
			return "";
		}
	} // getErrorMessage
}
