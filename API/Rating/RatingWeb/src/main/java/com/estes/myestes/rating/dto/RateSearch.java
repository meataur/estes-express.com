/**
 * @author: Todd Allen
 *
 * Creation date: 02/13/2019
 */

package com.estes.myestes.rating.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Rate quote history search criteria
 */
@ApiModel(description="Rate quote history search criteria")
public class RateSearch
{
	@ApiModelProperty(hidden=true, notes="My Estes user name")
	private String user;
	@ApiModelProperty(position=1, notes="Quote ID")
	private String quoteID;
	@ApiModelProperty(notes="From date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate fromDate;
	@ApiModelProperty(notes="To date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate toDate;
	@ApiModelProperty(notes="Account code/number - blank for local accounts")
	private String accountCode;
	@ApiModelProperty(notes="Origin point")
	private Point origin;
	@ApiModelProperty(notes="Destination point")
	private Point dest;
	@ApiModelProperty(notes="Service level ID")
	private int serviceLevel;
	@ApiModelProperty(notes="Minimum charge amount")
	private double minCharges;
	@ApiModelProperty( notes="Maximum charge amount")
	private double maxCharges;

	/**
	 * Create new rate quote history search request
	 */
	public RateSearch()
	{
		this.origin = new Point();
		this.dest = new Point();
	} // Constructor

	/**
	 * Get the account code/number
	 * 
	 * @return Account Code
	 */
	public String getAccountCode()
	{
		return accountCode;
	} // getAccountCode

	/**
	 * Get maximum charge amount
	 * 
	 * @return Maximum charges
	 */
	public double getMaxCharges()
	{
		return maxCharges;
	} // getMaxCharges

	/**
	 * Get minimum charge amount
	 * 
	 * @return Minimum charges
	 */
	public double getMinCharges()
	{
		return minCharges;
	} // getMinCharges

	/**
	 * Get the destination point
	 * 
	 * @return Destination point
	 */
	public Point getDest()
	{
		return this.dest;
	} // getDest

	/**
	 * Get the origin point
	 * 
	 * @return Origin point
	 */
	public Point getOrigin()
	{
		return this.origin;
	} // getOrigin

	/**
	 * Get from date
	 * 
	 * @return From date
	 */
	public LocalDate getFromDate()
	{
		return this.fromDate;
	} // getFromDate

	/**
	 * Get the quote ID
	 * 
	 * @return Quote ID
	 */
	public String getQuoteID()
	{
		return this.quoteID;
	} // getQuoteID

	/**
	 * Get service level ID
	 * 
	 * @return Service level
	 */
	public int getServiceLevel()
	{
		return this.serviceLevel;
	} // getServiceLevel

	/**
	 * Get to date
	 * 
	 * @return To date
	 */
	public LocalDate getToDate()
	{
		return this.toDate;
	} // getToDate

	/**
	 * Get the user name
	 * 
	 * @return My Estes user name
	 */
	public String getUser()
	{
		return this.user;
	} // getUser

	/**
	 * Set the account code/number
	 * 
	 * @param accountCode
	 */
	public void setAccountCode(String accountCode)
	{
		this.accountCode = accountCode;
	} // setAccountCode

	/**
	 * Set the destination point
	 * 
	 * @param pt Destination point
	 */
	public void setDest(Point pt)
	{
		this.dest = pt;
	} // setDest

	/**
	 * Set maximum charge amount
	 * 
	 * @param val Maximum charges
	 */
	public void setMaxCharges(double val)
	{
		this.maxCharges = val;
	} // setMaxCharges

	/**
	 * Set minimum charge amount
	 * 
	 * @param val Minimum charges
	 */
	public void setMinCharges(double val)
	{
		this.minCharges = val;
	} // setMinCharges

	/**
	 * Set the origin point
	 * 
	 * @param pt Origin point
	 */
	public void setOrigin(Point pt)
	{
		this.origin = pt;
	} // setOrigin

	/**
	 * Set from date
	 * 
	 * @param val From date
	 */
	public void setFromDate(LocalDate val)
	{
		this.fromDate = val;
	} // setFromDate

	/**
	 * Set the quote ID
	 * 
	 * @param val Quote ID
	 */
	public void setQuoteID(String val)
	{
		this.quoteID = val;
	} // setQuoteID

	/**
	 * Set service level ID
	 * 
	 * @param val Service level
	 */
	public void setServiceLevel(int val)
	{
		this.serviceLevel = val;
	} // setServiceLevel

	/**
	 * Set to date
	 * 
	 * @param val To date
	 */
	public void setToDate(LocalDate val)
	{
		this.toDate = val;
	} // setToDate

	/**
	 * Set the My Estes user name
	 * 
	 * @param val User name
	 */
	public void setUser(String val)
	{
		this.user = val.trim();
	} // setUser
}
