package com.estes.myestes.fuelsurcharge.service.iface;

import java.util.List;
import com.estes.myestes.fuelsurcharge.dto.Header;
import com.estes.myestes.fuelsurcharge.dto.History;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;

/**
 * Fuel Surcharge service interface
 */
public interface FuelSurchargeService {
	
	public Header getHeader() throws FuelSurchargeException;
	
	public List<History> getHistoryList() throws FuelSurchargeException;
	
}