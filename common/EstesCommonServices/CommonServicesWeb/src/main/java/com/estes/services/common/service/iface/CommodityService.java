/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */
package com.estes.services.common.service.iface;

import java.util.List;

import com.estes.services.common.dto.PackageType;
import com.estes.services.common.exception.ServiceException;


/**
 * Package type and class retrieval service
 */
public interface CommodityService
{
	/**
	 * Get the list of class values
	 * @return List<String>
	 * @throws ServiceException
	 */
	public List<String> getClasses() throws ServiceException;
	
	/**
	 * Get package type code and description
	 * 
	 * @return List<PackageType>
	 * @throws ServiceException
	 */
	public List<PackageType> getPackageTypes() throws ServiceException;
}
