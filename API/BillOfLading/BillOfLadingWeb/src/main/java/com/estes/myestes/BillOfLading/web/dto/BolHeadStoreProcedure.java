package com.estes.myestes.BillOfLading.web.dto;

import java.util.List;

import com.estes.myestes.BillOfLading.util.EstesUtil;

import lombok.Data;

@Data
public class BolHeadStoreProcedure {
	private String bolSeq; /* CHAR(15) */ 
	private String bolDate; /* CHAR(8) */ 
	private String bolNum; /* CHAR(35) */ 
	private String otPro; /* CHAR(11) */ 
	private String autoasgn; /* CHAR(1) */ 
	private String shipCn; /* CHAR(30) */ 
	private String shipFn; /* CHAR(25) */ 
	private String shipLn; /* CHAR(25) */ 
	private String shipLocn; /* CHAR(10) */ 
	private String shipPhone; /* CHAR(10) */ 
	private String shipPnXt; /* CHAR(5) */ 
	private String shipFax; /* CHAR(10) */ 
	private String shipCntry; /* CHAR(2) */ 
	private String shipSt1; /* CHAR(30) */ 
	private String shipSt2; /* CHAR(30) */ 
	private String shipCity; /* CHAR(20) */ 
	private String shipState; /* CHAR(2) */ 
	private String shipZip; /* CHAR(10) */ 
	private String consCn; /* CHAR(30) */ 
	private String consFn; /* CHAR(25) */ 
	private String consLn; /* CHAR(25) */ 
	private String consLocn; /* CHAR(10) */ 
	private String consPhone; /* CHAR(10) */ 
	private String consPnXt; /* CHAR(5) */ 
	private String consFax; /* CHAR(10) */ 
	private String consCntry; /* CHAR(2) */ 
	private String consStr1; /* CHAR(30) */ 
	private String consStr2; /* CHAR(30) */ 
	private String consCity; /* CHAR(20) */ 
	private String consState; /* CHAR(2) */ 
	private String consZip; /* CHAR(10) */ 
	private String payor; /* CHAR(1) */ 
	private String terms; /* CHAR(3) */ 
	private String tptyCn; /* CHAR(30) */ 
	private String tptyFn; /* CHAR(25) */ 
	private String tptyLn; /* CHAR(25) */ 
	private String tptyLocn; /* CHAR(10) */ 
	private String tptyPhone; /* CHAR(10) */ 
	private String tptyPnXt; /* CHAR(5) */ 
	private String tptyFax; /* CHAR(10) */ 
	private String tptyCntry; /* CHAR(2) */ 
	private String tptyStr1; /* CHAR(30) */ 
	private String tptyStr2; /* CHAR(30) */ 
	private String tptyCity; /* CHAR(20) */ 
	private String tptyState; /* CHAR(2) */ 
	private String tptyZip; /* CHAR(10) */ 
	private String codFlag; /* CHAR(1) */ 
	private String codAmt; /* CHAR(11) */ 
	private String codPayTp; /* CHAR(10) */ 
	private String codFeePb; /* CHAR(10) */ 
	private String codName; /* CHAR(30) */ 
	private String codFn; /* CHAR(25) */ 
	private String codLn; /* CHAR(25) */ 
	private String codLocn; /* CHAR(10) */ 
	private String codPhone; /* CHAR(10) */ 
	private String codPnXt; /* CHAR(5) */ 
	private String codCntry; /* CHAR(2) */ 
	private String codStr1; /* CHAR(30) */ 
	private String codStr2; /* CHAR(30) */ 
	private String codCity; /* CHAR(20) */ 
	private String codState; /* CHAR(2) */ 
	private String codZip; /* CHAR(10) */ 
	private String quote; /* CHAR(7) */ 
	private String shipEmail; /* CHAR(50) */ 
	private String consEmail; /* CHAR(50) */ 
	private String tptyEmail; /* CHAR(50) */ 
	private String codEmail; /* CHAR(50) */ 
	private String faxToOth; /* CHAR(10) */ 
	private String totSu; /* CHAR(5) */ 
	private String hmCnt; /* CHAR(30) */ 
	private String hmPhone; /* CHAR(10) */ 
	private String hmEx; /* CHAR(6) */ 
	private String specIn; /* CHAR(500) */ 
	private String cube; /* CHAR(9) */ 
	private String strLab; /* CHAR(15) */ 
	private String totLab; /* CHAR(15) */ 
	private String labelType; /* CHAR(1) */ 
	private String mstrbol; /* CHAR(1) */ 
	private String mstrbolNumber; /* CHAR(25) */ 
	private String boltype; /* CHAR(1) */ 
	private String emBolS; /* CHAR(1) */ 
	private String emBolC; /* CHAR(1) */ 
	private String emBolT; /* CHAR(1) */ 
	private String emBolO; /* CHAR(1) */ 
	private String emTrkS; /* CHAR(1) */ 
	private String emTrkC; /* CHAR(1) */ 
	private String emTrkT; /* CHAR(1) */ 
	private String emTrkO; /* CHAR(1) */ 
	private String emLabS; /* CHAR(1) */ 
	private String emLabC; /* CHAR(1) */ 
	private String emLabT; /* CHAR(1) */ 
	private String emLabO; /* CHAR(1) */ 
	private String faxBolS; /* CHAR(1) */ 
	private String faxBolC; /* CHAR(1) */ 
	private String faxBolT; /* CHAR(1) */ 
	private String faxBolO; /* CHAR(1) */ 
	private String emLabOa; /* CHAR(255) */ 
	private String siteId; /* CHAR(30) */ 
	private String hazmat; /* CHAR(1) */ 

