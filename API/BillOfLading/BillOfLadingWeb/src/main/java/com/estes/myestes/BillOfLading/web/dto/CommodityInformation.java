package com.estes.myestes.BillOfLading.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Commidity Information")
@Data
public class CommodityInformation{
	
	@ApiModelProperty(position=1, notes="List of Commodity")
	private List<Commodity> commodityList;

	@ApiModelProperty(position=2, notes="Hazmat Contact Name")
	private String contactName;

	@ApiModelProperty(position=3, notes="Hazmat Contact Phone - (xxx) xxx-xxxx")
	private String phone;

	@ApiModelProperty(position=4, notes="Hazmat Contact Phone Extension")
	private String phoneExt;

	@ApiModelProperty(position=5, notes="Total Cube")
	private String totalCube;

	@ApiModelProperty(position=6, notes="Special Instructions")
	private String specialIns;
	
	
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