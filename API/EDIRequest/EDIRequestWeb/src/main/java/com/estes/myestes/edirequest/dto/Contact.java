/**
 * @author: Lakshman K
 *
 * Creation date: 1/3/2019
 */
package com.estes.myestes.edirequest.dto;

import io.swagger.annotations.ApiModelProperty;

public class Contact {

	@ApiModelProperty(notes=" Contact name ")
	private String name = "";
	@ApiModelProperty(notes=" Contact title ")
	private String title = "";
	@ApiModelProperty(notes=" Contact email ")
	private String email = "";
	@ApiModelProperty(notes=" Contact phone : (xxx) xxx-xxxx ")
	private String phone = null;
	@ApiModelProperty(notes=" Phone Extn – xxxx ")
	private String extension;
	@ApiModelProperty(notes=" Contact fax : (xxx) xxx-xxxx ")
	private String fax = null;
	
	public Contact() {
	}
	
	public Contact(String name,String title,String email,String phone,String fax){
		this.name=name;
		this.title=title;
		this.email=email;
		this.phone=phone;
		this.fax=fax;		
	}	
	public String toString() {
		return getName()+":"+getTitle()+":"+getEmail();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
