package com.estes.myestes.BillOfLading.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Commodity")
@Data
public class Commodity{
	
	@ApiModelProperty(position=1, notes="Commodity/Id")
	private int commodityId;

	@ApiModelProperty(position=2, notes="Is Hazmat?")
	private boolean hazmat;

	@ApiModelProperty(position=3, notes="Goods Unit")
	private int goodsUnit;

	@ApiModelProperty(position=4, notes="Goods Type")
	private GoodsType goodsType;

	@ApiModelProperty(position=5, notes="Goods Weight (lbs)")
	private int goodsWeight;

	@ApiModelProperty(position=6, notes="Shipment Class")
	private ShipmentClass shipmentClass;

	@ApiModelProperty(position=7, notes="NAT MOTOR FGT CLASS (NMFC)")
	private String nmfc;

	@ApiModelProperty(position=8, notes="NAT MOTOR FGT CLASS (NMFC) Extension")
	private String nmfcExt;
	
	@ApiModelProperty(position=9,notes="No of Packages")
	private String numberOfPackage;
	
	@ApiModelProperty(position=10,notes="Package Type")
	private String packageType="";
	
	@ApiModelProperty(position=11, notes="Commodity Description")
	private String description="";
	
	@ApiModelProperty(position=11, notes="Commodity Height")
	private String height="";
	
	@ApiModelProperty(position=11, notes="Commodity Weight")
	private String weight="";
	@ApiModelProperty(position=11, notes="Commodity Length")
	private String length="";

}