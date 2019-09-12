package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="Bol Template Model")
@Data
public class Template {
	
	@ApiModelProperty(position=1,notes="Template Name")
	private String templateName;
	
	@ApiModelProperty(position=2,notes="BOL Number")
	private String bolNumber;

	@ApiModelProperty(position=3,notes="BOL date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date bolDate;

	@ApiModelProperty(position=4,notes="PRO number")
	private String proNumber;

	@ApiModelProperty(position=5,notes="Shipper Company Name.")
	private String shipper;
	
	@ApiModelProperty(position=6,notes="Shipper First Name")
	private String shipperFirstName;
	@ApiModelProperty(position=7,notes="Shipper Last Name.")
	private String shipperLastName;

	@ApiModelProperty(position=8,notes="Consignee Company Name")
	private String consignee;
	
	@ApiModelProperty(position=8,notes="Consignee First Name")
	private String consigneeFirstName;
	
	@ApiModelProperty(position=8,notes="Consignee Last Name")
	private String consigneeLastName;
	
}
