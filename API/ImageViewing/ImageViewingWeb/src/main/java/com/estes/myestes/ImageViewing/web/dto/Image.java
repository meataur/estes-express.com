package com.estes.myestes.ImageViewing.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description="Image")
public class Image {
	@ApiModelProperty(notes="document Id")
	private String documentId;
	
	@ApiModelProperty(notes="Image Output Directory")
	private String outputDirectory;
	
	@ApiModelProperty(notes="Image Output File Name")
	private String outputFileName;
	@ApiModelProperty(notes="NAS Direct Access")
	private String nasDirectAccess;
	
	@ApiModelProperty(notes="Image Location")
	private String imageLocation;
}
