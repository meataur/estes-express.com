/**
 * @author: Todd Allen
 *
 * Creation date: 10/05/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice payment
 */
@ApiModel(description="Estes customer invoice payment")
@Data
public class Payment
{
	@ApiModelProperty(notes="Estes shipment PRO XXX-XXXXXXX")
	String pro;
	@ApiModelProperty(notes="Open amount")
	double openAmount;
	@ApiModelProperty(notes="Payment pending amount")
	double pendingPayAmount;
	@ApiModelProperty(notes="Payment amount")
	double payment;
	@ApiModelProperty(notes="Payment amount change reason - NMB=Not my bill/PAI-Paid/PRI=Pricing/OTH=Other")
	String reasonCode;
	@ApiModelProperty(notes="Payment amount change explanation")
	String explain;

	public Payment()
	{
	} // constructor

	/**
	 * Get the explanation text
	 * 
	 * @return Explanation
	 */
	public String getExplain()
	{
		return this.explain;
	} // getExplain

	public double getOpenAmount()
	{
		return openAmount;
	}

	public double getPayment()
	{
		return this.payment;
	}

	public double getPendingPayAmount()
	{
		return pendingPayAmount;
	}

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
	 * Get the reason code
	 * 
	 * @return Reason code
	 */
	public String getReasonCode()
	{
		return this.reasonCode;
	} // getReasonCode

	/**
	 * Set the explanation text
	 * 
	 * @param val Explanation
	 */
	public void setExplain(String val)
	{
		this.explain = val.trim();
	} // setExplain

	/**
	 * Set the payment amount
	 * 
	 * @param val Payment
	 */
	public void setPayment(double val)
	{
		this.payment = val;
	} // setPayment

	public void setOpenAmount(double openAmount)
	{
		this.openAmount = openAmount;
	}

	public void setPendingPayAmount(double pendingPayAmount)
	{
		this.pendingPayAmount = pendingPayAmount;
	}

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
	 * Set the reason code
	 * 
	 * @param val Reason code
	 */
	public void setReasonCode(String val)
	{
		this.reasonCode = val.trim();
	} // setReasonCode
}
