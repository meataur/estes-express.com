/**
 * @author: Lakshman K
 *
 * Creation date: 11/12/2018
 */

package com.estes.myestes.commoditylibrary.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.commoditylibrary.dao.iface.CommodityLibraryDAO;
import com.estes.myestes.commoditylibrary.dao.iface.ErrorDAO;
import com.estes.myestes.commoditylibrary.dto.Commodity;
import com.estes.myestes.commoditylibrary.exception.CommodityLibraryException;
import com.estes.myestes.commoditylibrary.service.iface.CommodityLibraryService;
import com.estes.myestes.commoditylibrary.utils.CommodityLibraryConstant;
import com.estes.myestes.commoditylibrary.utils.DB2Timestamp;

@Service("commodityLibraryService")
@Scope("prototype")
public class CommodityLibraryServiceImpl implements CommodityLibraryService, CommodityLibraryConstant
{
	@Autowired
	private CommodityLibraryDAO commodityLibraryDAO;
	@Autowired
	private ErrorDAO errorDAO;

	@Override
	public Commodity getCommodityById(String commodityId, String user) throws CommodityLibraryException {
		return commodityLibraryDAO.getCommodityById(commodityId, user);
	}

	@Override
	public List<String> getCommodityErrorCodes(String user) throws CommodityLibraryException {
		DB2Timestamp timestamp = new DB2Timestamp();
		return commodityLibraryDAO.getCommodityErrorCodes(user, timestamp.getFormattedTimestamp());
	}

	@Override
	public List<Commodity> getCommodities(String user) throws CommodityLibraryException {
		return commodityLibraryDAO.getCommodities(user);
	}

	@Override
	public boolean addCommodity(Commodity commodity, String user) throws CommodityLibraryException {
		DB2Timestamp timestamp = new DB2Timestamp();
		return commodityLibraryDAO.saveCommodity(commodity, user, timestamp.getFormattedTimestamp());
	}
	
	@Override
	public boolean updateCommodity(Commodity commodity, String user) throws CommodityLibraryException {
		DB2Timestamp timestamp = new DB2Timestamp();
		return commodityLibraryDAO.saveCommodity(commodity, user, timestamp.getFormattedTimestamp());
	}

	@Override
	public boolean deleteCommodity(String user, String commodityId) throws CommodityLibraryException {
		return commodityLibraryDAO.deleteCommodity(user, commodityId);
	}

	@Override
	public ServiceResponse[] getErrors(String user) throws CommodityLibraryException {
		// Set error info for each code
		List<ServiceResponse> errorList = new ArrayList<ServiceResponse>();		
		for (String code : getCommodityErrorCodes(user)) {
			ServiceResponse resp = new ServiceResponse(code, errorDAO.getErrorMessage(code));
			errorList.add(resp);
		}
		return errorList.toArray( new ServiceResponse[errorList.size()] );
	}	
}
