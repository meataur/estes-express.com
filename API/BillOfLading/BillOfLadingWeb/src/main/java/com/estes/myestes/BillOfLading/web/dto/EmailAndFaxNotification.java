package com.estes.myestes.BillOfLading.web.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@ApiModel(description="Fax and Email Notifications")
@Data
public class EmailAndFaxNotification{

	@ApiModelProperty(position=2, notes="Shipper Email Address")
	private String shipperEmail;
	
	@ApiModelProperty(position=3, notes="Shipper BOL Update in Email")
    private boolean shipperEmailBolUpdate;
	
	@ApiModelProperty(position=4, notes="Shipper Tracking Update in Email")
    private boolean shipperEmailTrackingUpdate;
	
	@ApiModelProperty(position=5, notes="Shipper Pickup Notice in Email")
    private boolean shipperEmailPickupNotice;
	
	@ApiModelProperty(position=6, notes="Shipper Shipping Labels  in Email")
    private boolean shipperEmailShippingLabel;
	
	@ApiModelProperty(position=7, notes="Shipper BOL Update in Fax")
    private boolean shipperFaxBolUpdate;
	
	@ApiModelProperty(position=8, notes="Shipper Fax Number (xxx) xxx-xxxx")
	private String shipperFax; 
	
	
	@ApiModelProperty(position=2, notes="Consignee Email Address")
	private String consigneeEmail;
	
	@ApiModelProperty(position=3, notes="Consignee BOL Update in Email")
    private boolean consigneeEmailBolUpdate;
	
	@ApiModelProperty(position=4, notes="Consignee Tracking Update in Email")
    private boolean consigneeEmailTrackingUpdate;
	
	@ApiModelProperty(position=5, notes="Consignee Pickup Notice in Email")
    private boolean consigneeEmailPickupNotice;
	
	@ApiModelProperty(position=6, notes="Consignee Shipping Labels EmailAndFaxNotification in Email")
    private boolean consigneeEmailShippingLabel;
	
	@ApiModelProperty(position=7, notes="Consignee BOL Update in Fax")
    private boolean consigneeFaxBolUpdate;
	
	@ApiModelProperty(position=8, notes="Consignee Fax Number  (xxx) xxx-xxxx")
	private String consigneeFax; 
	
	
	@ApiModelProperty(position=2, notes="Third Party Email Address")
	private String thirdPartyEmail;
	
	@ApiModelProperty(position=3, notes="Third Party BOL Update in Email")
    private boolean thirdPartyEmailBolUpdate;
	
	@ApiModelProperty(position=4, notes="Third Party Tracking Update in Email")
    private boolean thirdPartyEmailTrackingUpdate;
	
	@ApiModelProperty(position=5, notes="Third Party Pickup Notice in Email")
    private boolean thirdPartyEmailPickupNotice;
	
	@ApiModelProperty(position=6, notes="Third Party Shipping Labels Notification in Email")
    private boolean thirdPartyEmailShippingLabel;
	
	@ApiModelProperty(position=7, notes="Third Party BOL Update in Fax")
    private boolean thirdPartyFaxBolUpdate;
	
	@ApiModelProperty(position=8, notes="Third Party Fax Number  (xxx) xxx-xxxx")
	private String thirdPartyFax; 
	@ApiModelProperty(position=2, notes="Other Email Address")
	private String otherEmail;
	
	@ApiModelProperty(position=3, notes="Other BOL Update in Email")
    private boolean otherEmailBolUpdate;
	
	@ApiModelProperty(position=4, notes="Other Tracking Update in Email")
    private boolean otherEmailTrackingUpdate;
	
	@ApiModelProperty(position=5, notes="Other Pickup Notice in Email")
    private boolean otherEmailPickupNotice;
	
	@ApiModelProperty(position=6, notes="Other Shipping Labels EmailAndFaxNotification in Email")
    private boolean otherEmailShippingLabel;
	
	@ApiModelProperty(position=7, notes="Other BOL Update in Fax")
    private boolean otherFaxBolUpdate;
	
	@ApiModelProperty(position=8, notes="Other Fax Number  (xxx) xxx-xxxx")
	private String otherFax;
	
	private String setFax(String fax){
		String returnFax = "";
		if(fax!=null){
			fax = fax.replaceAll("\\D+", "");
			if(fax.length()>=10){
				returnFax = fax;
			}
		}
		return returnFax;
	}
	
	private String formatFax(String fax){
		if(fax==null){
			return "";
		}
		
		fax = fax.replaceAll("\\D+", "");
		
		if(fax.length() < 10){
			return "";
		}
		
		return fax;
		
	}
	
	private String getFax(String fax){
		if(fax==null){
			return "";
		}
		
		fax = fax.replaceAll("\\D+", "");
		
		if(fax.length() < 10){
			return "";
		}
		
		return "("+fax.substring(0, 3)+") "+fax.substring(3, 6)+"-" +fax.substring(6);
		
	}

	public String formatShipperFax() {
		return formatFax(shipperFax);
	}

	
	public String getShipperFax() {
		return getFax(shipperFax);
	}

	public void setShipperFax(String shipperFax) {
		this.shipperFax = setFax(shipperFax);
	}

	public String formatConsigneeFax() {
		return formatFax(consigneeFax);
	}

	public String getConsigneeFax() {
		return getFax(consigneeFax);
	}

	public void setConsigneeFax(String consigneeFax) {
		this.consigneeFax = setFax(consigneeFax);
	}

	public String formatThirdPartyFax() {
		return formatFax(thirdPartyFax);
	}

	public String getThirdPartyFax() {
		return getFax(thirdPartyFax);
	}

	public void setThirdPartyFax(String thirdPartyFax) {
		this.thirdPartyFax = setFax(thirdPartyFax);
	}

	public String formatOtherFax() {
		return formatFax(otherFax);
	}
	
	public String getOtherFax() {
		return getFax(otherFax);
	}

	public void setOtherFax(String otherFax) {
		this.otherFax = setFax(otherFax);
	}
	
}