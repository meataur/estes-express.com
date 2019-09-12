/**
 * @author: Shelender singh
 *
 * Creation date: 09/13/2018
 */

package com.estes.myestes.terminallist.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.terminallist.dao.iface.TerminalListDAO;
import com.estes.myestes.terminallist.dto.EmailRequestDTO;
import com.estes.myestes.terminallist.dto.Terminal;
import com.estes.myestes.terminallist.utils.AppConstants;

@Repository("terminalListDAO")
public class TerminalListDAOImpl implements TerminalListDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	protected static final String SELECT_COMMON_FIELDS_AND_FILES = "SELECT DISTINCT " + "SPNAM1 AS \"State/Province\", "
			+ // 0
			"CT1AB AS \"Alpha Code\", " + // 1
			"DIGITS(TITID) AS \"Terminal #\", " + // 2
			"TINAME AS \"Terminal Name\", " + // 3
			"COALESCE(TISTRAE1, TISTRA) AS \"Street Address\", " + // 4
			"COALESCE(TICITYE, TICITY) AS \"City\", " + // 5
			"TIST AS \"State\", " + // 6
			"CT1ZP AS \"Zip\", " + // 7
			"TIPHO AS \"Phone\", " + // 8
			"CONCAT(CONCAT(CONCAT(CONCAT(CONCAT('(', " + "SUBSTRING(TIFAX, 2, 3)), ') '), "
			+ "SUBSTRING(TIFAX, 5, 3)), '-'), " + "SUBSTRING(TIFAX, 8, 4)) AS \"Fax\", " + // 9
			"CTAST1 AS \"Display Options\", " + // 10
			"SCCOUN AS \"Country\" " + // 11
			"FROM FBFILES.EEP015 " + "INNER JOIN FBFILES.FBP010 ON TIST = SPABBR "
			+ "INNER JOIN FBFILES.FBP010A ON TIST = SCABBR " + "INNER JOIN FBFILES.FRP015 ON TITID = CT1TID "
			+ "INNER JOIN FBFILES.FRP015A ON TITID = CTATID " + "LEFT OUTER JOIN FBFILES.EEP015E ON TITID = TITIDE "
			+ "RIGHT OUTER JOIN fbfiles.fbp074 ON TITID = AMTERM WHERE ";

	protected static final String INTERNAL_WHERE_CLAUSE = " CT1TYP = 'CT' "
			+ "AND (CT1DIV = 1 OR TITID = 145 OR TITID = 158) " + "AND CT1TID "
			+ "BETWEEN (SELECT PHFOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND (SELECT PHTOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND AMIL NOT IN ('OVLD', 'LKVL', 'GITC') AND AMST = ? " + "AND TIST = ? ";
	
	protected static final String ORDER_BY_CLAUSE = " ORDER BY SPNAM1, TINAME";
	
	protected static final String EXTERNAL_WHERE_CLAUSE = " CT1TYP = 'CT' "
			+ "AND (CT1DIV = 1 OR TITID = 158) " + "AND CT1TID "
			+ "BETWEEN (SELECT PHFOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND (SELECT PHTOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND AMIL NOT IN ('OVLD', 'LKVL', 'GITC') AND AMST = ? " + "AND TIST <> ? ";
	
	protected static final String STATE_CONDITION =" CTAST1 NOT LIKE '%A%' AND ";
	
	protected static final String TERMINAL_CLAUSE = " AND TITID = ?";
	
	protected static final String SELECT_ALL_US_TERMINALS = SELECT_COMMON_FIELDS_AND_FILES
			+ "CTAST1 NOT LIKE '%A%' AND CT1TYP = 'CT' " + "AND (CT1DIV = 1 OR TITID = 158) " + "AND CT1TID "
			+ "BETWEEN (SELECT PHFOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND (SELECT PHTOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') " + "AND SCCOUN = ? "
			+ "ORDER BY SPNAM1, TINAME";

	/**
	 * Retrieves active terminals that serve a particular state/province and are
	 * physically located within that state/province. This query does not include
	 * terminals that do not serve any points. Terminals are categorized by
	 * state/province and then organized alphabetically.
	 */
	protected static final String SELECT_TERMINALS_INSIDE_LOCATION = SELECT_COMMON_FIELDS_AND_FILES;
			/*+ "RIGHT OUTER JOIN fbfiles.fbp074 ON TITID = AMTERM WHERE " 
			//+ " CTAST1 NOT LIKE '%A%' "
			+ " AND CT1TYP = 'CT' "
			+ "AND (CT1DIV = 1 OR TITID = 145 OR TITID = 158) " + "AND CT1TID "
			+ "BETWEEN (SELECT PHFOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND (SELECT PHTOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND AMIL NOT IN ('OVLD', 'LKVL', 'GITC') AND AMST = ? " + "AND TIST = ? ORDER BY SPNAM1, TINAME";*/
	
	
	/**
	 * Retrieves active terminals that serve a particular state/province and are
	 * physically located outside that state/province. This query does not include
	 * terminals that do not serve any points. Terminals are categorized by
	 * state/province and then organized alphabetically.
	 */
	protected static final String SELECT_TERMINALS_OUTSIDE_LOCATION = SELECT_COMMON_FIELDS_AND_FILES;
			/*+ "RIGHT OUTER JOIN fbfiles.fbp074 ON TITID = AMTERM " 
			//+ "WHERE CTAST1 NOT LIKE '%A%' "
			+ "AND CT1TYP = 'CT' "
			+ "AND (CT1DIV = 1 OR TITID = 158) " + "AND CT1TID "
			+ "BETWEEN (SELECT PHFOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND (SELECT PHTOT FROM FBFILES.SCP003 WHERE PHSCAC = 'EXLA') "
			+ "AND AMIL NOT IN ('OVLD', 'LKVL', 'GITC') AND AMST = ? " + "AND TIST <> ? ORDER BY SPNAM1, TINAME";
*/
	@Override
	public List<Terminal> searchTerminals(EmailRequestDTO dto)
	{
		List<Terminal> terminals = null;
		String sql =  null;
		if(dto.getCountry().equals(AppConstants.MEXICO)) {
			dto.setState(AppConstants.MEXICO);
		}

		// State have more priority then country in search
		if (!dto.getState().equals("")) {
			if (dto.getState().equals("VI")) {
				sql = SELECT_TERMINALS_INSIDE_LOCATION + STATE_CONDITION + INTERNAL_WHERE_CLAUSE.replace("?", "'PR'") + TERMINAL_CLAUSE.replace("?", "145") + ORDER_BY_CLAUSE;
				terminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());
				sql = SELECT_TERMINALS_OUTSIDE_LOCATION + STATE_CONDITION +  EXTERNAL_WHERE_CLAUSE.replace("?", "'PR'") + TERMINAL_CLAUSE.replace("?", "145") + ORDER_BY_CLAUSE;
			}
			else if (dto.getState().equals("NU")) {
				sql = SELECT_TERMINALS_INSIDE_LOCATION + STATE_CONDITION + INTERNAL_WHERE_CLAUSE.replace("?", "'MB'") + TERMINAL_CLAUSE.replace("?", "354") + ORDER_BY_CLAUSE;
				terminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());
				sql = SELECT_TERMINALS_OUTSIDE_LOCATION + STATE_CONDITION +  EXTERNAL_WHERE_CLAUSE.replace("?", "'MB'") + TERMINAL_CLAUSE.replace("?", "354") +  ORDER_BY_CLAUSE;
			}
			else if (dto.getState().equals("AK")) {
				sql = SELECT_TERMINALS_INSIDE_LOCATION +  INTERNAL_WHERE_CLAUSE.replace("?", "'" + dto.getState() + "'");
				terminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());
				sql = SELECT_TERMINALS_OUTSIDE_LOCATION + EXTERNAL_WHERE_CLAUSE.replace("?", "'" + dto.getState() + "'");							
			}
			else {
				sql = SELECT_TERMINALS_INSIDE_LOCATION + STATE_CONDITION + INTERNAL_WHERE_CLAUSE.replace("?", "'" + dto.getState() + "'") + ORDER_BY_CLAUSE;
				terminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());
				sql = SELECT_TERMINALS_OUTSIDE_LOCATION + STATE_CONDITION +  EXTERNAL_WHERE_CLAUSE.replace("?", "'" + dto.getState() + "'") + ORDER_BY_CLAUSE;
			}
			List<Terminal> terminalOutsideLocation = jdbcMyEstes.query(sql, new Object[] {},
					new TerminalListRowMapper());
			terminals.addAll(terminalOutsideLocation);
		}
		// Show terminals for selected country
		else if (!dto.getCountry().equals(AppConstants.ALL_COUNTRIES)) {
			sql = SELECT_ALL_US_TERMINALS.replace("?", "'" + dto.getCountry() + "'");
			terminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());
		}
		// Show terminals for all countries
		else {
			// US terminals
			sql = SELECT_ALL_US_TERMINALS.replace("?", "'US'");
			terminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());
			// Canada terminals
			sql = SELECT_ALL_US_TERMINALS.replace("?", "'CN'");
			List<Terminal> cnTerminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());			
			terminals.addAll(cnTerminals);
			// Mexico terminals
			sql = SELECT_ALL_US_TERMINALS.replace("?", "'MX'");
			List<Terminal> mxTerminals = jdbcMyEstes.query(sql, new Object[] {}, new TerminalListRowMapper());			
			terminals.addAll(mxTerminals);
		}

		return terminals;
	} // searchTerminals
}

