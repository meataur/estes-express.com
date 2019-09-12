package com.estes.dto.pickuprequest.common;

public interface PickupRequestGlobalConstants{

	enum PickupOptions {
		serviceLevelGold("Gold", ""),
		serviceLevelLTL("LTL", ""),
		serviceLevelEFW("Air", ""),
		hookDropOption("Hook/Drop", " "),
		liftgateOption("Liftgate required at pickup", " "),
		palletStackOption("Do not stack pallets", " "),
		freezeOption("Freeze", " "),
		oversizeOption("Oversize/Extreme Length", " "),
		foodOption("Food", " "),
		poisonOption("Poison", " ");
		
		private final String valueWhenSelected;
		private final String valueWhenDeselected;
		
		private PickupOptions(String valueWhenSelected, String valueWhenDeselected){
			this.valueWhenSelected = valueWhenSelected;
			this.valueWhenDeselected = valueWhenDeselected;
		}
		
		public String getSelectedValue(){
			return this.valueWhenSelected;
		}
		
		public String getDeselectedValue(){
			return this.valueWhenDeselected;
		}
		
	}

	enum PartyType{
		SHIPPER("Shipper"), CONSIGNEE("Consignee"), THIRD_PARTY("Third Party"), OTHER("Fourth Party"); 
		private String value;
		private PartyType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
	
	enum NotificationType{
		REJECT("Reject"), ACCEPT("Accept"), COMPLETE("Complete"); 
		private String value;
		private NotificationType(String value){
			this.value = value;
		}
		
		public String getValue(){
			return this.value;
		}
	}
}
