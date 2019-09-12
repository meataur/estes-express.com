package com.estes.myestes.fuelsurcharge.dao.impl;

import java.math.BigDecimal;
import java.sql.Types;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.estes.myestes.fuelsurcharge.dao.iface.HeaderDao;
import com.estes.myestes.fuelsurcharge.dto.Header;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;
import com.estes.myestes.fuelsurcharge.utils.DateUtils;
import com.estes.framework.logger.ESTESLogger;

/**
 * HeaderDao Data Access Object implementation
 */
@Repository
public class HeaderDaoImpl implements HeaderDao
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	private static final String STORED_PROCEDURE = "RSQ291A1";

	/**
	 * Get Fuel Surcharge header information (National Average and Effective Date)
	 */
	public Header getHeader() throws FuelSurchargeException
	{
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withSchemaName(PROGRAM_SCHEMA);
		call.withProcedureName(STORED_PROCEDURE);
		call.addDeclaredParameter(new SqlInOutParameter("CURRENT_DATE", Types.NUMERIC));
		call.addDeclaredParameter(new SqlInOutParameter("EFFECTIVE_DATE", Types.NUMERIC));
		call.addDeclaredParameter(new SqlInOutParameter("NATL_AVG", Types.DECIMAL));
		call.addDeclaredParameter(new SqlInOutParameter("ERROR", Types.CHAR));

        Map<String, Object> input = new HashMap<String, Object>();
        input.put("CURRENT_DATE", DateUtils.getCurrentDate());
        input.put("EFFECTIVE_DATE", 0.00000); // default input value
        input.put("NATL_AVG", 0.00000); // default input value
        input.put("ERROR", "");

        try {
            Map<String, Object> output = call.execute(input);
            if (output != null) {
            	Header header = new Header();
            	header.setNationalAverage((BigDecimal) output.get("NATL_AVG"));
            	header.setEffectiveDate((BigDecimal) output.get("EFFECTIVE_DATE"));
            	return header;
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, HeaderDaoImpl.class, "getHeader()", "** Error occurred retrieving national average and effective date.");
        		throw new FuelSurchargeException("Retrieving National Average and Effective Date failed.");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, HeaderDaoImpl.class, "getHeader()", "** Error occurred retrieving national average and effective date.", e);
    		throw new FuelSurchargeException("Retrieving National Average and Effective Date failed.");
        }
	} // getHeader
}
