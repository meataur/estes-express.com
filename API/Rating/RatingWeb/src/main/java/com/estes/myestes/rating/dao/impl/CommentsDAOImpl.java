/**
 * @author: Todd Allen
 *
 * Creation date: 03/14/2019
 */

package com.estes.myestes.rating.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.CommentsDAO;
import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.utils.RatingUtil;

@Repository ("commentsDAO")
public class CommentsDAOImpl implements CommentsDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public CommentsDAOImpl()
	{
	} // Constructor

	@Override
	public String getComments(String id, String app)
	{
		String sql = "SELECT gsvcom FROM " + DATA_SCHEMA + ".gsc00p160 " +
				"WHERE gsvid = ? AND gsvapp = ?";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] { id, app }, String.class);
		}
		catch (Exception e) {
			return "";
		}
	} // getComments

	@Override
	public void stageComments(String id, RatingRequest req) throws RatingException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + ".gsc00p160 " +
				"(gsvid, gsvapp, gsvcus, gsvact, gsvcom, gsvusr) " +
				"VALUES(?, ?, 'Y', ?, ?, ?)";

		try {
			Object[] values = {id, RatingUtil.getApp(req.getApps()), req.getAccountCode(), req.getComments(), req.getUser()};
			jdbcMyEstes.update(sql, values);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommentsDAOImpl.class, "stageComments()", "** Error staging comments for quote ID " + id, e);
			throw new RatingException("Error staging comments.");
		}
	} // stageComments
}
