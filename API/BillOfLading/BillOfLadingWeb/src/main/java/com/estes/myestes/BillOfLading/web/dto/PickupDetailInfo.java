package com.estes.myestes.BillOfLading.web.dto;

import java.time.LocalTime;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="It will be used for Draft, Template & Default Pickup Information of BOL")
public class PickupDetailInfo{
	
	@ApiModelProperty(value="User Full Name")
	@NotNull
	private String name;
	
	@ApiModelProperty(value="User Phone No - (xxx) xxx-xxxx")
	@NotNull
	private String phone;
	
	@ApiModelProperty(value="Phone Extension")
	private String phoneExt;
	
	@ApiModelProperty(value="Email Address")
	@NotNull
	@Email
	private String email;
	
	@ApiModelProperty(value="User Role")
	@NotNull
	private Role role;
	
	@ApiModelProperty(value="Shipper Account Code")
	private String accountCode;
	
	@ApiModelProperty(value="Pickup Date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy",timezone="EST")
	@NotNull
	private Date pickupDate;
	
	@ApiModelProperty(value="Start Time / Available by - hh:mm a (ex. 08:30 AM / 05:00 PM)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	@NotNull
	private LocalTime startTime;
	
	@ApiModelProperty(value="End Time / Close At  - hh:mm a (ex. 08:30 AM / 05:00 PM)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	@NotNull
	private LocalTime endTime;
	
	@ApiModelProperty(value="Special Instructions")
	private String specialInstruction;
	
	@ApiModelProperty(value="Is Hook/Drop")
	private boolean hookOrDrop;
	
	@ApiModelProperty(value="Is Liftgate Required At Pickup")
	private boolean liftgateRequired;
	
	@ApiModelProperty(value="Do not Stack Palletes?")
	private boolean noStackPallet;
	
	
	@ApiModelProperty(value="Is Freeze?")
	private boolean freeze;
	 
	@ApiModelProperty(value="Is Oversized/Extreme Length?")
	private boolean oversize;
	 
	@ApiModelProperty(value="Is Food?")
	private boolean food;
	 
	@ApiModelProperty(value="Is Poision?")
	private boolean poision;

	@ApiModelProperty(notes = "Send Email When Accepted?")
	private boolean accepted;
	
	@ApiModelProperty(value="Send Email When Completed?")
	private boolean completed;
	
	@ApiModelProperty(value="Send Email When Rejected? This option should always be true")
	private boolean rejected=true;
	
	
	@AssertTrue
	public boolean isName(){
		if(name==null || name.trim().length()==0){
			return false;
		}
		return true;
	}
	@AssertTrue
	public boolean isEmail(){
		if(email==null || email.trim().length()==0){
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
