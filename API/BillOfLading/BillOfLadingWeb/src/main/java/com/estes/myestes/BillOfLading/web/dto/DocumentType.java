package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DocumentType{
	E("Estes BOL"), V("VICS BOL");
	private String label;
	
	public static DocumentType getValueOf(String value) {
		if (value == null || value.trim().equals("")) {
			return null;
		}

		switch (value.trim()) {
		case "E":
			return DocumentType.E;
		case "V":
			return DocumentType.V;
		default:
			return null;
		}

	}
}