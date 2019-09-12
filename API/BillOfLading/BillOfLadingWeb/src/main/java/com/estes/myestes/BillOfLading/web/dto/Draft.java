package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description="BOL Draft")
@Data
public class Draft {
	
	@ApiModelProperty(position=1, notes="BOL Number")
	private String bolNumber;

	@ApiModelProperty(position=2, notes="BOL date")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
	private Date bolDate;

	@ApiModelProperty(position=3, notes="PRO number")
	private String proNumber;

	@ApiModelProperty(position=4, notes="Shipper Information.")
	private String shipper;

	@ApiModelProperty(position=5, notes="Consignee Information")
	private String consignee;
	
}
