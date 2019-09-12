/**
 * @author: Todd Allen
 *
 * Creation date: 09/27/2018
 */

package com.estes.myestes.points.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dao.iface.DownloadDAO;
import com.estes.myestes.points.dao.iface.ErrorDAO;
import com.estes.myestes.points.dto.DownloadRequest;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.service.iface.DownloadService;

@Service("downloadService")
@Scope("prototype")
public class DownloadServiceImpl implements DownloadService
{
	@Autowired
	private DownloadDAO downloadDAO;
	@Autowired
	private ErrorDAO errDAO;

	@Override
	public ServiceResponse processDownload(DownloadRequest request) throws PointException
	{
		/*
		 *  Perform basic input data checks
		 */
		if (StringUtils.isEmpty(request.getFileFormat())) {
		 	return new ServiceResponse("error", "Invalid file format.");
		}

		String error = downloadDAO.sendFile(request);

		if (!ErrorDAO.isEmailError(error)) {
			return new ServiceResponse("", "Your request has been processed and will be sent to " + request.getEmail() + " in a few minutes.");
		}
		else {
			return new ServiceResponse(error, errDAO.getErrorMessage(error));
		}
	} // processDownload
}
