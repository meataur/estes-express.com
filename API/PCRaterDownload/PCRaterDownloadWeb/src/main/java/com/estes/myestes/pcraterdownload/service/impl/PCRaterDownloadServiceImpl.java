/**
 * @author: Pradeep K
 *
 */

package com.estes.myestes.pcraterdownload.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.pcraterdownload.dao.iface.PCRaterDownloadDAO;
import com.estes.myestes.pcraterdownload.dto.PCRaterDownloadDTO;
import com.estes.myestes.pcraterdownload.exception.PCRaterDownloadException;
import com.estes.myestes.pcraterdownload.service.iface.PCRaterDownloadService;

@Service("pcRaterDownloadService")
@Scope("prototype")
public class PCRaterDownloadServiceImpl implements PCRaterDownloadService
{
	@Autowired
	private PCRaterDownloadDAO pcRaterDownloadDAO;
	
	@Override
	public String getPCRaterDownloadLink(PCRaterDownloadDTO pcRaterDownloadDTO) throws PCRaterDownloadException {
		return pcRaterDownloadDAO.getPCRaterDownloadLink(pcRaterDownloadDTO);
	}
	
}
