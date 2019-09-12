/**
 * @author: Todd Allen
 *
 * Creation date: 09/24/2018
 */

package com.estes.myestes.points.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes points download request
 */
@ApiModel(description="Request to have file of Estes delivery point information sent via e-mail")
public class DownloadRequest
{
	@ApiModelProperty(notes="E-mail address to receive list of Estes delivery point information")
	String email;
	@ApiModelProperty(notes="Selection criteria for points - " +
			"*=All/2=All direct points/3=All North America/5=All non-direct points/" +
			"US=United States/CN=Canada/MX=Mexico")
	String criteria;
	@ApiModelProperty(notes="State selection for US or Canadian points")
	String state;
	@ApiModelProperty(notes="File format - xls/csv/txt")
	String fileFormat;
	@ApiModelProperty(notes="File sort order - cty=city/zip=zip code")
	String fileSort;

	/**
	 * Create new download request DTO
	 */
	public DownloadRequest()
	{
	} // Constructor

	/**
	 * Get the selection criteria
	 * 
	 * @return Selection criteria
	 */
	public String getCriteria()
	{
		return this.criteria;
	} // getCriteria

	/**
	 * Get the requester e-mail address
	 * 
	 * @return E-mail address
	 */
	public String getEmail()
	{
		return this.email;
	} // getEmail

	/**
	 * Get the file format
	 * 
	 * @return File format
	 */
	public String getFileFormat()
	{
		return this.fileFormat;
	} // getFileFormat

	/**
	 * Get the file sort order
	 * 
	 * @return Sort order of file
	 */
	public String getFileSort()
	{
		return this.fileSort;
	} // getFileSort

	/**
	 * Get the state
	 * 
	 * @return State
	 */
	public String getState()
	{
		// Truncate to 2 characters
		if (this.state != null && this.state.length() > 2) {
			return this.state.substring(0, 2);
		}

		return this.state;
	} // getState

	/**
	 * Set the selection criteria
	 * 
	 * @param val Selection criteria
	 */
	public void setCriteria(String val)
	{
		this.criteria = val;
	} // setCriteria

	/**
	 * Set the requester e-mail address
	 * 
	 * @param val E-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val.trim();
	} // setEmail

	/**
	 * Set the file format
	 * 
	 * @param val Format of file
	 */
	public void setFileFormat(String val)
	{
		this.fileFormat = val;
	} // setFileFormat

	/**
	 * Set the file sort order
	 * 
	 * @param val Sort order of file
	 */
	public void setFileSort(String val)
	{
		this.fileSort = val;
	} // setFileSort

	/**
	 * Set the state
	 * 
	 * @param val State
	 */
	public void setState(String val)
	{
		this.state = val;
	} // setState
}
