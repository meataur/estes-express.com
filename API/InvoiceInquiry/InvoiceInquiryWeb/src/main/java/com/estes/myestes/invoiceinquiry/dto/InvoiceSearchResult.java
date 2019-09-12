/**
 * @author: Todd Allen
 *
 * Creation date: 11/29/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import com.estes.dto.common.ServiceResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice search result
 */
@ApiModel(description="Estes customer invoice search result")
@Data
public class InvoiceSearchResult
{
	@ApiModelProperty(notes="Error information")
	ServiceResponse error;
	@ApiModelProperty(notes="Shipment invoice amount details")
	AgingDetail result;

	public InvoiceSearchResult()
	{
		this.error = new ServiceResponse();
		this.result = new AgingDetail();
	} // constructor

	/**
	 * Get the error information
	 * 
	 * @return Error info
	 */
	public ServiceResponse getError()
	{
		return this.error;
	} // getError

	/**
	 * Get the shipment details
	 * 
	 * @return Shipment details
	 */
	public AgingDetail getResult()
	{
		return this.result;
	} // getResult

	/**
	 * Set the error information
	 * 
	 * @param resp Error info
	 */
	public void setError(ServiceResponse resp)
	{
		this.error = resp;
	} // setError

	/**
	 * Set the shipment details
	 * 
	 * @param res Shipment details
	 */
	public void setResult(AgingDetail res)
	{
		this.result = res;
	} // setResult
}
