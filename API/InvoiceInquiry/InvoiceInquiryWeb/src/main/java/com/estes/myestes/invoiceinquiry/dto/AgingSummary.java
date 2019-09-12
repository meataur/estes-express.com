/**
 * @author: Todd Allen
 *
 * Creation date: 10/02/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice aging
 */
@ApiModel(description="Customer open invoice amount summary data")
@Data
public class AgingSummary
{
	@ApiModelProperty(notes="Last A/R aging refresh date - YYYYMMDD")
	String refreshDate;
	@ApiModelProperty(notes="List of aging amounts in days -> 0-15/16-30/31-45/46-60/61-75/76-90/91-120/121+/total")
	List<Double> aging;

	public AgingSummary()
	{
	} // constructor

	public AgingSummary(List<Double> amounts)
	{
		this();
		this.setAging(amounts);
	} // constructor

	/**
	 * Get the aging amounts
	 * 
	 * @return Aging amounts
	 */
	public List<Double> getAging()
	{
		return this.aging;
	} // getAging

	/**
	 * Get the last refresh date
	 * 
	 * @return Refresh date
	 */
	public String getRefreshDate()
	{
		return this.refreshDate;
	} // getRefreshDate

	/**
	 * Set the aging amounts
	 * 
	 * @param vals Aging amounts
	 */
	public void setAging(List<Double> vals)
	{
		this.aging = vals;
	} // setAging

	/**
	 * Set the last refresh date
	 * 
	 * @param val Refresh date
	 */
	public void setRefreshDate(String val)
	{
		this.refreshDate = val;
	} // setRefreshDate
}
