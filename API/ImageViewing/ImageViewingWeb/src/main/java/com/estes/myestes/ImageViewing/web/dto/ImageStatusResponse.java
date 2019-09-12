package com.estes.myestes.ImageViewing.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@ApiModel(description="Image Status")
@Data
@AllArgsConstructor
public class ImageStatusResponse {
	@ApiModelProperty(notes="Request Number")
	private String requestNumber;
	
	@ApiModelProperty(notes="Search Data")
	private String searchData;
	
	@ApiModelProperty(notes="Document type – BOL/DR/WR")
	private String docType;
	
	@ApiModelProperty(notes="Image Status")
	private ImageRequestStatus status;
	
}
