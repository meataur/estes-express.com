/**
 * @author: Todd Allen
 *
 * Creation date: 11/26/2018
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.StatementDAO;
import com.estes.myestes.invoiceinquiry.dto.StatementDetail;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

@Repository ("stmtDetailDAO")
public class StatementDAOImpl implements StatementDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class DetailMapper implements RowMapper<StatementDetail>
	{
		@Override
		public StatementDetail mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			StatementDetail row = new StatementDetail();
			row.setPro(rs.getString("ot") + "-" + rs.getString("pro"));
			row.setBol(rs.getString("bol#"));
			row.setPoNum(rs.getString("po#"));
			row.setFreightBillDate(rs.getInt("fbdate"));
			row.setOpenAmount(rs.getDouble("openamt"));
			return row;
		}
	} // DetailMapper

	/**
	 * Constructor
	 */
	public StatementDAOImpl()
	{
	} // Constructor

	/**
	 * Get the statement details.
	 */
	@Override
	public List<StatementDetail> getStatementDetails(String stmt, String sort) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		// Default sort by PRO
		if (!sort.matches("PRO|BOL|PO|FBD|OPN")) {
			sort = "PRO";
		}

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("arg10q001");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("statement", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("sort_by", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
        inParams.put("statement", stmt);
        inParams.put("sort_by", sort);
        sproc.returningResultSet("#result-set-1", new DetailMapper());

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
           	List<StatementDetail> rs = (List<StatementDetail>) outParms.get("#result-set-1");
           	return rs;
       }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, StatementDAOImpl.class, "getStatementDetails()",
					"** Error occurred getting statement details for statement# " + stmt + ".", e);
    		throw new InvoiceException("Error getting statement details.");
        }
	} // getStatementDetails
}
