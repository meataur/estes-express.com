/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */

package com.estes.services.common.dao.iface;

import com.estes.services.common.dto.Point;
import com.estes.services.common.exception.PointException;
import com.estes.services.common.exception.ServiceException;

public interface PointDAO extends BaseDAO
{
	/**
	 * Determine if a point is direct or not
	 * 
	 * @return List<PackageType>
	 * @throws PointException
	 */
	public boolean isDirect(Point point) throws PointException;

}
