/**
 * @author: Todd Allen
 *
 * Creation date: 01/31/2018
 */

package com.estes.services.common.service.iface;

import java.util.List;

import com.estes.services.common.dto.Point;
import com.estes.services.common.dto.PointLookup;
import com.estes.services.common.exception.PointException;

/**
 * Point lookup service
 */
public interface PointService
{
	/**
	 * Look up Estes points
	 * 
	 * @param lookup Point look info
	 * @return array of {@link PointDTO} objects
	 */
	public List<Point> search(PointLookup lookup) throws PointException;
	
	/**
	 * Determine if a point is direct or not
	 * 
	 * @param point
	 * @return
	 * @throws PointException
	 */
	public boolean isDirect(Point point) throws PointException;
}
