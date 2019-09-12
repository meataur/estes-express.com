package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BillToRole{
	S("Shipper"), C("Consignee"),T("Third Party");
	private String label;
	
	public static BillToRole getValueOf(String value) {
		if (value == null || value.trim().equals("")) {
			return null;
		}

		switch (value.trim()) {
		case "S":
			return BillToRole.S;
		case "C":
			return BillToRole.C;
		case "T":
			return BillToRole.T;
		default:
			return null;
		}
	}
}