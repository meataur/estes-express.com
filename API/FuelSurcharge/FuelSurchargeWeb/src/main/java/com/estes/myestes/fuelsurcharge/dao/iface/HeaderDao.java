package com.estes.myestes.fuelsurcharge.dao.iface;

import com.estes.myestes.fuelsurcharge.dto.Header;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;

/**
 * HeaderDao Data Access Object interface
 */
public interface HeaderDao extends BaseDao {
	
	/**
	 * Look up Fuel Surcharge information (National Average and Effective Date)
	 * @return
	 * @throws FuelSurchargeException
	 */
	public Header getHeader() throws FuelSurchargeException;
	
}