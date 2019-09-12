/**
 * @author: Todd Allen
 *
 * Creation date: 02/18/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.CommodityDAO;
import com.estes.myestes.rating.dto.Commodity;
import com.estes.myestes.rating.dto.CommodityPricing;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("commodityDAO")
public class CommodityDAOImpl implements CommodityDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public CommodityDAOImpl()
	{
	} // Constructor

	@Override
	public List<CommodityPricing> getQuoteCommodities(String id) throws RatingException
	{
		String sql = "SELECT " +
				"gsccls, gscpcs, tbdak1, gscpcwt, gscwgt, gsclen, " + 
				"gscwid, gschgt, gscdesc, gscrat, gsccst, gscdwgt, gscdcst " +
				"FROM " + DATA_SCHEMA + ".gsc00p130 " +
				"LEFT OUTER JOIN " + DATA_SCHEMA + ".scp002 " +
				"ON gscpctp = tbdad2 AND tbdkey = 'FDPKGC' " +
				"WHERE gscid = ? " +
				"ORDER BY gscid, gscseq";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql, new Object[] { id });
			ArrayList<CommodityPricing> prices = new ArrayList<CommodityPricing>();
			for (Map<String,Object> data : dataList) {
				CommodityPricing comm = new CommodityPricing();
				comm.getCommodity().setShipClass(((BigDecimal) data.get("gsccls")).doubleValue());
				comm.getCommodity().setWeight(((BigDecimal) data.get("gscwgt")).intValue());
				comm.getCommodity().setPieces(((BigDecimal) data.get("gscpcs")).intValue());
				comm.getCommodity().setPieceType((String) data.get("tbdak1"));
				comm.getCommodity().getDimensions().setLength(((BigDecimal) data.get("gsclen")).intValue());
				comm.getCommodity().getDimensions().setWidth(((BigDecimal) data.get("gscwid")).intValue());
				comm.getCommodity().getDimensions().setHeight(((BigDecimal) data.get("gschgt")).intValue());
				comm.getCommodity().setDescription((String) data.get("gscdesc"));
				comm.setRate(((BigDecimal) data.get("gscrat")).doubleValue());
				comm.setCharge(((BigDecimal) data.get("gsccst")).doubleValue());
				comm.setDeficitRate(false);
				prices.add(comm);
				// Check for deficit weight
				if (((BigDecimal) data.get("gscdwgt")).intValue() > 0) {
					CommodityPricing comm2 = new CommodityPricing();
					comm2.getCommodity().setWeight(((BigDecimal) data.get("gscdwgt")).intValue());
					comm2.getCommodity().setDescription("Added weight to provide next lower rate");
					comm2.setDeficitRate(true);
					comm2.setRate(((BigDecimal) data.get("gscrat")).doubleValue());
					comm2.setCharge(((BigDecimal) data.get("gscdcst")).doubleValue());
					prices.add(comm2);
				}
			}
			return prices;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityDAOImpl.class, "getQuoteCommodities()", "** No commodities found for quote ID " + id, e);
			throw new RatingException("No commodities found for quote.");
		}
	} // getQuoteCommodities

	@Override
	public void stageCommodities(String id, List<Commodity> commodities) throws RatingException
	{
		/**
		 * Insert rate quote request commodity data
		 * 
		 * The goods type description (NOT code) is in the GSC00P130 table so we must get it from the SCP002 table using the code.
		 * Comparing a selection that returns nothing to NULL does not work so a UNION must be added.
		 * On a hit only 1 row must be returned and it must be sorted descending so the blank row is not used.  
		 */
		String sql = "INSERT INTO " + DATA_SCHEMA + ".gsc00p130 " +
				"(gscid, gscseq, gsccls, gscpcs, gscpctp, gscwgt, gsclen, gscwid, " +
				"gschgt, gscdesc) " +
				"VALUES(?, ?, ?, ?, " +
				"(SELECT tbdad2 FROM " + DATA_SCHEMA + ".scp002 WHERE tbdkey = 'FDPKGC' AND tbdak1 = ? " +
				"UNION SELECT '' AS tbdad2 FROM " + DATA_SCHEMA + ".scp002 ORDER BY tbdad2 DESC FETCH FIRST 1 ROWS ONLY), " + 
				"?, ?, ?, ?, ?)";

		int seq = 1;
		try {
			for (Commodity comm : commodities) {
				Object[] values = {id, seq, comm.getShipClass(), comm.getPieces(), comm.getPieceType(), comm.getWeight(),
						comm.getDimensions().getLength(), comm.getDimensions().getWidth(), comm.getDimensions().getHeight(), comm.getDescription()};
				jdbcMyEstes.update(sql, values);
				seq++;
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityDAOImpl.class, "stageCommodities()", "** Error staging commodities for quote ID " + id, e);
			throw new RatingException("Error staging commodity data.");
		}
	} // stageCommodities
}
