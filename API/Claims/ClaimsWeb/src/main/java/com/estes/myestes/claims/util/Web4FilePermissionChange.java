package com.estes.myestes.claims.util;

import com.edps.logger.ESTESLogger;
import com.edps.sql.CallableProgram;
import com.edps.sql.Parameter;
import com.edps.sql.ParameterList;

public class Web4FilePermissionChange {

    public static boolean execute(String pathname) { 
    	CallableProgram program = new CallableProgram(ClaimsConstant.APP_WEB4_JNDI);
        program.setProgram("DPSGPL.uxg10c001");
        ParameterList parameters = new ParameterList(new String[] { "PATHNAME" } );
        parameters.setUsage(new String[] { Parameter.IN } );
        parameters.setValue("PATHNAME", pathname);
        program.setParameterList(parameters);
        
        ESTESLogger.log(ESTESLogger.DEBUG, Web4FilePermissionChange.class, "execute", "Before Call");
		ESTESLogger.logCallableProgram(Web4FilePermissionChange.class, "execute", program); 
		boolean success = program.execute();
		ESTESLogger.log(ESTESLogger.DEBUG, Web4FilePermissionChange.class, "execute", "Before Call");
		ESTESLogger.logCallableProgram(Web4FilePermissionChange.class, "execute", program); 
        
        return success;
    }
}