package com.estes.myestes.BillOfLading.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType{
	CC("Certified Check"),
	CCA("Consignee Check Accepted");
	private String label;
	
	public static PaymentType getValueOf(String value){
		if(value==null || value.trim().equals("")){
			return null;
		}
		
		switch(value.trim()){
			case "CC" : 
				return PaymentType.CC;
			case "CCA":
				return PaymentType.CCA;
			default:
				return null;
		}
		
	}
}