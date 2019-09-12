package com.estes.myestes.claims.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.claims.dao.iface.ValidateDAO;
import com.estes.myestes.claims.exception.ClaimsException;

@Repository ("validateDAO")
public class ValidateDAOImpl implements ValidateDAO {

	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public Boolean validateProNumber(String number) {
		String ot;
		String pro;

		int dash = number.indexOf("-");
		if(dash == -1){
			if(number.length() < 10) return false;
			ot = number.substring(0, 3);
			pro = number.substring(3);
		}
		else{
			if(number.length() < 3) return false;
			if(dash == number.length() -1) return false;
			if(dash == 0) return false;
			ot = number.substring(0,dash);
			pro = number.substring(dash+1);
		}

		if(ot.length() > 3 || pro.length() > 7){
			return false;
		}
		try{
			Integer.parseInt(ot);
			Integer.parseInt(pro);
		}catch(NumberFormatException e){
			return false;
		}

		String sql = "select count(AHCLM) FROM FBFILES.CLP001 WHERE AHOT = ? AND AHPRO = ?";
		Integer count = jdbcMyEstes.queryForObject(sql, new Object[] {ot, pro}, Integer.class);

		if(count > 0) return true;
		return false;
	}

	@Override
	public Boolean validateReferenceNumber(String account, String number) {
		String sql = "select count(AHCLM) FROM FBFILES.CLP001 WHERE AHCREF = ? AND AHCUST = ?";
		Integer count = jdbcMyEstes.queryForObject(sql, new Object[] {number.toUpperCase(), account}, Integer.class);

		if(count > 0) return true;
		return false;
	}

	@Override
	public Boolean validateClaimNumber(String account, String number) {
		String sql = "select count(AHCUST) FROM FBFILES.CLP001 WHERE AHCLM = ? AND AHCUST = ?";
		Integer count = jdbcMyEstes.queryForObject(sql, new Object[] {number, account}, Integer.class);

		if(count > 0) return true;
		return false;
	}

	@Override
	public Boolean validateAccount(String parentAccount, String subAccount, String accountType) throws ClaimsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName(ALT_PGM_SCHEMA);
		sproc.withProcedureName(SP_ISUBOF);
		sproc.addDeclaredParameter(new SqlInOutParameter("GROUP_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("ACCOUNT_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("ACCOUNT_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("ERROR", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("GROUP_NUMBER", parentAccount);
		inParams.put("ACCOUNT_NUMBER", subAccount);
		inParams.put("ACCOUNT_TYPE", accountType);
		inParams.put("ERROR", "");

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String valid = (String) outParms.get("ERROR");
	    		return "Y".equalsIgnoreCase(valid);
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, ValidateDAOImpl.class, "validateAccount()", "An error occurred checking if sub account of");
				return false;
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ValidateDAOImpl.class, "validateAccount()", "An error occurred checking if sub account of", e);
			return false;
		}
	}


}