package com.estes.myestes.BillOfLading.web.dto;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("BOL Response After creating new BOL")
public class BolResponse {
	@ApiModelProperty(position=0, notes="Bill of Lading Summary")
	private Bol bol;
	
	@ApiModelProperty(position=1, notes="Pickup Errors when processing. null if there are no errors")
	private List<ServiceResponse> pickupErrors;
	
	@ApiModelProperty(position=2, notes="Pickup details after processing. null If the BOL has not PickupRequest")
	private PickupRequest pickupRequest;
}
