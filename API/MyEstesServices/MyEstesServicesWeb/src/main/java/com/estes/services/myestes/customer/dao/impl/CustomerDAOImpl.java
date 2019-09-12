/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.customer.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.Address;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.framework.logger.ESTESLogger;
import com.estes.services.myestes.customer.dao.iface.CustomerDAO;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.customer.dto.ProfileDTO;
import com.estes.services.myestes.exception.ServiceException;

@Repository ("customerDAO")
public class CustomerDAOImpl implements CustomerDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstesServices;

	private String getAccountCode(String rand) throws ServiceException
	{
		String sql = "SELECT qxact# " +
				"FROM " + ALT_PGM_SCHEMA + ".qnp235 " +
				"WHERE qxrnum = ? ";
		String acct =  jdbcMyEstesServices.queryForObject(sql,  String.class, new Object[] {rand});
		if (acct != null && !acct.equals("")) {
			return acct;
		}

		throw new ServiceException("Error getting account code for session ID " + rand + ".");
	} // getAccountCode

	//@Override
	public String getAccountType(String rand) throws ServiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);

		sproc.withSchemaName(ALT_PGM_SCHEMA);
        sproc.withProcedureName("saq144c");
        sproc.addDeclaredParameter(new SqlInOutParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("account_type", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("random_number", rand);
        inParams.put("account_type", "");
 
        try {
     		Map<String, Object> outParms = sproc.execute(inParams);

    		if (outParms != null) {
    			return (String) outParms.get("account_type");
    		}
    		else {
    			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getAccountType()", "** Error occurred getting account type for session " + rand);
    			throw new ServiceException("Failure getting account type for session " + rand + ".");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getAccountType()", "** Error occurred getting account type for session " + rand);
			throw new ServiceException("Failure getting account type for session " + rand + ".", e);
        }
	} // getAccountType

	@Override
	public Page<AccountDTO> getSubAccounts(Pageable pageable, String account_code)
		throws ServiceException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);
		SubAccountMapper mapper = new SubAccountMapper();
		String orderBy;

		if(pageable != null && pageable.getSort() != null && pageable.getSort().equals("name")) {
			orderBy = "CMNAME";
		}
		if(pageable != null && pageable.getSort() != null && pageable.getSort().equals("address")) {
			orderBy = "CMADDR1";
		}
		if(pageable != null && pageable.getSort() != null && pageable.getSort().equals("city")) {
			orderBy = "CMCITY";
		}
		if(pageable != null && pageable.getSort() != null && pageable.getSort().equals("state")) {
			orderBy = "CMST";
		}
		if(pageable != null && pageable.getSort() != null && pageable.getSort().equals("zip")) {
			orderBy = "CMZIP";
		}
		else { // should be sort by acct
			orderBy = "CMCUST";
		}

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName(SP_SUBACCOUNTS);
		sproc.addDeclaredParameter(new SqlParameter("ACCOUNT_CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("START_REC", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ORDER_BY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NUM_RECORDS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("TOTAL_RECS", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlReturnResultSet("RESULT_SET", mapper));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("ACCOUNT_CODE", account_code);
		inParams.put("START_REC", pageable.getPage()); 
		inParams.put("ORDER_BY", orderBy);
		inParams.put("NUM_RECORDS", pageable.getSize()); 

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		Integer totalSize = ((BigDecimal) outParms.get("TOTAL_RECS")).intValue();
	    		List<AccountDTO> accounts = (List<AccountDTO>) outParms.get("RESULT_SET");
	    		Page<AccountDTO> pages = new Page<AccountDTO>(accounts, pageable, totalSize);
	    		return pages;
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getSubAccounts()", "** Error occurred getting the sub account information ");
				throw new ServiceException("Error occurred getting the sub account information");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getSubAccounts()", "** Error occurred getting the sub account information ", e);
			throw new ServiceException("Error occurred getting the sub account information");
		}
	} // getSubAccounts

	@Override
	public boolean isSubAccountOf(String group_account, String selected_account, String account_type)
			throws ServiceException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstesServices);
		sproc.withSchemaName(ALT_PGM_SCHEMA);
		sproc.withProcedureName(SP_ISUBOF);
		sproc.addDeclaredParameter(new SqlInOutParameter("GROUP_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("ACCOUNT_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("ACCOUNT_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("ERROR", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("GROUP_NUMBER", group_account);
		inParams.put("ACCOUNT_NUMBER", selected_account);
		inParams.put("ACCOUNT_TYPE", account_type);
		inParams.put("ERROR", "");

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String valid = (String) outParms.get("ERROR");
	    		return "Y".equalsIgnoreCase(valid);
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "isSubAccountOf()", "** Error occurred determining if sub account");
				throw new ServiceException("Error occurred getting the sub account information");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "isSubAccountOf()", "** Error occurred determining if sub account", e);
			throw new ServiceException("Error occurred getting the sub account information");
		}
	}
	
	private class SubAccountMapper implements RowMapper<AccountDTO>
	{
		@Override
		public AccountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountDTO account = new AccountDTO();
			account.setAccountNumber(rs.getString("XCMCUST"));
			account.setName(rs.getString("XCMNAME").trim());
			Address accountAddress = new Address();
			accountAddress.setStreetAddress(rs.getString("XCMADDR1").trim());
			accountAddress.setCity(rs.getString("XCMCITY").trim());
			accountAddress.setState(rs.getString("XCMST").trim());
			accountAddress.setZip(rs.getString("XCMZIP").trim());
			account.setAddress(accountAddress);
			return account;
		}
	}

	@Override
	public ProfileDTO getUserProfile(String username) throws ServiceException {
		/**
		 * Make username to UpperCase
		 */
		username = username.toUpperCase();

		String sql = "select qsun as username,"
				+ " qsact# as accountcode,"
				+ " qrcnam as companyname,"
				+ " qrfnam as firstname, "
				+ " qrlnam as lastname,"
				+ " qrem1 as email,"
				+ " CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,"
				+ " qrwebu as preference,"
				+ " qsrdat as createdate,"
				+ " CASE WHEN QZREFC IS  NULL THEN 'ENABLED' ELSE 'DISABLED' END AS status"
				+ " from "+ALT_PGM_SCHEMA+".qnp230"
				+ " LEFT  JOIN "+ALT_PGM_SCHEMA+".qnp150 ON qsun = qrun"
				+ " LEFT JOIN ( select QZREFC from "+ALT_PGM_SCHEMA+".qnp302 where  QZBLTP = 1 and QZREFT = 1) as t ON QZREFC=QSUN"
				+ " WHERE QSUN=?";

		Map<String,Object> info = jdbcMyEstesServices.queryForMap(sql, new Object[]{username});

		ProfileDTO profile = new ProfileDTO();
		profile.setUsername(((String) info.get("username")).trim());
		profile.setAccountCode(((String) info.get("accountcode")).trim());
		// Check whether data in qnp150 table is present; This can be removed later
		if (info.get("companyname") != null) {
			profile.setCompanyName(((String) info.get("companyname")).trim());
			profile.setFirstName(((String) info.get("firstname")).trim());
			profile.setLastName(((String) info.get("lastname")).trim());
			String phone = (String) info.get("phone");
			/**
			 * Trimming first digit of phone
			 */ 
			if(phone.length()>10){
				phone = phone.substring(1);
			}
			else if (phone.length() < 14) {
				phone = "";
			}
			profile.setPhone(phone);
			profile.setEmail(((String) info.get("email")).trim());
			profile.setPreference(((String) info.get("preference")).trim());
		}
		profile.setStatus(ProfileDTO.UserStatus.valueOf((String) info.get("status")));
		profile.setCreatedDate(info.get("createdate").toString());

		return profile;
	}

	private class AccountMapper implements RowMapper<AccountDTO> {
		@Override
		public AccountDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountDTO account = new AccountDTO();
			account.setName(rs.getString("CMNAME").trim());
			account.setContactName(rs.getString("CMCONT").trim());
			
			String phone = rs.getString("CMPHON").trim();
			
			if(phone.length()>10){
				phone = phone.substring(1);
			} 
			
			account.setPhone(phone);
			
			Address accountAddress = new Address();
			accountAddress.setStreetAddress(rs.getString("CMADR1").trim());
			accountAddress.setStreetAddress2(rs.getString("CMADR2").trim());
			accountAddress.setCity(rs.getString("CMCITY").trim());
			accountAddress.setState(rs.getString("CMST").trim());
			accountAddress.setZip(rs.getString("CMZIP").trim());
			accountAddress.setCountry(rs.getString("CMCC").trim());
			account.setAddress(accountAddress);
			return account;
		}	
	}

	@Override
	public AccountDTO getAccountInfo(String accountCode) throws ServiceException {
		AccountDTO accountDTO = null;
		String sql = "SELECT cmname, cmcont, cmadr1, cmadr2, cmcity, cmst, cmzip, cmcc, cmphon " +
				"FROM FBFILES.RAP001 AS master " +
				"INNER JOIN fbfiles.rap001a AS ext ON master.cmcust = ext.cmcust " +
				"WHERE master.cmcust= ?";
		try {
			List<AccountDTO> listQuery =  jdbcMyEstesServices.query(sql,  new Object[] {accountCode}, new AccountMapper());
			if (listQuery != null && listQuery.size() > 0) {
				accountDTO =  listQuery.get(0);
				accountDTO.setAccountNumber(accountCode);
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "getAccountInfo()", "** Error retrieving the account info.", e);
			throw new ServiceException("Account info could not be retrieved.");
		}
		return accountDTO;
	} //getAccountInfo
}
