/**
 * @author: Todd Allen
 * 
 * Cloned from points.service.iface#TerminalService
 *
 * Creation date: 03/29/2018
 */

package com.estes.myestes.rating.service.iface;

import com.estes.myestes.rating.dto.Point;
import com.estes.myestes.rating.dto.Terminal;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Terminal info service
 */
public interface TerminalService
{
	/**
	 * Retrieve terminal info for the given point
	 * 
	 * @param id Estes point
	 * @return {@link Terminal} object
	 */
	public Terminal getTerminal(Point point) throws RatingException;
}
