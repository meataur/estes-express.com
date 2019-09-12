package com.estes.myestes.requestinfo.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.requestinfo.dao.iface.AccountDAO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;

@Repository ("accountDAO")
public class AccountDAOImpl implements AccountDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/** 
	 * Returns true if the Estes Account Code is the payor to the freight. <br>
	 * OUT PR_ANSWER CHAR(1) returns "Y" if acct is payor or "N" if it is not payor <br>
	 * IN PR_OT CHAR(3) <br>
	 * IN PR_PRO CHAR(7) <br>
	 * IN PR_ACCOUNT CHAR(7) <br>
	 * IN PR_ACCT_TP CHAR(1)
	 */
	@Override
	public boolean isPayorOfFreight(String accountCode, String accountType, Integer OT, Integer PRO)
			throws RequestInfoException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("SP_IS_ACCOUNT_PAYOR");
		sproc.addDeclaredParameter(new SqlParameter("PR_ACCOUNT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PR_ACCT_TP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PR_OT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PR_PRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("PR_ANSWER", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PR_ACCOUNT", accountCode.toString());
		inParams.put("PR_ACCT_TP", accountType.toString());
		inParams.put("PR_OT", OT);
		inParams.put("PR_PRO", PRO);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String response = (String) outParms.get("PR_ANSWER");
	    		if("Y".equals(response)) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, AccountDAOImpl.class, "isPartyToShipment()", "An error occurred checking if party to shipment ");
				throw new RequestInfoException("Failed to see if account is party to shipment");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AccountDAOImpl.class, "isPartyToShipment()", "An error occurred checking if party to shipment ");
			throw new RequestInfoException("Failed to see if account is party to shipment");
		}
	}

	/**
	 * Returns true if the Estes Account Code is party to the shipment. <br>
	 * IN OT DECIMAL(3, 0) <br>
	 * IN PRO DECIMAL(7, 0) <br>
	 * IN ACCOUNT CHAR(7) <br>
	 * IN "TYPE" CHAR(1) <br>
	 * OUT ERROR CHAR(7) <br>
	 * Note: FBPGMS.SP_SHIPMENTPARTY returns error code 'VAE1001' for the ERROR
	 * parameter if the account is NOT party to the shipment; otherwise the
	 * parameter is returned blank to denote that the account IS party to the
	 * shipment.
	 */
	@Override
	public boolean isPartyToShipment(String accountCode, String accountType, Integer OT, Integer PRO)
			throws RequestInfoException {

		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("SP_SHIPMENTPARTY");
		sproc.addDeclaredParameter(new SqlParameter("ACCOUNT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("OT", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlParameter("PRO", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("ACCOUNT", accountCode);
		inParams.put("TYPE", accountType);
		inParams.put("OT", OT);
		inParams.put("PRO", PRO);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String error = (String) outParms.get("ERROR");
	    		if(null != error && "".equals(error.trim())) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, AccountDAOImpl.class, "isPartyToShipment()", "An error occurred checking if party to shipment ");
				throw new RequestInfoException("Failed to see if account is party to shipment");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AccountDAOImpl.class, "isPartyToShipment()", "An error occurred checking if party to shipment ");
			throw new RequestInfoException("Failed to see if account is party to shipment");
		}
	}
}