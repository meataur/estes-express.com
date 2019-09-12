/**
 * @author: Todd Allen
 *
 * Creation date: 11/26/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer statement detail item
 */
@ApiModel(description="Customer statement item details")
@Data
public class StatementDetail
{
	@ApiModelProperty(notes="Estes shipment PRO")
	String pro;
	@ApiModelProperty(notes="Bill of lading number")
	String bol;
	@ApiModelProperty(notes="Purchase order number")
	String poNum;
	@ApiModelProperty(notes="Freight bill date YYYYMMDD")
	int freightBillDate;
	@ApiModelProperty(notes="Open amount")
	double openAmount;

	public StatementDetail()
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

	public int getFreightBillDate()
	{
		return freightBillDate;
	}

	public double getOpenAmount()
	{
		return openAmount;
	}

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
	 * Set the bill of lading number
	 * 
	 * @param val BOL number
	 */
	public void setBol(String val)
	{
		this.bol = val.trim();
	} // setBol

	public void setFreightBillDate(int fbDate)
	{
		this.freightBillDate = fbDate;
	}

	public void setOpenAmount(double openAmount)
	{
		this.openAmount = openAmount;
	}

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
}
