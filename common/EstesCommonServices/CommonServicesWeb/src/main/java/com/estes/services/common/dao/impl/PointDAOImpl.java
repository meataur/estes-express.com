/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */
package com.estes.services.common.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.services.common.dao.iface.PointDAO;
import com.estes.services.common.dto.Point;
import com.estes.services.common.exception.PointException;

@Repository ("pointDAO")
public class PointDAOImpl implements PointDAO
{
	@Autowired
	private JdbcTemplate jdbcCommonServices;


	@Override
	public boolean isDirect(Point point) throws PointException {
		String sql = "SELECT AMDP FROM fbfiles.fbp074 WHERE AMCITY = ? AND AMST = ? AND AMRZIP = ? AND AMREF6 = ?";
		String resp = null;
		try {
			resp =  jdbcCommonServices.queryForObject(sql, String.class, new Object[] {point.getCity(), point.getState(), point.getZip(), point.getCountry()});
			if(resp.equals("Y")) 
				return true;
			else
				return false;
		}
		catch(Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityDAOImpl.class, "isDirect()", "** No values found", e);
			throw new PointException("Point flag could not be retrieved.");
		}	
	} // isDirect
} 
