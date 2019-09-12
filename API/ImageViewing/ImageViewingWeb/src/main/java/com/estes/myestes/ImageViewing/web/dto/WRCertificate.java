package com.estes.myestes.ImageViewing.web.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description="Weight & Research Certificate")
public class WRCertificate {
	
	@ApiModelProperty(notes = "Checkbox")
	private String checkbox;

	@ApiModelProperty(notes = "PRO Number")
	private String proNumber;

	@ApiModelProperty(notes = "BOL Numbber")
	private String bolNumber;

	@ApiModelProperty(notes="PO Number")
	private String poNumber;

	@ApiModelProperty(notes = "Date Of Correction")
	private String correctionDate;

	@ApiModelProperty(notes = "Type Of Correction")
	private String correctionType;

	@ApiModelProperty(notes = "Document Link")
	private String documentLink;
}
