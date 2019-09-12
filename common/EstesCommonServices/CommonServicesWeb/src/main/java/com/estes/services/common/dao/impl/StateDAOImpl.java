/**
 * @author: Todd Allen
 *
 * Creation date: 11/08/2018
 */

package com.estes.services.common.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.services.common.dao.iface.StateDAO;
import com.estes.services.common.exception.ServiceException;

@Repository ("stateDAO")
public class StateDAOImpl implements StateDAO
{
	@Autowired
	private JdbcTemplate jdbcCommonServices;

	@Override
	public List<String> getStatesForCountry(String abbr) throws ServiceException
	{
		String sql = "SELECT DISTINCT scabbr FROM fbfiles.fbp010a " + 
				"WHERE sccoun = ? ORDER BY scabbr";

		try {
			return jdbcCommonServices.queryForList(sql, String.class, new Object[] {new String(abbr)});
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, StateDAOImpl.class, "getStatesForCountry()", "** No states/provinces found for " + abbr, e);
			throw new ServiceException("Could not retrieve states for " + abbr + ".", e);
		}
	} // getStatesForCountry
}
