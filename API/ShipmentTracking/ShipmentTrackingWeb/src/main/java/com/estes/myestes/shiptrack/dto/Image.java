/**
 * @author: Todd Allen
 *
 * Creation date: 07/23/2018
 */

package com.estes.myestes.shiptrack.dto;

import com.estes.dto.common.ServiceResponse;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment tracking images
 */
@ApiModel(description="Scanned image information")
public class Image
{
	/**
	 * Bill of lading document type code
	 */
	public final static String BOL = "BOL";
	/**
	 * Delivery receipt document type code
	 */
	public final static String DR = "DR";
	/**
	 * Weight and research document type code
	 */
	public final static String WR = "WR";

	@ApiModelProperty(notes="Error code/message")
	ServiceResponse errorInfo;
	@ApiModelProperty(notes="Image request number")
	String requestNum;
	@ApiModelProperty(notes="PRO associated with image")
	String pro;
	@ApiModelProperty(notes="Image type - BOL/DR/WR")
	String type;
	@ApiModelProperty(notes="Image path")
	String path;
	@ApiModelProperty(notes="Is image found?")
	boolean found;
	@ApiModelProperty(notes="Retry image retrieval?")
	boolean retry;

	public Image()
	{
		this.errorInfo = new ServiceResponse();
	} // constructor

	public Image(String pro, String type, boolean retry)
	{
		this();
		this.setPro(pro);
		this.setType(type);
		this.setFound(false);
		this.setRetry(retry);
	} // constructor

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
	 * Get the PRO
	 * 
	 * @return PRO
	 */
	public String getPro()
	{
		return this.pro;
	} // getPro

	/**
	 * Get the request number
	 * 
	 * @return Request number
	 */
	public String getRequestNum()
	{
		return this.requestNum;
	} // getRequestNum

	/**
	 * Get the shipment type
	 * 
	 * @return Shipment type
	 */
	public String getType()
	{
		return this.type;
	} // getType

	/**
	 * Get image path
	 * 
	 * @return Image path
	 */
	public String getPath()
	{
		return this.path;
	} // getPath

	/**
	 * Is the image found?
	 * 
	 * @return Image found indicator
	 */
	public boolean isFound()
	{
		return this.found;
	} // isFound

	/**
	 * Retry the image retrieval?
	 * 
	 * @return Retry image retrieval indicator
	 */
	public boolean isRetry()
	{
		return this.retry;
	} // isRetry
	
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
	 * Set the found indicator
	 * 
	 * @param val Image found boolean
	 */
	public void setFound(boolean val)
	{
		this.found = val;
	} // setFound

	/**
	 * Set the found indicator
	 * 
	 * @param val Image retry string from DB table
	 */
	public void setFound(String val)
	{
		this.found = val.equalsIgnoreCase("Y");
	} // setFound

	/**
	 * Set the image path
	 * 
	 * @param val Image path
	 */
	public void setPath(String val)
	{
		this.path = val.trim();
	} // setPath

	/**
	 * Set the PRO
	 * 
	 * @param val PRO
	 */
	public void setPro(String val)
	{
		this.pro = val;
	} // setPro

	/**
	 * Set the request number
	 * 
	 * @param val Request number
	 */
	public void setRequestNum(String val)
	{
		this.requestNum = val;
	} // setRequestNum

	/**
	 * Set the retry indicator
	 * 
	 * @param val Image retry boolean
	 */
	public void setRetry(boolean val)
	{
		this.retry = val;
	} // setRetry

	/**
	 * Set the retry indicator
	 * 
	 * @param val Image retry string from DB table
	 */
	public void setRetry(String val)
	{
		this.retry = !val.equalsIgnoreCase("Y"); // val.equals("W")
	} // setRetry

	/**
	 * Set the shipment type
	 * 
	 * @param val Shipment type
	 */
	public void setType(String val)
	{
		this.type = val.trim();
	} // setType
}
