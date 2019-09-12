/**
 * @author: Todd Allen
 *
 * Creation date: 10/05/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer aging detail request
 */
@ApiModel(description="Estes customer aging detail request")
@Data
public class AgingDetailRequest
{
	@ApiModelProperty(notes="Aging bucket : 1-8; 0 for all")
	int bucket;
	@ApiModelProperty(notes="Sort order - " +
			"0=PRO/1=pickup date/2=delivery date/3=invoice date/4=BOL/5=PO/" +
			"6=statement#/7=open amount/8=pending pay amount")
	String sort;
	@ApiModelProperty(notes="Sort direction - 0=ascending/1=descending")
	String direction;
	@ApiModelProperty(notes="Current page number of results")
	int page;
	@ApiModelProperty(notes="Maximum number of rows to return")
	int maxRows;

	public AgingDetailRequest()
	{
	} // constructor

	/**
	 * Get the aging bucket#
	 * 
	 * @return Aging bucket
	 */
	public int getBucket()
	{
		return this.bucket;
	} // getBucket

	/**
	 * Get the sort direction
	 * 
	 * @return Sort direction
	 */
	public String getDirection()
	{
		return this.direction;
	} // getDirection

	/**
	 * Get the maximum number of rows to be returned
	 * 
	 * @return Maximum # rows
	 */
	public int getMaxRows()
	{
		return this.maxRows;
	} // getMaxRows

	/**
	 * Get the page number
	 * 
	 * @return Page number
	 */
	public int getPage()
	{
		return this.page;
	} // getPage

	/**
	 * Get the sort order
	 * 
	 * @return Sort order
	 */
	public String getSort()
	{
		return this.sort;
	} // getSort

	/**
	 * Set the aging bucket#
	 * 
	 * @param val Aging bucket
	 */
	public void setBucket(int val)
	{
		this.bucket = val;
	} // setBucket

	/**
	 * Set the sort direction
	 * 
	 * @param val Sort direction
	 */
	public void setDirection(String val)
	{
		this.direction = val;
	} // setDirection

	/**
	 * Get the maximum number of rows to be returned
	 * 
	 * @param val Maximum # rows
	 */
	public void setMaxRows(int val)
	{
		this.maxRows = val;
	} // setMaxRows

	/**
	 * Set the page
	 * 
	 * @param val Page number
	 */
	public void setPage(int val)
	{
		this.page = val;
	} // setPage

	/**
	 * Set the sort order
	 * 
	 * @param val Sort order
	 */
	public void setSort(String val)
	{
		this.sort = val;
	} // setSort
}
