/**
 * @author: Todd Allen
 *
 * Creation date: 09/27/2018
 */

package com.estes.myestes.points.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dto.DownloadRequest;
import com.estes.myestes.points.exception.PointException;

/**
 * Point download service
 */
public interface DownloadService
{
	/**
	 * Process request for Estes points download
	 * 
	 * @param {@link DownloadRequest} request info
	 * @return {@link ServiceResponse} object
	 */
	public ServiceResponse processDownload(DownloadRequest request) throws PointException;
}
