package com.estes.myestes.MyEstesAuditLog;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.estes.framework.logger.ESTESLogger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * MyEstes Audit Logger
 * Writes logs for each request into Database Table MEG10P400
 */
@Aspect
public class MyEstesAuditLogger {
    
    private static final String SCHEMA           = "FBPGMS";
	private static final String STORED_PROCEDURE = "MEG10Q400";
	
	private static final String MY_ESTES_AUDIT_LOG  = "myestes_auditLogs.properties";
	private static final String AUDIT_LOG_PROPERTY  = "AuditLogs";
	
    private JdbcTemplate jdbcTemplate;
    
    private Properties properties;
   
    
    public MyEstesAuditLogger(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        properties = new Properties();
        try {
        	properties.load(new FileInputStream(System.getProperty(MY_ESTES_AUDIT_LOG)));
        } catch (IOException ex) {
        	ESTESLogger.log(ESTESLogger.INFO, getClass(),
                    "MyEstesAuditLogger",
                    "Failed to load myestes_auditLogs.properties file.",
                    ex);
        }
    }
    /**
     * Write Log Request for MyEstes Application into Database
     * @param userName
     * @param appName
     */
	private void writeLogRequest(String username, String appName) {

		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withSchemaName(SCHEMA);
		call.withProcedureName(STORED_PROCEDURE);
		call.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
		call.addDeclaredParameter(new SqlParameter("APPNAME", Types.CHAR));
        
        Map<String, Object> inputs = new HashMap<>();
        inputs.put("USRNAME", username==null? "": username);
        inputs.put("APPNAME", appName.length() > 50 ? appName.substring(0, 50) : appName);
        
        call.execute(inputs);
    }
    
	   /**
     * Pointcut Expression for all annotated rest controller
     */
    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void restController() {}
    
    /**
     * Pointcut Expression for all public methods
     */
    @Pointcut("execution(public * *(..))")
	protected void loggingPublicOperation() {
    }
    
    /**
     * Execution for all rest controllers & it's public method executions
     * @param joinPoint
     */
    @Before("restController() && loggingPublicOperation()")
	public void logBefore(JoinPoint joinPoint){
    	/**
    	 * Get HttpServelet details
    	 */
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        /**
         * Get Controller Method Name
         */
        String methodName     = joinPoint.getSignature().getName();
        /**
         * Get Controller Full-qualified name
         */
        String className      =  joinPoint.getSignature().getDeclaringTypeName();
        /**
         * Get Controller Class Name
         */
        String controllerName = className.substring(className.lastIndexOf('.')+1);
        
        /**
         * Get Username
         */
        String username       = request.getUserPrincipal()==null? "GUEST_USER" : request.getUserPrincipal().getName();
        
        /**
         * Get API URL
         */
        String apiUrl         = request.getRequestURI().replaceAll("/api/myestes/","");
        /**
         * Get AppName from the API URL
         */
        String appName        = apiUrl.substring(0, apiUrl.indexOf('/'));
        /**
         * Check if the Property files has the property
         * Sample Property in the property file: AuditLogs.AppName.ControllerName.ControllerMethodName=Create App
         */
        
        //System.out.println(AUDIT_LOG_PROPERTY+"."+appName+"."+controllerName+"."+methodName);
        
        if(properties.containsKey(AUDIT_LOG_PROPERTY+"."+appName+"."+controllerName+"."+methodName)){
        	String app = properties.getProperty(AUDIT_LOG_PROPERTY+"."+appName+"."+controllerName+"."+methodName);
        	try{
                writeLogRequest(username, app);
                
            }catch(Exception ex){
                ESTESLogger.log(ESTESLogger.INFO, getClass(),
                 "logBefore(JoinPoint joinPoint)",
                 "Failed to write audit log:  uri="+ apiUrl+";username="+username,
                 ex);
            }
        }
    }
}