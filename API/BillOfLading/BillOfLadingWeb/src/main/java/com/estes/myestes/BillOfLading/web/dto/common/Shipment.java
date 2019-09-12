package com.estes.myestes.BillOfLading.web.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Shipment Information")
public class Shipment {
	
	@ApiModelProperty(position=1, value="Request Number. To create new pickup request, the value should null")
	private String requestNumber;
	
	@ApiModelProperty(position=2,value="Commodity Information ")
	private PickupCommodity commodity;
	
	@ApiModelProperty(position=3,value="Shipment Option")
	private ShipmentOption shipmentOption;
	
	@ApiModelProperty(position=4,value="Shipment Service")
	private ShipmentService shipmentService;
	
	@ApiModelProperty(position=5,value="Email Notification")
	private Notification notification;
	
}
