package com.estes.myestes.ImageViewing.web.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Weight & Research Email Request")
public class WREmailRequest {

	@ApiModelProperty(notes = "Pro Numbers")
	private List<String> proNumbers;

	@ApiModelProperty(notes = " Email Addresses")
	private List<String> emailAddresses;

}
