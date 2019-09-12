package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description="Accessorial")
@Data
@NoArgsConstructor
public class Accessorial{
	
	@ApiModelProperty(position=1,notes="Accessorial Code")
	private String code;

	@ApiModelProperty(position=2,notes="Accessorial description")
	private String description;
	
	@ApiModelProperty(position=2,notes="Accessorial description")
	private boolean display;
	
	public Accessorial(String code){
		this.code = code;
	}
}