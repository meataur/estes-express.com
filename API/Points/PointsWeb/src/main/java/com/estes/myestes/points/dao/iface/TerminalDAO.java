/**
 * @author: Todd Allen
 *
 * Creation date: 02/13/2018
  */

package com.estes.myestes.points.dao.iface;

import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.exception.PointException;

public interface TerminalDAO
{
	/**
	 * Get Estes terminal for given ID
	 * 
	 * @param id Terminal ID
	 */
	Terminal getTerminalInfo(Point point) throws PointException;

	/**
	 * Get Estes servicing terminal for given point
	 * 
	 * @param pt Estes point
	 */
	Terminal getServicingTerminal(Point pt) throws PointException;
}
