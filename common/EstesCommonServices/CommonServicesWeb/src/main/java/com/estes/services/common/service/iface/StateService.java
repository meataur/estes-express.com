/**
 * @author: Todd Allen
 *
 * Creation date: 11/08/2018
 */

package com.estes.services.common.service.iface;

import java.util.List;

import com.estes.services.common.exception.ServiceException;


/**
 * State retrieval service
 */
public interface StateService
{
	/**
	 * Retrieve all states for given country
	 * 
	 * @param ctry Country abbreviation
	 * @return Array of state abbreviations
	 */
	public List<String> getStates(String ctry) throws ServiceException;
}
