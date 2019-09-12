/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.myestes.rating.dto.FoodWarehouse;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for food warehouses
 */
public interface FoodWarehouseDAO extends BaseDAO
{
	/**
	 * Get food warehouses
	 * 
	 * @return List of {@link FoodWarehouse}
	 */
	public List<FoodWarehouse> getFoodWarehouses() throws RatingException;

	/**
	 * Get food warehouse name
	 * 
	 * @param id Food warehouse ID
	 * @return Food warehouse name
	 */
	public String getName(int id);
}
