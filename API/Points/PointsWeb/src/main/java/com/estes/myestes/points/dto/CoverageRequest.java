/**
 * @author: Todd Allen
 *
 * Creation date: 09/18/2018
 */

package com.estes.myestes.points.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes coverage service request
 */
@ApiModel(description="Request for Estes service coverage to a given point")
public class CoverageRequest
{
	@ApiModelProperty(notes="2-letter country abbreviation of point")
	String country;
	@ApiModelProperty(notes="City name of point")
	String city;
	@ApiModelProperty(notes="State/province abbreviation of point")
	String state;
	@ApiModelProperty(notes="Zip/Postal code of point")
	String zip;
	@ApiModelProperty(notes="E-mail address to receive list of next day points from terminal")
	String email;
	@ApiModelProperty(notes="File format - XLS/CSV/TXT")
	String fileFormat;
	@ApiModelProperty(notes="File sort order - CTY = city/ZIP = zip code")
	String fileSort;

	/**
	 * Create new point DTO
	 */
	public CoverageRequest()
	{
	} // Constructor

	/**
	 * Create new point DTO
	 */
	public CoverageRequest(String ctry, String cty, String st, String zp)
	{
		this();
		this.country = ctry;
		this.city = cty;
		this.state = st;
		this.zip = zp;
	} // Constructor

	/**
	 * Get the city
	 * 
	 * @return City
	 */
	public String getCity()
	{
		// Truncate to 20 characters
		if (this.city != null && this.city.length() > 20) {
			return this.city.substring(0, 20);
		}

		return this.city.toUpperCase();
	} // getCity

	/**
	 * Get the country abbreviation
	 * 
	 * @return Country abbreviation
	 */
	public String getCountry()
	{
		return this.country.toUpperCase();
	} // getCountry

	/**
	 * Get the requester e-mail address
	 * 
	 * @return E-mail address
	 */
	public String getEmail()
	{
		return this.email;
	} // getEmail

	/**
	 * Get the file format
	 * 
	 * @return File format
	 */
	public String getFileFormat()
	{
		return this.fileFormat;
	} // getFileFormat

	/**
	 * Get the file sort order
	 * 
	 * @return Sort order of file
	 */
	public String getFileSort()
	{
		return this.fileSort;
	} // getFileSort

	/**
	 * Get the state
	 * 
	 * @return State
	 */
	public String getState()
	{
		// Truncate to 2 characters
		if (this.state != null && this.state.length() > 2) {
			return this.state.substring(0, 2).toUpperCase();
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
		// Truncate to 2 characters
		if (this.zip != null && this.zip.length() > 6) {
			return this.zip.substring(0, 6).toUpperCase();
		}
		return this.zip.toUpperCase();
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
	 * Set the requester e-mail address
	 * 
	 * @param val E-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val.trim();
	} // setEmail

	/**
	 * Set the file format
	 * 
	 * @param val Format of file
	 */
	public void setFileFormat(String val)
	{
		this.fileFormat = val;
	} // setFileFormat

	/**
	 * Set the file sort order
	 * 
	 * @param val Sort order of file
	 */
	public void setFileSort(String val)
	{
		this.fileSort = val;
	} // setFileSort

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
}

