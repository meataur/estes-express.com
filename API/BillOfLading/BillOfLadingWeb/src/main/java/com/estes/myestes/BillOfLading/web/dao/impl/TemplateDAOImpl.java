package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;
import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.DATALIB;
import static com.estes.myestes.BillOfLading.web.dao.sql.TemplateQuery.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.util.QueryUtil;
import com.estes.myestes.BillOfLading.web.dao.iface.PagingAndSortingDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.TemplateDAO;
import com.estes.myestes.BillOfLading.web.dao.mapper.AccessorialMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.CommodityMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.TemplateHeadMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.ReferenceMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.TemplateMapper;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.TemplateHead;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.Reference;
import com.estes.myestes.BillOfLading.web.dto.TemeplateFilter;
import com.estes.myestes.BillOfLading.web.dto.Template;

@Repository("templateDAO")
public class TemplateDAOImpl implements TemplateDAO {
	
	@Autowired
	private PagingAndSortingDAO<Template> pagingAndSortingDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page<Template> getTemplatetList(String username, Pageable pageable, TemeplateFilter filter) {
		/**
		 * Params are needed in query
		 */
		
		List<Object> params = new ArrayList<>();
		params.add(username);
		
		
		/**
		 * Filtering UserDefaultQuery Preparation
		 */
		
		TemeplateFilter.FilterBy filterBy = filter.getFilterBy();
		
		String query = GET_TEMPlATE_LIST;
		
		switch(filterBy){
		
			case SHOW_ALL:
				break;
			
			case TEMPLATE_NAME:
				query +=" AND UPPER(templateName) LIKE ? ";
				
				if(filter.getTemplateName()==null || filter.getTemplateName().trim()==""){
					throw new BolException(DEFAULT_ERROR_CODE, "Template Name is required","templateName");
				}
				
				params.add("%"+filter.getTemplateName().toUpperCase()+"%");
				
				break;	
			case BOL_NUMBER:
				query +=" AND UPPER(bolNumber) LIKE ? ";
				
				if(filter.getBolNumber()==null || filter.getBolNumber().trim()==""){
					throw new BolException(DEFAULT_ERROR_CODE, "BOL # is required","bolNumber");
				}
				
				params.add("%"+filter.getBolNumber().toUpperCase()+"%");
				
				break;
				
				
			case BOL_DATE_RANGE:
				query += " AND bolDate between ? AND ?";
				
				if(filter.getBolStartDate()==null){
					throw new BolException(DEFAULT_ERROR_CODE, "From date is required","bolStartDate");
				}
				
				if(filter.getBolEndDate()==null){
					filter.setBolEndDate(new Date());
				}

				params.add(filter.getBolStartDate());
				params.add(filter.getBolEndDate());
				break;
				
				
			case PRO_NUMBER:
				query +=" AND proNumber LIKE ? ";
				
				if(filter.getProNumber()==null || filter.getProNumber().trim()==""){
					throw new BolException(DEFAULT_ERROR_CODE, "PRO Number is required","proNumber");
				}
				
				params.add("%"+filter.getProNumber()+"%");
				break;
				
			case SHIPPER:
				query +=" AND UPPER(shipper) LIKE ? ";
				
				if(filter.getShipper()==null || filter.getShipper().trim()==""){
					throw new BolException(DEFAULT_ERROR_CODE,"Shipper is required","shipper");
				}
				
				params.add("%"+filter.getShipper().toUpperCase()+"%");
				break;
				
			case CONSIGNEE:
				query +=" AND UPPER(consignee) LIKE ? ";
				
				if(filter.getConsignee()==null || filter.getConsignee().trim()==""){
					throw new BolException(DEFAULT_ERROR_CODE,"Consignee is required","consignee");
				}
				
				params.add("%"+filter.getConsignee().toUpperCase()+"%");
				
				break;
				
			default:
				break;
		}
		
		/**
		 *  Pagination 
		 */
		
		String countQuery = query.replace("*", "count(*) as total");
		
		
		
		/**
		 * Sorting query Generation
		 */
		
		String sort = pageable.getSort();
		
		if(sort!=null){
			
			String order = pageable.getOrder().name();
			
			switch(sort){
				case "templateName":
					query +=" ORDER BY UPPER(templateName) "+order;
					break;
				
				case "bolNumber":
					query +=" ORDER BY UPPER(bolNumber) "+order;
					break;
		
				case "bolDate":
					query +=" ORDER BY bolDate "+order;
					break;
					
					
				case "proNumber":
					query +=" ORDER BY proNumber "+order;
					break;
					
				case "shipper":
					query +=" ORDER BY UPPER(shipper) "+order;
					break;
					
				case "consignee":
					query +=" ORDER BY UPPER(consignee) "+order;
					break;
					
				default:
					break;
			}
		}
		
		
		Page<Template> results = pagingAndSortingDAO.getResult(countQuery, query, params, pageable, new TemplateMapper());
		return results;
	}
	
