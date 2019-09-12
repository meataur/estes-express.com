/**
 * @author: Todd Allen
 *
 * Creation date: 02/08/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Commodity pricing
 */
@ApiModel(description="Commodity pricing information")
@Data
public class CommodityPricing
{
	@ApiModelProperty(position=1, notes="Commodity information")
	Commodity commodity;
	@ApiModelProperty(position=2, notes="Rate")
	double rate;
	@ApiModelProperty(position=3, notes="Charge")
	double charge;
	
	private boolean deficitRate=false;
	public CommodityPricing()
	{
		this.commodity = new Commodity();
	} // Constructor

	public CommodityPricing(Commodity comm)
	{
		this.commodity = comm;
	} // Constructor

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
	 * Get commodity info
	 * 
	 * @return Commodity info
	 */
	public Commodity getCommodity()
	{
		return this.commodity;
	} // getCommodity

	/**
	 * Get the rate
	 * 
	 * @return Rate
	 */
	public double getRate()
	{
		return this.rate;
	} // getRate

	/**
	 * Set charge amount
	 * 
	 * @param val Charge amount
	 */
	public void setCharge(double val)
	{
		this.charge = val;
	} // setCharge

	/**
	 * Get commodity info
	 * 
	 * @return Commodity info
	 */
	public void setCommodity(Commodity val)
	{
		this.commodity = val;
	} // setCommodity

	/**
	 * Set rate
	 * 
	 * @param val Rate
	 */
	public void setRate(double val)
	{
		this.rate = val;
	} // setRate
}
