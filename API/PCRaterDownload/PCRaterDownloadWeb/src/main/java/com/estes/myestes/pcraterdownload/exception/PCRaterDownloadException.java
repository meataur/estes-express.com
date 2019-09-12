/**
 * @author: Pradeep K
 *
 * Creation date: 10/29/2018
 */

package com.estes.myestes.pcraterdownload.exception;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.exception.ESTESException;

/**
 * Estes rater download application exception
 */
public class PCRaterDownloadException extends ESTESException
{
	private static final long serialVersionUID = -2647673053567792077L;
	

	private List<ServiceResponse> serviceResponseList;
	
	/**
	 * @return the serviceResponseList
	 */
	public List<ServiceResponse> getServiceResponseList() {
		return serviceResponseList;
	}

	/**
	 * @param serviceResponseList the serviceResponseList to set
	 */
	public void setServiceResponseList(List<ServiceResponse> serviceResponseList) {
		this.serviceResponseList = serviceResponseList;
	}

	public PCRaterDownloadException(String messageId)
	{
		super(messageId);
	} // Constructor

	public PCRaterDownloadException(Exception ex)
	{
		super(ex);
	} // Constructor

	public PCRaterDownloadException(List<ServiceResponse> serviceResponseList)
	{
		 super();
		 this.serviceResponseList = serviceResponseList;
	} // Constructor
	
	public PCRaterDownloadException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
