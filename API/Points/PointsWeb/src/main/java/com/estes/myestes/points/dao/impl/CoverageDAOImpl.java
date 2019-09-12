/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2018
 *
 */

package com.estes.myestes.points.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.points.dao.iface.CoverageDAO;
import com.estes.myestes.points.dto.CoverageRequest;
import com.estes.myestes.points.dto.PartialTerminal;
import com.estes.myestes.points.exception.PointException;

@Repository ("coverageDAO")
public class CoverageDAOImpl implements CoverageDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class TerminalMapper implements RowMapper<PartialTerminal>
	{
		@Override
		public PartialTerminal mapRow(ResultSet rs, int rowNm) throws SQLException {
			PartialTerminal elem = new PartialTerminal();
			// SPROC call can return result set of terminal info OR error message
			try {
				elem.getErrorInfo().setErrorCode("");
				elem.getErrorInfo().setMessage("");
				elem.setId(rs.getString("servterm"));
				elem.setCity(rs.getString("city"));
				elem.setState(rs.getString("state"));
				elem.setZip(rs.getString("zip"));
			}
			catch (Exception e) {
				elem.getErrorInfo().setErrorCode(rs.getString("flag"));
				elem.getErrorInfo().setMessage(rs.getString("error"));
			}
			return elem;
		}
	} // TerminalMapper

	@Override
	public List<PartialTerminal> getServicingTerminals(CoverageRequest cv) throws PointException
	{
		try {
			return jdbcMyEstes.query("CALL fbpgms.sp_getServicingTermAndEmailDP(?, ?, ?, ?, ?, ?)",
					new TerminalMapper(), new Object[] { cv.getZip(), cv.getCity(), cv.getState(), cv.getFileFormat(), cv.getFileSort(), cv.getEmail() });
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CoverageDAOImpl.class, "getServicingTerminals()", "** Error occurred getting serving terminals.");
			throw new PointException("Error occurred getting serving terminals.", e);
		}
	} // getServicingTerminals
}
