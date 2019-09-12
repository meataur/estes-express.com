/**
 * @author: Pradeep K
 *
 */

package com.estes.myestes.pcraterdownload.dao.iface;

import com.estes.myestes.pcraterdownload.dto.PCRaterDownloadDTO;
import com.estes.myestes.pcraterdownload.exception.PCRaterDownloadException;

public interface PCRaterDownloadDAO
{
	
	/**
	 * Get the PC rater download link for My Estes user
	 * 
	 * @param pcRaterDownloadDTO
	 * @return String
	 * @throws PCRaterDownloadException
	 */
	public String getPCRaterDownloadLink(PCRaterDownloadDTO pcRaterDownloadDTO) throws PCRaterDownloadException;

	
	
}
