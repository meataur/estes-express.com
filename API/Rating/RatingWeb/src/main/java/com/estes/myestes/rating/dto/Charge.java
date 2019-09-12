/**
 * @author: Todd Allen
 *
 * Creation date: 03/27/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rate quote charge info
 */
@ApiModel(description="Rate quote charge information")
public class Charge
{
	@ApiModelProperty(notes="Display charge?")
	private boolean show;
	@ApiModelProperty(notes="Service level ID")
	private int serviceLevel;
	@ApiModelProperty(notes="Charge amount")
	private String amount;
	@ApiModelProperty(notes="Charge description")
	private String description;

	/**
	 * Create new charge
	 */
	public Charge()
	{
	} // Constructor

	/**
	 * Create new quote charge
	 */
	public Charge(int i, String txt)
	{
		this();
		this.serviceLevel = i;
		this.description = txt;
	} // Constructor

	/**
	 * Get the charge amount
	 * 
	 * @return Amount
	 */
	public String getAmount()
	{
		return this.amount.trim();
	} // getAmount

	/**
	 * Get the charge description
	 * 
	 * @return Description
	 */
	public String getDescription()
	{
		return this.description.trim();
	} // getDescription

	/**
	 * Get the service level ID
	 * 
	 * @return ID
	 */
	public int getServiceLevel()
	{
		return this.serviceLevel;
	} // getServiceLevel

	/**
	 * Get the show indicator
	 * 
	 * @return Shoe indicator
	 */
	public boolean isShow()
	{
		return this.show;
	} // isShow

	/**
	 * Set the charge amount
	 * 
	 * @param val Amount
	 */
	public void setAmount(String val)
	{
		this.amount = val;
	} // setAmount

	/**
	 * Set the description
	 * 
	 * @param val Description
	 */
	public void setDescription(String val)
	{
		this.description = val;
	} // setDescription

	/**
	 * Get the service level ID
	 * 
	 * @param val Service level
	 */
	public void setServiceLevel(int val)
	{
		this.serviceLevel = val;
	} // setServiceLevel

	/**
	 * Set the show indicator
	 * 
	 * @param tf
	 */
	public void setShow(boolean tf)
	{
		this.show = tf;
	} // setShow
}
