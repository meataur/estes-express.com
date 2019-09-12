/**
 * @author: Todd Allen
 *
 * Creation date: 09/26/2018
 */

package com.estes.myestes.points.dao.impl;

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
import com.estes.myestes.points.dao.iface.DownloadDAO;
import com.estes.myestes.points.dao.iface.ErrorDAO;
import com.estes.myestes.points.dto.DownloadRequest;
import com.estes.myestes.points.exception.PointException;

@Repository ("downloadDAO")
public class DownloadDAOImpl implements DownloadDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public String sendFile(DownloadRequest req) throws PointException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName("fbpgms");
        sproc.withProcedureName("plg10q001");
        sproc.addDeclaredParameter(new SqlParameter("state", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("format", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("eaddress", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("sort", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        if (req.getCriteria().equalsIgnoreCase("US") || req.getCriteria().equalsIgnoreCase("CN")) { // US=United States, CN=Canada
        	 inParams.put("state", req.getState());
		} else { // *=All, 2=All direct points, 3=All North America, 5=All non-direct points
			 inParams.put("state", req.getCriteria());
		}	
        inParams.put("format", req.getFileFormat());
        inParams.put("eaddress", req.getEmail());
        inParams.put("sort", req.getFileSort());
        inParams.put("delay", "");

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	return (String) outParms.get("error");
    		} else {
    			return ErrorDAO.DEFAULT_ERROR_CODE;
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, DownloadDAOImpl.class, "sendFile()", "** Error processing points download request.");
    		throw new PointException("Points download request failed.", e);
        }
	} // sendFile
}
