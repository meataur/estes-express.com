/**
 * 
 */
package com.estes.myestes.BillOfLading.util;

/**
 * @author sinhasu
 *
 */
public interface PickupRequestConstants {
	String PICKUPREQUEST_CONFIG = "pickuprequest.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	String APP_ID = "PKN200";
	String COMMA_SEPARATOR = ", ";
	String ACTION_FAILED = "F";
	String EMPTY_STR = "";
	String BLANK_STR = " ";
	
	String VOLUME_TRUCK_STR = "Truckload Quote#";
	String VOLUME_TRUCK_QUOTE_ID = "voltrkldquoteid";
	String LTL_RATE_QUOTE_ID = "ltlratequoteid";
	String RANDOM_NUMBER = "random_number";
	String HASH_VALUE = "hash_value";
	
	String YES_Y = "Y";
	String NO_N = "N";
	 
	String TRUE = "true";
	
	String TEMPLATE = "TEMPLATE";
	String DRAFT = "DRAFT";
	String DEFAULT = "DEFAULT";
	String NOTIFY_ACCEPT = "ACCEPT";
	String NOTIFY_COMPLETE = "COMPLETE";
	String NOTIFY_REJECT = "REJECT";
	
	enum PickupDetailsType {
		TEMPLATE, DRAFT, DEFAULT
	}
	
	enum UserContactType{
        ACTUAL_SUBMITTER("Actual Submitter", "A", "Actual Submitter"), CONSIGNEE("Consignee Contact", "C", "Consignee"), REQUESTED_RESPONSE(
				"Requested Response Contact", "R", "Requested Response"), SHIPPER("Shipper Contact", "S", "Shipper"), THIRD_PARTY(
				"Third Party Contact", "3","Third Party"), FOURTH_PARTY("Fourth Party Contact", "4","Fourth Party"), FIFTH_PARTY(
				"Fifth Party Contact", "5","Fifth Party"), SIXTH_PARTY("Sixth Party Contact", "6","Sixth Party");
        
        private String value;
        private String name;
        private String roleName;

        private UserContactType(String name, String value, String roleName) {
        	this.name = name;
        	this.value = value;
        	this.roleName = roleName;
        	
        }
        public String getValue(){
        	return this.value;
        }
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getRoleName() {
			return roleName;
		}
		public void setRoleName(String roleName) {
			this.roleName = roleName;
		}
	};
	
	enum InfoType{
        TEMPLATE("TEMPLATE"), DRAFT("DRAFT"), DEFAULT("DEFAULT"), HISTORY("HISTORY");
        private String value;
        private InfoType(String value) {
                this.value = value;
        }

        public String getValue(){
        	return this.value;
        }	
	};
	
	enum PartyType{
        CONSIGNEE("Consignee","C"),  SHIPPER("Shipper","S"), THIRD_PARTY("Third Party","3"), OTHER("Fourth Party","4");
        private String value;
        private String name;

        private PartyType(String name, String value) {
        	this.name = name;
        	this.value = value;
        }
        public String getValue(){
        	return this.value;
        }
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	};
	
	enum AccountType{
        GROUP("G"), WEB("W"), NATIONAL("N"), LOCAL("L");
        private String value;
        private AccountType(String value) {
                this.value = value;
        }
        public String getValue(){
        	return this.value;
        }	
	};
	
	//UI field names
	String SUB_ACCOUNT_CODE = "shipperInfo.accountCode";
	String USER_PERSONALINFO_NAME = "userInfo.name";
	
	String USER_PERSONALINFO_FIRST_NAME = "userInfo.firstName";
	String USER_PERSONALINFO_LAST_NAME = "userInfo.lastName";
	
	String USER_PERSONALINFO_PHONE = "userInfo.phone";
	String USER_PERSONALINFO_PHONE_EXT = "userInfo.phoneExt";
	String USER_PERSONALINFO_EMAIL = "userInfo.emailAddress";
	String USER_ROLE = "userInfo.role";
	
	String SHIPPER_CUSTOMER_PERSONALINFO_NAME ="shipperInfo.name";
	
