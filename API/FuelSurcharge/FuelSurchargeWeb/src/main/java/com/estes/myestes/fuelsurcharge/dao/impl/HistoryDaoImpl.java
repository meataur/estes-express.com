package com.estes.myestes.fuelsurcharge.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.fuelsurcharge.dao.iface.HistoryDao;
import com.estes.myestes.fuelsurcharge.dto.History;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;
import com.estes.myestes.fuelsurcharge.utils.AppConstants;
import com.estes.myestes.fuelsurcharge.utils.DateUtils;
/**
 * HistoryDao Data Access Object implementation
 */
@Repository
public class HistoryDaoImpl implements HistoryDao
{
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private static final String TL_TABLE_NUMBER = "8888888";
	private static final String LTL_TABLE_NUMBER = "9999999";
	private static final String STORED_PROCEDURE = "RSQ291T2";

	private static final String SELECT_EFFECTIVE_DATES_AND_NATIONAL_AVERAGES =
		"SELECT SFADAT,SFAPRC FROM " + DATA_SCHEMA + ".RSP291A WHERE SFADAT>=? ORDER BY SFADAT DESC";

	/**
	 * Retrieve Fuel Surcharge history list
	 */
	public List<History> getHistoryList() throws FuelSurchargeException
	{
		List<History> list = null;
		int yearsAgo = 1;
		try {
			yearsAgo = Integer.parseInt(ESTESConfigUtil.getProperty(AppConstants.HISTORY, "years"));
		} catch (NumberFormatException e) {
			ESTESLogger.log(ESTESLogger.ERROR, HistoryDaoImpl.class, "getHistoryList()", "An error occurred retrieving 'yearsAgo' value.", e);
		}
		Object[] parameters = new Object[1];
		parameters[0] = DateUtils.getDateFromYearsAgo(yearsAgo);
		try {
			list = jdbcTemplate.query(SELECT_EFFECTIVE_DATES_AND_NATIONAL_AVERAGES, parameters, new HistoryMapper());
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, HistoryDaoImpl.class, "getHistoryList()", "An error occurred retrieving history list.", e);
			throw new FuelSurchargeException("Retrieving fuel surcharge history list failed.");
		}
		return list;
	} // getHistoryList

	/**
	 * Utilized to retrieve fuel surcharge percentage based on effective date, table number, and national average
	 * @param tableNumber
	 * @param effectiveDate
	 * @param nationalAverage
	 * @return
	 * @throws FuelSurchargeException
	 */
	private BigDecimal getPercentage(String tableNumber, String effectiveDate, String nationalAverage) throws FuelSurchargeException
	{
        Map<String, Object> input = new HashMap<String, Object>();
        input.put("TABLE_NUM", tableNumber);
        input.put("EFFECTIVE_DATE", effectiveDate);
        input.put("COST", nationalAverage);
        input.put("PERCENTAGE", 0.00000); // default input value
        input.put("ERROR", "");
        
		SimpleJdbcCall call = new SimpleJdbcCall(jdbcTemplate);
		call.withSchemaName(PROGRAM_SCHEMA);
		call.withProcedureName(STORED_PROCEDURE);
		call.withoutProcedureColumnMetaDataAccess();
		call.addDeclaredParameter(new SqlParameter("TABLE_NUM", Types.DECIMAL));
		call.addDeclaredParameter(new SqlParameter("EFFECTIVE_DATE", Types.NUMERIC));
		call.addDeclaredParameter(new SqlParameter("COST", Types.DECIMAL));
		call.addDeclaredParameter(new SqlInOutParameter("PERCENTAGE", Types.NUMERIC));
		call.addDeclaredParameter(new SqlInOutParameter("ERROR", Types.CHAR));

        try {
            Map<String, Object> output = call.execute(input);
            if (output != null) { // percentage output format ex: 0.24800 - change format to 24.80
            	DecimalFormat decimalFormat = new DecimalFormat();
            	decimalFormat.setMaximumFractionDigits(2);        
            	decimalFormat.setMinimumFractionDigits(2);
            	BigDecimal percentage = (BigDecimal) output.get("PERCENTAGE");
    	        return new BigDecimal(decimalFormat.format(percentage.multiply(new BigDecimal(100))));
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, HistoryDaoImpl.class, "getPercentage()", "** Error occurred retrieving surcharge percentage.");
        		throw new FuelSurchargeException("Retrieving surcharge percentage failed.");
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, HistoryDaoImpl.class, "getPercentage()", "** Error occurred retrieving surcharge percentage.", e);
			throw new FuelSurchargeException(e);
        }
	} // getPercentage

	/**
	 * Custom RowMapper inner class for History
	 */
	private final class HistoryMapper implements RowMapper<History>
	{
		@Override
		public History mapRow(ResultSet rs, int rowNum) throws SQLException
		{
			History history = new History();
			history.setEffectiveDate(rs.getString("SFADAT").trim());
			history.setNationalAverage(rs.getString("SFAPRC").trim());
			try { // setTlSurcarge utilizing getPercentage method
				history.setTlSurcharge(getPercentage(TL_TABLE_NUMBER, history.getEffectiveDate(), history.getNationalAverage()));
			} catch (FuelSurchargeException e) {
				ESTESLogger.log(ESTESLogger.ERROR, HistoryDaoImpl.class, "mapRow()", "An error occurred setting tlSurcharge.", e);
			}
			try { // setLtlSurcharge utilizing getPercentage method
				history.setLtlSurcharge(getPercentage(LTL_TABLE_NUMBER, history.getEffectiveDate(), history.getNationalAverage()));
			} catch (FuelSurchargeException e) {
				ESTESLogger.log(ESTESLogger.ERROR, HistoryDaoImpl.class, "mapRow()", "An error occurred setting ltlSurcharge.", e);
			}
			return history;
		} // mapRow
	}
}
