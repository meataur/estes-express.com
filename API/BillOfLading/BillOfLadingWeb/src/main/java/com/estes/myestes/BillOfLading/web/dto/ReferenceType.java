package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum ReferenceType{
	PO("PO"),SID("SID"),CID("CID"),LN("Load #"),CO("Cust Order #"),OT("Other");
	private String label;
	
	public static ReferenceType getValueOf(String value){
		
		if(value==null || "".equals(value.trim())){
			return null;
		}
		
		switch(value.trim()){
			case "PO":
				return ReferenceType.PO;
			case "SID":
				return ReferenceType.SID;
			case "CID":
				return ReferenceType.CID;
			case "LN":
				return ReferenceType.LN;
			case "CO":
				return ReferenceType.CO;
			case "OT":
				return ReferenceType.OT;
			default:
				return null;
		
		}
		
	}
}