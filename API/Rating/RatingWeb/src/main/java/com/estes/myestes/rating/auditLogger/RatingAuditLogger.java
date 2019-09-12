package com.estes.myestes.rating.auditLogger;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.dto.RatingRequest;


@Component
@Aspect
@EnableAspectJAutoProxy(proxyTargetClass=false)
public class RatingAuditLogger {
	 	public static final String SCHEMA           = "FBPGMS";
	 	public static final String STORED_PROCEDURE = "MEG10Q400";
		
	 	public static final String GUEST_USER = "GUEST_USER";
		public static final String LTL_APP = "LTL Rate Quote";
		public static final String TC_APP = "Time Critical";
		public static final String VTL_APP = "VTL Rate Quote";
		public static final String QUOTE_HISTORY = "Quote History";
		
		@Autowired
	    private JdbcTemplate jdbcTemplate;

	    /**
	     * Write Log Request for MyEstes Application into Database
	     * @param userName
	     * @param appName
	     */
		private void writeLogRequest(String userName, String appName) {

			SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
			call.withSchemaName(SCHEMA);
			call.withProcedureName(STORED_PROCEDURE);
			call.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
			call.addDeclaredParameter(new SqlParameter("APPNAME", Types.CHAR));
	        
	        Map<String, Object> inputs = new HashMap<>();
	        inputs.put("USRNAME", userName.toUpperCase());
	        inputs.put("APPNAME", appName.length() > 50 ? appName.substring(0, 50) : appName);
	        
	        call.execute(inputs);
	    }
	    
		
		 
	    /**
	     * Execution for all rest controllers & it's public method executions
	     * @param joinPoint
	     */
	    @Before("execution(* com.estes.myestes.rating.controller.RatingController.bookQuote(..))")
		public void logBookQuote(JoinPoint joinPoint){
	    	
	    	 HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
		        
		        /**
		         * Get Username
		         */
		        
		        String username       = request.getUserPrincipal()==null? GUEST_USER : request.getUserPrincipal().getName();
		       
		        BookingRequest bookingRequest = (BookingRequest) joinPoint.getArgs()[0];
		        
		        String appName = bookingRequest.getApp();

		        String message = "";
		        switch(appName){
		        	case "LTL":
		        		message = "Book " + LTL_APP;
		        		break;
		        	case "TC":
		        		message = "Book " + TC_APP;
		        		break;
		        	case "VTL":
		        		message = "Book  "+ VTL_APP;
		        		break;
	        	}
		        
		        try{
		            writeLogRequest(username,message);
		        }catch(Exception ex){
		            ESTESLogger.log(ESTESLogger.INFO, getClass(),
		             "logBookQuote(JoinPoint joinPoint)",
		             "Failed to write audit log",
		             ex);
		        }
		        
	    }
	   
	    /**
	     * Execution for all rest controllers & it's public method executions
	     * @param joinPoint
	     */
	    @Before("execution(* com.estes.myestes.rating.controller.RatingController.generateQuotes(..))")
		public void logGenerateQuotes(JoinPoint joinPoint){

	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	        
	        /**
	         * Get Username
	         */
	        
	        String username       = request.getUserPrincipal()==null? GUEST_USER : request.getUserPrincipal().getName();
	       
	        RatingRequest rating = (RatingRequest) joinPoint.getArgs()[1];
	        
	        
	        List<String> apps =  rating.getApps();
	        
	        
	        for(String app : apps){
	        	
	        	String message = "";
	        	
	        	switch(app){
		        	case "LTL":
		        		message = LTL_APP;
		        		break;
		        	case "TC":
		        		message = TC_APP;
		        		break;
		        	case "VTL":
		        		message = VTL_APP;
		        		break;
	        	}
	        	
	        	try{
		            writeLogRequest(username,message);
		        }catch(Exception ex){
		            ESTESLogger.log(ESTESLogger.INFO, getClass(),
		             "logGenerateQuotes(JoinPoint joinPoint)",
		             "Failed to write audit log",
		             ex);
		        }
	        }
	    }
	    
	    /**
	     * Execution for all rest controllers & it's public method executions
	     * @param joinPoint
	     */
	    @Before("execution(* com.estes.myestes.rating.controller.QuoteHistoryController.*(..))")
		public void logBefore(JoinPoint joinPoint){

	        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
	        /**
	         * Get Username
	         */
	        String username       = request.getUserPrincipal()==null? GUEST_USER : request.getUserPrincipal().getName();
	      
	        	
        	try{
	            writeLogRequest(username, QUOTE_HISTORY);
	        }catch(Exception ex){
	            ESTESLogger.log(ESTESLogger.INFO, getClass(),
	             "writeAuditLog(JoinPoint joinPoint, WebRequest request)",
	             "Failed to write audit log",
	             ex);
	        }

	    }
}
