package com.estes.myestes.fuelsurcharge.dao.iface;

import java.util.List;
import com.estes.myestes.fuelsurcharge.dto.History;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;

/**
 * HistoryDao Data Access Object interface
 */
public interface HistoryDao extends BaseDao {
	
	/**
	 * Retrieve list of Fuel Surcharge history for Truckload and Less-than Truckload
	 * @return
	 * @throws FuelSurchargeException
	 */
	public List<History> getHistoryList() throws FuelSurchargeException;
	
}