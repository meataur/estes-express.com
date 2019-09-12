package com.estes.myestes.ImageViewing.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ImageSearchType {
	PRO_NUMBER("F","PRO Number"),
	BILL_OF_LADING_NUMBER("B", "Bill of Lading Number (LTL shipments only)"),
	PURCHASE_ORDER_NUMBER("P", "Purchase Order Number (LTL shipments only)"),
	INTERLINE_PRO_NUMBER("F", "Interline PRO Number");
	private final String key;
	private final String value;
}
