package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(description="Goods Type / Packages")
@Data
@NoArgsConstructor
public class GoodsType{
	@ApiModelProperty(position=1,notes="Goods Type Code")
	private String code;

	@ApiModelProperty(position=2,notes="Goods Type Description")
	private String description;
	
	public GoodsType(String code){
		this.code = code;
	}
}