package com.estes.framework.dataaccess.audit.iimpl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.estes.framework.dataaccess.audit.iface.AuditRequestDAO;
import com.estes.framework.logger.ESTESLogger;

@Repository("auditRequestDAO")
@Scope("prototype")
public class AuditRequestDAOImpl implements AuditRequestDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	private static final String SCHEMA = "FBPGMS";
	private static final String STORED_PROCEDURE = "MEG10Q400";

	@Override
	public void logRequest(String userName, String appName) {
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withSchemaName(SCHEMA);
		call.withProcedureName(STORED_PROCEDURE);
		call.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
		call.addDeclaredParameter(new SqlParameter("APPNAME", Types.CHAR));
        
        Map<String, Object> inputs = new HashMap<String, Object>();
        inputs.put("USRNAME", userName.toUpperCase());
        inputs.put("APPNAME", appName.length() > 50 ? appName.substring(0, 50) : appName);
        
        try { // no outputs, just execute sproc
        	call.execute(inputs);
        } catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AuditRequestDAOImpl.class, "logRequest()", "** Error occurred writing request to log " + e);
        }
	}
	
}