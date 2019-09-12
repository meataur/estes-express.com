/**
 * @author: Todd Allen
 *
 * Creation date: 10/17/2018
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

import com.estes.dto.customer.Account;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.AgingBuildStatusDAO;
import com.estes.myestes.invoiceinquiry.dto.AgingBuildStatus;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

@Repository ("agingStatusDAO")
public class AgingBuildStatusDAOImpl implements AgingBuildStatusDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;
	private String method;

	private static final class StatusMapper implements RowMapper<AgingBuildStatus>
	{
		@Override
		public AgingBuildStatus mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			AgingBuildStatus status = new AgingBuildStatus();
			/*
			 * A different result set can be returned so no guarantee of column names
			 * Each rs.get* must be in try/catch block
			 */
			try {
				status.setCode(rs.getString("CODE"));
			}
			catch (Exception e) {}
			try {
				status.setDesc(rs.getString("DESC"));
			}
			catch (Exception e) {}
			try {
				status.setBusy(rs.getString("BUSY"));
			}
			catch (Exception e) {}
			try {
				status.setBillCount(rs.getString("BILLCOUNT"));
			}
			catch (Exception e) {}

			return status;
		}
	} // StatusMapper

	@SuppressWarnings("unchecked")
	private List<AgingBuildStatus> callBuildStatus(Account acc) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_manageInvoiceInquiry");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("acct", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("type", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("method", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
        inParams.put("acct", acc.getCode());
        inParams.put("type", acc.getType());
        inParams.put("method", this.method);
        sproc.returningResultSet("#result-set-1", new StatusMapper());

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	return (List<AgingBuildStatus>) outParms.get("#result-set-1");
    		}
            else {
    			ESTESLogger.log(ESTESLogger.ERROR, AgingBuildStatusDAOImpl.class, "getBuildStatus()", "** Error getting invoice inquiry build status.");
        		throw new InvoiceException("Error getting build status.");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AgingBuildStatusDAOImpl.class, "getBuildStatus()", "** Error getting invoice inquiry build status.", e);
    		throw new InvoiceException("Error getting build status for account" + acc.getCode() + ".");
        }
	} // getBuildStatus

	@Override
	public List<AgingBuildStatus> getBuildStatus(Account acc) throws InvoiceException
	{
		// RPG procedure name in program ARG10R015
		this.method = "getBuildStatus()";
		return callBuildStatus(acc);
	} // getBuildStatus

	@Override
	public List<AgingBuildStatus> startBuildProcess(Account acc) throws InvoiceException
	{
		// RPG procedure name in program ARG10R015
		this.method = "startBuildProcess()";
		return callBuildStatus(acc);
	} // startBuildProcess
}
