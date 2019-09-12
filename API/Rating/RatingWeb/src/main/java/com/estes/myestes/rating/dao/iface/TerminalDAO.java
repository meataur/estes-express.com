/**
 * @author: Todd Allen
 * 
 * Cloned from points.dao.iface#TerminalDAO
 *
 * Creation date: 03/29/2018
  */

package com.estes.myestes.rating.dao.iface;

import com.estes.myestes.rating.dto.Point;
import com.estes.myestes.rating.dto.Terminal;
import com.estes.myestes.rating.exception.RatingException;

public interface TerminalDAO
{
	/**
	 * Get Estes terminal for given ID
	 * 
	 * @param id Terminal ID
	 */
	Terminal getTerminalInfo(Point point) throws RatingException;
}
