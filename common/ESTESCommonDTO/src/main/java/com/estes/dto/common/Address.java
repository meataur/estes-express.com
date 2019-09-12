/**
 * @author: Todd Allen
 *
 * Creation date: 02/08/2018
 */

package com.estes.dto.common;

/**
 * Address DTO
 */
public class Address
{
	String streetAddress;
	String streetAddress2;
	String city;
	String state;
	String zip;
	String zip4;
	String country;

	/**
	 * Get the city
	 * 
	 * @return City
	 */
	public String getCity()
	{
		return this.city == null ? null : this.city.trim();
	} // getCity

	/**
	 * Get the country
	 * 
	 * @return Country
	 */
	public String getCountry()
	{
		return this.country == null ? null : this.country.trim();
	} // getCountrty

	/**
	 * Get the state
	 * 
	 * @return State
	 */
	public String getState()
	{
		return this.state == null ? null : this.state.trim();
	} // getState

	/**
	 * Get the street address
	 * 
	 * @return Street address
	 */
	public String getStreetAddress()
	{
		return this.streetAddress == null ? null : this.streetAddress.trim();
	} // getStreetAddress

	/**
	 * Get the 2nd street address
	 * 
	 * @return Street address #2
	 */
	public String getStreetAddress2()
	{
		return this.streetAddress2 == null ? null : this.streetAddress2.trim();
	} // getStreetAddress2

	/**
	 * Get the postal/zip code
	 * 
	 * @return Zip code
	 */
	public String getZip()
	{
		return this.zip == null ? null : this.zip.trim();
	} // getZip

	/**
	 * Get the zip+4 code
	 * 
	 * @return Zip+4 code
	 */
	public String getZip4()
	{
		return this.zip4 == null ? null : this.zip4.trim();
	} // getZip4

	/**
	 * Set the city
	 * 
	 * @param val City
	 */
	public void setCity(String val)
	{
		this.city = val;
	} // setCity

	/**
	 * Set the country
	 * 
	 * @param val Country
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
	 * Set the street address
	 * 
	 * @param val Street address
	 */
	public void setStreetAddress(String val)
	{
		this.streetAddress = val;
	} // setStreetAddress

	/**
	 * Set the 2nd street address
	 * 
	 * @param val Street address #2
	 */
	public void setStreetAddress2(String val)
	{
		this.streetAddress2 = val;
	} // setStreetAddress2

	/**
	 * Set the postal/zip code
	 * 
	 * @param val Zip code
	 */
	public void setZip(String val)
	{
		this.zip = val;
	} // setZip

	/**
	 * Set the zip+4 code
	 * 
	 * @param val Zip+4 code
	 */
	public void setZip4(String val)
	{
		this.zip4 = val;
	} // setZip4

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Address [streetAddress=" + streetAddress + ", streetAddress2=" + streetAddress2 + ", city=" + city
				+ ", state=" + state + ", zip=" + zip + ", zip4=" + zip4 + ", country=" + country + "]";
	}//toString() 
	
	
}
