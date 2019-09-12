package com.estes.myestes.requestinfo.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.requestinfo.dao.iface.BusinessTypeDAO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;

@Repository ("businessTypeDAO")
public class BusinessTypeDAOImpl implements BusinessTypeDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public boolean isL2LShipment(String OT) throws RequestInfoException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("L2L_SQL_isTerminalL2L");
		sproc.addDeclaredParameter(new SqlParameter("PR_OTPRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("PR_VALID", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PR_OTPRO", OT);
		inParams.put("PR_VALID", "");

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String response = (String) outParms.get("PR_VALID");
	    		if("Y".equals(response)) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, BusinessTypeDAOImpl.class, "isL2LShipment()", "An error occurred checking if l2l shipment ");
				throw new RequestInfoException("Failed checking if l2l shipment ");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, BusinessTypeDAOImpl.class, "isL2LShipment()", "An error occurred checking if l2l shipment ");
			throw new RequestInfoException("Failed checking if l2l shipment ");
		}
	}

	/** 
	 * Returns true if the shipment is EFW.
	 * @param OT 'Origin Terminal' - first 3 digits of the PRO 
	 */
	@Override
	public boolean isEFWShipment(String OT) throws RequestInfoException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("EFW_IS_Terminal_EFW");
		sproc.addDeclaredParameter(new SqlParameter("PR_OTPRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("PR_VALID", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PR_OTPRO", OT);
		inParams.put("PR_VALID", "");

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String response = (String) outParms.get("PR_VALID");
	    		if("Y".equals(response)) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, BusinessTypeDAOImpl.class, "isL2LShipment()", "An error occurred checking if l2l shipment ");
				throw new RequestInfoException("Failed checking if l2l shipment ");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, BusinessTypeDAOImpl.class, "isL2LShipment()", "An error occurred checking if l2l shipment ");
			throw new RequestInfoException("Failed checking if l2l shipment ");
		}
	}

}