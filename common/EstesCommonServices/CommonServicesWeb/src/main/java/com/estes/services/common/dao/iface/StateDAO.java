/**
 * @author: Todd Allen
 *
 * Creation date: 11/08/2018
*/

package com.estes.services.common.dao.iface;

import java.util.List;

import com.estes.services.common.exception.ServiceException;


public interface StateDAO
{
	/**
	 * Get all states in country
	 * 
	 * @param abbr Country abbreviation
	 */
	List<String> getStatesForCountry(String abbr) throws ServiceException;
}
