/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 *
 */

package com.estes.myestes.login.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.login.dao.iface.LoginDAO;
import com.estes.myestes.login.dto.AppAccessRequest;
import com.estes.myestes.login.dto.AuthenticationResponse;
import com.estes.myestes.login.dto.AuthorizationResponse;
import com.estes.myestes.login.dto.Credentials;
import com.estes.myestes.login.exception.LoginException;

@Repository ("loginDAO")
public class LoginDAOImpl implements LoginDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	public AuthenticationResponse authenticate(Credentials cred) throws LoginException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(ALT_PGM_SCHEMA);
        sproc.withProcedureName("meg10q100");
        sproc.addDeclaredParameter(new SqlParameter("user_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("password", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("other", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("ip_address", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("query_string", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("hash_value", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("user_name", cred.getUserName());
        inParams.put("password", cred.getPassword());
        inParams.put("other", "");
        inParams.put("ip_address", "");
        inParams.put("query_string", "");
 
    	Map<String, Object> outParms = sproc.execute(inParams);

    	AuthenticationResponse resp = new AuthenticationResponse();

		if (outParms != null) {
			resp.getError().setErrorCode(((String) outParms.get("error")).trim());
			resp.setSession((String) outParms.get("random_number"));
			resp.setHash((String) outParms.get("hash_value"));
			if (resp.getError().getErrorCode().isEmpty()) {
				resp.setAccountCode(getAccountCode(resp.getSession()));
				resp.setAccountType(getAccountType(resp.getSession()));
			}
			if (!LoginDAO.isAuthenticationError((String) outParms.get("error"))) {
		    	AppAccessRequest appReq = new AppAccessRequest();
		    	appReq.setSession((String) outParms.get("random_number"));
		    	appReq.setHash((String) outParms.get("hash_value"));
		    	appReq.setOther("");
				resp.setAppNames(authorize(cred.getUserName(), appReq));
				
				if(resp.getAppNames().length==0){
					ESTESLogger.log(ESTESLogger.ERROR, LoginDAOImpl.class, "authenticate()", "User is blocked/disabled to access any application. Username: " + cred.getUserName());
		    		resp.getError().setErrorCode("GEN9996");
				}
				
			}
			return resp;
		}
		else {
			ESTESLogger.log(ESTESLogger.ERROR, LoginDAOImpl.class, "authenticate()", "An error occurred during login for user " + cred.getUserName());
    		throw new LoginException("Authentication failed for user " + cred.getUserName() + ".");
		}
	} // authenticate

	private String[] authorize(String user, AppAccessRequest access) throws LoginException
	{
		Vector<String> allowedApps = new Vector<String>();
		String acct = getAccountCode(access.getSession());
		String[] apps = getUserApps(user, acct);

		for (String app : apps) {
			access.setAppName(app);
			if (!LoginDAO.isAuthorizationError(authorizeApp(access).getError().getErrorCode())) {
				allowedApps.add(app.trim());
			}
		}

		return allowedApps.toArray( new String[allowedApps.size()] );
	} // authorize

	public AuthorizationResponse authorizeApp(AppAccessRequest appAccess) throws LoginException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(ALT_PGM_SCHEMA);
        sproc.withProcedureName("qnq400");
        sproc.addDeclaredParameter(new SqlParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("hash_value", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("other", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("app_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("query_string", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("all_me", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("all_app", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("input", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("output", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("fields", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("hit_type", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("random_number", appAccess.getSession());
        inParams.put("hash_value", appAccess.getHash());
        inParams.put("other", appAccess.getOther());
        inParams.put("app_name", appAccess.getAppName());
        inParams.put("query_string", "");
 
    	Map<String, Object> outParms = sproc.execute(inParams);

		if (outParms != null) {
			AuthorizationResponse authResp = new AuthorizationResponse();
			if ( ((String) outParms.get("all_app")).equalsIgnoreCase("X") ) {
				authResp.getError().setErrorCode(AUTHOR_ERROR_CODE);
			}
			else {
				authResp.getError().setErrorCode((String) outParms.get("error"));
				// Return blocked fields if no error
				if (!LoginDAO.isAuthorizationError((String) outParms.get("error"))) {
					authResp.setBlockedFields(((String) outParms.get("fields")).split(" "));
				}
			}
			return authResp;
		}
		else {
			ESTESLogger.log(ESTESLogger.ERROR, LoginDAOImpl.class, "authorizeApp()", "An error occurred authorizing session " + appAccess.getSession());
    		throw new LoginException("Authorization failed for session " + appAccess.getSession() + ".");
		}
	} // authorizeApp

	private String getAccountCode(String rand) throws LoginException
	{
		String sql = "SELECT qxact# " +
				"FROM " + ALT_PGM_SCHEMA + ".qnp235 " +
				"WHERE qxrnum = ? ";
		String acct =  jdbcMyEstes.queryForObject(sql,  String.class, new Object[] {rand});
		if (acct != null && !acct.equals("")) {
			return acct;
		}

		throw new LoginException("Error getting account code for session ID " + rand + ".");
	} // getAccountCode

	private String getAccountType(String rand) throws LoginException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(ALT_PGM_SCHEMA);
        sproc.withProcedureName("saq144c");
        sproc.addDeclaredParameter(new SqlInOutParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("account_type", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("random_number", rand);
        inParams.put("account_type", "");
 
 		Map<String, Object> outParms = sproc.execute(inParams);

		if (outParms != null) {
			return (String) outParms.get("account_type");
		}
		else {
			ESTESLogger.log(ESTESLogger.ERROR, LoginDAOImpl.class, "getAccountType()", "An error occurred getting account type for session " + rand);
			throw new LoginException("Failure getting account type for session " + rand + ".");
		}
	} // getAccountType

	/**
	 * Get the list of User permitted Apps.
	 *
	 * @param My Estes user name
	 * @param account code
	 * @return Array of application names 
	 * @throws LoginException
	 */
	private String[] getUserApps(String userName, String acctCode) throws LoginException
	{
		String sql = "SELECT LEFT(qwmenu,10) " +
				"FROM " + ALT_PGM_SCHEMA + ".qnp200 " +
				"INNER JOIN " + ALT_PGM_SCHEMA + ".qnp220 " +
				"ON qntype = qwtype " +
				"WHERE qnun = ? AND qnact# = ? " +
				"ORDER BY qwsub";
		List<String> listQuery =  jdbcMyEstes.queryForList(sql,  String.class, new Object[] {userName, acctCode});
		
		/**
		 * Check if the user is admin, put ADMINSUBS as scope.
		 */
		if(isAdmin(userName)){
			listQuery.add("ADMIN");
		}
		
		if (listQuery != null && listQuery.size() > 0) {
			
			return listQuery.toArray( new String[listQuery.size()] );
		}

		if (listQuery.size() == 0) {
			ESTESLogger.log(ESTESLogger.ERROR, LoginDAOImpl.class, "authorizeApp()", "No apps found for user " + userName);
			return null;
		}
		

		throw new LoginException("Error getting apps for " + userName + ".");
	} // getUserApps

	/**
	 * To check if the user is admin
	 */
	
	@Override
	public boolean isAdmin(String username) {
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName("FBPGMS");
        sproc.withProcedureName("SP_CHECKIFADMIN");
        sproc.addDeclaredParameter(new SqlParameter("USER", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ANSWER", Types.CHAR));
        
        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("USER", username); 
        try{
        	Map<String, Object> outParms = sproc.execute(inParams);

    		if (outParms != null) {
    			String answer = (String) outParms.get("ANSWER");
    			
    			if(answer!=null && answer.equalsIgnoreCase("Y")){
    				return true;
    			}
    		}

        }catch(Exception ex){
        	ESTESLogger.log(ESTESLogger.DEBUG, LoginDAOImpl.class, "isAdmin()", username+" is not admin");
        }
 		
		return false;
	}
}