	@Override
	public void saveTemplateHeader(String username, String template, BillOfLading bol){
		TemplateHead templateHead = new TemplateHead(bol);
		Map<String,String> params = new LinkedHashMap<>();
		
		params.put("CALLED_BY_PGM","EBG10O100");
		params.put("BOL_DATE", EstesUtil.formatDate(templateHead.getBolDate(),EstesUtil.MMDDYYYY)); /* CHAR(10) */ 
		params.put("PICK_UP", templateHead.getPickUp()); /* CHAR(1) */ 
		params.put("PICK_UP_DATE", templateHead.getPickUpDate()); /* CHAR(10) */ 
		params.put("BOL_NUMBER", templateHead.getBolNumber()); /* CHAR(35) */ 
		params.put("PRO_ORIG_TERM", templateHead.getProOrigTerm()); /* CHAR(3) */ 
		params.put("PRO_NUMBER", templateHead.getProNumber()); /* CHAR(7) */ 
		params.put("AUTO_ASIGN_PRO", templateHead.getAutoAsignPro()); /* CHAR(1) */ 
		params.put("PICKUP_REQ_USER", templateHead.getPickupReqUser()); /* CHAR(1) */ 
		params.put("USER_FIRST_NAME", templateHead.getUserFirstName()); /* CHAR(25) */ 
		params.put("USER_LAST_NAME", templateHead.getUserLastName()); /* CHAR(25) */ 
		params.put("USER_AREA_CODE", templateHead.getUserAreaCode()); /* CHAR(3) */ 
		params.put("USER_EXCHANGE", templateHead.getUserExchange()); /* CHAR(3) */ 
		params.put("USER_LAST_4", templateHead.getUserLast4()); /* CHAR(4) */ 
		params.put("USER_EXT", templateHead.getUserExt()); /* CHAR(5) */ 
		params.put("USER_EMAIL", templateHead.getUserEmail()); /* CHAR(50) */ 
		params.put("SHIP_COMPANY", templateHead.getShipCompany()); /* CHAR(30) */ 
		params.put("SHIP_ACCOUNT", templateHead.getShipAccount()); /* CHAR(7) */ 
		params.put("SHIP_FIRST_NAME", templateHead.getShipFirstName()); /* CHAR(25) */ 
		params.put("SHIP_LAST_NAME", templateHead.getShipLastName()); /* CHAR(25) */ 
		params.put("SHIP_LOCATION", templateHead.getShipLocation()); /* CHAR(10) */ 
		params.put("SHIP_AREA_CODE", templateHead.getShipAreaCode()); /* CHAR(3) */ 
		params.put("SHIP_EXCHANGE", templateHead.getShipExchange()); /* CHAR(3) */ 
		params.put("SHIP_LAST_4", templateHead.getShipLast4()); /* CHAR(4) */ 
		params.put("SHIP_EXT", templateHead.getShipExt()); /* CHAR(5) */ 
		params.put("SHIP_FAX_AREA_CODE", templateHead.getShipFaxAreaCode()); /* CHAR(3) */ 
		params.put("SHIP_FAX_EXCHANGE", templateHead.getShipFaxExchange()); /* CHAR(3) */ 
		params.put("SHIP_FAX_LAST_4", templateHead.getShipFaxLast4()); /* CHAR(4) */ 
		params.put("SHIP_COUNTRY", templateHead.getShipCountry()); /* CHAR(2) */ 
		params.put("SHIP_STR1_ADDRESS", templateHead.getShipStr1Address()); /* CHAR(30) */ 
		params.put("SHIP_STR2_ADDRESS", templateHead.getShipStr2Address()); /* CHAR(30) */ 
		params.put("SHIP_CITY", templateHead.getShipCity()); /* CHAR(20) */ 
		params.put("SHIP_STATE", templateHead.getShipState()); /* CHAR(2) */ 
		params.put("SHIP_ZIP", templateHead.getShipZip()); /* CHAR(6) */ 
		params.put("SHIP_ZIP_4", templateHead.getShipZip4()); /* CHAR(4) */ 
		params.put("CONS_COMPANY_NAME", templateHead.getConsCompanyName()); /* CHAR(30) */ 
		params.put("CONS_ACCOUNT", templateHead.getConsAccount()); /* CHAR(7) */ 
		params.put("CONS_FIRST_NAME", templateHead.getConsFirstName()); /* CHAR(25) */ 
		params.put("CONS_LAST_NAME", templateHead.getConsLastName()); /* CHAR(25) */ 
		params.put("CONS_LOCATION_NUMBER", templateHead.getConsLocationNumber()); /* CHAR(10) */ 
		params.put("CONS_PN_AREA_CODE", templateHead.getConsPnAreaCode()); /* CHAR(3) */ 
		params.put("CONS_PN_EXCHANGE", templateHead.getConsPnExchange()); /* CHAR(3) */ 
		params.put("CONS_PN_LAST_4", templateHead.getConsPnLast4()); /* CHAR(4) */ 
		params.put("CONS_PN_EXTENSION", templateHead.getConsPnExtension()); /* CHAR(5) */ 
		params.put("CONS_FN_AREA_CODE", templateHead.getConsFnAreaCode()); /* CHAR(3) */ 
		params.put("CONS_FN_EXCHANGE", templateHead.getConsFnExchange()); /* CHAR(3) */ 
		params.put("CONS_FN_LAST_4", templateHead.getConsFnLast4()); /* CHAR(4) */ 
		params.put("CONS_COUNTRY", templateHead.getConsCountry()); /* CHAR(2) */ 
		params.put("CONS_ST_ADDR_1", templateHead.getConsStAddr1()); /* CHAR(30) */ 
		params.put("CONS_ST_ADDR_2", templateHead.getConsStAddr2()); /* CHAR(30) */ 
		params.put("CONSCITY", templateHead.getConsCity()); /* CHAR(20) */ 
		params.put("CONSSTATE", templateHead.getConsState()); /* CHAR(2) */ 
		params.put("CONSZIP", templateHead.getConsZip()); /* CHAR(6) */ 
		params.put("CONS_ZIP_4", templateHead.getConsZip4()); /* CHAR(4) */ 
		params.put("PAYOR_CODE", templateHead.getPayorCode()); /* CHAR(1) */ 
		params.put("TERMS_CODE", templateHead.getTermsCode()); /* CHAR(3) */ 
		params.put("TPTY_COMPANY_NAME", templateHead.getTptyCompanyName()); /* CHAR(30) */ 
		params.put("TPTY_ACCOUNT", templateHead.getTptyAccount()); /* CHAR(7) */ 
		params.put("TPTY_FIRST_NAME", templateHead.getTptyFirstName()); /* CHAR(25) */ 
		params.put("TPTY_LAST_NAME", templateHead.getTptyLastName()); /* CHAR(25) */ 
		params.put("TPTY_LOCATION_NUMBER", templateHead.getTptyLocationNumber()); /* CHAR(10) */ 
		params.put("TPTY_PN_AREA_CODE", templateHead.getTptyPnAreaCode()); /* CHAR(3) */ 
		params.put("TPTY_PN_EXCHANGE", templateHead.getTptyPnExchange()); /* CHAR(3) */ 
		params.put("TPTY_PN_LAST_4", templateHead.getTptyPnLast4()); /* CHAR(4) */ 
		params.put("TPTY_PN_EXTENSION", templateHead.getTptyPnExtension()); /* CHAR(5) */ 
		params.put("TPTY_FN_AREA_CODE", templateHead.getTptyFnAreaCode()); /* CHAR(3) */ 
		params.put("TPTY_FN_EXCHANGE", templateHead.getTptyFnExchange()); /* CHAR(3) */ 
		params.put("TPTY_FN_LAST_4", templateHead.getTptyFnLast4()); /* CHAR(4) */ 
		params.put("TPTY_COUNTRY", templateHead.getTptyCountry()); /* CHAR(2) */ 
		params.put("TPTY_ST_ADDR_1", templateHead.getTptyStAddr1()); /* CHAR(30) */ 
		params.put("TPTY_ST_ADDR_2", templateHead.getTptyStAddr2()); /* CHAR(30) */ 
		params.put("TPTYCITY", templateHead.getTptyCity()); /* CHAR(20) */ 
		params.put("TPTYSTATE", templateHead.getTptyState()); /* CHAR(2) */ 
		params.put("TPTYZIP", templateHead.getTptyZip()); /* CHAR(6) */ 
		params.put("TPTY_ZIP_4", templateHead.getTptyZip4()); /* CHAR(4) */ 
		params.put("COD_FLG", templateHead.getCodFlg()); /* CHAR(1) */ 
		params.put("COD_AMOUNT", templateHead.getCodAmount()); /* CHAR(11) */ 
		params.put("COD_PAYMENT_TYPE", templateHead.getCodPaymentType()); /* CHAR(10) */ 
		params.put("COD_FEE_PAIDBY", templateHead.getCodFeePaidby()); /* CHAR(10) */ 
		params.put("CODNAME", templateHead.getCodname()); /* CHAR(30) */ 
		params.put("COD_FIRST_NAME", templateHead.getCodFirstName()); /* CHAR(25) */ 
		params.put("COD_LAST_NAME", templateHead.getCodLastName()); /* CHAR(25) */ 
		params.put("COD_LOCATION_NUMBER", templateHead.getCodLocationNumber()); /* CHAR(10) */ 
		params.put("COD_PN_AREA_CODE", templateHead.getCodPnAreaCode()); /* CHAR(3) */ 
		params.put("COD_PN_EXCHANGE", templateHead.getCodPnExchange()); /* CHAR(3) */ 
		params.put("COD_PN_LAST_4", templateHead.getCodPnLast4()); /* CHAR(4) */ 
		params.put("COD_PN_EXTENSION", templateHead.getCodPnExtension()); /* CHAR(5) */ 
		params.put("COD_COUNTRY", templateHead.getCodCountry()); /* CHAR(2) */ 
		params.put("COD_ST_ADDR_1", templateHead.getCodStAddr1()); /* CHAR(30) */ 
		params.put("COD_ST_ADDR_2", templateHead.getCodStAddr2()); /* CHAR(30) */ 
		params.put("CODCITY", templateHead.getCodCity()); /* CHAR(20) */ 
		params.put("CODSTATE", templateHead.getCodState()); /* CHAR(2) */ 
		params.put("CODZIP", templateHead.getCodZip()); /* CHAR(6) */ 
		params.put("COD_ZIP_4", templateHead.getCodZip4()); /* CHAR(4) */ 
		params.put("GOLD_STAR", templateHead.getGoldStar()); /* CHAR(1) */ 
		params.put("GOLD_STAR_PICKUP", templateHead.getGoldStarPickup()); /* CHAR(10) */ 
		params.put("GOLD_STAR_QUOTE", templateHead.getGoldStarQuote()); /* CHAR(7) */ 
		params.put("GOLD_SERVICE", templateHead.getGoldService()); /* CHAR(5) */ 
		params.put("VOLUME_SHIPMENT", templateHead.getVolumeShipment()); /* CHAR(1) */ 
		params.put("VOLUME_QUOTE", templateHead.getVolumeQuote()); /* CHAR(7) */ 
		params.put("SHIPPER_EMAIL", templateHead.getShipperEmail()); /* CHAR(50) */ 
		params.put("CONSIGNEE_EMAIL", templateHead.getConsigneeEmail()); /* CHAR(50) */ 
		params.put("THIRDPARTY_EMAIL", templateHead.getThirdpartyEmail()); /* CHAR(50) */ 
		params.put("COD_EMAIL", templateHead.getCodEmail()); /* CHAR(50) */ 
		params.put("EMAIL_BOL_SHIPPER", templateHead.getEmailBolShipper()); /* CHAR(1) */ 
		params.put("EMAIL_BOL_CONS", templateHead.getEmailBolCons()); /* CHAR(1) */ 
		params.put("EMAIL_BOL_TPTY", templateHead.getEmailBolTpty()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_SHIPPER", templateHead.getEmailTrkShipper()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_CONS", templateHead.getEmailTrkCons()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_TPTY", templateHead.getEmailTrkTpty()); /* CHAR(1) */ 
		params.put("TOTAL_PACKAGES", templateHead.getTotalPackages()); /* CHAR(5) */ 
		params.put("TOTAL_SHIP_UNITS", templateHead.getTotalShipUnits()); /* CHAR(5) */ 
		params.put("TOTAL_WEIGHT", templateHead.getTotalWeight()); /* CHAR(7) */ 
		params.put("TOTAL_VALUE", templateHead.getTotalValue()); /* CHAR(9) */ 
		params.put("HAZMAT_CONTACT", templateHead.getHazmatContact()); /* CHAR(30) */ 
		params.put("HAZMAT_AREA_CODE", templateHead.getHazmatAreaCode()); /* CHAR(3) */ 
		params.put("HAZMAT_EXCHANGE", templateHead.getHazmatExchange()); /* CHAR(3) */ 
		params.put("HAZMAT_LAST_4", templateHead.getHazmatLast4()); /* CHAR(4) */ 
		params.put("HAZMAT_EXTENSION", templateHead.getHazmatExtension()); /* CHAR(6) */ 
		params.put("PICKUP_INSTRUCTIONS", templateHead.getPickupInstructions()); /* CHAR(256) */ 
		params.put("SPECIAL_INSTRUCTIONS", templateHead.getSpecialInstructions()); /* CHAR(500) */ 
		params.put("CUBE", templateHead.getCube()); /* CHAR(9) */ 
		params.put("AVAILABLE_TIME", templateHead.getAvailableTime()); /* CHAR(4) */ 
		params.put("AVAILABLE_AM_PM", templateHead.getAvailableAmPm()); /* CHAR(2) */ 
		params.put("CLOSE_TIME", templateHead.getCloseTime()); /* CHAR(4) */ 
		params.put("CLOSE_AM_PM", templateHead.getCloseAmPm()); /* CHAR(2) */ 
		params.put("START_LABEL", templateHead.getStartLabel()); /* CHAR(15) */ 
		params.put("TOTAL_LABEL", templateHead.getTotalLabel()); /* CHAR(15) */ 
		params.put("FAX_TO_SHIPPER", templateHead.getFaxToShipper()); /* CHAR(1) */ 
		params.put("FAX_TO_CONSIGNEE", templateHead.getFaxToConsignee()); /* CHAR(1) */ 
		params.put("FAX_TO_THIRD_PARTY", templateHead.getFaxToThirdParty()); /* CHAR(1) */ 
		params.put("TRAILER_NUMBER", templateHead.getTrailerNumber()); /* CHAR(15) */ 
		params.put("SEAL_NUMBER", templateHead.getSealNumber()); /* CHAR(15) */ 
		params.put("S_C_A_C", templateHead.getScac()); /* CHAR(4) */ 
		params.put("MASTER_BOL", templateHead.getMasterBol()); /* CHAR(1) */ 
		params.put("MASTER_BOL_NUM", templateHead.getMasterBolNum()); /* CHAR(25) */ 
		params.put("BOL_TYPE", templateHead.getBolType()); /* CHAR(1) */ 
		params.put("PICKUP_DETAILS", templateHead.getPickupDetails()); /* CHAR(10) */ 
		params.put("SHIPMENT_DETAILS", templateHead.getShipmentDetails()); /* CHAR(10) */ 
		params.put("NOTIFY_PICKUP_RECEIPT", templateHead.getNotifyPickupReceipt()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_ACCEPT", templateHead.getNotifyPickupAccept()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_REJECT", templateHead.getNotifyPickupReject()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_BEGIN", templateHead.getNotifyPickupBegin()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_COMPLETE", templateHead.getNotifyPickupComplete()); /* CHAR(1) */ 
		params.put("EMAIL_BOL_OTHER", templateHead.getEmailBolOther()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_OTHER", templateHead.getEmailTrkOther()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_SHIPPER", templateHead.getEmailLabelShipper()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_CONS", templateHead.getEmailLabelCons()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_TPTY", templateHead.getEmailLabelTpty()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_OTHER", templateHead.getEmailLabelOther()); /* CHAR(1) */ 
		params.put("FAX_TO_OTHER", templateHead.getFaxToOther()); /* CHAR(1) */ 
		params.put("OTHER_FAX_AREA_CODE", templateHead.getOtherFaxAreaCode()); /* CHAR(3) */ 
		params.put("OTHER_FAX_EXCHANGE", templateHead.getOtherFaxExchange()); /* CHAR(3) */ 
		params.put("OTHER_FAX_LAST_4", templateHead.getOtherFaxLast4()); /* CHAR(4) */ 
		params.put("LABEL_TYPE", templateHead.getLabelType()); /* CHAR(1) */ 
		params.put("EMAIL_OTHER_ADDR", templateHead.getEmailOtherAddr()); /* CHAR(255) */ 
		params.put("FAX_BOL_SHIPPER", templateHead.getFaxBolShipper()); /* CHAR(1) */ 
		params.put("FAX_BOL_CONSIGNEE", templateHead.getFaxBolConsignee()); /* CHAR(1) */ 
		params.put("FAX_BOL_TPTY", templateHead.getFaxBolTpty()); /* CHAR(1) */ 
		params.put("FAX_BOL_OTHER", templateHead.getFaxBolOther()); /* CHAR(1) */ 
		params.put("FAX_BOL_NUMBER", templateHead.getFaxBolNumber()); /* CHAR(10) */ 
		
		params.put("USER_NAME",username);
		params.put("TEMPLATE_DESC",template);
		
		

		LinkedHashMap<String,String> filteredParams = new LinkedHashMap<String,String>();
		
	    //will this iterator iterate the keys in the order of the map (1, 2, 3, 4)? Or just random order?
	    Iterator<String> itr = params.keySet().iterator();
	    while(itr.hasNext()) {
	        String key = itr.next();
	        if(params.get(key)!=null){
	        	filteredParams.put(key, params.get(key));
	        }
	    }

		
		String query = "";
		
		if(isTemplateExist(username, template)==false){			
			query = QueryUtil.prepareInsertQuery(DATALIB, TEMPLATE_HEADER_TABLE, filteredParams.keySet());
		}else{
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("USER_NAME");
			whereParams.add("TEMPLATE_DESC");
			query = QueryUtil.prepareUpdateQuery(DATALIB, TEMPLATE_HEADER_TABLE, filteredParams.keySet(),whereParams);
		}

		Object[] values = filteredParams.values().toArray();
		

		
		jdbcTemplate.update(query,values);
		
	}
	
	@Override
	public boolean isTemplateExist(String username, String template) {
		int count = (int) jdbcTemplate.queryForObject(COUNT_TEMPLATE, new Object[]{username,template}, Integer.class);
		return count>0;
	}
	
	@Override
	public void deleteTemplateHeader(String username, String template){
		jdbcTemplate.update(DELETE_TEMPLATE_HEADER, new Object[]{username,template});
	}
	
	@Override
	public void deleteTemplateReference(String username, String template){
		jdbcTemplate.update(DELETE_TEMPLATE_REFERENCE, new Object[]{username,template});
	}
	
	@Override
	public void deleteTemplateCommodity(String username, String template){
		jdbcTemplate.update(DELETE_TEMPLATE_COMMODITY, new Object[]{username,template});
	}
	
	@Override
	public void deleteTemplateAccessorial(String username, String template){
		jdbcTemplate.update(DELETE_TEMPLATE_ACCESSORIAL, new Object[]{username,template});
	}
	
	@Override
	public void saveTemplateReferences(String username, String template, BillOfLading billOfLading){
		
		/**
		 * Delete all the existing reference for the template
		 */
		if(billOfLading.getReferences()!=null){
			deleteTemplateReference(username,template);
		}
		
		
		
		/**
		 * save all the refrences
		 */
		if(billOfLading.getReferences()!=null){
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("USER_NAME",username);
			params.put("CALLED_BY_PGM","EBG10R320"); 
			params.put("TEMPLATE_DESC",template);
			params.put("SHIPPER_INFO","");
			int i=1;
			
			for(Reference reference : billOfLading.getReferences()){
				
				if(reference.getReferenceType()!=null && reference.getReferenceNumber()!=null ){
					params.put("REFERENCE_NO_SEQUENCE",i); 
					params.put("REF_NO", reference.getReferenceNumber());
					
					params.put("TYPE", "");
					if(reference.getReferenceType()!=null){
						params.put("TYPE", reference.getReferenceType().name());
					}
					
					params.put("CARTONS",reference.getCartoon());
					params.put("WEIGHT", reference.getWeight());
					
					String query = QueryUtil.prepareInsertQuery(DATALIB, TEMPLATE_REFERENCE_TABLE, params.keySet());
					
					jdbcTemplate.update(query,params.values().toArray());
					i++;
				}
			}
			
		}
	}
	
	
	@Override
	public void saveTemplateCommodities(String username, String template, BillOfLading billOfLading){
		
		/**
		 * Delete all the existing commodities for the template
		 */
		if(billOfLading.getCommodityInfo()!=null && billOfLading.getCommodityInfo().getCommodityList()!=null){
			deleteTemplateCommodity(username, template);
		}
		
		
		/**
		 * Save all the commodities
		 */
		
		
		if(billOfLading.getCommodityInfo()!=null && billOfLading.getCommodityInfo().getCommodityList().isEmpty()==false){
			Map<String, Object> params = new LinkedHashMap<>();
			
			params.put("USER_NAME", username); 
			params.put("CALLED_BY_PGM","EBG10R330");
			params.put("TEMPLATE_DESC",template);
			
			int i =1;
			for(Commodity commodity : billOfLading.getCommodityInfo().getCommodityList()){
				
				params.put("SHIP_INFO_SEQUENCE", i); 
				params.put("SHIP_QUANTITY", commodity.getNumberOfPackage()); 
				params.put("SHIP_GOODS_TYPE", commodity.getPackageType()); 
				params.put("WEIGHT", commodity.getGoodsWeight()); 
				
				params.put("CLASS", "");
				if(commodity.getShipmentClass()!=null){
					params.put("CLASS", commodity.getShipmentClass().getCode()); 
				}
				
				params.put("NMFC", commodity.getNmfc()); 
				params.put("GOODS_QUANTITY", commodity.getGoodsUnit()); 
				params.put("GOODS_TYPE", "");
				
				if(commodity.getGoodsType()!=null){
					params.put("GOODS_TYPE", commodity.getGoodsType().getCode()); 
				}
				
				params.put("HAZ_MAT", commodity.isHazmat()? "Y" : "N"); 
				params.put("DECLARED_VALUE", ""); 
				params.put("HAZMAT_CLASS",""); 
				params.put("HAZMAT_DESCRIPTION", ""); 
				params.put("NMFC_SUB", commodity.getNmfcExt()); 
				params.put("UNNA_NUM",""); 
				params.put("DESCRIPTION", commodity.getDescription()); 
				params.put("DIM_HEIGHT",""); 
				params.put("DIM_LENGTH",""); 
				params.put("DIM_WIDTH",""); 
				params.put("LINEAR_FEET",""); 
				
				
				for (Map.Entry<String, Object> entry : params.entrySet()) {
					if (entry.getValue() == null) {
						entry.setValue("");
					}
				}
				
				
				String query = QueryUtil.prepareInsertQuery(DATALIB, TEMPLATE_COMMODITY_TABLE, params.keySet());
				
				jdbcTemplate.update(query, params.values().toArray());
				
				i++;
			}
			
		}
	}
	
	@Override
	public void saveTemplateAccessorials(String username, String template, BillOfLading billOfLading){
		
		/**
		 * delete accessorials 
		 */
		
		if(billOfLading.getAccessorials()!=null){
			deleteTemplateAccessorial(username, template);
		}
		
		if(billOfLading.getAccessorials().isEmpty()==false){
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("USER_NAME", username); 
			params.put("TEMPLATE_DESC",template);
			params.put("CALLED_BY_PGM","EBG10R340");
			
			
			if(billOfLading.getBillingInfo()!=null 
					&& billOfLading.getBillingInfo().getBillTo()!=null 
					&& billOfLading.getBillingInfo().getBillTo().getFee()!=null 
					&& billOfLading.getBillingInfo().getBillTo().getFee().name()!=null){
				params.put("TERMS", billOfLading.getBillingInfo().getBillTo().getFee().name()); 
			}
			
			int i =1;
			for(Accessorial accessorial : billOfLading.getAccessorials()){
				params.put("ACCESSORIAL_SEQUENCE",i);  
				params.put("ACCESSORIAL_CODE", accessorial.getCode());
				
				String query = QueryUtil.prepareInsertQuery(DATALIB, TEMPLATE_ACCESSORIAL_TABLE, params.keySet());	
				jdbcTemplate.update(query, params.values().toArray());
				
				i++;
			}
		}
		
	}
	

	@Override
	public BillOfLading getBillOfLading(String username, String templateName){
		
		
		TemplateHead templateHead= jdbcTemplate.queryForObject(GET_TEMPLATE_HEADER, new Object[]{username,templateName},new TemplateHeadMapper());
		
		BillOfLading billOfLading = templateHead.getBillOfLading();
		
		
		/**
		 * Retrieve Commodity List
		 */
		
		List<Commodity> commodityList = jdbcTemplate.query(GET_TEMPLATE_COMMODITY, new Object[]{username,templateName},new CommodityMapper());
		
		billOfLading.getCommodityInfo().setCommodityList(commodityList);
		
		/**
		 * Retrieve Reference List
		 */
		List<Reference> references = jdbcTemplate.query(GET_TEMPLATE_REFERENCE, new Object[]{username,templateName},new ReferenceMapper());
		
		
		billOfLading.setReferences(references);
		
		/**
		 * Retrieve Accessorial List
		 */
		
		List<Accessorial> accessorials = jdbcTemplate.query(GET_TEMPLATE_ACCESSORIAL, new Object[]{username,templateName},new AccessorialMapper());
		
		billOfLading.setAccessorials(accessorials);
		
		return billOfLading;
		
	}

}
