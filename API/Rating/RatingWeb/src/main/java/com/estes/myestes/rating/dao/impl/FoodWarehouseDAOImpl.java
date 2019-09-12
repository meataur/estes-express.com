/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
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
import com.estes.myestes.rating.dao.iface.FoodWarehouseDAO;
import com.estes.myestes.rating.dto.FoodWarehouse;
import com.estes.myestes.rating.exception.RatingException;

@Repository ("foodWarehouseDAO")
public class FoodWarehouseDAOImpl implements FoodWarehouseDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public FoodWarehouseDAOImpl()
	{
	} // Constructor

	@Override
	public List<FoodWarehouse> getFoodWarehouses() throws RatingException
	{
		String sql = "SELECT * FROM " + DATA_SCHEMA + ".gsc40p510";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql);
			ArrayList<FoodWarehouse> warehouses = new ArrayList<FoodWarehouse>();
			for (Map<String,Object> data : dataList) {
				FoodWarehouse center = new FoodWarehouse();
				center.setId(((BigDecimal) data.get("g4lfdid")).intValue());
				center.setName((String) data.get("g4lfdnm"));
				warehouses.add(center);
			}
			return warehouses;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, FoodWarehouseDAOImpl.class, "getFoodWarehouses()", "** No food warehouses found.", e);
			throw new RatingException("No food warehouses found.");
		}
	} // getFoodWarehouses

	@Override
	public String getName(int id)
	{
		String sql = "SELECT g4lfdnm FROM " + DATA_SCHEMA + ".gsc40p510 " +
				"WHERE g4lfdid = ?";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] { id }, String.class);
		}
		catch (Exception e) {
			return "";
		}
	} // getName
}
