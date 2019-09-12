package com.estes.myestes.PickupRequest.web.dto;

import lombok.Data;

@Data
public class PickupRequestError {
	private String field;
	private String code;
	private String description;
	private String badData;
	private String defaultTo;
	private String flag;
	private String flag2;
}
