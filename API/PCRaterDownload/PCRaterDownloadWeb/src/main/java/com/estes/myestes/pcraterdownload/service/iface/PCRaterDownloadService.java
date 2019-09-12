package com.estes.myestes.pcraterdownload.service.iface;

import com.estes.myestes.pcraterdownload.dto.PCRaterDownloadDTO;
import com.estes.myestes.pcraterdownload.exception.PCRaterDownloadException;

/**
 * PC Rater Download service
 */
public interface PCRaterDownloadService
{
	

	/**
	 * @return String
	 * @throws PCRaterDownloadException
	 */
	public String getPCRaterDownloadLink(PCRaterDownloadDTO pcRaterDownloadDTO) throws PCRaterDownloadException;
	
	/**
	 * @param user
	 * @return ServiceResponse[]
	 * @throws PCRaterDownloadException
	 */
	//public ServiceResponse[] getErrors() throws PCRaterDownloadException;
	
}
