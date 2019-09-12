/**
 * @author: James Arthur
 *
 * Creation date: 11/30/2018
 */

package com.estes.services.common.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.services.common.dao.iface.ShipmentDAO;
import com.estes.services.common.exception.ServiceException;

@Repository ("shipmentDAO")
public class ShipmentDAOImpl implements ShipmentDAO {
	@Autowired
	private JdbcTemplate jdbcMyEstesServices;

	@Override
	public Boolean isL2L(String otPro) throws ServiceException {
		String otArray[] = otPro.split("-");
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("L2L_SQL_isTerminalL2L");
		sproc.addDeclaredParameter(new SqlParameter("PR_OTPRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("PR_VALID", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PR_OTPRO", otArray[0]);
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
	        	ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "isL2L()", "An error occurred checking if l2l shipment ");
				throw new ServiceException("Failed checking if l2l shipment ");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "isL2L()", "An error occurred checking if l2l shipment", e);
			throw new ServiceException("Failed checking if l2l shipment ");
		}
	} // isL2L
	
	@Override
	public Boolean isEFW(String otPro) throws ServiceException {
		String otArray[] = otPro.split("-");
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("EFW_IS_Terminal_EFW");
		sproc.addDeclaredParameter(new SqlParameter("PR_OTPRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("PR_VALID", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("PR_OTPRO", otArray[0]);
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
	        	ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "isEFW()", "An error occurred checking if efw shipment");
				throw new ServiceException("Failed checking if efw shipment ");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "isEFW()", "An error occurred checking if efw shipment", e);
			throw new ServiceException("Failed checking if efw shipment ");
		}
	}
	
}
