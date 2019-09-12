package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;
import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_MESSAGE;
import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.ALT_PGM_SCHEMA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.web.dao.iface.BolErrorDAO;
@Repository ("bolErrorDAO")
public class BolErrorDAOImpl implements BolErrorDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public String getErrorMessage(String errCode)
	{
		String sql = "SELECT LTRIM(ndcomm) " +
				"FROM " + ALT_PGM_SCHEMA + ".ndp950 " +
				"WHERE nder = ? ";
		
		
		try {
			String errorMessage=  jdbcTemplate.queryForObject(sql, new Object[] {errCode}, String.class);
		    return errorMessage.trim();
		}
		catch (Exception e) {
			throw new BolException(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE);
		}
	}

	@Override
	public ServiceResponse getServiceResponse(Object errorCode) {
		String message = getErrorMessage((String) errorCode);
		
		return new ServiceResponse((String) errorCode, message);
	} 
}