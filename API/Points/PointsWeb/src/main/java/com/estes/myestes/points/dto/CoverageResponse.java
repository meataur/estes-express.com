/**
 * @author: Todd Allen
 *
 * Creation date: 09/19/2018
 */

package com.estes.myestes.points.dto;

import java.util.List;

import com.estes.dto.common.ServiceResponse;

/**
 * Estes coverage service response
 */
public class CoverageResponse
{
	ServiceResponse errorInfo;
	List<Terminal> terminals;

	/**
	 * Create new point DTO
	 */
	public CoverageResponse()
	{
		this.errorInfo = new ServiceResponse();
	} // Constructor

	/**
	 * Get the service response
	 * 
	 * @return Authentication {@link ServiceResponse}
	 */
	public ServiceResponse getErrorInfo()
	{
		return this.errorInfo;
	} // getErrorInfo

	/**
	 * Get the servicing terminals
	 * 
	 * @return Terminals
	 */
	public List<Terminal> getTerminals()
	{
		return this.terminals;
	} // getTerminals

	/**
	 * Set the service response
	 * 
	 * @param val Service response
	 */
	public void setErrorInfo(ServiceResponse val)
	{
		this.errorInfo = val;
	} // setErrorInfo

	/**
	 * Set the servicing terminals
	 * 
	 * @param term Terminals
	 */
	public void setTerminals(List<Terminal> terms)
	{
		this.terminals = terms;
	} // setTerminal
}
