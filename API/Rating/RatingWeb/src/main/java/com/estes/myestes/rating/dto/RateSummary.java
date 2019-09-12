/**
 * @author: Todd Allen
 *
 * Creation date: 02/28/2019
 */

package com.estes.myestes.rating.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Rate quote history summary data
 */
@ApiModel(description="Rate quote history summary data")
@Data
public class RateSummary
{
	@ApiModelProperty(position=1, notes="Quote ID")
	private String quoteID;
	@ApiModelProperty(notes="Quote source - G=Global solution center/Q=Net.Data")
	String source;
	@ApiModelProperty(notes="Quote date - MM/dd/yyyy")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate quoteDate;
	@ApiModelProperty(notes="Origin point")
	Point origin;
	@ApiModelProperty(notes="Destination point")
	Point dest;
	@ApiModelProperty(notes="Service level description")
	private int serviceLevelId;
	@ApiModelProperty(notes="Service level description")
	private String serviceLevel;
	@ApiModelProperty(notes="Estimated total charges")
	double estCharges;

	@ApiModelProperty(notes="Delivery Date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private LocalDate deliveryDate;
	
	@ApiModelProperty(notes="Delivery Time - hh:mm a (ex. 08:30 AM / 05:00 PM)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	private LocalTime deliveryTime;
	/**
	 * Create new rate quote summary
	 */
	public RateSummary()
	{
		this.origin = new Point();
		this.dest = new Point();
	} // Constructor

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
	 * Get estimated charges amount
	 * 
	 * @return Estimated charges
	 */
	public double getEstCharges()
	{
		return estCharges;
	} // getEstCharges

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
	 * Get quotation date
	 * 
	 * @return Quote date
	 */
	public LocalDate getQuoteDate()
	{
		return this.quoteDate;
	} // getQuoteDate

	/**
	 * Get the quote ID
	 * 
	 * @return Quote ID
	 */
	public String getQuoteID()
	{
		return this.quoteID.trim();
	} // getQuoteID

	/**
	 * Get service level description
	 * 
	 * @return Service level
	 */
	public String getServiceLevel()
	{
		return this.serviceLevel.trim();
	} // getServiceLevel

	/**
	 * Get the quote source
	 * 
	 * @return Quote source
	 */
	public String getSource()
	{
		return this.source.trim();
	} // getSource

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
	 * Set estimated charges amount
	 * 
	 * @param val Estimated charges
	 */
	public void setEstCharges(double val)
	{
		this.estCharges = val;
	} // setEstCharges

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
	 * Set quotation date
	 * 
	 * @param val Quote date
	 */
	public void setQuoteDate(LocalDate val)
	{
		this.quoteDate = val;
	} // setQuoteDate

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
	 * Set service level description
	 * 
	 * @param val Service level
	 */
	public void setServiceLevel(String val)
	{
		this.serviceLevel = val;
	} // setServiceLevel

	/**
	 * Set the quote source code
	 * 
	 * @param val Quote source
	 */
	public void setSource(String val)
	{
		this.source = val;
	} // setSource

	public LocalDate getDeliveryDate() {
		return deliveryDate;
	}

	public void setDeliveryDate(LocalDate deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public LocalTime getDeliveryTime() {
		return deliveryTime;
	}

	public void setDeliveryTime(LocalTime deliveryTime) {
		this.deliveryTime = deliveryTime;
	}
	
	
}
