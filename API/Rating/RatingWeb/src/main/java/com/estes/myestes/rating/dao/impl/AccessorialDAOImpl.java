/**
 * @author: Todd Allen
 *
 * Creation date: 02/19/2019
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
import com.estes.myestes.rating.dao.iface.AccessorialDAO;
import com.estes.myestes.rating.dto.Accessorial;
import com.estes.myestes.rating.dto.AccessorialPricing;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("accessorialDAO")
public class AccessorialDAOImpl implements AccessorialDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public AccessorialDAOImpl()
	{
	} // Constructor

	@Override
	public List<Accessorial> getAccessorials() throws RatingException
	{
		String sql = "SELECT a1.code, " +
				"CASE WHEN app_id = 1 THEN 'LTL' WHEN app_id = 4 THEN 'TC' WHEN app_id = 5 then 'VTL' END AS app_id, " +
				"on_scr, a2.description " +
				"FROM " + DATA_SCHEMA + ".acces00011 a1, " + DATA_SCHEMA + ".acces00010 a2 " +
				"WHERE a1.CODE= a2.ACS10CDE  and (app_id = 1 or app_id = 4 or  app_id = 5 ) " +
				"ORDER BY on_scr, a2.description";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql);
			ArrayList<Accessorial> services = new ArrayList<Accessorial>();
			for (Map<String,Object> data : dataList) {
				Accessorial svc = new Accessorial();
				svc.setCode((String) data.get("code"));
				svc.setAppID((String) data.get("app_id"));
				svc.setDisplay(((String) data.get("on_scr")).equals("Y"));
				svc.setDescription((String) data.get("description"));
				services.add(svc);
			}
			return services;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AccessorialDAOImpl.class, "getAccessorials()", "** No accessorials found.", e);
			throw new RatingException("No accessorial data found.");
		}
	} // getAccessorials

	@Override
	public List<AccessorialPricing> getQuoteAccessorials(String id) throws RatingException
	{
		String sql = "SELECT " +
				"gsqsvc, gsaacc, gsatxt, gsacst " + 
				"FROM " + DATA_SCHEMA + ".gsc00p120 " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p110 " +
				"ON gsaid = gsqid " +
				"WHERE gsaid = ? " +
				"ORDER BY gsaseq";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql, new Object[] { id });
			ArrayList<AccessorialPricing> prices = new ArrayList<AccessorialPricing>();
			for (Map<String,Object> data : dataList) {
				// Suppress accessorial charge for service levels 92, 93 and 94
				if (!((String) data.get("gsaacc")).matches("92|93|94")) {
					AccessorialPricing acc = new AccessorialPricing();
					acc.getAccessorial().setCode((String) data.get("gsaacc"));
					acc.getAccessorial().setDescription((String) data.get("gsatxt"));
					acc.setCharge(((BigDecimal) data.get("gsqsvc")).doubleValue());
					prices.add(acc);
				}
			}
			return prices;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AccessorialDAOImpl.class, "getQuoteAccessorials()", "** Error occurred getting accessorials for quote ID " + id, e);
			throw new RatingException("Error getting accessorials for quote ID " + id);
		}
	} // getQuoteAccessorials

	public void stageAccessorials(String id, List<Accessorial> accessorials) throws RatingException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + ".gsc00p120 " +
				"(gsaid, gsaseq, gsaacc, gsashw, gsaorg) " +
				"VALUES(?, ?, UPPER(?), 'Y', 'C')";

		int seq = 1;
		try {
			for (Accessorial acc : accessorials) {
				Object[] values = {id, seq, acc.getCode()};
				jdbcMyEstes.update(sql, values);
				seq++;
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AccessorialDAOImpl.class, "stageAccessorials()", "** Error staging accessorials for quote ID " + id, e);
			throw new RatingException("Error staging accessorial data.");
		}
	} // stageAccessorials
}
