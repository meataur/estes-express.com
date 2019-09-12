package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="Template Filter")
@Data
public class TemeplateFilter {
	public enum FilterBy {
		SHOW_ALL,
		TEMPLATE_NAME,
		BOL_NUMBER,
		BOL_DATE_RANGE,
		PRO_NUMBER,
		SHIPPER,
		CONSIGNEE
	}
	@ApiModelProperty(position=0, notes="Filter By")
	private FilterBy filterBy=FilterBy.SHOW_ALL;
	
	
	@ApiModelProperty(position=1,notes="Template Name")
	private String templateName;
	
	@ApiModelProperty(position=2,notes="BOL Number")
	private String bolNumber;
	
	@ApiModelProperty(position=3, notes="BOL Start Date")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date bolStartDate;
	
	@ApiModelProperty(position=4, notes="BOL End Date")
	@DateTimeFormat(pattern = "MM/dd/yyyy")
	private Date bolEndDate;
	
	@ApiModelProperty(position=5, notes="Pro Number")
	private String proNumber;
	
	@ApiModelProperty(position=6, notes="Shipper Information")
	private String shipper;
	
	@ApiModelProperty(position=7, notes="Consignee Information")
	private String consignee;
}
