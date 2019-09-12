/**
 * @author: Todd Allen
 *
 * Creation date: 09/26/2018
 */

package com.estes.myestes.points.dao.iface;

import com.estes.myestes.points.dto.DownloadRequest;
import com.estes.myestes.points.exception.PointException;

public interface DownloadDAO
{
	/**
	 * Process request to receive list of Estes points
	 * 
	 * @param req {@link DownloadRequest} info
	 * @return {@link ServiceResponse} object containing error/success message
	 */
	String sendFile(DownloadRequest req) throws PointException;
}
