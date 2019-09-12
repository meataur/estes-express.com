/**
 * @author: shelender singh
 *
 * Creation date: 09/13/2018
 */

package com.estes.myestes.terminallist.dto;


import io.swagger.annotations.ApiModelProperty;

/**
 * Terminal List Object
 */
public class Terminal
{
	@ApiModelProperty(notes="Terminal state/province served")
	public String stateName;
	@ApiModelProperty(notes="Terminal alpha code")
	String alphaCode;
	@ApiModelProperty(notes="Terminal number")
	String terminal ;
	@ApiModelProperty(notes="Terminal name ")
	String terminalName;
	@ApiModelProperty(notes="Terminal street address")
	String  streetaddress;
	@ApiModelProperty(notes="Terminal city ")
	String  city;
	@ApiModelProperty(notes="Terminal 2-letter state abbreviation")
	String state;
	@ApiModelProperty(notes="Terminal zip/postal code")
	String zip;
	@ApiModelProperty(notes="Terminal phone number (XXX) XXX-XXXX" )
	String phone;
	@ApiModelProperty(notes="Terminal fax number (XXX) XXX-XXXX")
	String fax;
	@ApiModelProperty(notes="Display Options - 3 bytes containing any combination of 3 flags - " +
			"A = omit terminal from terminal list/" + 
			"B = display terminal as a consolidation terminal/" + 
			"C = omit link to terminal map and details")
	String displayOptions;
	@ApiModelProperty(notes="Country -US/CN/MX")
	String country;
	@ApiModelProperty(notes="Link to map image")
	String maps;
	
	public String getAlphaCode() {
		return alphaCode;
	}
	public void setAlphaCode(String alphaCode) {
		this.alphaCode = alphaCode;
	}
	public String getTerminal() {
		return terminal;
	}
	public void setTerminal(String terminal) {
		this.terminal = terminal;
	}
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public String getStreetaddress() {
		return streetaddress;
	}
	public void setStreetaddress(String streetaddress) {
		this.streetaddress = streetaddress;
	}
	public String getCity() {
		return city;
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
		return zip == null ? null : zip.trim();
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getDisplayOptions() {
		return displayOptions;
	}
	public void setDisplayOptions(String displayOptions) {
		this.displayOptions = displayOptions;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getMaps() {
		return maps;
	}
	public void setMaps(String maps) {
		this.maps = maps;
	}

}
