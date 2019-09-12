package com.estes.myestes.BillOfLading.web.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;



@ApiModel(description="Shipper/Consignee/Third Party/Other Address Information")
@Data
public class AddressInformation{
	
	@ApiModelProperty(position=1,notes="Company Name")
	@NotNull
	private String companyName;

	@ApiModelProperty(position=2,notes="First Name")
	@NotNull
	private String firstName;

	@ApiModelProperty(position=3, notes="Last Name")
	private String lastName;

	@ApiModelProperty(position=4, notes="Location")
	private String location;

	@ApiModelProperty(position=5, notes="Phone Number: Expected Format - (xxx) xxx-xxxx")
	@NotNull
	private String phone;

	@ApiModelProperty(position=6,notes="Phone Extension")
	private String phoneExt;

	@ApiModelProperty(position=7, notes="Fax Number : Expected Format - (xxx) xxx-xxxx")
	private String fax;

	@ApiModelProperty(position=8, notes="Country")
	private Country country;

	@ApiModelProperty(position=9, notes="Address Line 1")
	@NotNull
	private String address1;

	@ApiModelProperty(position=10, notes="Address Line 2")
	private String address2;

	@ApiModelProperty(position=11, notes="City")
	@NotNull
	private String city;

	@ApiModelProperty(position=12, notes="State")
	@NotNull
	private String state;

	@ApiModelProperty(position=13, notes="ZIP")
	@NotNull
	private String zip;

	@ApiModelProperty(position=14, notes="Email Address")
	@NotNull
	@Email
	private String email;
	
	@AssertTrue
	public boolean isEmail(){
		if(email==null || email.trim().length()==0){
			return false;
		}
		return true;
	}
	
	@AssertTrue
	public boolean isZip(){
		if(zip==null || zip.trim().length()==0){
			return false;
		}
		return true;
	}
	
	@AssertTrue
	public boolean isState(){
		if(state==null || state.trim().length()==0){
			return false;
		}
		return true;
	}
	
	
	@AssertTrue
	public boolean isCity(){
		if(city==null || city.trim().length()==0){
			return false;
		}
		return true;
	}
	
	
	@AssertTrue
	public boolean isAddress1(){
		if(address1==null || address1.trim().length()==0){
			return false;
		}
		return true;
	}
	
	@AssertTrue
	public boolean isFirstName(){
		if(firstName==null || firstName.trim().length()==0){
			return false;
		}
		return true;
	}
	
	@AssertTrue
	public boolean isCompanyName(){
		if(companyName==null || companyName.trim().length()==0){
			return false;
		}
		return true;
	}
	
	@AssertTrue
	public boolean isPhoneExt(){
		if(phone==null || phone.trim().length()==0){
			return false;
		}
		return true;
	}
	
	@AssertTrue
	public boolean isPhone(){
		if(phone==null || phone.trim().length()==0 || phone.replaceAll("\\D+", "").length()!=10){
			return false;
		}
		return true;
	}
	
	
	public void setPhone(String phone){
		this.phone = "";
		if(phone!=null){
			phone = phone.replaceAll("\\D+", "");
			if(phone.length()>=10){
				this.phone = phone;
			}
		}
	}
	

	public String formatPhone(){
		if(phone==null){
			return "";
		}
		phone = phone.replaceAll("\\D+", "");
		
		if(phone.length() < 10){
			return "";
		}
		return phone;
	}
	
	public String getPhone(){
		if(phone==null){
			return "";
		}
		
		phone = phone.replaceAll("\\D+", "");
		
		if(phone.length() < 10){
			return "";
		}
		
		return "("+phone.substring(0, 3)+") "+phone.substring(3, 6)+"-" +phone.substring(6);
		
	}
	
	public void setFax(String fax){
		this.fax = "";
		if(fax!=null){
			fax = fax.replaceAll("\\D+", "");
			if(fax.length()>=10){
				this.fax = fax;
			}
		}
	}

	public String formatFax(){
		if(fax==null){
			return "";
		}
		fax = fax.replaceAll("\\D+", "");
		
		if(fax.length() < 10){
			return "";
		}
		return fax;
	}
	public String getFax(){
		if(fax==null){
			return "";
		}
		
		fax = fax.replaceAll("\\D+", "");
		
		if(fax.length() < 10){
			return "";
		}
		
		return "("+fax.substring(0, 3)+") "+fax.substring(3, 6)+"-" +fax.substring(6);
		
	}

	public String formatName(){
		return (firstName + " " + lastName).trim();
	}
}