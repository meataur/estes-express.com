/**
 * @author: Todd Allen
 *
 * Creation date: 02/07/2019
 */

package com.estes.myestes.invoiceinquiry.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice details
 */
@ApiModel(description="Set of customer shipment invoice details")
@Data
public class AgingDetails
{
	@ApiModelProperty(notes="Total number of rows in set")
	int totalRows;
	@ApiModelProperty(notes="List of individual invoice details")
	List<AgingDetail> details;

	public AgingDetails()
	{
	} // constructor

	public AgingDetails(int tot, List<AgingDetail> dtls)
	{
		setTotalRows(tot);
		setDetails(dtls);
	} // constructor

	public AgingDetails(String tot, List<AgingDetail> dtls)
	{
		this(Integer.parseInt(tot.trim()), dtls);
	} // constructor

	/**
	 * Get the aging details
	 * 
	 * @return Aging details
	 */
	public List<AgingDetail> getDetails()
	{
		return this.details;
	} // getDetails

	/**
	 * Get the total # of rows in the set
	 * 
	 * @return Total rows
	 */
	public int getTotalRows()
	{
		return this.totalRows;
	} // getTotalRows

	/**
	 * Set the aging details
	 * 
	 * @param res Aging details
	 */
	public void setDetails(List<AgingDetail> res)
	{
		this.details = res;
	} // setDetails

	/**
	 * Set # of total rows
	 * 
	 * @param val Total rows
	 */
	public void setTotalRows(int val)
	{
		this.totalRows = val;
	} // setTotalRows
}
