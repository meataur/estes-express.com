package com.estes.services.common.dao.impl;



import static com.estes.services.common.dao.iface.BaseDAO.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.services.common.dao.iface.ErrorDAO;
import com.estes.services.common.exception.ValidationException;

@Repository ("errorDAO")
public class ErrorDAOImpl implements ErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcCommonServices;

	public String getErrorMessage(String errCode)
	{
		String sql = "SELECT LTRIM(ndcomm) " +
				"FROM " + ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";
		
		
		try {
			String errorMessage=  jdbcCommonServices.queryForObject(sql, new Object[] {errCode}, String.class);
		    return errorMessage.trim();
		}
		catch (Exception e) {
			throw new ValidationException(ValidationException.DEFAULT_ERROR_CODE, ValidationException.DEFAULT_ERROR_MESSAGE);
		}
	}

	@Override
	public ServiceResponse getServiceResponse(Object errorCode) {
		String message = getErrorMessage((String) errorCode);
		
		return new ServiceResponse((String) errorCode, message);
	} 
}