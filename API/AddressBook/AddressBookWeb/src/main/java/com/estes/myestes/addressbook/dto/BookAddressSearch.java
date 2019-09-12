/**
 * @author: Todd Allen
 *
 * Creation date: 05/22/2018
 */

package com.estes.myestes.addressbook.dto;

import io.swagger.annotations.ApiModelProperty;

public class BookAddressSearch
{
	@ApiModelProperty(notes="My Estes user name - leave blank")
	private String user;
	@ApiModelProperty(notes="Company name to search for")
	private String company;
	@ApiModelProperty(notes="Search for exact match on company name?")
	private boolean companyExact;
	@ApiModelProperty(notes="Search for addresses containing company name?")
	private boolean companyContains;
	@ApiModelProperty(notes="Street address to search for")
	private String streetAddress;
	@ApiModelProperty(notes="Search for exact match on street address?")
	private boolean streetExact;
	@ApiModelProperty(notes="Search for addresses containing company name?")
	private boolean streetContains;
	@ApiModelProperty(notes="City to search for")
	private String city;
	@ApiModelProperty(notes="Search for exact match on city?")
	private boolean cityExact;
	@ApiModelProperty(notes="Search for addresses containing city?")
	private boolean cityContains;
	@ApiModelProperty(notes="State/province to search for")
	private String state;
	@ApiModelProperty(notes="Search for exact match on state/province?")
	private boolean stateExact;
	@ApiModelProperty(notes="Search for addresses containing state/province?")
	private boolean stateContains;
	@ApiModelProperty(notes="Postal/zip code to search for")
	private String zip;
	@ApiModelProperty(notes="Search for exact match on postal/zip code?")
	private boolean zipExact;
	@ApiModelProperty(notes="Search for addresses containing postal/zip code?")
	private boolean zipContains;
	@ApiModelProperty(notes="Location number to search for")
	private String locationNumber;
	@ApiModelProperty(notes="Search for exact match on location number?")
	private boolean locExact;
	@ApiModelProperty(notes="Search for addresses containing location number?")
	private boolean locContains;

	public BookAddressSearch() {
	}

	public String getCity() {
		return city;
	}

	public String getCompany() {
		return company;
	}

	public String getLocationNumber() {
		return this.locationNumber;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public String getState() {
		return state;
	}

	public String getUser() {
		return user;
	}

	public String getZip() {
		return zip;
	}

	public boolean isCityContains() {
		return cityContains;
	}

	public boolean isCityExact() {
		return cityExact;
	}

	public boolean isCompanyContains() {
		return companyContains;
	}

	public boolean isCompanyExact() {
		return companyExact;
	}

	public boolean isLocContains() {
		return locContains;
	}

	public boolean isLocExact() {
		return locExact;
	}

	public boolean isStateContains() {
		return stateContains;
	}

	public boolean isStateExact() {
		return stateExact;
	}

	public boolean isStreetContains() {
		return streetContains;
	}

	public boolean isStreetExact() {
		return streetExact;
	}

	public boolean isZipContains() {
		return zipContains;
	}

	public boolean isZipExact() {
		return zipExact;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setCityContains(boolean contains) {
		this.cityContains = contains;
	}

	public void setCityExact(boolean exact) {
		this.cityExact = exact;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setCompanyContains(boolean contains) {
		this.companyContains = contains;
	}

	public void setCompanyExact(boolean exact) {
		this.companyExact = exact;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setStateContains(boolean contains) {
		this.stateContains = contains;
	}

	public void setStateExact(boolean exact) {
		this.stateExact = exact;
	}

	public void setLocationNumber(String location) {
		this.locationNumber = location;
	}

	public void setLocContains(boolean contains) {
		this.locContains = contains;
	}

	public void setLocExact(boolean exact) {
		this.locExact = exact;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public void setStreetContains(boolean contains) {
		this.streetContains = contains;
	}

	public void setStreetExact(boolean exact) {
		this.streetExact = exact;
	}

	public void setUser(String userName) {
		this.user = userName;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setZipContains(boolean contains) {
		this.zipContains = contains;
	}

	public void setZipExact(boolean exact) {
		this.zipExact = exact;
	}
}
