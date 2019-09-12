/**
 * @author: Todd Allen
 *
 * Creation date: 02/13/2018
 */

package com.estes.myestes.points.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.points.dao.iface.TerminalDAO;
import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.dto.TerminalContact;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.utils.PointConstants;

@Repository ("terminalDAO")
public class TerminalDAOImpl implements TerminalDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class TerminalContactMapper implements RowMapper<TerminalContact>
	{
		@Override
		public TerminalContact mapRow(ResultSet rs, int rowNm) throws SQLException {
			TerminalContact elem = new TerminalContact();
			elem.setFullName(rs.getString("timgr"));
			elem.setEmail(rs.getString("ct4uf"));
			return elem;
		}
	} // TerminalContactMapper

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
			if("CN".equals(rs.getString("country"))) {
			elem.getAddress().setZip(rs.getString("amrzip"));
			}else
				elem.getAddress().setZip(rs.getString("tizip"));
			elem.getAddress().setZip4("");
			elem.getAddress().setCountry(rs.getString("country"));
			elem.setPhone(rs.getString("tipho"));
			elem.setFax(rs.getString("tifax"));
			elem.setEmail(rs.getString("email"));
			elem.setNationalMap(ESTESConfigUtil.getProperty(PointConstants.MAPS, PointConstants.BASE_URL)
					+ elem.getAbbr().toLowerCase() + ".pdf");
			elem.setServiceMap(ESTESConfigUtil.getProperty(PointConstants.MAPS, PointConstants.BASE_URL)
					+ elem.getAbbr().toLowerCase() + "_next_day.pdf");
			elem.setServiceArea(ESTESConfigUtil.getProperty(PointConstants.MAPS, PointConstants.BASE_URL)
					+ elem.getAbbr().toLowerCase() + ".gif");
			elem.setDisplayOptions(rs.getString("ctast1").trim());
			return elem;
		}
	} // TerminalMapper

	@Override
	public Terminal getTerminalInfo(Point point) throws PointException
	{
		Terminal terminal = null;
		List<Terminal> terminalList = new ArrayList<Terminal>();
		String commonSql = " amst, amrzip, amterm, amdp, amil,  "
				+ "DIGITS(ct1tid) AS ct1tid, tiname, ct1st, ctast1, ct1ab, "
				+ "COALESCE(tistrae1 ,tistra) AS tistra, "
				+ "COALESCE(ticitye ,ticity) AS ticity, "
				+ " tist, tizip, tipho, "
				+ "CONCAT(CONCAT(CONCAT(CONCAT(CONCAT('(', SUBSTRING(tifax , 2, 3)), ') '), SUBSTRING(tifax, 5, 3)), '-'), SUBSTRING(tifax, 8, 4)) AS tifax "
				+ ", email,"
				+ "AMREF6 AS country " 
				+ "FROM fbfiles.fbp074 " 
				+ "INNER JOIN fbfiles.frp015 ON amterm = ct1tid " 
				+ "INNER JOIN fbfiles.frp015A ON amterm = ctatid "  
				+ "INNER JOIN fbfiles.eep015 ON amterm = titid "  
				+ "LEFT OUTER JOIN fbfiles.terminalemail ON amterm = terminal "  
				+ "LEFT OUTER JOIN fbfiles.eep015e ON amterm = titide  ";
				
		String whereClause = " WHERE fbfiles.fbp074.AMRZIP= ? "  
				+ "AND fbfiles.fbp074.AMST=? "  ;
		/**
		 * For Country Canada
		 */
        if(point.getCountry().equalsIgnoreCase("CN")){
        	whereClause += " AND fbfiles.fbp074.AMREF6='CN'";
        }
        
		String orderBy = " ORDER BY amrzip	"  
				+ "FETCH FIRST 20 ROWS ONLY" ;
		
		String sql = "SELECT ampnam AS amcity, " + commonSql + whereClause + " AND fbfiles.fbp074.AMPNAM=? AND amzsts='P' " + orderBy;
		String sql1 = "SELECT DISTINCT ampnam AS amcity, " + commonSql + whereClause + " AND fbfiles.fbp074.AMPNAM=? AND ampnam != '' " + orderBy;
		String sql2 = "SELECT amcity, " + commonSql + whereClause + " AND fbfiles.fbp074.AMCITY=? " + orderBy;

		try {
			terminalList  = (List<Terminal>)jdbcMyEstes.query(sql, new TerminalMapper(), new Object[] {point.getZip().toUpperCase(), point.getState().toUpperCase(), point.getCity().toUpperCase()});
			 if(terminalList.size() == 0) {
				 terminalList  = (List<Terminal>)jdbcMyEstes.query(sql1, new TerminalMapper(), new Object[] {point.getZip().toUpperCase(), point.getState().toUpperCase(), point.getCity().toUpperCase()});
				 if(terminalList.size() == 0) {
					 terminalList  = (List<Terminal>)jdbcMyEstes.query(sql2, new TerminalMapper(), new Object[] {point.getZip().toUpperCase(), point.getState().toUpperCase(), point.getCity().toUpperCase()});
				 }
			 }
			 if(terminalList.size() > 0) {
				 terminal = terminalList.get(0);
				 // Get terminal contact info
				TerminalContact contact = getTerminalContact(Integer.parseInt(terminal.getId()));
				terminal.setManager(contact.getFullName());
				terminal.setManagerEmail(contact.getEmail());
			 }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, TerminalDAOImpl.class, "getTerminalInfo()", "** Terminal info not found for the given point." );
			throw new PointException("Terminal info not found.", e);
		}
		return terminal;
	} // getTerminalInfo

	@Override
	public Terminal getServicingTerminal(Point pt) throws PointException
	{

		
		String sql = "SELECT ct1ab, DIGITS(ct1tid) AS ct1tid, tiname, tistra, ticity, tist, tizip, amrzip,'' AS country, tipho, tifax, email,'' AS ctast1 " + 
				"FROM fbfiles.fbp074 " + 
				"INNER JOIN fbfiles.frp015 ON amterm = ct1tid " + 
				"INNER JOIN fbfiles.eep015 ON amterm = titid " + 
				"LEFT OUTER JOIN fbfiles.terminalemail ON amterm = terminal " + 
				"WHERE amrzip = ? AND amcity = ? AND amst = ? " + 
				"FETCH FIRST 1 ROWS ONLY";

		try {
			Terminal term = jdbcMyEstes.queryForObject(sql, new TerminalMapper(), new Object[] {pt.getZip(), pt.getCity(), pt.getState()});
			TerminalContact contact = getTerminalContact(Integer.parseInt(term.getId()));
			term.setManager(contact.getFullName());
			term.setManagerEmail(contact.getEmail());
			return term;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, TerminalDAOImpl.class, "getServicingTerminal()", "** Error getting servicing terminal for point.", e);
			throw new PointException("Servicing terminal info could not be retrieved for point.", e);
		}
	} // getServicingTerminal

	private TerminalContact getTerminalContact(int term) throws PointException
	{
		String sql = "SELECT ct4uf, timgr " + 
				"FROM fbfiles.fbp017a " + 
				"INNER JOIN fbfiles.eep015 ON titid = ct4tid " + 
				"WHERE ct4up='TMGR' AND ct4tid=? " + 
				"FETCH FIRST 1 ROWS ONLY";

		try {
			return jdbcMyEstes.queryForObject(sql, new TerminalContactMapper(), new Object[] {term});
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, TerminalDAOImpl.class, "getTerminalContact()", "** Terminal contact info missing for terminal " + term + ".");
			return new TerminalContact();
		}
	} // getTerminalContact
}
