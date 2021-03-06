package com.estes.myestes.PickupRequest.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Party Notification")
public class PartyNotification {
	@ApiModelProperty(position=1, value="Party Type")
	private Role role;
	@ApiModelProperty(position=2, value="Party Name")
	private String name;
	@ApiModelProperty(position=3, value="Email Address")
	private String email;
	@ApiModelProperty(position=4, value="Phone Number - (xxx) xxx-xxxx")
	private String phone;
	
	@ApiModelProperty(position=5, value="Phone Extension")
	private String phoneExt;
	
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
