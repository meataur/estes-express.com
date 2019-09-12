package com.estes.services.common.dao.impl;


import static com.estes.services.common.dao.iface.BaseDAO.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.dao.iface.ErrorDAO;
import com.estes.services.common.dao.iface.ValidationDAO;
import com.estes.services.common.exception.ValidationException;



@Repository("validationDAO")
public class ValidationDAOImpl implements ValidationDAO {
	
	@Autowired
	private JdbcTemplate jdbcCommonServices;
	@Autowired
	private ErrorDAO errorDAO;
	@Override
	public boolean validateProNumber(String proNumber) {
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcCommonServices);
		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("SP_VALIDATEPRO");
		sproc.addDeclaredParameter(new SqlParameter("OT", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlParameter("PRO", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlOutParameter("TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		
		
		Map<String, Object> params = new HashMap<>();
		String[] proNumbers = proNumber.split("-");
	
		params.put("OT", Integer.parseInt(proNumbers[0]));
		params.put("PRO", Integer.parseInt(proNumbers[1]));
		params.put("TYPE", "");
		params.put("ERROR","");
		
		Map<String, ?> outParms = sproc.execute(params);
		
		if (outParms != null) {
			String errorCode = (String) outParms.get("ERROR");
			
			if (ErrorDAO.isError(errorCode)) {
				throw new ValidationException(errorDAO.getServiceResponse(outParms.get("ERROR")));
			}
		} else {

			throw new ValidationException(ValidationException.DEFAULT_ERROR_CODE,ValidationException.DEFAULT_ERROR_MESSAGE);
		}

		
		return true;
	}
	
	private class ZipCityStateErrorMapper implements ResultSetExtractor<List<ServiceResponse>>{

		@Override
		public List<ServiceResponse> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			List<ServiceResponse> serviceResponseList = new ArrayList<>();
			
			while(rs.next()){
				String field        = rs.getString("FIELD").trim();
				String code         = rs.getString("CODE").trim();
				String description  = rs.getString("DESC").trim();
				
				ServiceResponse serviceResponse = new ServiceResponse();
				serviceResponse.setFieldName(field);
				serviceResponse.setErrorCode(code);
				serviceResponse.setMessage(description);
				
				serviceResponseList.add(serviceResponse);
			}
			
			return serviceResponseList;
		}

		
	}

	@Override
	public boolean validateCityStateZip(String city, String state, String zip) {
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcCommonServices);
		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("SP_PICKUP_VALIDATE_CITYSTATEZIP");
		sproc.addDeclaredParameter(new SqlParameter("ZIP", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlParameter("CITY", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlParameter("STATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new ZipCityStateErrorMapper()));
		Map<String, Object> params = new HashMap<>();
		
		params.put("ZIP", zip);
		params.put("CITY", city);
		params.put("STATE", state);
		params.put("ERROR","");
		
		Map<String, ?> outParms = sproc.execute(params);
		
		if (outParms != null) {
			
			if (!((String) outParms.get("ERROR")).equalsIgnoreCase("N")) {
				
				@SuppressWarnings("unchecked")
				List<ServiceResponse> errors = (List<ServiceResponse>) outParms.get("DATA"); 
				throw new ValidationException(errors);
				
			}
		} else {

			throw new ValidationException(ValidationException.DEFAULT_ERROR_CODE,ValidationException.DEFAULT_ERROR_MESSAGE);
		}

		return true;
	}

}
