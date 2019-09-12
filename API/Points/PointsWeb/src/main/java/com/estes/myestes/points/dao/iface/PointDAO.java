/**
 * @author: Todd Allen
 *
 * Creation date: 01/31/2018
 * 
 * @deprecated No longer used by {@LookupService}
 */

package com.estes.myestes.points.dao.iface;

import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.exception.PointException;

@Deprecated
public interface PointDAO
{
	/**
	 * Get Estes points from database
	 * 
	 * @param pt Estes point
	 */
	Point[] getPoints(Point pt) throws PointException;
}
