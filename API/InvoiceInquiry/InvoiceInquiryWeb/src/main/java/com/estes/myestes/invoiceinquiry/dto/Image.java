/**
 * @author: Todd Allen
 *
 * Creation date: 12/07/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Shipment image information
 */
@ApiModel(description="Customer shipment image information")
@Data
public class Image
{
	@ApiModelProperty(notes="Estes shipment PRO")
	String pro;
	@ApiModelProperty(notes="Image retrieval status - Y=found/N=not found/W=working")
	String status;
	@ApiModelProperty(notes="File location")
	String location;

	public Image()
	{
	} // constructor

	/**
	 * Get the file location
	 * 
	 * @return File location
	 */
	public String getLocation()
	{
		return this.location;
	} // getLocation

	/**
	 * Get the PRO
	 * 
	 * @return Estes PRO
	 */
	public String getPro()
	{
		return this.pro;
	} // getPro

	/**
	 * Get the file status
	 * 
	 * @return File status
	 */
	public String getStatus()
	{
		return this.status;
	} // getStatus

	/**
	 * Set the file location
	 * 
	 * @param val File location
	 */
	public void setLocation(String val)
	{
		this.location = val.trim();
	} // setLocation

	/**
	 * Set the PRO
	 * 
	 * @param val PRO
	 */
	public void setPro(String val)
	{
		this.pro = val.trim();
	} // setPro

	/**
	 * Set the file status
	 * 
	 * @param val File status
	 */
	public void setStatus(String val)
	{
		this.status = val;
	} // setStatus
}
