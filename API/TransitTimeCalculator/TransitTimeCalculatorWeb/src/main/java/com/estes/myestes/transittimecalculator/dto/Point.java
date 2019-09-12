/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 */

package com.estes.myestes.transittimecalculator.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes point DTO
 */

@ApiModel(description="Estes delivery point")
public class Point
{
	@ApiModelProperty(notes="2-letter country abbreviation")
	String country;
	@ApiModelProperty(notes="City name")
	String city;
	@ApiModelProperty(notes="2-letter state/province abbreviation; 3 letters for MX")
	String state;
	@ApiModelProperty(notes="Zip/Postal code")
	String zip;

	/**
	 * Create new point DTO
	 */
	public Point()
	{
	} // Constructor

	/**
	 * Create new point DTO
	 */
	public Point(String ctry, String cty, String st, String zp)
	{
		this();
		this.country = ctry.toUpperCase();
		this.city = cty.toUpperCase();
		this.state = st.toUpperCase();
		this.zip = zp.toUpperCase();
	} // Constructor

	/**
	 * Get the city
	 * 
	 * @return City
	 */
	public String getCity()
	{
		return this.city;
	} // getCity

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
	 * Get the state
	 * 
	 * @return State
	 */
	public String getState()
	{
		// Truncate to 3 characters (Mexican states are 3 characters) 
		if (this.state != null && this.state.length() > 3) {
			return this.state.substring(0, 3).toUpperCase();
		}

		return this.state.toUpperCase();
	} // getState

	/**
	 * Get the postal/zip code
	 * 
	 * @return Zip code
	 */
	public String getZip()
	{
		return this.zip;
	} // getZip

	/**
	 * Set the city
	 * 
	 * @param val City
	 */
	public void setCity(String val)
	{
		this.city = val.trim();
	} // setCity

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
	 * Set the state
	 * 
	 * @param val State
	 */
	public void setState(String val)
	{
		this.state = val;
	} // setState

	/**
	 * Set the postal/zip code
	 * 
	 * @param val Zip code
	 */
	public void setZip(String val)
	{
		this.zip = val;
	} // setZip

	@Override
	public String toString() {
		return "country=" + country + ", city=" + city + ", state=" + state + ", zip=" + zip;
	}
	
}