final class TerminalListRowMapper implements ResultSetExtractor<List<Terminal>>
{
	@Override
	public List<Terminal> extractData(ResultSet rs) throws SQLException, DataAccessException {
		List<Terminal> terminals = new ArrayList<Terminal>();
		while (rs.next()) {
			Terminal terminal = new Terminal();
			terminal.setStateName(rs.getString("State/Province").trim());
			terminal.setAlphaCode(rs.getString("Alpha Code"));
			terminal.setTerminal(rs.getString("Terminal #"));
			terminal.setTerminalName(rs.getString("Terminal Name").trim());
			terminal.setStreetaddress(rs.getString("Street Address").trim());
			terminal.setCity(rs.getString("City").trim());
			terminal.setState(rs.getString("State"));
			terminal.setZip(rs.getString("Zip"));
			terminal.setPhone(rs.getString("Phone"));
			terminal.setFax(rs.getString("Fax"));
			terminal.setDisplayOptions(rs.getString("Display Options"));
			terminal.setCountry(rs.getString("Country"));
			terminal.setMaps(
					!rs.getString("Alpha Code").equals("") ? ESTESConfigUtil.getProperty(AppConstants.MAPS, AppConstants.BASE_URL) + rs.getString("Alpha Code").toLowerCase() + ".pdf"
							: "");
			terminals.add(terminal);
		}
		return terminals;
	} // TerminalListRowMapper
}
