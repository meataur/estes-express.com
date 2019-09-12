package com.estes.myestes.ImageViewing.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
	Y("YES"),N("NO");
	private String value;
}
