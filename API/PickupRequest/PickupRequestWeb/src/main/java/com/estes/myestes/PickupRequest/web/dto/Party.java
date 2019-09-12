package com.estes.myestes.PickupRequest.web.dto;

import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Party Information")
public class Party {
	
	@ApiModelProperty(position=1, value="Account Code")
	@Size(max=7)
	private String accountCode;
	
	@ApiModelProperty(position=2, value="Shipper Name")
	private String name;
	
	@ApiModelProperty(position=3, value="Shipper Company Name")
	private String company;
	
	@ApiModelProperty(position=4, value="Shipper Address Line 1")
	private String addressLine1;
	
	@ApiModelProperty(position=5, value="Shipper Address Line 2")
	private String addressLine2;
	
	@ApiModelProperty(position=6, value="City")
	private String city;
	
	@ApiModelProperty(position=7, value="State")
	private String state;
	
	@ApiModelProperty(position=8, value="ZIP Code")
	private String zip;
	
	@ApiModelProperty(position=9, value="ZIP Extension")
	private String zipExt;
	
	@ApiModelProperty(position=10, value="Phone No - (xxx) xxx-xxxx")
	private String phone;
	
	@ApiModelProperty(position=11, value="Phone No Extension")
	private String phoneExt;
	
	@ApiModelProperty(position=12, value="Email Address")
	private String email;
	

	public void setPhone(String phone){
		this.phone = "";
		if(phone!=null){
			phone = phone.replaceAll("\\D+", "");
			if(phone.length()==10){
				this.phone = phone;
			}
		}
	}
	
	public String formatPhoneForDb(){
		if(phone==null){
			return "";
		}
		
		phone = phone.replaceAll("\\D+", "");
		
		if(phone.length() != 10){
			return "";
		}
		
		return phone;
		
	}
	
	public String getPhone(){
		if(phone==null){
			return "";
		}
		
		phone = phone.replaceAll("\\D+", "");
		
		if(phone.length() != 10){
			return "";
		}
		
		return "("+phone.substring(0, 3)+") "+phone.substring(3, 6)+"-" +phone.substring(6);
		
	}
}
