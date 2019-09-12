/**
 * @author: Todd Allen
 *
 * Creation date: 01/04/2019
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice payment reasons
 */
@ApiModel(description="Estes customer invoice short payment reasons")
@Data
public class PaymentReason
{
	@ApiModelProperty(notes="Payment adjustment reason code")
	String code;
	@ApiModelProperty(notes="Payment reason code description")
	String description;
	@ApiModelProperty(notes="Payment adjustment explanation required?")
	boolean explain;

	public PaymentReason()
	{
	} // constructor

	/**
	 * Get the reason code
	 * 
	 * @return Reason code
	 */
	public String getCode()
	{
		return this.code;
	} // getCode

	/**
	 * Get the description
	 * 
	 * @return Description
	 */
	public String getDescription()
	{
		return this.description;
	} // getDescription

	/**
	 * Get the explanation flag
	 * 
	 * @return Explanation flag
	 */
	public boolean isExplain()
	{
		return this.explain;
	} // getExplain

	/**
	 * Set the reason code
	 * 
	 * @param val Reason code
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
	public void setDescription(String val)
	{
		this.description = val.trim();
	} // setDescription

	/**
	 * Set the explanation flag
	 * 
	 * @param tf Explanation flag
	 */
	public void setExplain(boolean tf)
	{
		this.explain = tf;
	} // setExplain
}
