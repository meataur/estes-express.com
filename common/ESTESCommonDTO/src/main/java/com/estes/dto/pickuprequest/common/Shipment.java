package com.estes.dto.pickuprequest.common;

import java.util.Date;
import java.util.List;

/**
 * Represents the shipment details for a pickup request.
 */
public class Shipment {
	private Date pickupDate;
	private String startTimeHH;
	private String startTimeMM;
	private String startAmPm;
	private String endTimeHH;
	private String endTimeMM;
	private String endAmPm;
	private List<String> pickupInstructions;	
	private List<CommodityDetail> commodityDetailList;
	private String notificationType;	
	private boolean termsForGuaranteed;
	public Date getPickupDate() {
		return pickupDate;
	}
	public void setPickupDate(Date pickupDate) {
		this.pickupDate = pickupDate;
	}
	public String getStartTimeHH() {
		return startTimeHH;
	}
	public void setStartTimeHH(String startTimeHH) {
		this.startTimeHH = startTimeHH;
	}
	public String getStartTimeMM() {
		return startTimeMM;
	}
	public void setStartTimeMM(String startTimeMM) {
		this.startTimeMM = startTimeMM;
	}
	public String getStartAmPm() {
		return startAmPm;
	}
	public void setStartAmPm(String startAmPm) {
		this.startAmPm = startAmPm;
	}
	public String getEndTimeHH() {
		return endTimeHH;
	}
	public void setEndTimeHH(String endTimeHH) {
		this.endTimeHH = endTimeHH;
	}
	public String getEndTimeMM() {
		return endTimeMM;
	}
	public void setEndTimeMM(String endTimeMM) {
		this.endTimeMM = endTimeMM;
	}
	public String getEndAmPm() {
		return endAmPm;
	}
	public void setEndAmPm(String endAmPm) {
		this.endAmPm = endAmPm;
	}
	public List<String> getPickupInstructions() {
		return pickupInstructions;
	}
	public void setPickupInstructions(List<String> pickupInstructions) {
		this.pickupInstructions = pickupInstructions;
	}
	public List<CommodityDetail> getCommodityDetailList() {
		return commodityDetailList;
	}
	public void setCommodityDetailList(List<CommodityDetail> commodityDetailList) {
		this.commodityDetailList = commodityDetailList;
	}
	public boolean getTermsForGuaranteed() {
		return termsForGuaranteed;
	}
	public void setTermsForGuaranteed(boolean termsForGuaranteed) {
		this.termsForGuaranteed = termsForGuaranteed;
	}
	public String getNotificationType() {
		return notificationType;
	}
	public void setNotificationType(String notificationType) {
		this.notificationType = notificationType;
	}
}
