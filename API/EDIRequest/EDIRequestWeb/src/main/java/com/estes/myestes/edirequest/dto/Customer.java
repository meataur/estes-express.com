/**
 *  * @author: Lakshman K
 *
 * Creation date: 1/3/2019
 */
package com.estes.myestes.edirequest.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Customer Information
 */
public class Customer {
		@ApiModelProperty(notes=" Customer name ")
		private String name="";
		@ApiModelProperty(notes=" Customer address ")
		private String address="";
		@ApiModelProperty(notes=" Customer city ")
		private String city="";
		@ApiModelProperty(notes=" Customer state ")
		private String state="";
		@ApiModelProperty(notes=" Customer zip ")
		private String zip="";	
		@ApiModelProperty(notes=" Customer phone: (xxx) xxx-xxxx ")
		private String phone;
		@ApiModelProperty(notes=" Phone Extn – xxxx ")
		private String extension;
		@ApiModelProperty(notes=" Customer fax: (xxx) xxx-xxxx")
		private String fax;
		@ApiModelProperty(notes=" Customer website ")
		private String website = "";
		@ApiModelProperty(notes=" Freight payment agency ")
		private String freightPaymentAgency="";
		@ApiModelProperty(notes=" Freight management company ")
		private String freightManagementCompany="";
		/**
		 * @return the name
		 */
		public String getName() {
			return name;
		}
		/**
		 * @param name the name to set
		 */
		public void setName(String name) {
			this.name = name;
		}
		/**
		 * @return the address
		 */
		public String getAddress() {
			return address;
		}
		/**
		 * @param address the address to set
		 */
		public void setAddress(String address) {
			this.address = address;
		}
		/**
		 * @return the city
		 */
		public String getCity() {
			return city;
		}
		/**
		 * @param city the city to set
		 */
		public void setCity(String city) {
			this.city = city;
		}
		/**
		 * @return the state
		 */
		public String getState() {
			return state;
		}
		/**
		 * @param state the state to set
		 */
		public void setState(String state) {
			this.state = state;
		}
		/**
		 * @return the zip
		 */
		public String getZip() {
			return zip;
		}
		/**
		 * @param zip the zip to set
		 */
		public void setZip(String zip) {
			this.zip = zip;
		}
		/**
		 * @return the phone
		 */
		public String getPhone() {
			return phone;
		}
		/**
		 * @param phone the phone to set
		 */
		public void setPhone(String phone) {
			this.phone = phone;
		}
		/**
		 * @return the fax
		 */
		public String getFax() {
			return fax;
		}
		/**
		 * @param fax the fax to set
		 */
		public void setFax(String fax) {
			this.fax = fax;
		}
		/**
		 * @return the website
		 */
		public String getWebsite() {
			return website;
		}
		/**
		 * @param website the website to set
		 */
		public void setWebsite(String website) {
			this.website = website;
		}
		/**
		 * @return the freightPaymentAgency
		 */
		public String getFreightPaymentAgency() {
			return freightPaymentAgency;
		}
		/**
		 * @param freightPaymentAgency the freightPaymentAgency to set
		 */
		public void setFreightPaymentAgency(String freightPaymentAgency) {
			this.freightPaymentAgency = freightPaymentAgency;
		}
		/**
		 * @return the freightManagementCompany
		 */
		public String getFreightManagementCompany() {
			return freightManagementCompany;
		}
		/**
		 * @param freightManagementCompany the freightManagementCompany to set
		 */
		public void setFreightManagementCompany(String freightManagementCompany) {
			this.freightManagementCompany = freightManagementCompany;
		}
		/**
		 * @return the extension
		 */
		public String getExtension() {
			return extension;
		}
		/**
		 * @param extension the extension to set
		 */
		public void setExtension(String extension) {
			this.extension = extension;
		}		
}
