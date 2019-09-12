/**
 * @author: Todd Allen
 *
 * Creation date: 02/28/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.estes.dto.common.rest.Pageable;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.RateHistoryDAO;
import com.estes.myestes.rating.dto.RateSearch;
import com.estes.myestes.rating.dto.RateSummary;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("rateHistoryDAO")
public class RateHistoryDAOImpl implements RateHistoryDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public RateHistoryDAOImpl()
	{
	} // Constructor

	/**
	 * Build rating history search where clause
	 * 
	 * @param srch {@link RateSearch} info
	 * @return SQL SELECT WHERE clause
	 */
	private String buildWhereClause(RateSearch srch) 
	{
		StringBuilder where = new StringBuilder("RTRIM(curusr) = '" + srch.getUser() + "'");
		
		/**
		 * Add filter for selection quotes only
		 */
		where.append(" AND SELFLG='Y' ");
		
		
		// Quote ID
		if (!StringUtils.isEmpty(srch.getQuoteID())) {
			where.append(" AND RTRIM(quotenum) = '" + srch.getQuoteID() + "'");
		}
		// From date
		if (srch.getFromDate() != null) {
			where.append(" AND DATE(timst) >= '" + Date.valueOf(srch.getFromDate()) + "'");
		}
		// To date
		if (srch.getToDate() != null) {
			where.append(" AND DATE(timst) <= '" + Date.valueOf(srch.getToDate()) + "'");
		}
		// Account code
		if (!StringUtils.isEmpty(srch.getAccountCode())) {
			where.append(" AND actnum = '" + srch.getAccountCode() + "'");
		}
		// Origin point
		if (!StringUtils.isEmpty(srch.getOrigin().getCity())) {
			where.append(" AND RTRIM(orgcity) = '" + srch.getOrigin().getCity() + "'");
		}
		if (!StringUtils.isEmpty(srch.getOrigin().getState())) {
			where.append(" AND RTRIM(orgst) = '" + srch.getOrigin().getState() + "'");
		}
		if (!StringUtils.isEmpty(srch.getOrigin().getZip())) {
			where.append(" AND RTRIM(orgzip) = '" + srch.getOrigin().getZip() + "'");
		}
		// Destination point
		if (!StringUtils.isEmpty(srch.getDest().getCity())) {
			where.append(" AND RTRIM(descity) = '" + srch.getDest().getCity() + "'");
		}
		if (!StringUtils.isEmpty(srch.getDest().getState())) {
			where.append(" AND RTRIM(desst) = '" + srch.getDest().getState() + "'");
		}
		if (!StringUtils.isEmpty(srch.getDest().getZip())) {
			where.append(" AND RTRIM(deszip) = '" + srch.getDest().getZip() + "'");
		}
		// Service level
		if (srch.getServiceLevel() > 0) {
			where.append(" AND gsdsvc = " + srch.getServiceLevel());
		}
		// Estimated charges
		if (srch.getMinCharges() > 0) {
			where.append(" AND netchg >= " + srch.getMinCharges());
		}
		if (srch.getMaxCharges() > 0) {
			where.append(" AND netchg <= " + srch.getMaxCharges());
		}

		return where.toString();
	} // buildWhereClause

	@Override
	public int getSearchTotal(RateSearch search)
	{
		String sql = "SELECT COUNT(*) " +
				"FROM " + DATA_SCHEMA + ".gsc00v001 " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p590 " +
				"ON svclvl = gsdsst " +
				"WHERE " + buildWhereClause(search) + " ";
		

		int rows = jdbcMyEstes.queryForObject(sql, Integer.class);
		
		if(rows > MAX_HISTORY_ROWS){
			return MAX_HISTORY_ROWS;
		}
		return rows;
	} // getSearchTotal

	@Override
	public List<RateSummary> getAllServiceLevels(int ref) throws RatingException
	{

		String sql = "SELECT quotenum, frmfile,timst, orgcity, orgst, orgzip," +
				"descity, desst, deszip, gsqsvc, svclvl, netchg,gsqdat,gsqtim " +
				"FROM " + DATA_SCHEMA + ".gsc00v001 " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p100 ON quotenum=gsrid " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p110 ON gsrid=gsqid " +
				"WHERE gsrref = ? AND gsqdsp = 'S'";

		try {
			return jdbcMyEstes.query(sql, new Object[] { ref }, new QuoteHistoryMapper());
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RateHistoryDAOImpl.class, "getAllServiceLevels()", "** No quotes found for ref# " + ref + ".", e);
			throw new RatingException("No quotes found for quote reference number.");
		}
	} // getAllServiceLevels

	private final class QuoteHistoryMapper implements RowMapper<RateSummary>
	{
		@Override
		public RateSummary mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			RateSummary row = new RateSummary();
			row.setQuoteID(rs.getString("quotenum"));
			row.setSource(rs.getString("frmfile"));
			row.setQuoteDate(rs.getDate("timst").toLocalDate());
			row.getOrigin().setCity(rs.getString("orgcity"));
			row.getOrigin().setState(rs.getString("orgst"));
			row.getOrigin().setZip(rs.getString("orgzip"));
			row.getDest().setCity(rs.getString("descity"));
			row.getDest().setState(rs.getString("desst"));
			row.getDest().setZip(rs.getString("deszip"));
			row.setServiceLevelId(rs.getInt("gsqsvc"));
			row.setServiceLevel(rs.getString("svclvl"));
			row.setEstCharges(rs.getDouble("netchg"));
			row.setEstCharges(rs.getDouble("netchg"));
			row.setDeliveryDate(rs.getDate("gsqdat").toLocalDate());
			row.setDeliveryTime(rs.getTime("gsqtim").toLocalTime());
			return row;
		}
	} // QuoteHistoryMapper

	@Override
	public List<RateSummary> searchQuoteHistory(Pageable pageable, RateSearch search) throws RatingException
	{
		String sql = "SELECT * FROM ( SELECT * " +
				"FROM " + DATA_SCHEMA + ".gsc00v001 " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p590 " +
				"ON svclvl = gsdsst " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p110 ON quotenum=gsqid " +
				"WHERE " + buildWhereClause(search)+" order by TIMST desc fetch first "+MAX_HISTORY_ROWS+" rows only) as t ";

		int offset = (pageable.getPage() - 1)  * pageable.getSize();
		if(pageable != null && !pageable.getSort().equalsIgnoreCase("QUOTENUM")){

				sql += " ORDER BY " + pageable.getSort() + " " + pageable.getOrder();

				
				sql += " LIMIT " + pageable.getSize() + " OFFSET " + offset;
				
				try {
					return jdbcMyEstes.query(sql, new QuoteHistoryMapper());
				}
				catch (Exception e) {
					ESTESLogger.log(ESTESLogger.ERROR, RateHistoryDAOImpl.class, "searchQuoteHistory()", "** Error searching quote history.", e);
					throw new RatingException("An error occurred searching quote history.");
				}
		}else{
			
			try {
				
				List<RateSummary> ratingSummaryList = jdbcMyEstes.query(sql, new QuoteHistoryMapper());
				
				
				if(pageable.getOrder().equals(Pageable.Order.desc)){
					ratingSummaryList.sort(Comparator.comparing(RateSummary::getQuoteID).reversed());
				}else{
					ratingSummaryList.sort(Comparator.comparing(RateSummary::getQuoteID));
				}
				
				
				
				int endingIndex = offset+pageable.getSize(); 
				
				if(endingIndex > MAX_HISTORY_ROWS){
					throw new RatingException("No results were found.");
				}
				
				return ratingSummaryList.subList(offset, endingIndex);				
			}
			catch (Exception e) {
				ESTESLogger.log(ESTESLogger.ERROR, RateHistoryDAOImpl.class, "searchQuoteHistory()", "** Error searching quote history.", e);
				throw new RatingException("An error occurred searching quote history.");
			}
		}

		
	} // searchQuoteHistory

	@Override
	public List<String> getAppsByRef(int ref) {
		List<String> apps = new ArrayList<>();
		
		String sql = "SELECT GSRLTLPK, GSRVTLPK, GSRTCPK, GSRAPP FROM " + DATA_SCHEMA + ".GSC00P100 WHERE GSRREF = ? LIMIT 1";
		
		Map<String, Object> appList = jdbcMyEstes.queryForMap(sql,new Object[]{ref});
		
		if(((String) appList.get("GSRLTLPK")).equalsIgnoreCase("Y")){
			apps.add("LTL");
		}
		
		if(((String) appList.get("GSRVTLPK")).equalsIgnoreCase("Y")){
			apps.add("VTL");
		}
		
		if(((String) appList.get("GSRTCPK")).equalsIgnoreCase("Y")){
			apps.add("TC");
		}
		
		if(apps.isEmpty()){
			apps.add((String) appList.get("GSRAPP"));
		}
		
		return apps;
	}
}
