package com.estes.myestes.BillOfLading.web.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import lombok.Data;

@Data
public class PickupCalendar {
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy")
	private Date date;
	private boolean holiday;
	private boolean weekend;
}
