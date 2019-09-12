package com.estes.myestes.ImageViewing.web.dto;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Fax Infomation")
public class FaxInfo {
	
	@ApiModelProperty(position=1, example="Estes Express Lines", notes="Company Name")
	private String companyName;
	@ApiModelProperty(position=2, example="Director", notes="Attention")
	private String attention;
	@NotNull
	@ApiModelProperty(position=3, example="(804) 345-9876",notes="Fax Number: format should be (xxx) xxx-xxxx")
	private String faxNumber;
	
	public String getAreaCode(){
		return faxNumber==null?"":faxNumber.replaceAll("\\D+", "").substring(0, 3);
	}
	public String getExchange(){
		return faxNumber==null?"":faxNumber.replaceAll("\\D+", "").substring(3, 6);
	}
	public String getLastFour(){
		return faxNumber==null?"":faxNumber.replaceAll("\\D+", "").substring(6,10);
	}
}
