/**
 * @author: Todd Allen
 *
 * Creation date: 11/19/2018
 */

package com.estes.services.common.dao.impl;

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
import com.estes.services.common.dao.iface.CustomerDAO;
import com.estes.services.common.exception.ServiceException;

@Repository ("customerDAO")
public class CustomerDAOImpl implements CustomerDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstesServices;

	@Override
	public String getAccountTypeByAccount(String acc) throws ServiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_get_account_type_by_account");
        sproc.addDeclaredParameter(new SqlInOutParameter("accounttype", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("acct", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("accounttype", "");
        inParams.put("acct", acc);
 
        try {
     		Map<String, Object> outParms = sproc.execute(inParams);

    		if (outParms != null) {
    			return (String) outParms.get("accounttype");
    		}
    		else {
    			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getAccountTypeByAccount()", "** Error occurred getting account type for " + acc);
    			throw new ServiceException("Failure getting account type for account " + acc + ".");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getAccountTypeByAccount()", "** Error occurred getting account type for " + acc, e);
			throw new ServiceException("Failure getting account type for account " + acc + ".", e);
        }
	} // getAccountType

	@Override
	public boolean isAccountPayor(String acc, String pro) throws ServiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_is_account_payor");
        sproc.addDeclaredParameter(new SqlOutParameter("pr_answer", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pr_ot", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pr_pro", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pr_account", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pr_acct_tp", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
 
        try {
            inParams.put("pr_ot", pro.substring(0, 3));
            inParams.put("pr_pro", pro.substring(3, 10));
            inParams.put("pr_account", acc);
            inParams.put("pr_acct_tp", getAccountTypeByAccount(acc));
     		Map<String, Object> outParms = sproc.execute(inParams);

    		if (outParms != null) {
    			String answer = (String) outParms.get("pr_answer");
    			return answer.equalsIgnoreCase("Y");
    		}
    		else {
    			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "isAccountPayor()", "** Error checking payor for account " + acc);
    			throw new ServiceException("Failure checking payor for account " + acc + ".");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getAccouisAccountPayorntTypeByAccount()", "** Error checking payor for account " + acc, e);
			throw new ServiceException("Failure checking payor for account " + acc + ".", e);
        }
	} // getAccountType
}
