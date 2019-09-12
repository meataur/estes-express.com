/**
 *
 */

package com.estes.myestes.requestinfo.dto;

import java.io.Serializable;

import com.estes.myestes.requestinfo.util.ProblemType;

import io.swagger.annotations.ApiModelProperty;

/**
 * Info request DTO
 */
public class InfoRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1701436030006447386L;
	
	@ApiModelProperty(notes="Whether BOL is selected (part of imaging problem)")
	private boolean BOLSelected;
	@ApiModelProperty(notes="Whether DR is selected (part of imaging problem)")
	private boolean DRSelected;
	@ApiModelProperty(notes="Whether WR is selected (part of imaging problem)")
	private boolean WRSelected;
	@ApiModelProperty(notes="First/Last name of submitter")
	private String name;
	@ApiModelProperty(notes="Phone number of submitter - (xxx) xxx-xxxx")
	private String phoneNumber;
	@ApiModelProperty(notes="Phone number extention of submitter")
	private String phoneNumberExt;
	@ApiModelProperty(notes="Fax number of submitter - (xxx) xxx-xxxx")
	private String faxNumber;
	@ApiModelProperty(notes="Email Address of submitter")
	private String emailAddress;
	@ApiModelProperty(notes="OT Pro number associated with request. - ddd-ddddddd")
	private String proNumber;
	@ApiModelProperty(notes="Problem being faced (options gotten from getProblemTypes endpoint)")
	private ProblemType problem;
	@ApiModelProperty(notes="Further description of problem")
	private String description;
	
	public boolean isBOLSelected() {
		return BOLSelected;
	}
	public void setBOLSelected(boolean bOLSelected) {
		BOLSelected = bOLSelected;
	}
	public boolean isDRSelected() {
		return DRSelected;
	}
	public void setDRSelected(boolean dRSelected) {
		DRSelected = dRSelected;
	}
	public boolean isWRSelected() {
		return WRSelected;
	}
	public void setWRSelected(boolean wRSelected) {
		WRSelected = wRSelected;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getPhoneNumberExt() {
		return phoneNumberExt;
	}
	public void setPhoneNumberExt(String phoneNumberExt) {
		this.phoneNumberExt = phoneNumberExt;
	}
	public String getFaxNumber() {
		return faxNumber;
	}
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getProNumber() {
		return proNumber;
	}
	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}
	public ProblemType getProblem() {
		return problem;
	}
	public void setProblem(ProblemType problem) {
		this.problem = problem;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}