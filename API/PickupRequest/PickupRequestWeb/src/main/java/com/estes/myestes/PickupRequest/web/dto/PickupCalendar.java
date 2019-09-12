package com.estes.myestes.PickupRequest.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel("Pickup Calendar")
@Data
public class PickupCalendar {
	@ApiModelProperty(notes="Pickup Date - MM/dd/yyyy")
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy",timezone="EST")
	private Date date;
	@ApiModelProperty(notes="Is holiday?")
	private boolean holiday;
	@ApiModelProperty(notes="Is weekend?")
	private boolean weekend;
}
