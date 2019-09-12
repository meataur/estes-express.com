/**
 * @author: Todd Allen
 *
 * Creation date: 08/06/2018
 */

package com.estes.myestes.signup.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.signup.dao.iface.ErrorDAO;
import com.estes.myestes.signup.dao.iface.SignupDAO;
import com.estes.myestes.signup.dto.SignupInfo;
import com.estes.myestes.signup.exception.SignupException;
import com.estes.myestes.signup.utils.PhoneUtil;

@Repository ("signupDAO")
public class SignupDAOImpl implements SignupDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private List<String> captureErrors(Map<String, Object> parms)
	{
		List<String> errs = new ArrayList<String>();

		if (ErrorDAO.isError((String) parms.get("first_name_err"))) {
			errs.add((String) parms.get("first_name_err"));
		}
		if (ErrorDAO.isError((String) parms.get("last_name_err"))) {
			errs.add((String) parms.get("last_name_err"));
		}
		if (ErrorDAO.isError((String) parms.get("acct_code_err"))) {
			errs.add((String) parms.get("acct_code_err"));
		}
		if (ErrorDAO.isError((String) parms.get("email_err"))) {
			errs.add((String) parms.get("email_err"));
		}
		if (ErrorDAO.isError((String) parms.get("area_code_err"))) {
			errs.add((String) parms.get("area_code_err"));
		}
		if (ErrorDAO.isError((String) parms.get("exchange_err"))) {
			errs.add((String) parms.get("exchange_err"));
		}
		if (ErrorDAO.isError((String) parms.get("last_four_err"))) {
			errs.add((String) parms.get("last_four_err"));
		}
		if (ErrorDAO.isError((String) parms.get("user_name_err"))) {
			errs.add((String) parms.get("user_name_err"));
		}
		if (ErrorDAO.isError((String) parms.get("password_err"))) {
			errs.add((String) parms.get("password_err"));
		}
		if (ErrorDAO.isError((String) parms.get("notify_err"))) {
			errs.add((String) parms.get("notify_err"));
		}

		return errs;
	} // captureErrors

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.signup.dao.iface.SignupDAO#createProfile
	 */
	@Override
	public List<String> createProfile(SignupInfo info) throws SignupException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(ALT_PGM_SCHEMA);
        sproc.withProcedureName("qnq154");
        sproc.addDeclaredParameter(new SqlParameter("first_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("first_name_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("last_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("last_name_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("co_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("co_name_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("acct_code", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("acct_code_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("acct_rep", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("acct_rep_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("email", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("email_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("area_code", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("area_code_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("exchange", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("exchange_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("last_four", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("last_four_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("user_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("user_name_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("password", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("password_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pw_confirm", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("pw_confirm_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("notify", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("notify_err", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("query_string", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("first_name", info.getFirstName());
        inParams.put("last_name", info.getLastName());
        inParams.put("co_name", info.getCompany());
        inParams.put("acct_code", info.getAccountCode());
        inParams.put("acct_rep", "");
        inParams.put("email", info.getEmail());
        inParams.put("area_code", PhoneUtil.getAreaCode(info.getPhone()));
        inParams.put("exchange", PhoneUtil.getExchange(info.getPhone()));
        inParams.put("last_four", PhoneUtil.getLast4(info.getPhone()));
        inParams.put("user_name", info.getUserName());
        inParams.put("password", info.getPassword());
        inParams.put("pw_confirm", info.getPassword());
        inParams.put("notify", info.isNotify() ? "Y" : "N");
        inParams.put("query_string", "");

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
        		if (!ErrorDAO.isError((String) outParms.get("error"))) {
        			return null;
        		}
        		else {
                	return captureErrors(outParms);
        		}
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, SignupDAOImpl.class, "createProfile()", "** Error occurred processing signup request.");
        		throw new SignupException("My Estes profile signup failed.");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, SignupDAOImpl.class, "createProfile()", "** Error occurred processing signup request.", e);
    		throw new SignupException("My Estes profile signup failed.", e);
        }
	} // createProfile

	public boolean isL2L(String acct) throws SignupException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("l2l_sql_isAccountL2L");
        sproc.addDeclaredParameter(new SqlParameter("pr_acct", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("pr_isl2l", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("pr_acct", acct);
        inParams.put("pr_isl2l", "");

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	return ((String) outParms.get("pr_isl2l")).equalsIgnoreCase("Y");
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, SignupDAOImpl.class, "isL2L()", "** Error occurred checking L2L account status.");
        		throw new SignupException("L2L account check failed.");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, SignupDAOImpl.class, "isL2L()", "** Error occurred checking L2L account status.", e);
    		throw new SignupException("L2L account check failed.", e);
        }
	} // isL2L
}
