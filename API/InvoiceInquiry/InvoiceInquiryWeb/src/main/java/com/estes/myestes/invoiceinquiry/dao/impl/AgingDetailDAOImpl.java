/**
 * @author: Todd Allen
 *
 * Creation date: 10/15/2018
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
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.customer.Account;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.AgingDetailDAO;
import com.estes.myestes.invoiceinquiry.dto.AgingDetail;
import com.estes.myestes.invoiceinquiry.dto.AgingDetailRequest;
import com.estes.myestes.invoiceinquiry.dto.AgingDetails;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

@Repository ("agingDetailDAO")
public class AgingDetailDAOImpl implements AgingDetailDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class AgingMapper implements RowMapper<AgingDetail>
	{
		@Override
		public AgingDetail mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			AgingDetail row = new AgingDetail();
			row.setPro(rs.getString("ot") + "-" + rs.getString("pro"));
			row.setPickupDate(rs.getInt("fhpud8"));
			row.setDeliveryDate(rs.getInt("fhdda8"));
			row.setInvoiceDate(rs.getInt("stdate"));
			row.setBol(rs.getString("bol#"));
			row.setPoNum(rs.getString("po#"));
			row.setStatementNum(rs.getInt("stnumber"));
			row.setOpenAmount(rs.getDouble("openamt"));
			row.setPendingPayAmount(rs.getDouble("pendingAmt"));
			return row;
		}
	} // AgingMapper

	/**
	 * Constructor
	 */
	public AgingDetailDAOImpl()
	{
	} // Constructor

	/**
	 * Get the aging detail results.
	 */
	@Override
	public AgingDetails getAgingDetail(AgingDetailRequest dtl, Account acctInfo) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName(PGM_SCHEMA);

		// Set SPROC to be called based on account type
		if ((acctInfo.isGroup()) || (acctInfo.isWebGroup())) {
	        sproc.withProcedureName("sp_getAgingWebgroupDetail");
		}
		else {
	        sproc.withProcedureName("sp_getAgingDetail");
		}

        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("startrow", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("maxrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("retrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("totrows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("acct", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("theAge", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("theType", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("theSort", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("theDir", Types.CHAR));

        // Calculate starting row based on page#
		int rows = dtl.getMaxRows();
		if (rows <= 0 || rows > MAX_ROWS) {
			rows = DEFAULT_ROWS;
		}
		int start = 1;
		try {
			start = ((dtl.getPage()-1) * rows) + 1;
		}
        catch (Exception e) { }

        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
        inParams.put("startrow", start);
        inParams.put("maxrows", rows);
        inParams.put("acct", acctInfo.getCode());
        inParams.put("theAge", dtl.getBucket());
        inParams.put("theType", acctInfo.getType());
        inParams.put("theSort", dtl.getSort());
        inParams.put("theDir", dtl.getDirection());
        sproc.returningResultSet("#result-set-1", new AgingMapper());

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	String tot = (String) outParms.get("totrows");
            	List<AgingDetail> rs = (List<AgingDetail>) outParms.get("#result-set-1");
             	return new AgingDetails(tot, rs);
    		} else {
				ESTESLogger.log(ESTESLogger.ERROR, AgingDetailDAOImpl.class, "getAgingDetail()",
						"** Error occurred getting aging details for account " + acctInfo.getCode() + ".");
        		throw new InvoiceException("Error getting aging detail.");
    		}
       }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AgingDetailDAOImpl.class, "getAgingDetail()",
					"** Error occurred getting aging details for account " + acctInfo.getCode() + ".", e);
    		throw new InvoiceException("Error getting aging detail.", e);
        }
	} // getAgingDetail
}
