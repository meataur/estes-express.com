/**
 * @author: Todd Allen
 *
 * Creation date: 10/11/2018
 *
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.AgingSummaryDAO;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object for aging summary data
 */
@Repository ("agingsummaryDAO")
public class AgingSummaryDAOImpl implements AgingSummaryDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class AgingMapper implements RowMapper<List<Double>>
	{
		@Override
		public List<Double> mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			ArrayList<Double> tots = new ArrayList<Double>();
			tots.add(rs.getDouble("CABAL1"));
			tots.add(rs.getDouble("CABAL2"));
			tots.add(rs.getDouble("CABAL3"));
			tots.add(rs.getDouble("CABAL4"));
			tots.add(rs.getDouble("CABAL5"));
			tots.add(rs.getDouble("CABAL6"));
			tots.add(rs.getDouble("CABAL7"));
			tots.add(rs.getDouble("CABAL8"));
			tots.add(rs.getDouble("CABALT"));
			return tots;
		}
	} // AgingMapper

	/**
	 * Constructor
	 */
	public AgingSummaryDAOImpl()
	{
	} // Constructor

	/**
	 * Get the aging summary results.
	 */
	@Override
	public List<Double> getAgingSummary(String acct) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_getAging");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("startrow", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("maxrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("retrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("acct", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "0");
		// Set start row and maximum rows to 1
        inParams.put("startrow", "000001");
        inParams.put("maxrows", "001");
        inParams.put("acct", acct);
        sproc.returningResultSet("#result-set-1", new AgingMapper());

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	ArrayList<ArrayList<Double>> arr = (ArrayList<ArrayList<Double>>) outParms.get("#result-set-1");
            	ArrayList<Double> tots = arr.get(0);
             	return tots;
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, AgingSummaryDAOImpl.class, "getAgingSummary()", "** An error occurred getting aging summary for account " + acct + ".");
        		throw new InvoiceException("Error getting aging summary.");
    		}
       }
        catch (Exception e) {
        	
			ESTESLogger.log(ESTESLogger.ERROR, AgingSummaryDAOImpl.class, "getAgingSummary()", "** An error occurred getting aging summary for account " + acct + ".");
			return new ArrayList<Double>();
		}
	} // getAgingSummary()

	/**
	 * Get the web group aging summary results.
	 */
	@Override
	public List<Double> getAgingWebGroupSummary(String acct) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_getAgingWebgroup");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("startrow", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("maxrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("retrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("webgroup", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
		// Set start row and maximum rows to 1
        inParams.put("startrow", "000001");
        inParams.put("maxrows", "001");
        inParams.put("retrows", "");
        inParams.put("webgroup", acct);
        sproc.returningResultSet("#result-set-1", new AgingMapper());

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	ArrayList<ArrayList<Double>> arr = (ArrayList<ArrayList<Double>>) outParms.get("#result-set-1");
            	ArrayList<Double> tots = arr.get(0);
             	return tots;
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, AgingSummaryDAOImpl.class, "getAgingWebGroupSummary()", "** An error occurred getting web group aging summary for account " + acct + ".");
        		throw new InvoiceException("Error getting web group aging summary.");
    		}
       }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AgingSummaryDAOImpl.class, "getAgingWebGroupSummary()", "** An error occurred getting web group aging summary for account " + acct + ".", e);
    		throw new InvoiceException("Error getting web group aging summary.");
        }
	} // getAgingWebGroupSummary
}
