package com.estes.myestes.requestinfo.dao.iface;


import com.estes.myestes.requestinfo.dto.InfoRequestDTO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;

public interface RequestLogDAO extends BaseDAO
{
	/**
	 * Write the request to the log table
	 * 
	 * @param  InfoRequestDTO
	 */
	public void writeLog(String username, InfoRequestDTO request) throws RequestInfoException;

}