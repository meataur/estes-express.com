/**
 * @author: Todd Allen
 *
 * Creation date: 12/13/2017
 */

package com.estesexpress.currency.convert.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Currency rate DTO
 */
public class RateDTO
{
	@ApiModelProperty(notes="3-letter country currency abbreviation")
	String country;
	@ApiModelProperty(notes="Date of exchange rate MM/DD/CCYY")
	String exchangeDate;
	@ApiModelProperty(notes="Day of week - 0=Sunday")
	int dayOfWeek;
	@ApiModelProperty(notes="Exchange rate")
	double rate;
	@ApiModelProperty(notes="US exchange rate")
	double rateUS;

	/**
	 * Create a new rate object
	 */
	public RateDTO()
	{
		super();
	} // Constructor

	/**
	 * Get the country abbreviation
	 * 
	 * @return Country abbreviation
	 */
	public String getCountry()
	{
		return this.country;
	} // getCountry

	/**
	 * Get the numeric day of the week
	 * 
	 * @return Numeric day of the week
	 */
	public int getDayOfWeek()
	{
		return this.dayOfWeek;
	} // getDayOfWeek

	/**
	 * Get the exchange date
	 * 
	 * @return Exchange date
	 */
	public String getExchangeDate()
	{
		return this.exchangeDate;
	} // getExchangeDate

	/**
	 * Get the currency rate
	 * 
	 * @return Currency rate
	 */
	public double getRate()
	{
		return this.rate;
	} // getRate

	/**
	 * Get the US exchange rate
	 * 
	 * @return US exchange rate
	 */
	public double getRateUS()
	{
		return this.rateUS;
	} // getRateUS

	/**
	 * Set the country abbreviation
	 * 
	 * @param val Country abbreviation
	 */
	public void setCountry(String val)
	{
		this.country = val;
	} // setCountry

	/**
	 * Set the numeric day of the week
	 * Week starts with Sunday = 1
	 * 
	 * @param val Numeric day of the week
	 */
	public void setDayOfWeek(int val)
	{
		this.dayOfWeek = val;
	} // setDayOfWeek

	/**
	 * Set the exchange date
	 * 
	 * @param val Exchange date
	 */
	public void setExchangeDate(String val)
	{
		this.exchangeDate = val;
	} // setExchangeDate

	/**
	 * Set the currency rate
	 * 
	 * @param val Currency rate
	 */
	public void setRate(double val)
	{
		this.rate = val;
	} // setRate

	/**
	 * Set the US exchange rate
	 * 
	 * @param val US exchange rate
	 */
	public void setRateUS(double val)
	{
		this.rateUS = val;
	} // setRateUS
}
