/**
 * @author: Todd Allen
 * 
 * Cloned from points.dao.impl#TerminalDAOImpl
 *
 * Creation date: 03/29/2018
 */

package com.estes.myestes.rating.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.TerminalDAO;
import com.estes.myestes.rating.dto.Point;
import com.estes.myestes.rating.dto.Terminal;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("terminalDAO")
public class TerminalDAOImpl implements TerminalDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

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
			return elem;
		}
	} // TerminalMapper

	@Override
	public Terminal getTerminalInfo(Point point) throws RatingException
	{
		List<Terminal> terminalList = new ArrayList<Terminal>();
		String commonSql = " amst, amrzip, amterm, amdp, amil,  "
				+ "DIGITS(ct1tid) AS ct1tid, tiname, ct1st, ctast1, ct1ab, "
				+ "COALESCE(tistrae1 ,tistra) AS tistra, "
				+ "COALESCE(ticitye ,ticity) AS ticity, "
				+ " tist, tizip, tipho, "
				+ "CONCAT(CONCAT(CONCAT(CONCAT(CONCAT('(', SUBSTRING(tifax , 2, 3)), ') '), SUBSTRING(tifax, 5, 3)), '-'), SUBSTRING(tifax, 8, 4)) AS tifax "
				+ "FROM fbfiles.fbp074 " 
				+ "INNER JOIN fbfiles.frp015 ON amterm = ct1tid " 
				+ "INNER JOIN fbfiles.frp015A ON amterm = ctatid "  
				+ "INNER JOIN fbfiles.eep015 ON amterm = titid "  
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

			return terminalList.get(0);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, TerminalDAOImpl.class, "getTerminalInfo()", "** Terminal info not found for the given point." );
			return new Terminal();
		}
	} // getTerminalInfo
}
