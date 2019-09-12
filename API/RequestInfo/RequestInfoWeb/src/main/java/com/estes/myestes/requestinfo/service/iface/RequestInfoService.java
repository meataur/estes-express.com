/**
 *
 */

package com.estes.myestes.requestinfo.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.requestinfo.dto.InfoRequestDTO;
import com.estes.myestes.requestinfo.dto.ProblemTypeRequestDTO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;
import com.estes.myestes.requestinfo.util.ProblemType;
import com.estes.myestes.requestinfo.util.RequestInfoConstant;

/**
 * Document retrieval service
 */
public interface RequestInfoService
{
	/**
	 * ReST service endpoint JNDI name
	 */
	public static final String SERVICE_URI_JNDI = RequestInfoConstant.APP_JNDI;
	
	/**
	 * Mail service endpoint JNDI name
	 */
	public static final String MAIL_URI_JNDI = RequestInfoConstant.APP_MAIL_JNDI;

	/**
	 * Submit request to retrieve documents
	 * 
	 * @param String of account username doing request
	 * @param  req Document request info
	 * @return {@link DocumentResponseDTO} object
	 */
	public ServiceResponse submitRequest(String username, InfoRequestDTO req)  throws RequestInfoException;
	
	/**
	 * Get the problem types available to this user for OT PRO
	 * 
	 * @param String of account code doing request
	 * @param String of account type doing request
	 * @param  req Document request info
	 * @return Problem types available
	 */
	public List<ProblemType> getProblemTypes(String accountCode, String accountType, ProblemTypeRequestDTO request);
	
}