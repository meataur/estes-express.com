/**
 * @author: Todd Allen
 *
 * Creation date: 12/05/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Request to view shipment invoice documents
 */
@ApiModel(description="Customer shipment invoice amount details")
@Data
public class ImageRequest
{
	@ApiModelProperty(notes="Estes shipment PRO XXX-XXXXXXX")
	String pro;
	@ApiModelProperty(notes="Show scanned bill of lading image?")
	boolean showBol;
	@ApiModelProperty(notes="Show scanned delivery receipt image?")
	boolean showDr;

	public ImageRequest()
	{
	} // constructor

	/**
	 * Get the PRO
	 * 
	 * @return Estes PRO
	 */
	public String getPro()
	{
		return this.pro;
	} // getPro

	/**
	 * Show BOL image?
	 * 
	 * @return Indicator of whether to show scanned BOL image
	 */
	public boolean isShowBol()
	{
		return this.showBol;
	} // isShowBol

	/**
	 * Show DR image?
	 * 
	 * @return Indicator of whether to show scanned DR image
	 */
	public boolean isShowDr()
	{
		return this.showDr;
	} // isShowDr

	/**
	 * Set the PRO
	 * 
	 * @param val PRO
	 */
	public void setPro(String val)
	{
		this.pro = val.trim();
	} // setPro

	/**
	 * Set indicator of whether to get BOL image
	 * 
	 * @param tf Indicator of whether to show scanned BOL image
	 */
	public void setShowBol(boolean tf)
	{
		this.showBol = tf;
	} // setShowBol

	/**
	 * Set indicator of whether to get DR image
	 * 
	 * @param tf Indicator of whether to show scanned DR image
	 */
	public void setShowDr(boolean tf)
	{
		this.showDr = tf;
	} // setShowDr
}
