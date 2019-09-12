/**
 * @author: Todd Allen
 *
 * Creation date: 02/19/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.DisclaimerDAO;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("disclaimerDAO")
public class DisclaimerDAOImpl implements DisclaimerDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public DisclaimerDAOImpl()
	{
	} // Constructor

	@Override
	public List<String> getDisclaimers() throws RatingException
	{
		String sql = "SELECT gsttxt " +
				"FROM " + DATA_SCHEMA + ".gsc00p530 " +
				"WHERE gsttyp = 'LTLDIS'" +
				"ORDER BY gstseq";

		try {
			List<String> dataList = jdbcMyEstes.queryForList(sql, String.class);
//			ArrayList<String> messages = new ArrayList<String>();
//			for (String data : dataList) {
//				messages.add(TextUtil.scrubHtml(data).trim());
//			}
			return dataList;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, DisclaimerDAOImpl.class, "getDisclaimers()", "** Error occurred getting disclaimer messages.", e);
			throw new RatingException("Error getting disclaimer messages.");
		}
	} // getDisclaimers
}
