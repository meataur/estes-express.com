/**
 * @author: Todd Allen
 *
 * Creation date: 02/20/2018
 */

package com.estes.myestes.points.service.iface;

import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.exception.PointException;

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
	public Terminal getTerminal(Point point) throws PointException;
}
