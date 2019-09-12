package com.estes.myestes.terminallist.dto;

import io.swagger.annotations.ApiModelProperty;

public class EmailRequestDTO
{
	@ApiModelProperty(notes="Country - US/CN/MX/** = all terminals")
	String country;
	@ApiModelProperty(notes="Emails seperated by (,)")
	String emails;
	@ApiModelProperty(notes = "2-letter state abbreviation")
	String state;
	@ApiModelProperty(notes = "File Type - .xls/.csv")
	String fileType;

	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
}
