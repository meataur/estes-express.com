/**
 * 
 * @author SinghPa
 *
 */
package com.estes.framework.dto;

public class AddressPoint
{
    private String city;
    private String state;
    private String zip;
    private String country;
    private String point;

	public AddressPoint()
	{
	} // Constructor

	/**
	 * Create new address point DTO
	 */
	public AddressPoint(String ctry, String cty, String st, String zp)
	{
		this();
		setCountry(ctry);
		setCity(cty);
		setState(st);
		setZip(zp);
	} // Constructor

	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getCity() {
		return city;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
} 
