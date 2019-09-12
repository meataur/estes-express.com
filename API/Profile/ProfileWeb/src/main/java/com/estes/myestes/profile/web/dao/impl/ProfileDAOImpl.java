package com.estes.myestes.profile.web.dao.impl;

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
import com.estes.myestes.profile.exception.AppException;
import com.estes.myestes.profile.web.dao.iface.ErrorDAO;
import com.estes.myestes.profile.web.dao.iface.ProfileDAO;
import com.estes.myestes.profile.web.dto.BasicProfile;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.UserStatus;

@Repository("profileDAO")
public class ProfileDAOImpl implements ProfileDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private ErrorDAO errorDAO;
	@Override
	public boolean updateUsername(String currentUsername, String newUsername) {
		/**
		 * make newUsername to UpperCase
		 */
		newUsername = newUsername.toUpperCase();
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(PROFILE_PGM_SCHEMA);
        sproc.withProcedureName("SP_UPDUSERNAME");

        sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("NEWUSERNAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("USRNAME", currentUsername);
        inParams.put("NEWUSERNAME", newUsername);
        
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
    		if (!ErrorDAO.isError((String) outParms.get("ERROR"))) {
    			return true;
    		}
    		throw new AppException(errorDAO.getErrorMessage(((String) outParms.get("ERROR")).trim()));
    	
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "updateUsername()",
					"An error occurred processing username updating request.");
    		throw new AppException("My Estes update username failed.");
		}
	}
	
	@Override
	public boolean updateEmailAddress(String username,String emailAddress) {
		
		/**
		 * Make Username to UpperCase
		 */
		username = username.toUpperCase();
		
		
		/**
		 * Make emailAddess to UpperCase
		 */
		emailAddress = emailAddress.toUpperCase();
		
		
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(PROFILE_PGM_SCHEMA);
        sproc.withProcedureName("SP_UPDEMAILADDR");
        
        sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("EMAIL", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("USRNAME", username);
        inParams.put("EMAIL", emailAddress);
        
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
    		if (!ErrorDAO.isError((String) outParms.get("ERROR"))) {
    			return true;
    		}
    		
    		throw new AppException(errorDAO.getErrorMessage(((String) outParms.get("ERROR")).trim()));
    		
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "updateEmailAddress()",
					"An error occurred processing email address changing request.");
    		throw new AppException("My Estes update email address failed.");
		}
	}

	@Override
	public boolean changePassword(String username, Password password) {
		/**
		 * Make Username to UpperCase
		 */
		username = username.toUpperCase();
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(PROFILE_PGM_SCHEMA);
        sproc.withProcedureName("SP_UPDPASSWORD");
        
        sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("NEWPWRD", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("VERIFYPWRD", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("USRNAME", username);
        inParams.put("NEWPWRD", password.getPassword());
        inParams.put("VERIFYPWRD", password.getConfirmPassword());
        
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
    		if (!ErrorDAO.isError((String) outParms.get("ERROR"))) {
    			return true;
    		}
    		
    		throw new AppException(errorDAO.getErrorMessage(((String) outParms.get("ERROR")).trim()));
    		
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "changePassword()",
					"An error occurred processing password changing request.");
    		throw new AppException("My Estes password changing failed.");
		}
	}

	@Override
	public boolean updateEmailPrefence(String username, String flag) {
		/**
		 * Make Username to UpperCase
		 */
		username = username.toUpperCase();
		
		/**
		 * Make flag to UpperCase
		 */
		flag = flag.toUpperCase();
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(PROFILE_PGM_SCHEMA);
        sproc.withProcedureName("SP_UPDUPDATEPREF");

        sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("UPDATE_FLG", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("USRNAME", username);
        inParams.put("UPDATE_FLG", flag);
        
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
    		if (!ErrorDAO.isError((String) outParms.get("ERROR"))) {
    			return true;
    		}
    		throw new AppException(errorDAO.getErrorMessage(((String) outParms.get("ERROR")).trim()));
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "updateEmailPrefence()",
					"An error occurred processing on updating email preference request.");
    		throw new AppException("My Estes updating email preference failed.");
		}
	}

	@Override
	public Profile getUserProfile(String username) {
		
		/**
		 * Make username to UpperCase
		 */
		username = username.toUpperCase();
		
		Map<String,Object> info = jdbcTemplate.queryForMap(ProfileDAO.PROFILE_INFO, new Object[]{username});
		
		Profile profile = new Profile();
		profile.setFirstName(((String) info.get("FIRSTNAME")).trim());
		profile.setLastName(((String) info.get("LASTNAME")).trim());
		profile.setAccountCode(((String) info.get("ACCOUNTCODE")).trim());
		profile.setPhone((String) info.get("PHONE"));
		profile.setCompanyName(((String) info.get("COMPANYNAME")).trim());
		profile.setUsername(((String) info.get("USERNAME")).trim());
		profile.setEmail(((String) info.get("EMAIL")).trim());
		profile.setPreference(((String) info.get("PREFERENCE")).trim());
		profile.setStatus(UserStatus.valueOf((String) info.get("STATUS")));
		profile.setCreatedDate(info.get("CREATEDDATE").toString());
		
		if(profile.getPhone().length() < 14){
			profile.setPhone("");
		}
		
		return profile;
	}

	@Override
	public boolean hasProfileInfo(String username) {
		
		int hasFound = jdbcTemplate.queryForObject(ProfileDAO.CHECK_PROFILE,Integer.class, new Object[]{username});
		
		return hasFound>0;
	}
	
	/**
	 * Update Basic Profile Information If the User is found in QNP150 TABLE
	 */
	
	@Override
	public void updateProfile(String username, BasicProfile profile) {
		jdbcTemplate.update(ProfileDAO.UPDATE_USER, 
				new Object[]{
						profile.getFirstName(),
						profile.getLastName(),
						profile.getCompanyName(),
						profile.getAreaCode(),
						profile.getExchange(),
						profile.getLastFour(),
						profile.getPreference(),
						username
						});
	}
	
	/**
	 * Add Basic Profile Information in QNP150 If the User is not found in QNP150 TABLE
	 */
	
	@Override
	public void addProfile(String username,
			String accountCode,
			BasicProfile profile) {
		jdbcTemplate.update(ProfileDAO.ADD_USER, 
				new Object[]{
						profile.getFirstName(),
						profile.getLastName(),
						profile.getCompanyName(),
						accountCode,
						profile.getAreaCode(),
						profile.getExchange(),
						profile.getLastFour(),
						username,
						profile.getPreference()
						});
	}
	
	
	

}
