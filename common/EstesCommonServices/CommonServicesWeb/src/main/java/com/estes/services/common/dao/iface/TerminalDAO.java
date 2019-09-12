/**
 * @author: Todd Allen
 *
 * Creation date: 11/16/2018
  */

package com.estes.services.common.dao.iface;

import com.estes.services.common.dto.Terminal;
import com.estes.services.common.exception.ServiceException;

public interface TerminalDAO
{
	/**
	 * Get Estes terminal for given ID
	 * 
	 * @param id Terminal ID
	 */
	Terminal getTerminalInfo(int id) throws ServiceException;
}
