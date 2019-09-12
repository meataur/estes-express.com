package com.estes.myestes.BillOfLading.web.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PickupNotification{
	REJECTED("Rejected"),
	ACCEPTED("Accepted"),
	COMPLETED("Completed");
	private String label;
}