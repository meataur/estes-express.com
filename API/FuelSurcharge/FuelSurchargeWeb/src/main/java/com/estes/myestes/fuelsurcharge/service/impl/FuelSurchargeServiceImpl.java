package com.estes.myestes.fuelsurcharge.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import com.estes.myestes.fuelsurcharge.dao.iface.HeaderDao;
import com.estes.myestes.fuelsurcharge.dao.iface.HistoryDao;
import com.estes.myestes.fuelsurcharge.dto.Header;
import com.estes.myestes.fuelsurcharge.dto.History;
import com.estes.myestes.fuelsurcharge.exception.FuelSurchargeException;
import com.estes.myestes.fuelsurcharge.service.iface.FuelSurchargeService;

/**
 * Fuel Surcharge service implementation
 */
@Service
@Scope("prototype")
public class FuelSurchargeServiceImpl implements FuelSurchargeService
{
	@Autowired
	private HeaderDao headerDao;
	
	@Autowired
	private HistoryDao historyDao;
	
	@Override
	public Header getHeader() throws FuelSurchargeException {
		return headerDao.getHeader();
	}

	@Override
	public List<History> getHistoryList() throws FuelSurchargeException {
		return historyDao.getHistoryList();
	}
}
