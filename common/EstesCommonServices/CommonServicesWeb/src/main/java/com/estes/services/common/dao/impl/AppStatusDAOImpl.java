/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.common.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.services.common.dao.iface.AppStatusDAO;
import com.estes.services.common.exception.ServiceException;

@Repository ("appStatusDAO")
public class AppStatusDAOImpl implements AppStatusDAO
{
	@Autowired
	private JdbcTemplate jdbcCommonServices;

	@Override
	public boolean isAppAvailable(String app) throws ServiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcCommonServices);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_isAppRunning");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("app_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "0");
        inParams.put("app_name", app);

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	return AppStatusDAO.isActive((String) outParms.get("error"));
    		}
            else {
    			ESTESLogger.log(ESTESLogger.ERROR, AppStatusDAOImpl.class, "isAppAvailable()", "** Error getting app status.");
        		throw new ServiceException("Error getting invoice inquiry app status.");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AppStatusDAOImpl.class, "isAppAvailable()", "** Error getting app status.");
    		throw new ServiceException("Error getting invoice inquiry app status.", e);
        }
	} // isAppRunning

	@Override
	public boolean isInvoiceInquiryAvailable() throws ServiceException
	{
		return isAppAvailable(INVOICE_INQUIRY);
	} // isInvoiceInquiryAvailable
}
