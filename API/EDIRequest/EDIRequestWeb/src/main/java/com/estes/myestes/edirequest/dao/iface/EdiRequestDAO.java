/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.edirequest.dao.iface;

import java.util.List;

import com.estes.myestes.edirequest.dto.BillingHeaderType;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.estes.myestes.edirequest.exception.EdiRequestException;

public interface EdiRequestDAO
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

}
