package com.estes.myestes.ImageViewing.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageRequestStatus {
	ERROR("ERROR"),
	WORKING("Working"),
	NOT_AVAILABLE("Not Available due to internal errors"),
	AVAILABLE("Available"),
	FAX_SENT("Fax has been sent"),
	FAX_ERROR("Fax has not been sent due to errors");
	private String message;
}
