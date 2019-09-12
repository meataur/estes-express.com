/**
 * @author: Todd Allen
 *
 * Creation date: 11/16/2018
 */

package com.estes.services.common.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.services.common.dao.iface.TerminalDAO;
import com.estes.services.common.dto.Terminal;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.utils.ServiceConstants;

@Repository ("terminalDAO")
public class TerminalDAOImpl implements TerminalDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplatePoints;

	private static final class TerminalMapper implements RowMapper<Terminal>
	{
		@Override
		public Terminal mapRow(ResultSet rs, int rowNm) throws SQLException {
			Terminal elem = new Terminal();
			elem.setId(rs.getString("ct1tid"));
			elem.setAbbr(rs.getString("ct1ab"));
			elem.setName(rs.getString("tiname"));
			elem.getAddress().setStreetAddress(rs.getString("tistra"));
			elem.getAddress().setStreetAddress2("");
			elem.getAddress().setCity(rs.getString("ticity"));
			elem.getAddress().setState(rs.getString("tist"));
			elem.getAddress().setZip(rs.getString("tizip"));
			elem.getAddress().setZip4("");
			elem.getAddress().setCountry("");
			elem.setPhone(rs.getString("tipho"));
			elem.setFax(rs.getString("tifax"));
			elem.setManager(rs.getString("timgr"));
			elem.setEmail(rs.getString("email"));
			elem.setManagerEmail(rs.getString("ct4uf"));
			elem.setNationalMap(ESTESConfigUtil.getProperty(ServiceConstants.MAPS, ServiceConstants.BASE_URL)
					+ elem.getAbbr().toLowerCase() + ".pdf");
			elem.setServiceMap(ESTESConfigUtil.getProperty(ServiceConstants.MAPS, ServiceConstants.BASE_URL)
					+ elem.getAbbr().toLowerCase() + "_next_day.pdf");
			elem.setServiceArea(ESTESConfigUtil.getProperty(ServiceConstants.MAPS, ServiceConstants.BASE_URL)
					+ elem.getAbbr().toLowerCase() + ".gif");
			elem.setDisplayOptions(rs.getString("ctast1").trim());
			return elem;
		}
	} // TerminalMapper

	@Override
	public Terminal getTerminalInfo(int id) throws ServiceException
	{
		Terminal terminal = null;
		String sql = "SELECT DIGITS(ct1tid) AS ct1tid, ct1ab, ct1nam AS tiname, tistra, ticity, tist, tizip, tipho, tifax, ct4uf, timgr, email,ctast1 " + 
				"FROM fbfiles.frp015 " + 
				"INNER JOIN fbfiles.eep015 ON ct1tid = titid " + 
				"INNER JOIN fbfiles.fbp017a ON ct1tid = ct4tid " + 
				"LEFT OUTER JOIN fbfiles.terminalemail ON ct1tid = terminal " + 
				"LEFT OUTER JOIN FBFILES.FRP015A ON titid = ctatid " +
				"WHERE ct1tid = ? AND ct4up='TMGR' " +
				"FETCH FIRST 1 ROWS ONLY";

		try {
			List<Terminal> listQuery = jdbcTemplatePoints.query(sql, new Object[] {new Integer(id)}, new TerminalMapper());
			if (listQuery != null && listQuery.size() > 0) {
				terminal = listQuery.get(0);			
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, TerminalDAOImpl.class, "getTerminalInfo()", "** Terminal info not found for terminal " + id + ".", e);
			throw new ServiceException("Terminal info not found.", e);
		}
		return terminal;
	} // getTerminalInfo
}