	String SHIPPER_PERSONALINFO_FIRST_NAME = "shipperInfo.firstName";
	String SHIPPER_PERSONALINFO_LAST_NAME = "shipperInfo.lastName";
	
	String SHIPPER_CUSTOMER_PERSONALINFO_EMAIL = "shipperInfo.emailAddress";
	String SHIPPER_CUSTOMER_PERSONALINFO_PHONE ="shipperInfo.phone";
	String SHIPPER_CUSTOMER_PERSONALINFO_PHONE_EXT ="shipperInfo.phoneExt";
	
	String SHIPPER_COMPANY = "shipperInfo.company";
	String SHIPPER_SHIPPERADDRESS_ADDRESS_LINE1 = "shipperInfo.addressLine1";
	String SHIPPER_SHIPPERADDRESS_ADDRESS_CITY = "shipperInfo.city";
	String SHIPPER_SHIPPERADDRESS_ADDRESS_STATE = "shipperInfo.state";
	String SHIPPER_SHIPPERADDRESS_ADDRESS_ZIP5 ="shipperInfo.zipCode5";
	
	String CONSIGNEE_NAME ="consigneeInfo.name";
	String CONSIGNEE_PHONE = "consigneeInfo.phone";
	String CONSIGNEE_PHONE_EXT ="consigneeInfo.phoneExt";
	String CONSIGNEE_ADDRESS_LINE1 = "consigneeInfo.addressLine1";
	String CONSIGNEE_ADDRESS_CITY = "consigneeInfo.city";
	String CONSIGNEE_ADDRESS_STATE = "consigneeInfo.state";
	String CONSIGNEE_ADDRESS_ZIP5 ="consigneeInfo.zipCode5";
	String CONSIGNEE_ACCOUNT_CODE = "consigneeInfo.accountCode";
	String CONSIGNEE_EMAIL = "consigneeInfo.emailAddress";
	
	String THIRD_PARTY_PHONE = "thirdPartyInfo.phone";
	String THIRD_PARTY_PHONE_EXT ="thirdPartyInfo.phoneExt";
	String THIRD_PARTY_EMAIL = "thirdPartyInfo.emailAddress";
	
	String OTHER_PARTY_PHONE = "otherPartyInfo.phone";
	String OTHER_PARTY_PHONE_EXT ="otherPartyInfo.phoneExt";
	String OTHER_PARTY_EMAIL = "otherPartyInfo.emailAddress";
	
	String SHIPMENT_DETAILS_ENDTIME_HH_FIELD = "shipmentInfo.endTimeHH";
	String SHIPMENT_DETAILS_ENDTIME_MM_FIELD = "shipmentInfo.endTimeMM";
	String SHIPMENT_DETAILS_ENDTIME_AMPM_FIELD = "shipmentInfo.endAmPm";
	String SHIPMENT_DETAILS_STARTTIME_HH_FIELD = "shipmentInfo.startTimeHH";
	String SHIPMENT_DETAILS_STARTTIME_MM_FIELD = "shipmentInfo.startTimeMM";
	String SHIPMENT_DETAILS_STARTTIME_AMPM_FIELD = "shipmentInfo.startAmPm";
	String SHIPMENT_DETAILS_PICKUPDATE = "shipmentInfo.pickupDate";
	String SHIPMENT_DETAILS_NOTIFICATION_TYPE = "shipmentInfo.notificationType";
	
	String SHIPMENT_DESCR_TOTAL_PIECES = ".totalPieces";
	String SHIPMENT_DESCR_TOTAL_WEIGHT = ".totalWeight";
	String SHIPMENT_DESCR_TOTAL_SKIDS = ".totalSkids";
	String SHIPMENT_DESCR_HAZMAT= ".hazmat";
	String SHIPMENT_DESCR_SERVICE_LVL = ".serviceLevel";
	
	int MAX_SHPMENT_CMDTY_WEIGHT = 45000;
	int MAX_PICKUP_DAYS = 30;
	Integer ZERO = 0;
	String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
	String EMAIL = "E";
	String YES = "Y";
	String NO = "N";
	int EMAIL_LENGTH = 5;
	String AM = "AM";
	String PM = "PM";
}

