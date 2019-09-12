package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("Bol/Draft Filter Model")
@Data
public class BolFilter {
	
	public enum FilterBy {
		SHOW_ALL,
		BOL_NUMBER,
		BOL_DATE_RANGE,
		PRO_NUMBER,
		SHIPPER,
		CONSIGNEE
	}
	
	@ApiModelProperty(position=0, notes="Filter By")
	private FilterBy filterBy=FilterBy.SHOW_ALL;
	
	@ApiModelProperty(position=1,notes="BOL Number")
	private String bolNumber;
	
	@ApiModelProperty(position=2, notes="BOL Start Date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy",timezone="EST")
	private Date bolStartDate;
	
	@ApiModelProperty(position=3, notes="BOL End Date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy",timezone="EST")
	private Date bolEndDate;
	
	@ApiModelProperty(position=4, notes="Pro Number")
	private String proNumber;
	
	@ApiModelProperty(position=5, notes="Shipper Information")
	private String shipper;
	
	@ApiModelProperty(position=6, notes="Consignee Information")
	private String consignee;
	
}
