/**
 * 
 */
package com.estes.myestes.profile.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.profile.exception.AppException;
import com.estes.myestes.profile.exception.StoreProcedureCallException;
import com.estes.myestes.profile.web.dao.iface.ErrorDAO;
import com.estes.myestes.profile.web.dao.iface.PagingAndSortingDAO;
import com.estes.myestes.profile.web.dao.iface.UserDAO;
import com.estes.myestes.profile.web.dao.mapper.ProfileMapper;
import com.estes.myestes.profile.web.dto.App;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.User;
import com.estes.myestes.profile.web.dto.UserStatus;

/**
 * @author rahmaat<Ataur Rahman>
 *
 */
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private PagingAndSortingDAO<Profile> pagingAndSortingDAO;
	
	
	@Override
	public void createUser(User user, String username) {
		/**
		 * Make username to UpperCase
		 */
		username = username.toUpperCase();
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(PROFILE_PGM_SCHEMA);
		
        sproc.withProcedureName("SP_CREATESUBACCOUNT");

        sproc.addDeclaredParameter(new SqlParameter("SIGNED_IN_USER", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("FIRST_NAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("FIRST_NAME_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("LAST_NAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("LAST_NAME_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("CO_NAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("CO_NAME_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("ACCT_CODE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ACCT_CODE_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("ACCT_REP", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ACCT_REP_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("EMAIL", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("EMAIL_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("AREA_CODE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("AREA_CODE_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("EXCHANGE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("EXCHANGE_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("LAST_FOUR", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("LAST_FOUR_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("USER_NAME_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("PASSWORD", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("PASSWORD_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("PW_CONFIRM", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("PW_CONFIRM_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlParameter("NOTIFY", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("NOTIFY_ERR", Types.CHAR));
        
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        
        inParams.put("SIGNED_IN_USER", username);
        inParams.put("FIRST_NAME", user.getFirstName().toUpperCase());
        inParams.put("LAST_NAME", user.getLastName().toUpperCase());
        inParams.put("CO_NAME", user.getCompanyName());
        inParams.put("ACCT_CODE", user.getAccountCode());
        inParams.put("ACCT_REP", user.getAccountType());
        inParams.put("EMAIL", user.getEmail().toUpperCase());
        inParams.put("AREA_CODE", user.getAreaCode());
        inParams.put("EXCHANGE", user.getExchange());
        inParams.put("LAST_FOUR", user.getLastFour());
        inParams.put("USER_NAME", user.getUsername().toUpperCase());
        inParams.put("PASSWORD", user.getPassword());
        inParams.put("PW_CONFIRM", user.getConfirmPassword());
        inParams.put("NOTIFY", user.getNotify());

        Map<String, Object> outParms = sproc.execute(inParams);
       
       
        
        if (outParms != null) {
    		if (ErrorDAO.isError((String) outParms.get("ERROR"))) {
    			throw new StoreProcedureCallException(getUserCreationErrorCodes(outParms));
    		}
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "createUser()",
					"An error occurred processing on creating new user request.");
    		throw new AppException("My Estes creating new user failed.");
		}
	}
	
	
	
	/**
	 * Get Error Codes Provided by Store Procedure Call
	 * @param output parameters
	 * @return 
	 */
	private Map<String,String> getUserCreationErrorCodes(Map<String, Object> parms)
	{
		Map<String,String> errs = new HashMap<>();

		if (ErrorDAO.isError((String) parms.get("FIRST_NAME_ERR"))) {
			errs.put("firstName",(String) parms.get("FIRST_NAME_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("LAST_NAME_ERR"))) {
			errs.put("lastName",(String) parms.get("LAST_NAME_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("ACCT_CODE_ERR"))) {
			errs.put("accountCode",(String) parms.get("ACCT_CODE_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("EMAIL_ERR"))) {
			errs.put("email",(String) parms.get("EMAIL_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("AREA_CODE_ERR"))) {
			errs.put("phone",(String) parms.get("AREA_CODE_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("EXCHANGE_ERR"))) {
			errs.put("phone", (String) parms.get("EXCHANGE_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("LAST_FOUR_ERR"))) {
			errs.put("phone",(String) parms.get("LAST_FOUR_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("USER_NAME_ERR"))) {
			errs.put("username",(String) parms.get("USER_NAME_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("PASSWORD_ERR"))) {
			errs.put("password",(String) parms.get("PASSWORD_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("PW_CONFIRM_ERR"))) {
			errs.put("confirmPassword",(String) parms.get("PW_CONFIRM_ERR"));
		}
		
		if (ErrorDAO.isError((String) parms.get("NOTIFY_ERR"))) {
			errs.put("notify",(String) parms.get("NOTIFY_ERR"));
		}

		return errs;
	}

	

	@Override
	public void disableUser(String childUsername, String parentUsername) {
		/**
		 * Make childUsername to UpperCase
		 */
		childUsername=childUsername.toUpperCase();
		
		List<Object> params = new ArrayList<>();
		params.add(childUsername);
		params.add(parentUsername);
		jdbcTemplate.update(UserDAO.DISABLE_USER,params.toArray());
		
	}

	@Override
	public void enableUser(String childUsername) {
		/**
		 * Make childUsername to UpperCase
		 */
		childUsername=childUsername.toUpperCase();
		
		List<Object> params = new ArrayList<>();
		params.add(childUsername);
		jdbcTemplate.update(UserDAO.ENABLE_USER,params.toArray());
	}


	@Override
	public Page<Profile> getUsers(String username, Pageable pageable, String search,String qFirstName, String qLastName,String qUsername) {
		
		/**
		 *  Get User Query to retrieve user list created by signed in user.
		 */
		
		
		String query = UserDAO.USER_LIST_QUERY;
		
		/**
		 * Prepare query parmeters
		 */
		
		List<Object> params = new ArrayList<>();
		/**
		 * add signed in username as createdBy param
		 */
		params.add(username);
		
	
		
		/**
		 * check the search parameter is passed or not. if it has value, add to query parameters list
		 * Search can be done firstName, lastName, username & email address.
		 * That's why same search string is added four times to prepare query.
		 */
		
		if(search!=null && search.trim()!=""){
		
			query = query + UserDAO.USER_SEARCH_QUERY;
			
			
			search = "%"+search.toUpperCase()+"%";
			
			params.add(search);
			params.add(search);
			params.add(search);
			params.add(search);
		}
		
		/**
		 * Prepare query for Search by firstName
		 */
		
		if(qFirstName!=null && qFirstName.trim()!=""){
			query = query + UserDAO.USER_SEARCH_BY_FIRST_NAME;
			qFirstName = "%"+qFirstName.toUpperCase()+"%";
			params.add(qFirstName);
		}
		
		/**
		 * Prepare query for Search by lastName
		 */
		
		if(qLastName!=null && qLastName.trim()!=""){
			query = query + UserDAO.USER_SEARCH_BY_LAST_NAME;
			qLastName = "%"+qLastName.toUpperCase()+"%";
			params.add(qLastName);
		}
		
		/**
		 * Prepare query for Search by UserName
		 */
		
		if(qUsername!=null && qUsername.trim()!=""){
			query = query + UserDAO.USER_SEARCH_BY_USER_NAME;
			qUsername = "%"+qUsername.toUpperCase()+"%";
			params.add(qUsername);
		}
		

	  
		/**
		 * Pagination
		 */

		String countQuery = query.replace("*", "count(*) as total");

		/**
		 * Sorting query Generation
		 */

		String sort = pageable.getSort();

		if (sort != null) {

			String order = pageable.getOrder().name();

			switch (sort) {

			case "firstName":
				query += " ORDER BY FIRSTNAME " + order;
				break;

			case "lastName":
				query += " ORDER BY LASTNAME " + order;
				break;

			case "accountCode":
				query += " ORDER BY ACCOUNTCODE " + order;
				break;

			case "phone":
				query += " ORDER BY PHONE " + order;
				break;

			case "companyName":
				query += " ORDER BY COMPANYNAME " + order;
				break;
			case "username":
				query += " ORDER BY USERNAME " + order;
				break;
			case "email":
				query += " ORDER BY EMAIL " + order;
				break;
			case "preference":
				query += " ORDER BY PREFERENCE " + order;
				break;
			case "status":
				query += " ORDER BY STATUS " + order;
				break;
			case "createdDate":
				query += " ORDER BY CREATEDDATE " + order;
				break;
			default:
				break;
			}
		}

		
		Page<Profile> results = pagingAndSortingDAO.getResult(countQuery, query, params, pageable, new ProfileMapper());
		return results;
	}
	
	
	
	/**
	 * Get Total Results
	 */
	@Override
	public int getTotal(String query,Object[] objects) {
		return  (int) jdbcTemplate.queryForObject(query, objects, Integer.class);
	}

	private List<App> getApps(List<Map<String,Object>> results){
		List<App> apps = new ArrayList<>();
		for(Map<String,Object> result : results){
		  App app = new App();
		  app.setName(((String) result.get("QWMENU")).trim());
		  app.setCategory(((String) result.get("QNTYPE")).trim());
		  app.setDescription(((String) result.get("QWMEN6")).trim());
		  apps.add(app);
		}
		return apps;
	}

	@Override
	public List<App> getAuthorizedApps(String parentUsername, String childUsername) {
		/**
		 * Make Username to UpperCase
		 */
		childUsername = childUsername.toUpperCase();
		
		/**
		 * Prepare query parmeters
		 */
		
		List<Object> params = new ArrayList<>();
		/**
		 * add username to params 3 times needed in query
		 */
		params.add(childUsername);
		params.add(childUsername);
		params.add(parentUsername);
		
		List<Map<String,Object>> results = jdbcTemplate.queryForList(GET_ACCESS_APPS, params.toArray());
		List<App> apps = getApps(results);
		return apps;
	}



	@Override
	public List<App> getBlockedApps(String parentUsername, String childUsername) {
		/**
		 * Make Username to UpperCase
		 */
		childUsername = childUsername.toUpperCase();
		
		/**
		 * Prepare query parmeters
		 */
		
		List<Object> params = new ArrayList<>();
		/**
		 * add username to params 3 times needed in query
		 */
		params.add(childUsername);
		params.add(childUsername);
		params.add(parentUsername);
		
		List<Map<String,Object>> results = jdbcTemplate.queryForList(GET_BLOCKED_APPS, params.toArray());
		//System.out.println(GET_BLOCKED_APPS);
		
		List<App> apps = getApps(results);
		return apps;
	}



	@Override
	public void addToBlockedApps(List<String> appNames, String username) {
		/**
		 * Make username to upperCase
		 */
		final String usernameUpper=username.toUpperCase();
		
		jdbcTemplate.batchUpdate(UserDAO.INSERT_BLOCKED_APPS, new BatchPreparedStatementSetter() {

		    @Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
		    	String appName = appNames.get(i);
		    	if(appName.equals(SUB_ACCOUNT_APP)){
		    		throw new AppException(HttpStatus.BAD_REQUEST,"You have already added "+SUB_ACCOUNT_APP+" to blocked list");
		    	}
		        ps.setString(1, usernameUpper);
		        ps.setString(2, appName);
		   }

		    @Override
		    public int getBatchSize() {
		        return appNames.size();
		    }
		});
	}

	


	@Override
	public void deleteAllBlockedApps(String parentUsername, String childUsername) {
		/**
		 * Make username to upperCase
		 */
		childUsername = childUsername.toUpperCase();
		
		jdbcTemplate.update(UserDAO.DELETE_ALL_BLOCKED_APPS,new Object[]{childUsername,parentUsername});
	}

	
	@Override
	public void deleteBlockedApps(List<String> appNames,String parentUsername,String childUsername) {
		/**
		 * Make username to upperCase
		 */
		final String childUsernameUpper = childUsername.toUpperCase();
		
		/**
		 * Delete AppNames from Blocked List
		 */
		jdbcTemplate.batchUpdate(UserDAO.DELETE_BLOCKED_APPS, new BatchPreparedStatementSetter() {

		    @Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
		    	String appName = appNames.get(i);
		        ps.setString(1, childUsernameUpper);
		        ps.setString(2, parentUsername);
		        ps.setString(3, appName);
		   }

		    @Override
		    public int getBatchSize() {
		        return appNames.size();
		    }
		});
	}


	
	@Override
	public boolean checkChildUser(String parentUsername, String childUsername) {
		
		/**
		 * Make username to upperCase
		 */
		childUsername = childUsername.toUpperCase();
		
		/**
		 * Prepare query parmeters
		 */
		
		List<Object> params = new ArrayList<>();
		/**
		 * add signed in User ( parentUsername) as createdBy param
		 */
		params.add(parentUsername);
		
		/**
		 * add child Username as Username param
		 */
		
		params.add(childUsername);
		
		int isExists = (int) jdbcTemplate.queryForObject(UserDAO.CHILD_USERNAME_CHECK, params.toArray(), Integer.class);
		
		return (isExists > 0);
	}
	
	

	@Override
	public UserStatus getUserStatus(String username) {
		/**
		 * Make username to UpperCase
		 */
		username = username.toUpperCase();
		
		String query = UserDAO.GET_USER_STATUS;
		Integer response  = jdbcTemplate.queryForObject(query, Integer.class, new Object[]{username});
		return response==0? UserStatus.ENABLED : UserStatus.DISABLED;
	}
	

}
