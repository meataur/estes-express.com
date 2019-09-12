/**
 * @author: Todd Allen
 *
 * Creation date: 02/08/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Accessorial pricing
 */
@ApiModel(description="Accessorial pricing information")
public class AccessorialPricing
{
	@ApiModelProperty(position=1, notes="Accessorial information")
	Accessorial accessorial;
	@ApiModelProperty(position=3, notes="Charge")
	double charge;

	public AccessorialPricing()
	{
		this.accessorial = new Accessorial();
	} // Constructor

	/**
	 * Get accessorial info
	 * 
	 * @return Accessorial info
	 */
	public Accessorial getAccessorial()
	{
		return this.accessorial;
	} // getAccessorial

	/**
	 * Get the charge amount
	 * 
	 * @return Charge
	 */
	public double getCharge()
	{
		return this.charge;
	} // getCharge

	/**
	 * Get accessorial info
	 * 
	 * @return Accessorial info
	 */
	public void setAccessorial(Accessorial val)
	{
		this.accessorial = val;
	} // setAccessorial

	/**
	 * Set charge amount
	 * 
	 * @param val Charge amount
	 */
	public void setCharge(double val)
	{
		this.charge = val;
	} // setCharge
}
