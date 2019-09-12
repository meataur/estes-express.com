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
 * Customer invoice details
 */
@ApiModel(description="Customer shipment invoice details")
@Data
public class AgingDetail
{
	@ApiModelProperty(notes="Estes shipment PRO XXX-XXXXXXX")
	String pro;
	@ApiModelProperty(notes="Pickup date YYYYMMDD")
	int pickupDate;
	@ApiModelProperty(notes="Delivery date YYYYMMDD")
	int deliveryDate;
	@ApiModelProperty(notes="Invoice date YYYYMMDD")
	int invoiceDate;
	@ApiModelProperty(notes="Bill of lading number")
	String bol;
	@ApiModelProperty(notes="Purchase order number")
	String poNum;
	@ApiModelProperty(notes="Statement number")
	int statementNum;
	@ApiModelProperty(notes="Open amount")
	double openAmount;
	@ApiModelProperty(notes="Payment pending amount")
	double pendingPayAmount;

	public AgingDetail()
	{
	} // constructor

	/**
	 * Get the bill of lading number
	 * 
	 * @return BOL number
	 */
	public String getBol()
	{
		return this.bol;
	} // getBol

	/**
	 * Get the delivery date
	 * 
	 * @return Delivery date
	 */
	public int getDeliveryDate()
	{
		return this.deliveryDate;
	} // getDeliveryDate

	public int getInvoiceDate()
	{
		return invoiceDate;
	}

	public double getOpenAmount()
	{
		return openAmount;
	}

	public double getPendingPayAmount()
	{
		return pendingPayAmount;
	}

	/**
	 * Get the pickup date
	 * 
	 * @return Pickup date
	 */
	public int getPickupDate()
	{
		return this.pickupDate;
	} // getPickupDate

	/**
	 * Get the purchase order number
	 * 
	 * @return PO number
	 */
	public String getPoNum()
	{
		return this.poNum;
	} // getPoNum

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
	 * Get the statement number
	 * 
	 * @return Statement number
	 */
	public int getStatementNum()
	{
		return this.statementNum;
	} // getStatementNum

	/**
	 * Set the bill of lading number
	 * 
	 * @param val BOL number
	 */
	public void setBol(String val)
	{
		this.bol = val.trim();
	} // setBol

	/**
	 * Set the delivery date
	 * 
	 * @param val Delivery date
	 */
	public void setDeliveryDate(int val)
	{
		this.deliveryDate = val;
	} // setDeliveryDate

	public void setInvoiceDate(int invoiceDate)
	{
		this.invoiceDate = invoiceDate;
	}

	public void setOpenAmount(double openAmount)
	{
		this.openAmount = openAmount;
	}

	public void setPendingPayAmount(double pendingPayAmount)
	{
		this.pendingPayAmount = pendingPayAmount;
	}

	/**
	 * Set the pickup date
	 * 
	 * @param val Pickup date
	 */
	public void setPickupDate(int val)
	{
		this.pickupDate = val;
	} // setPickupDate

	/**
	 * Set the purchase order number
	 * 
	 * @param val PO number
	 */
	public void setPoNum(String val)
	{
		this.poNum = val.trim();
	} // setPoNum

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
	 * Set the statement number
	 * 
	 * @param val Statement number
	 */
	public void setStatementNum(int val)
	{
		this.statementNum = val;
	} // setStatementNum
}
