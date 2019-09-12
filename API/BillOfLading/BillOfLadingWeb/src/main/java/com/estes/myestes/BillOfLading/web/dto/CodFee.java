package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CodFee {
	S("Shipper"), C("Consignee");
	private String label;

	public static CodFee getValueOf(String value) {
		if (value == null || value.trim().equals("")) {
			return null;
		}

		switch (value.trim()) {
		case "S":
			return CodFee.S;
		case "C":
			return CodFee.C;
		default:
			return null;
		}

	}
}