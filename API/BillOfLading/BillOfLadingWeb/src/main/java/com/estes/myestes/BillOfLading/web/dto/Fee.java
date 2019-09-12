package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Fee{
	COL("Collect"), PPD("Prepaid");
	private String label;
	
	public static Fee getValueOf(String value) {
		if (value == null || value.trim().equals("")) {
			return null;
		}

		switch (value.trim()) {
		case "COL":
			return Fee.COL;
		case "PPD":
			return Fee.PPD;
		default:
			return null;
		}

	}
}