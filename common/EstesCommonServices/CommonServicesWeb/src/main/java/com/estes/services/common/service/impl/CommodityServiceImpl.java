/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */
package com.estes.services.common.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.services.common.dao.iface.CommodityDAO;
import com.estes.services.common.dto.PackageType;
import com.estes.services.common.exception.ServiceException;
import com.estes.services.common.service.iface.CommodityService;

/**
 * Estes packageType/class list implementation 
 */
@Service("commodityService")
@Scope("prototype")
public class CommodityServiceImpl implements CommodityService
{
	@Autowired
	private CommodityDAO commodityDAO;

	/**
	 * Create a new service
	 */
	public CommodityServiceImpl()
	{
		super();
	} // Constructor

	@Override
	public List<String> getClasses() throws ServiceException {
		return commodityDAO.getClasses();
	}

	@Override
	public List<PackageType> getPackageTypes() throws ServiceException {
		return commodityDAO.getPackageTypes();
	}
}
