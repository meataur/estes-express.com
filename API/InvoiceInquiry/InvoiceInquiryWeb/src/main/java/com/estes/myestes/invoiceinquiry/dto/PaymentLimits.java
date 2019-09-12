/**
 * @author: Todd Allen
 *
 * Creation date: 01/07/2019
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice payment limits
 */
@ApiModel(description="Estes customer invoice payment")
@Data
public class PaymentLimits
{
	@ApiModelProperty(notes="Minimum total payment amount")
	double minimum;
	@ApiModelProperty(notes="Maximum total payment amount")
	double maximum;

	public PaymentLimits()
	{
	} // constructor

	public double getMaximum()
	{
		return maximum;
	}

	public double getMinimum()
	{
		return minimum;
	}

	/**
	 * Set the maximum total payment amount
	 * 
	 * @param val payment amount
	 */
	public void setMaximum(double val)
	{
		this.maximum = val;
	}

	/**
	 * Set the minimum total payment amount
	 * 
	 * @param val payment amount
	 */
	public void setMinimum(double val)
	{
		this.minimum = val;
	}
}
