/**
 * @author: Todd Allen
 *
 * Creation date: 10/18/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import lombok.Data;

/**
 * Customer aging build status
 */
@Data
public class AgingBuildStatus 
{
	private boolean running = false;
	private String code;
	private String desc;
	private String busy;
	private String billCount;

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
	 * Get the aging build busy status.
	 * 
	 * @return Aging build busy status
	 */
	public String getBusy()
	{
		return busy;
	}

	/**
	 * Get the return code
	 * 
	 * @return Code in resulet set
	 */
	public String getCode()
	{
		return code;
	} // getCode

	/**
	 * Get the description
	 * 
	 * @return Description
	 */
	public String getDesc()
	{
		return this.desc;
	} // getDesc

	/**
	 * Determine whether the invoice aging is running.
	 * 
	 * @return Boolean indicating invoice aging run status
	 */
	public boolean isRunning()
	{
		return running;
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
	public void setBusy(String val)
	{
		this.busy = val;
	}

	/**
	 * Set the return code
	 * 
	 * @param val Code
	 */
	public void setCode(String val)
	{
		this.code = val.trim();
	} // setCode

	/**
	 * Set the description
	 * 
	 * @param val Description
	 */
	public void setDesc(String val)
	{
		this.desc = val.trim();
	} // setDesc
}
