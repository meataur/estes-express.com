/**
 * @author: Todd Allen
 *
 * Creation date: 02/19/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.QuoteMessageDAO;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.utils.TextUtil;

@Repository ("messageDAO")
public class QuoteMessageDAOImpl implements QuoteMessageDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public QuoteMessageDAOImpl()
	{
	} // Constructor

	@Override
	public Map<String, String> getErrorMessages(int id) throws RatingException
	{
		String sql = "SELECT gsemid, gsemsg " + 
				"FROM " + DATA_SCHEMA + ".gsc00p190 " +
				"WHERE gseidx = ? and gseeow = '" + ERROR_TYPE + "' ";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql, new Object[] { id });
			Map<String, String> errors = new HashMap<String, String>();
			for (Map<String, Object> data : dataList) {
				errors.put((String) data.get("gsemid"), (String) data.get("gsemsg"));
			}
			return errors;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, QuoteMessageDAOImpl.class, "getErrorMessages()", "** Error occurred getting messages for index " + id, e);
			throw new RatingException("Error getting error messages for index " + id);
		}
	} // getErrorMessages

	@Override
	public List<String> getQuoteMessages(String id) throws RatingException
	{
		String sql = "SELECT gsemsg " + 
				"FROM " + DATA_SCHEMA + ".gsc00p190 " +
				"WHERE gseid = ? and gseeow <> '" + ERROR_TYPE + "' " +
				"ORDER BY gseidx";

		try {
			List<String> dataList = jdbcMyEstes.queryForList(sql, new Object[] { id }, String.class);
			ArrayList<String> messages = new ArrayList<String>();
			for (String data : dataList) {
				messages.add(data.trim());
			}
			return messages;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, QuoteMessageDAOImpl.class, "getQuoteMessages()", "** Error occurred getting messages for quote ID " + id, e);
			throw new RatingException("Error getting messages for quote ID " + id);
		}
	} // getQuoteMessages
}
