package com.estesexpress.currency.convert.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estesexpress.currency.convert.controller.RateController;
import com.estesexpress.currency.convert.dao.iface.RatesDAO;
import com.estesexpress.currency.convert.dto.RateDTO;
import com.estesexpress.currency.convert.service.exception.CurrencyConversionException;

@Repository ("ratesDAO")
public class RatesDAOImpl implements RatesDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplateExla;

	private static final class RateMapper implements RowMapper<RateDTO>
	{
		@Override
		public RateDTO mapRow(ResultSet rs, int rowNm) throws SQLException {
			RateDTO elem = new RateDTO();
			elem.setCountry(rs.getString("CXCURCD"));
			elem.setExchangeDate(rs.getString("CXDATE"));
			elem.setDayOfWeek(rs.getInt("CXDAY"));
			elem.setRate(rs.getDouble("CXCRATE"));
			elem.setRateUS(rs.getDouble("CXURATE"));
			return elem;
		}
	} // RateMapper

	@Override
	public RateDTO[] getRates(String currency) throws CurrencyConversionException
	{
		String sql = "SELECT cxcurcd, CHAR(cxexdt, USA) AS cxdate, DAYOFWEEK(cxexdt) AS cxday, cxcrate, cxurate " +
				"FROM fbfiles.cex10p100 " +
				"WHERE cxexdt > CURRENT_DATE - 1 YEAR and cxcurcd = UPPER('" + currency + "') " +
				"AND DAYOFWEEK(cxexdt) BETWEEN 2 AND 6 " +
				"ORDER BY cxexdt DESC";
		List<RateDTO> listQuery =  jdbcTemplateExla.query(sql, new RateMapper());
		if (listQuery != null && listQuery.size() > 0) {
			return listQuery.toArray( new RateDTO[listQuery.size()] );
		}

        ESTESLogger.log(ESTESLogger.ERROR, RateController.class, "getRates()", "** Error getting currency rates");
		throw new CurrencyConversionException("Currency rates could not be retrieved.");
	} // getRates
}
