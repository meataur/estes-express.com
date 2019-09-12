/**
 * @author: Todd Allen
 *
 * Creation date: 10/19/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer aging build progress
 */
@ApiModel(description="Estes customer A/R aging build progress")
@Data
public class AgingBuildProgress
{
	@ApiModelProperty(notes="Build status error")
	private boolean buildError = false;
	@ApiModelProperty(notes="Last A/R aging refresh date - YYYYMMDD")
	private String refreshDate;
	@ApiModelProperty(notes="Build busy indicator")
	private boolean busy;
	@ApiModelProperty(notes="Number of bills processed in build")
	private String billCount;

	public AgingBuildProgress()
	{
	} // constructor

	/**
	 * Get the invoice count.
	 * 
	 * @return Number of invoices found
	 */
	public String getBillCount()
	{
		return billCount;
	}

	/**
	 * Retrieve the last build date of the aging summary.
	 * 
	 * @return Aging summary refresh date
	 */
	public String getRefreshDate()
	{
		return refreshDate;
	}

	/**
	 * Determine whether an error occurred in the aging build.
	 * 
	 * @return Boolean indicating whether an aging build error occurred
	 */
	public boolean isBuildError()
	{
		return buildError;
	}

	/**
	 * Get the aging build busy status.
	 * 
	 * @return Aging build busy status
	 */
	public boolean isBusy()
	{
		return busy;
	}

	/**
	 * Set the invoice count.
	 * 
	 * @param val Number of invoices found
	 */
	public void setBillCount(String val)
	{
		this.billCount = val;
	}

	/**
	 * Set the aging build busy status.
	 * 
	 * @param val Aging build busy status
	 */
	public void setBusy(boolean val)
	{
		this.busy = val;
	}

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
