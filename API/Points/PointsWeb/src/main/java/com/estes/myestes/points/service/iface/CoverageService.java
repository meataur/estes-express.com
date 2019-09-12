/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2018
 */

package com.estes.myestes.points.service.iface;

import com.estes.myestes.points.dto.CoverageRequest;
import com.estes.myestes.points.dto.CoverageResponse;
import com.estes.myestes.points.exception.PointException;

/**
 * Servicing terminal and coverage service
 */
public interface CoverageService
{
	/**
	 * Get servicing terminal for Estes point
	 * 
	 * @param {@link CoverageRequest} request info
	 * @return {@link CoverageResponse} object
	 */
	public CoverageResponse getTerminals(CoverageRequest cover) throws PointException;
}
