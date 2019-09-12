/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.EmailDAO;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("emailDAO")
public class EmailDAOImpl implements EmailDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public EmailDAOImpl()
	{
	} // Constructor

	@Override
	public Map<String, String> getMailProps() throws RatingException
	{
		String sql = "SELECT gsttyp, gsttxt FROM " + DATA_SCHEMA + ".gsc00p530 " +
				"ORDER BY gstseq";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql);
			Map<String, String> textMap = new HashMap<String, String>();
			for (Map<String,Object> data : dataList) {
				// Concatenate string if key exists
				if (textMap.containsKey(((String) data.get("gsttyp")).trim())) {
					textMap.put(((String) data.get("gsttyp")).trim(),
							textMap.get(((String) data.get("gsttyp")).trim()) + ((String) data.get("gsttxt")).trim());
				}
				else {
					textMap.put(((String) data.get("gsttyp")).trim(), ((String) data.get("gsttxt")).trim());
				}
			}
			return textMap;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EmailDAOImpl.class, "getMailProps()", "** Error occurred getting e-mail properties.", e);
			throw new RatingException("Error getting e-mail properties.");
		}
	} // getMailProps


	@Override
	public void updateContact(String id)
	{
		String sql = "UPDATE " + DATA_SCHEMA + ".gsc40p100 " +
				"SET g4vsts = 'O' WHERE g4vid = ?";

		try {
			jdbcMyEstes.update(sql, new Object[] { id });
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EmailDAOImpl.class, "updateContact()", "** Error occurred updating contact flag for quote " + id, e);
		}
	} // updateContact
}
