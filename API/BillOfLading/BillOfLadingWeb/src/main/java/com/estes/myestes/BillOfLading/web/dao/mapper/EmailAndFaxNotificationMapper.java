package com.estes.myestes.BillOfLading.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;

public class EmailAndFaxNotificationMapper implements RowMapper<EmailAndFaxNotification> {

	@Override
	public EmailAndFaxNotification mapRow(ResultSet rs, int rowNum) throws SQLException {
		
		String shipperEmail                    = rs.getString("SHIPPER_EMAIL").trim();	   
	    boolean shipperEmailBolUpdate          = rs.getString("SHIPPER_EMAIL_BOL_UPDATE").trim().equalsIgnoreCase("Y");
	    boolean shipperEmailTrackingUpdate     = rs.getString("SHIPPER_EMAIL_TRACKING_UPDATE").trim().equalsIgnoreCase("Y"); 
	    boolean shipperEmailPickupNotice       = rs.getString("SHIPPER_EMAIL_PICKUP_NOTICE").trim().equalsIgnoreCase("Y");  
	    boolean shipperEmailShippingLabel      = rs.getString("SHIPPER_EMAIL_SHIPPING_LABEL").trim().equalsIgnoreCase("Y");  
	    boolean shipperFaxBolUpdate            = rs.getString("SHIPPER_FAX_BOL_UPDATE").trim().equalsIgnoreCase("Y"); 
		String shipperFax                      = rs.getString("SHIPPER_FAX").trim();
		
		String consigneeEmail                    = rs.getString("CONSIGNEE_EMAIL").trim();	   
	    boolean consigneeEmailBolUpdate          = rs.getString("CONSIGNEE_EMAIL_BOL_UPDATE").trim().equalsIgnoreCase("Y");
	    boolean consigneeEmailTrackingUpdate     = rs.getString("CONSIGNEE_EMAIL_TRACKING_UPDATE").trim().equalsIgnoreCase("Y"); 
	    boolean consigneeEmailPickupNotice       = rs.getString("CONSIGNEE_EMAIL_PICKUP_NOTICE").trim().equalsIgnoreCase("Y");  
	    boolean consigneeEmailShippingLabel      = rs.getString("CONSIGNEE_EMAIL_SHIPPING_LABEL").trim().equalsIgnoreCase("Y");  
	    boolean consigneeFaxBolUpdate            = rs.getString("CONSIGNEE_FAX_BOL_UPDATE").trim().equalsIgnoreCase("Y"); 
		String consigneeFax                      = rs.getString("CONSIGNEE_FAX").trim();
		
		String thirdPartyEmail                    = rs.getString("THIRD_PARTY_EMAIL").trim();	   
	    boolean thirdPartyEmailBolUpdate          = rs.getString("THIRD_PARTY_EMAIL_BOL_UPDATE").trim().equalsIgnoreCase("Y");
	    boolean thirdPartyEmailTrackingUpdate     = rs.getString("THIRD_PARTY_EMAIL_TRACKING_UPDATE").trim().equalsIgnoreCase("Y"); 
	    boolean thirdPartyEmailPickupNotice       = rs.getString("THIRD_PARTY_EMAIL_PICKUP_NOTICE").trim().equalsIgnoreCase("Y");  
	    boolean thirdPartyEmailShippingLabel      = rs.getString("THIRD_PARTY_EMAIL_SHIPPING_LABEL").trim().equalsIgnoreCase("Y");  
	    boolean thirdPartyFaxBolUpdate            = rs.getString("THIRD_PARTY_FAX_BOL_UPDATE").trim().equalsIgnoreCase("Y"); 
		String thirdPartyFax                      = rs.getString("THIRD_PARTY_FAX").trim();
		
		String otherEmail                    = rs.getString("OTHER_EMAIL").trim();	   
	    boolean otherEmailBolUpdate          = rs.getString("OTHER_EMAIL_BOL_UPDATE").trim().equalsIgnoreCase("Y");
	    boolean otherEmailTrackingUpdate     = rs.getString("OTHER_EMAIL_TRACKING_UPDATE").trim().equalsIgnoreCase("Y"); 
	    boolean otherEmailPickupNotice       = rs.getString("OTHER_EMAIL_PICKUP_NOTICE").trim().equalsIgnoreCase("Y");  
	    boolean otherEmailShippingLabel      = rs.getString("OTHER_EMAIL_SHIPPING_LABEL").trim().equalsIgnoreCase("Y");  
	    boolean otherFaxBolUpdate            = rs.getString("OTHER_FAX_BOL_UPDATE").trim().equalsIgnoreCase("Y"); 
		String otherFax                      = rs.getString("OTHER_FAX").trim();
		
		EmailAndFaxNotification emailAndFaxNotification = new EmailAndFaxNotification();
		
		emailAndFaxNotification.setShipperEmail(shipperEmail);
		emailAndFaxNotification.setShipperEmailBolUpdate(shipperEmailBolUpdate);
		emailAndFaxNotification.setShipperEmailTrackingUpdate(shipperEmailTrackingUpdate);
		emailAndFaxNotification.setShipperEmailPickupNotice(shipperEmailPickupNotice);
		emailAndFaxNotification.setShipperEmailShippingLabel(shipperEmailShippingLabel);
		emailAndFaxNotification.setShipperFaxBolUpdate(shipperFaxBolUpdate);
		emailAndFaxNotification.setShipperFax(shipperFax);
		
		emailAndFaxNotification.setConsigneeEmail(consigneeEmail);
		emailAndFaxNotification.setConsigneeEmailBolUpdate(consigneeEmailBolUpdate);
		emailAndFaxNotification.setConsigneeEmailTrackingUpdate(consigneeEmailTrackingUpdate);
		emailAndFaxNotification.setConsigneeEmailPickupNotice(consigneeEmailPickupNotice);
		emailAndFaxNotification.setConsigneeEmailShippingLabel(consigneeEmailShippingLabel);
		emailAndFaxNotification.setConsigneeFaxBolUpdate(consigneeFaxBolUpdate);
		emailAndFaxNotification.setConsigneeFax(consigneeFax);
		
		emailAndFaxNotification.setThirdPartyEmail(thirdPartyEmail);
		emailAndFaxNotification.setThirdPartyEmailBolUpdate(thirdPartyEmailBolUpdate);
		emailAndFaxNotification.setThirdPartyEmailTrackingUpdate(thirdPartyEmailTrackingUpdate);
		emailAndFaxNotification.setThirdPartyEmailPickupNotice(thirdPartyEmailPickupNotice);
		emailAndFaxNotification.setThirdPartyEmailShippingLabel(thirdPartyEmailShippingLabel);
		emailAndFaxNotification.setThirdPartyFaxBolUpdate(thirdPartyFaxBolUpdate);
		emailAndFaxNotification.setThirdPartyFax(thirdPartyFax);
		
		emailAndFaxNotification.setOtherEmail(otherEmail);
		emailAndFaxNotification.setOtherEmailBolUpdate(otherEmailBolUpdate);
		emailAndFaxNotification.setOtherEmailTrackingUpdate(otherEmailTrackingUpdate);
		emailAndFaxNotification.setOtherEmailPickupNotice(otherEmailPickupNotice);
		emailAndFaxNotification.setOtherEmailShippingLabel(otherEmailShippingLabel);
		emailAndFaxNotification.setOtherFaxBolUpdate(otherFaxBolUpdate);
		emailAndFaxNotification.setOtherFax(otherFax);
		
		
		return emailAndFaxNotification;
	}

}
