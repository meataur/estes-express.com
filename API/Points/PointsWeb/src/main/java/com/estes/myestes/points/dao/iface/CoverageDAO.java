/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2018
 * 
 */

package com.estes.myestes.points.dao.iface;

import java.util.List;

import com.estes.myestes.points.dto.CoverageRequest;
import com.estes.myestes.points.dto.PartialTerminal;
import com.estes.myestes.points.exception.PointException;

public interface CoverageDAO
{
	/**
	 * Get Estes servicing terminal for given point
	 * 
	 * @param cvr {@link CoverageRequest} info
	 * @return List of {@link PartialTerminal} objects
	 */
	List<PartialTerminal> getServicingTerminals(CoverageRequest cvr) throws PointException;
}
