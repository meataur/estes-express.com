/**
 * @author: Todd Allen
 *
 * Creation date: 11/19/2018
 */

package com.estes.services.common.service.iface;

import com.estes.services.common.dto.Terminal;
import com.estes.services.common.exception.ServiceException;

/**
 * Terminal info service
 */
public interface TerminalService
{
	/**
	 * Retrieve terminal info for given ID
	 * 
	 * @param id Estes terminal ID
	 * @return {@link Terminal} object
	 */
	public Terminal getTerminal(int id) throws ServiceException;
}
