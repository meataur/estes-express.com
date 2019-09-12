package com.estes.myestes.pcraterdownload.dao.impl;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.pcraterdownload.dao.iface.ErrorDAO;
import com.estes.myestes.pcraterdownload.utils.PCRaterDownloadConstants;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
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
			return PCRaterDownloadConstants.EMPTY_STRING;
		}
	} // getErrorMessage
	
	
}