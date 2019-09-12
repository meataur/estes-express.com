/**
 * @author Lakshman K
 * 
 */
package com.estes.myestes.edirequest.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.edirequest.dto.BillingHeaderType;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.estes.myestes.edirequest.exception.EdiRequestException;

/**
 * Edi Form transmission request service
 */
public interface EdiRequestService
{
	
	/**
	 * Get the Billing Types for My Estes user
	 * 
	 * @return List<BillingHeaderType>
	 * @throws EDIRequestException
	 */
	public List<BillingHeaderType> getEdiBillingTypes() throws EdiRequestException;
	
	/**
	 * Get the Header Types for My Estes user
	 * 
	 * @return List<BillingHeaderType>
	 * @throws EDIRequestException
	 */
	public List<BillingHeaderType> getEdiHeaderTypes() throws EdiRequestException;
	
	/**
	 * Save Edi FormTransmission Request for My Estes user
	 * 
	 * @param ediRequest
	 * @return ediNewRequestNumber
	 */
	public String saveEdiFormTransmissionRequest(EdiRequest ediRequest) throws EdiRequestException;
	
	/**
	 *  Validate the Edi Request
	 *  
	 * @param ediRequest
	 * @return List<ServiceResponse>
	 */
	public List<ServiceResponse> validateRequest(EdiRequest ediRequest);
	
}
