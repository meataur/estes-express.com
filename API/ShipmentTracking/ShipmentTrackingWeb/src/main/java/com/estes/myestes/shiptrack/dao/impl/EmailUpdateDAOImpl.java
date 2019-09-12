/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 */

package com.estes.myestes.shiptrack.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.shiptrack.dao.iface.EmailUpdateDAO;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

@Repository ("emailUpdateDAO")
public class EmailUpdateDAOImpl implements EmailUpdateDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	public ServiceResponse writeRequest(String sess, String pro, String email) throws ShipTrackException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("STQ10C100");
        sproc.addDeclaredParameter(new SqlParameter("delay#", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("otpro", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("otpro_e", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("random_number_e", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("email", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("email_e", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("gen_error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("delay", "");
        inParams.put("otpro", pro);
        inParams.put("random_number", sess);
        inParams.put("email", email);
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
        	ServiceResponse result;
        	String proErr = (String) outParms.get("otpro_e");
        	String sessionErr = (String) outParms.get("random_number_e");
        	String emailErr = (String) outParms.get("email_e");
    		if (sessionErr.trim().length() > 0) { //user logged out/session expired
            	result = new ServiceResponse(sessionErr, "");
    		}
    		else if (emailErr.trim().length() > 0) { //invalid email address
    			result = new ServiceResponse(emailErr, "");
    		}
    		else if (proErr.trim().length() > 0) {
    			result = new ServiceResponse(proErr, "");
    		} else {
    			result = null;
    		}

    		return result;
		}
        else {
			ESTESLogger.log(ESTESLogger.ERROR, EmailUpdateDAOImpl.class, "writeRequest()", "** Error occurred adding shipment e-mail notification.");
    		throw new ShipTrackException("Add shipment notifcation for " + email + " on PRO " + pro + " failed.");
		}
	} // writeRequest
}
