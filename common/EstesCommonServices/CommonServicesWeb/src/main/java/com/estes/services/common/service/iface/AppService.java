/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.common.service.iface;

import com.estes.services.common.exception.ServiceException;

/**
 * Service to retrieve invoice inquiry app information
 */
public interface AppService
{
	/**
	 * Check application availability
	 * 
	 * @return application availability status
	 */
	public boolean getAvailability() throws ServiceException;

	/**
	 * Check application availability
	 * 
	 * @param app Application name
	 * @return application availability status
	 */
	public boolean getAvailability(String app) throws ServiceException;
}