	public BolHeadStoreProcedure(BillOfLading bol){
		
		this.bolSeq = bol.getBolId()==0? "" : String.valueOf(bol.getBolId());
		
		if(bol.getDocumentType()!=null){
			this.boltype = bol.getDocumentType().name();
		}
		
		if(bol.getGeneralInfo()!=null){
			
			if(bol.getGeneralInfo().getBolDate()!=null){
				this.bolDate = EstesUtil.formatDate(bol.getGeneralInfo().getBolDate(),EstesUtil.US_DATE_FORMAT_WITHOUT_SLASH);
			}
			
			this.bolNum = bol.getGeneralInfo().getBolNumber();
			this.otPro = bol.getGeneralInfo().getProNumber();
			this.autoasgn = bol.getGeneralInfo().isAssignProNumber()? "Y" : "N";
			
			this.quote = bol.getGeneralInfo().getQuote();
			
			this.mstrbol = bol.getGeneralInfo().isMasterBol()? "Y" : "N";
			
			this.mstrbolNumber = bol.getGeneralInfo().getMasterBolNumber();
		}
		
		if(bol.getShipperInfo()!=null){
			
			this.shipCn = bol.getShipperInfo().getCompanyName();
			this.shipFn = bol.getShipperInfo().getFirstName();
			this.shipLn = bol.getShipperInfo().getLastName();
			this.shipLocn = bol.getShipperInfo().getLocation();
			this.shipPhone = bol.getShipperInfo().formatPhone();
			this.shipPnXt = bol.getShipperInfo().getPhoneExt();
			this.shipFax = bol.getShipperInfo().formatFax();
			
			if(bol.getShipperInfo().getCountry()!=null){
				this.shipCntry = bol.getShipperInfo().getCountry().name();
			}
		
			this.shipSt1 = bol.getShipperInfo().getAddress1();
			this.shipSt2 = bol.getShipperInfo().getAddress2();
			this.shipCity = bol.getShipperInfo().getCity();
			this.shipState = bol.getShipperInfo().getState();
			this.shipZip = bol.getShipperInfo().getZip();
			
			
			this.shipEmail = bol.getShipperInfo().getEmail();
		}
		
		
		if(bol.getConsigneeInfo()!=null){
			this.consCn = bol.getConsigneeInfo().getCompanyName();
			this.consFn = bol.getConsigneeInfo().getFirstName();
			this.consLn = bol.getConsigneeInfo().getLastName();
			this.consLocn = bol.getConsigneeInfo().getLocation();
			this.consPhone = bol.getConsigneeInfo().formatPhone();
			this.consPnXt = bol.getConsigneeInfo().getPhoneExt();
			this.consFax = bol.getConsigneeInfo().formatFax();
			
			if(bol.getConsigneeInfo().getCountry()!=null){
				this.consCntry = bol.getConsigneeInfo().getCountry().name();
			}
			
			this.consStr1 = bol.getConsigneeInfo().getAddress1();
			this.consStr2 = bol.getConsigneeInfo().getAddress2();
			this.consCity = bol.getConsigneeInfo().getCity();
			this.consState = bol.getConsigneeInfo().getState();
			this.consZip = bol.getConsigneeInfo().getZip();
			
			this.consEmail = bol.getConsigneeInfo().getEmail();
		}
		
		if(bol.getBillingInfo()!=null){
			
			if(bol.getBillingInfo().getBillTo()!=null){
				
				if(bol.getBillingInfo().getBillTo().getPayor()!=null){
					this.payor = bol.getBillingInfo().getBillTo().getPayor().name();
				}
				
				
				if(bol.getBillingInfo().getBillTo().getFee()!=null){
					this.terms = bol.getBillingInfo().getBillTo().getFee().name();
				}
				
				if(bol.getBillingInfo().getBillTo().getBillingAddressInfo()!=null){
					
					this.tptyCn = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCompanyName();
					this.tptyFn = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getFirstName();
					this.tptyLn = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getLastName();
					this.tptyLocn = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getLocation();
					this.tptyPhone = bol.getBillingInfo().getBillTo().getBillingAddressInfo().formatPhone();
					this.tptyPnXt = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getPhoneExt();
					this.tptyFax = bol.getBillingInfo().getBillTo().getBillingAddressInfo().formatFax();
					
					if(bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCountry()!=null){
						this.tptyCntry = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCountry().name();
					}
					
					this.tptyStr1 = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getAddress1();
					this.tptyStr2 = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getAddress2();
					this.tptyCity = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getCity();
					this.tptyState = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getState();
					this.tptyZip = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getZip();
					
					this.tptyEmail = bol.getBillingInfo().getBillTo().getBillingAddressInfo().getEmail();
				}
			}
			
			
			this.codFlag = bol.getBillingInfo().isCodRemitTo()? "Y" : "N";
			
			
			if(bol.getBillingInfo().getCodRemitToInfo()!=null){
				
				this.codAmt = bol.getBillingInfo().getCodRemitToInfo().getAmount();
				
				if(bol.getBillingInfo().getCodRemitToInfo().getPaymentType()!=null){
					this.codPayTp = bol.getBillingInfo().getCodRemitToInfo().getPaymentType().name();
				}
				
				if(bol.getBillingInfo().getCodRemitToInfo().getFee()!=null){
					this.codFeePb = bol.getBillingInfo().getCodRemitToInfo().getFee().name();
				}
				
				if(bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo()!=null){
					this.codName = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCompanyName();
					this.codFn = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getFirstName();
					this.codLn = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getLastName();
					this.codLocn = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getLocation();
					this.codPhone = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().formatPhone();
					this.codPnXt = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getPhoneExt();
					
					if(bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCountry()!=null){
						this.codCntry = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCountry().name();
					}
					
					this.codStr1 = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getAddress1();
					this.codStr2 = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getAddress2();
					this.codCity = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getCity();
					this.codState = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getState();
					this.codZip = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getZip();
					
					this.codEmail = bol.getBillingInfo().getCodRemitToInfo().getCodAddressInfo().getEmail();
				}
			}
			
		}
		
		
		
		if(bol.getEmailAndFaxNotification()!=null){
			this.faxToOth = bol.getEmailAndFaxNotification().formatOtherFax();
			this.emBolS = bol.getEmailAndFaxNotification().isShipperEmailBolUpdate()? "Y" : "N";
			this.emBolC = bol.getEmailAndFaxNotification().isConsigneeEmailBolUpdate()? "Y" : "N";
			this.emBolT = bol.getEmailAndFaxNotification().isThirdPartyEmailBolUpdate()? "Y" : "N";
			this.emBolO = bol.getEmailAndFaxNotification().isOtherEmailBolUpdate()? "Y" : "N";
			
			this.emTrkS = bol.getEmailAndFaxNotification().isShipperEmailTrackingUpdate()? "Y" : "N";
			this.emTrkC = bol.getEmailAndFaxNotification().isConsigneeEmailTrackingUpdate()? "Y" : "N";
			this.emTrkT = bol.getEmailAndFaxNotification().isThirdPartyEmailTrackingUpdate()? "Y" : "N";
			this.emTrkO = bol.getEmailAndFaxNotification().isOtherEmailTrackingUpdate()? "Y" : "N";
			
			this.emLabS = bol.getEmailAndFaxNotification().isShipperEmailShippingLabel()? "Y" : "N";
			this.emLabC = bol.getEmailAndFaxNotification().isConsigneeEmailShippingLabel()? "Y" : "N";
			this.emLabT = bol.getEmailAndFaxNotification().isThirdPartyEmailShippingLabel()? "Y" : "N";
			this.emLabO = bol.getEmailAndFaxNotification().isOtherEmailShippingLabel()? "Y" : "N";
			
			this.faxBolS = bol.getEmailAndFaxNotification().isShipperFaxBolUpdate()? "Y" : "N";
			this.faxBolC = bol.getEmailAndFaxNotification().isConsigneeFaxBolUpdate()? "Y" : "N";
			this.faxBolT = bol.getEmailAndFaxNotification().isThirdPartyFaxBolUpdate()? "Y" : "N";
			this.faxBolO = bol.getEmailAndFaxNotification().isOtherFaxBolUpdate()? "Y" : "N";
			
			this.emLabOa = bol.getEmailAndFaxNotification().getOtherEmail();
		}
		
		
		if(bol.getCommodityInfo()!=null){
			this.hmCnt = bol.getCommodityInfo().getContactName();
			this.hmPhone = bol.getCommodityInfo().formatPhoneForDb();
			this.hmEx = bol.getCommodityInfo().getPhoneExt();
			this.specIn = bol.getCommodityInfo().getSpecialIns();
			this.cube = bol.getCommodityInfo().getTotalCube();
			
			if(bol.getCommodityInfo().getCommodityList().isEmpty()){
				this.hazmat = "N";
			}else{
				this.hazmat = getHazmat(bol.getCommodityInfo().getCommodityList());
			}
		}else{
			this.hazmat = "N";
		}
		
		
		if(bol.getShippingLabel()!=null){
			this.strLab = bol.getShippingLabel().getStartLabel();
			this.totLab = bol.getShippingLabel().getNumberOfLabel();
			this.labelType = bol.getShippingLabel().getLabelType();
		}

	}
	
	private String getHazmat(List<Commodity> commodityList){
		
		for( Commodity commodity : commodityList){
			if(commodity.isHazmat()){
				return "Y";
			}
		}
		
		return "N";
		
	}
	
}
