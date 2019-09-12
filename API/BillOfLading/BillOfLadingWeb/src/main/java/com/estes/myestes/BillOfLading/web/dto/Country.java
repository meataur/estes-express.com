package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Country {
	US("United States"), CN("Canada");
	private String label;
	public static Country getValueOf(String value) {
		if (value == null || value.trim().equals("")) {
			return null;
		}

		switch (value.trim()) {
		case "US":
			return Country.US;
		case "CN":
			return Country.CN;
		default:
			return null;
		}

	}
}
