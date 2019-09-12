package com.estes.myestes.ImageViewing.web.dto;



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Document Type: name value pair for Dropdown")
public class DocumentType {
	@ApiModelProperty(notes="Document Type Name")
	private String name;
	@ApiModelProperty(notes="Document Type Description")
	private String description;
	@ApiModelProperty(notes="Is Faxable")
	private boolean faxable;
}
