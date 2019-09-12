/**
 * @author: Todd Allen
 *
 * Creation date: 08/21/2018
 */

package com.estes.myestes.addressbook.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.Address;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.addressbook.dao.iface.PointsDAO;
import com.estes.myestes.addressbook.exception.AddressBookException;

@Repository ("pointsDAO")
public class PointsDAOImpl implements PointsDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public boolean getPoint(Address addr) throws AddressBookException
	{
		String sql = "SELECT COUNT(*) " +
				"FROM " + DATA_SCHEMA + ".fbp074 " +
				"WHERE amcity=? and amst=? and amrzip=?";

		try {
			Integer cnt = jdbcMyEstes.queryForObject(sql, Integer.class, new Object[] { addr.getCity().toUpperCase(),
					addr.getState().toUpperCase(), addr.getZip().toUpperCase() });
			return cnt != null && cnt > 0;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "insertAddress()", "** Error looking up address.");
    		throw new AddressBookException("Error looking up address .");
		}
	} // getPoint
}
