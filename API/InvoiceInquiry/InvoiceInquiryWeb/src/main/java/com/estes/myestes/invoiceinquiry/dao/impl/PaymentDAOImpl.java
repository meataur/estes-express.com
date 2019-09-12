/**
 * @author: Todd Allen
 *
 * Creation date: 01/04/2019
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import java.math.BigDecimal;
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

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.PaymentDAO;
import com.estes.myestes.invoiceinquiry.dto.Payment;
import com.estes.myestes.invoiceinquiry.dto.PaymentLimits;
import com.estes.myestes.invoiceinquiry.dto.PaymentReason;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access object for SCP002 reference table
 */
@Repository ("paymentDAO")
public class PaymentDAOImpl implements PaymentDAO
{
	/**
	 * The on/off switch reference table column value
	 */
	private static final String EXPLAIN_REQUIRED = "1";

	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public PaymentDAOImpl()
	{
	} // Constructor

	private final class PaymentReasonMapper implements RowMapper<PaymentReason>
	{
		@Override
		public PaymentReason mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			PaymentReason row = new PaymentReason();
			row.setCode(rs.getString("tbdak2"));
			row.setDescription(rs.getString("tbdad3"));
			row.setExplain(rs.getString("tbdoo").equals(EXPLAIN_REQUIRED));
			return row;
		}
	} // PaymentReasonMapper

	@Override
	public String applyPaymentFee(String user) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_applywufee");
        sproc.addDeclaredParameter(new SqlParameter("inusername", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("applyfee", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("inusername", user);

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	return (String) outParms.get("applyfee");
    		}
            else {
				ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getPaymentId()",
						"** Error applying payment fee for user " + user);
        		throw new InvoiceException("Error applying payment fee for user " + user);
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getPaymentId()", "** Error getting payment ID.", e);
    		throw new InvoiceException("Error getting payment ID.");
        }
	} // applyPaymentFee

	@Override
	public String finalizePayment(int id, String action) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("raq682");
        sproc.addDeclaredParameter(new SqlParameter("paymentid", Types.DECIMAL));
        sproc.addDeclaredParameter(new SqlParameter("action", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("paymentid", id);
        inParams.put("action", action);

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
           	return ((String) outParms.get("error")).trim();
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "finalizePayment()", "** Error finalizing payment on ID " + id, e);
    		throw new InvoiceException("Error finalizing payment fee ID " + id);
        }
	} // finalizePayment

	private double getMaxInvoicePayment() throws InvoiceException
	{
		String sql = "SELECT tbdnd2 " +
				"FROM " + DATA_SCHEMA + ".scp002 " + 
				"WHERE tbdkey = 'WUPAY' and tbdak1 = 'MAXAMT'";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] {}, Double.class);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getMaxInvoicePayment()", "** Max invoice payment not found.", e);
			throw new InvoiceException("Max invoice payment not found.");
		}
	} // finalizePayment

	private double getMinInvoicePayment() throws InvoiceException
	{
		String sql = "SELECT tbdnd2 " +
				"FROM " + DATA_SCHEMA + ".scp002 " + 
				"WHERE tbdkey = 'WUPAY' and tbdak1 = 'MINAMT'";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] {}, Double.class);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getMinInvoicePayment()", "** Min invoice payment not found.", e);
			throw new InvoiceException("Min invoice payment not found.");
		}
	} // getMinInvoicePayment

	private int getPaymentId() throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("raq683");
        sproc.addDeclaredParameter(new SqlOutParameter("refnum", Types.INTEGER));
        Map<String, Object> inParams = new HashMap<String, Object>();

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	BigDecimal biggie = (BigDecimal) outParms.get("refnum");
            	return biggie.intValue();
    		}
            else {
    			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getPaymentId()", "** Error getting payment ID.");
        		throw new InvoiceException("Error getting payment ID");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getPaymentId()", "** Error getting payment ID.", e);
    		throw new InvoiceException("Error getting payment ID.");
        }
	} // getPaymentId

	@Override
	public PaymentLimits getPaymentLimits() throws InvoiceException
	{
		PaymentLimits limits = new PaymentLimits();
		limits.setMaximum(getMaxInvoicePayment());
		limits.setMinimum(getMinInvoicePayment());
		return limits;
	} // getPaymentLimits

	@Override
	public List<PaymentReason> getPayReasonCodes() throws InvoiceException
	{
		String sql = "SELECT tbdak2, tbdoo, tbdad3 " +
				"FROM " + DATA_SCHEMA + ".scp002 " + 
				"WHERE tbdkey = 'WUPAY' and tbdak1 = 'REASONCD' " + 
				"ORDER BY tbdnd1";

		try {
			return jdbcMyEstes.query(sql, new Object[] {}, new PaymentReasonMapper());
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "getPayReasonCodes()", "** No payment reasons found.", e);
			throw new InvoiceException("No payment reasons found.");
		}
	} // getPayReasonCodes

	private void insertPaymentControl(int id, String usr, String acct, double tot) throws InvoiceException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + ".rap682 " +
				"(pcpayid, pcstatus, pcuser, pcaccount, pctotal, pcfee) " +
				"VALUES (?, 'A', ?, ?, ?, 'Y')";

		Object[] values = {id, usr, acct, tot};

		try {
			jdbcMyEstes.update(sql,  values);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "insertPaymentControl()",
					"** Error occurred writing payment control data for payment ID " + id, e);
			throw new InvoiceException("Error writing payment control data");
		}
	} // insertPaymentControl

	private void insertPaymentDetail(int id, Payment pay) throws InvoiceException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + ".rap683 " +
				"(pdpayid, pdstatus, pdot, pdpro, pdamount, pdrsncode, pdreason) " +
				"VALUES (?, 'A', ?, ?, ?, ?, ?)";

		Object[] values = { id, Integer.parseInt(pay.getPro().substring(0, 3)),
				Integer.parseInt(pay.getPro().substring(4, 11)), pay.getPayment(), pay.getReasonCode(), pay.getExplain() };

		try {
			jdbcMyEstes.update(sql,  values);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentDAOImpl.class, "insertPaymentDetail()",
					"** Error occurred writing payment detail data for PRO " + pay.getPro(), e);
			throw new InvoiceException("Error writing payment detail data.");
		}
	} // insertPaymentDetail

	@Override
	public int verify(String usr, String acct, List<Payment> payments) throws InvoiceException
	{
		int payId = getPaymentId();

		// Get total payment amount
		double payTot = 0;
		for (Payment pmt : payments) {
			payTot = payTot + pmt.getPayment();
		}

		// Insert payment control
		insertPaymentControl(payId, usr, acct, payTot);

		// Insert all payment details
		for (Payment pmt : payments) {
			insertPaymentDetail(payId, pmt);
		}

		return payId;
	} // verify
}
