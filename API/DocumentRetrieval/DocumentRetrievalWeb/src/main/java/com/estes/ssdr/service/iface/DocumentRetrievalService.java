/**
 * @author: Todd Allen
 *
 * Creation date: 03/10/2016
 *
 */

package com.estes.ssdr.service.iface;

import com.estes.ssdr.dto.DocumentRequestDTO;
import com.estes.ssdr.dto.DocumentResponseDTO;
import com.estes.ssdr.exception.DocumentRetrievalException;
import com.estes.ssdr.rest.message.SSDRRequest;
import com.estes.ssdr.rest.message.SSDRResponse;

/**
 * Document retrieval service
 */
public interface DocumentRetrievalService
{
	/**
	 * ReST service endpoint JNDI name
	 */
	public static final String SERVICE_URI_JNDI = "wmSSDRURI";

	/**
	 * Invoke service and process request
	 * 
	 * @param  req Document request info
	 * @return {@link DocumentResponseDTO} object
	 */
	public DocumentResponseDTO invokeService(SSDRRequest req) throws DocumentRetrievalException;

	/**
	 * Map service response to DTO
	 * 
	 * @param  resp Document response data
	 * @return {@link DocumentResponseDTO} object
	 */
	public DocumentResponseDTO mapResponse(SSDRResponse resp);

	/**
	 * Submit request to retrieve documents
	 * 
	 * @param  req Document request info
	 * @return {@link DocumentResponseDTO} object
	 */
	public DocumentResponseDTO submitRequest(DocumentRequestDTO req)  throws DocumentRetrievalException;
}