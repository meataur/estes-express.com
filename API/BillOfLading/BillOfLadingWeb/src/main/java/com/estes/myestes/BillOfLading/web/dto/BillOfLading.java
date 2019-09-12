package com.estes.myestes.BillOfLading.web.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Bill of Lading. This Model will be used to create New BOL, BOL Template & Bol Draft, to list BOL History, BOL Template & BOL Draft")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BillOfLading {
	
	@ApiModelProperty(position=1, notes="Bol Id/Sequence")
	private int bolId;
	
	@ApiModelProperty(position=2, notes="Document Type Information : E for ESTES BOL, V for VICS BOL")
	private DocumentType documentType;

	@ApiModelProperty(position=3, notes="General Information")
	private GeneralInformation generalInfo;

	@ApiModelProperty(position=4, notes="Pickup Request Details Information")
	private PickupDetailInfo pickupDetailInfo;

	@ApiModelProperty(position=5, notes="Shipper Information")
	private AddressInformation shipperInfo;

	@ApiModelProperty(position=6, notes="Consignee Information")
	private AddressInformation consigneeInfo;

	@ApiModelProperty(position=7, notes="Commodities Information")
	private CommodityInformation commodityInfo;	

	@ApiModelProperty(position=8, notes="Reference Numbers Information")
	private List<Reference> references;

	@ApiModelProperty(position=9, notes="Billing Information")
	private BillingInformation billingInfo;

	@ApiModelProperty(position=10, notes="Accessorials Information")
	private List<Accessorial> accessorials;

	@ApiModelProperty(position=11, notes="Shipping Label Information")
	private ShippingLabel shippingLabel;

	@ApiModelProperty(position=12, notes="Fax and Email EmailAndFaxNotification Information")
	private EmailAndFaxNotification emailAndFaxNotification;

	public EmailAndFaxNotification emailAndFaxNotification() {
		if(emailAndFaxNotification==null){
			return new EmailAndFaxNotification();
		}
		return emailAndFaxNotification;
	}
}