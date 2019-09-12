/**
 * @author: Todd Allen
 *
 * Creation date: 02/06/2019
 */

package com.estes.myestes.rating.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Pricing
 */
@ApiModel(description="Rate quote pricing")
public class Pricing
{
	@ApiModelProperty(notes="Display pricing = C=Contact me/H=hide/S=Show")
	String display;
	@ApiModelProperty(notes="Discount percentage")
	double discount;
	@ApiModelProperty(notes="Total discount")
	double totalDiscount;
	@ApiModelProperty(notes="Total price")
	double totalPrice;

	public Pricing()
	{
		super();
	} // Constructor

	public Pricing(double disc, double totDisc, double totPrc)
	{
		this();
		setDiscount(disc);
		setTotalDiscount(totDisc);
		setTotalPrice(totPrc);
	} // Constructor

	/**
	 * Get the discount %
	 * 
	 * @return Discount
	 */
	public double getDiscount()
	{
		return this.discount;
	} // getDiscount

	/**
	 * Get the display flag
	 * 
	 * @return Display flag
	 */
	public String getDisplay()
	{
		return this.display;
	} // getDisplay

	/**
	 * Get the shipping lane
	 * 
	 * @return Lane
	 */
	public double getTotalDiscount()
	{
		return totalDiscount;
	} // getLane

	/**
	 * Get the total price
	 * 
	 * @return Total price
	 */
	public double getTotalPrice()
	{
		return totalPrice;
	} // getTotalPrice

	/**
	 * Set discount  %
	 * 
	 * @param val Discount
	 */
	public void setDiscount(double val)
	{
		this.discount = val;
	} // setDiscount

	/**
	 * Set the display indicator
	 * 
	 * @param val Flag
	 */
	public void setDisplay(String val)
	{
		this.display = val;
	} // setDisplay

	/**
	 * Set the total discount
	 * 
	 * @param val Total discount
	 */
	public void setTotalDiscount(double val)
	{
		this.totalDiscount = val;
	} // setTotalDiscount

	/**
	 * Set total price
	 * 
	 * @param val Total price
	 */
	public void setTotalPrice(double val)
	{
		this.totalPrice = val;
	} // setTotalPrice
}
