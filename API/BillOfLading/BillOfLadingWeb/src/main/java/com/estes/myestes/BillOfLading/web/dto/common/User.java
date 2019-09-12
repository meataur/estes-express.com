package com.estes.myestes.BillOfLading.web.dto.common;

import com.estes.myestes.BillOfLading.web.dto.Role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("User Information")
public class User {
	
	@ApiModelProperty(position=1,value="User Full Name")
	private String name;
	
	@ApiModelProperty(position=2,value="User Phone No . Phone format should be : (xxx) xxx-xxxx")
	private String phone;
	
	@ApiModelProperty(position=3,value="Phone Extension")
	private String phoneExt;
	
	@ApiModelProperty(position=4, value="Email Address")
	private String email;
	
	@ApiModelProperty(position=5, value="User Role")
	private Role role;
	
	public void setPhone(String phone){
		this.phone = "";
		if(phone!=null){
			phone = phone.replaceAll("\\D+", "");
			if(phone.length()>=10){
				this.phone = phone;
			}
		}
	}

	public String formatPhoneForDb(){
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
	
}
