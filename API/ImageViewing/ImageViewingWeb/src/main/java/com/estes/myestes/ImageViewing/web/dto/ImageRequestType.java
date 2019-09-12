package com.estes.myestes.ImageViewing.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageRequestType {
	F("FAX"),V("VIEW");
	private String value;
}
