/**
 * @author: Todd Allen
 *
 * Creation date: 06/18/2018
 */

package com.estes.myestes.shiptrack.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Shipment tracking request
 */
@ApiModel(description="Estes shipment search and display information")
public class TrackingRequest
{
	@ApiModelProperty(notes="Session ID - leave blank")
	String session;
	@ApiModelProperty(notes="Search type - PRO/BOL/PO/INTERLINEPRO/LOADNUMBER/EXLAID")
	String type;
	@ApiModelProperty(notes="Search criteria - separated by British pound sign (£)")
	String search;
	@ApiModelProperty(notes="Sort order - PRO/PICKUPDATE/BOL/PO/STATUS")
	String sort;
	@ApiModelProperty(notes="Sort direction - ASCEND/DESCEND")
	String direction;
	@ApiModelProperty(notes="Number of rows per page - 0 for all rows")
	int rowsPerPage;
	@ApiModelProperty(notes="Current page number of results")
	int page;

	public TrackingRequest()
	{
	} // constructor

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
	 * Get the # rows per page
	 * 
	 * @return Rows per page
	 */
	public int getRowsPerPage()
	{
		return this.rowsPerPage;
	} // getRowsPerPage

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
	 * Get the requested search criteria
	 * 
	 * @return Search criteria
	 */
	public String getSearch()
	{
		return this.search;
	} // getSearch

	/**
	 * Get the session ID
	 * 
	 * @return Session ID
	 */
	public String getSession()
	{
		return this.session;
	} // getSession

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
	 * Get the search type
	 * 
	 * @return Search type
	 */
	public String getType()
	{
		return this.type;
	} // getType

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
	 * Set the page
	 * 
	 * @param val Page number
	 */
	public void setPage(int val)
	{
		this.page = val;
	} // setPage

	/**
	 * Set the # rows per page
	 * 
	 * @param val Rows per page
	 */
	public void setRowsPerPage(int val)
	{
		this.rowsPerPage = val;
	} // setRowsPerPage

	/**
	 * Set the search criteria
	 * 
	 * @param val Search criteria
	 */
	public void setSearch(String val)
	{
		this.search = val;
	} // setSearch

	/**
	 * Set the session ID
	 * 
	 * @param va Session ID
	 */
	public void setSession(String val)
	{
		this.session = val.trim();
	} // setSession

	/**
	 * Set the sort order
	 * 
	 * @param val Sort order
	 */
	public void setSort(String val)
	{
		this.sort = val;
	} // setSort

	/**
	 * Set the search type
	 * 
	 * @param val Search type
	 */
	public void setType(String val)
	{
		this.type = val;
	} // setType

	@Override
	public String toString() {
		return "TrackingRequest [session=" + session + ", type=" + type + ", search=" + search + ", sort=" + sort
				+ ", direction=" + direction + ", rowsPerPage=" + rowsPerPage + ", page=" + page + "]";
	}
	
	
}
