/**
 * @author: Todd Allen
 *
 * Creation date: 03/29/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.LegacyRatingDAO;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.utils.RatingConstants;

@Repository ("leagyRatingDAO")
public class LegacyRatingDAOImpl implements LegacyRatingDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public RateQuote getQuoteData(int quoteNum) throws RatingException
	{
		String sql = "SELECT * " +
				"FROM " + ALT_PGM_SCHEMA + ".qup100 " +
				"WHERE qcq# = ?";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] { quoteNum }, new QuoteMapper());
		}
		catch (EmptyResultDataAccessException erde) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "getQuoteData()", "** Quote ID " + quoteNum + " not found.");
			return null;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "getQuoteData()", "** An error occurred getting quote details.", e);
    		throw new RatingException("Error getting quote details.");
		}
	} // getQuoteData

	private final class QuoteMapper implements RowMapper<RateQuote>
	{
		@Override
		public RateQuote mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			RateQuote row = new RateQuote();
			row.setQuoteID(rs.getString("qcq#"));
			row.setAccountCode(rs.getString("qcact#"));
			row.setAccountName(rs.getString("qccnam"));
			row.setContactName(rs.getString("qctnam"));
			row.setContactEmail(rs.getString("qcemal"));
			row.setRole(rs.getString("qcdwp"));
			row.setTerms(rs.getString("qcdtrm"));
			//row.setLane(rs.getInt("gsqlan"));
			row.getOrigin().setCountry("");
			row.getOrigin().setCity(rs.getString("qcfcty"));
			row.getOrigin().setState(rs.getString("qcfst"));
			row.getOrigin().setZip(rs.getString("qcfrom"));
			row.getDest().setCountry("");
			row.getDest().setCity(rs.getString("qctcty"));
			row.getDest().setState(rs.getString("qctst"));
			row.getDest().setZip(rs.getString("qcto"));
			row.setTransitMessage("");

			row.setComments(rs.getString("qccomm"));
			row.getPricing().setTotalDiscount(rs.getDouble("qcdis$"));
			row.getPricing().setTotalPrice(rs.getDouble("qcftc"));
			row.getPricing().setDisplay(RatingConstants.SHOW_PRICE);

			return row;
		}
	}
}
