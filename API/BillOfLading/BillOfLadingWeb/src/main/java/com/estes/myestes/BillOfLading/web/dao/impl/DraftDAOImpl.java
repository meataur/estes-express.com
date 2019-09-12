package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.COUNT_DRAFT;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DELETE_DRAFT_ACCESSORIAL;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DELETE_DRAFT_COMMODITY;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DELETE_DRAFT_HEADER;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DELETE_DRAFT_REFERENCE;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DRAFT_ACCESSORIAL_TABLE;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DRAFT_COMMODITY_TABLE;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DRAFT_HEADER_TABLE;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.DRAFT_REFERENCE_TABLE;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.GET_DRAFT_ACCESSORIAL;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.GET_DRAFT_COMMODITY;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.GET_DRAFT_HEADER;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.GET_DRAFT_LIST;
import static com.estes.myestes.BillOfLading.web.dao.sql.DraftQuery.GET_DRAFT_REFERENCE;
import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.DATALIB;

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
import com.estes.myestes.BillOfLading.web.dao.iface.DraftDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.PagingAndSortingDAO;
import com.estes.myestes.BillOfLading.web.dao.mapper.AccessorialMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.CommodityMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.DraftMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.DraftHeadMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.ReferenceMapper;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.Draft;
import com.estes.myestes.BillOfLading.web.dto.DraftHead;
import com.estes.myestes.BillOfLading.web.dto.Reference;
@Repository("draftDAO")
public class DraftDAOImpl implements DraftDAO {

	@Autowired
	private PagingAndSortingDAO<Draft> pagingAndSortingDAO;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Page<Draft> getDraftList(String username, Pageable pageable, BolFilter filter) {
		/**
		 * Params are needed in query
		 */
		
		List<Object> params = new ArrayList<>();
		params.add(username);
		
		
		/**
		 * Filtering UserDefaultQuery Preparation
		 */
		
		BolFilter.FilterBy filterBy = filter.getFilterBy();
		
		String query = GET_DRAFT_LIST;
		//System.out.println(filter);
		
		switch(filterBy){
		
			case SHOW_ALL:
				break;
				
			case BOL_NUMBER:
				query +=" AND UPPER(bolNumber) LIKE ? ";
				
				if(filter.getBolNumber()==null || filter.getBolNumber().trim()==""){
					throw new BolException(DEFAULT_ERROR_CODE, "BOL # is required", "bolNumber");
				}
				
				params.add("%"+filter.getBolNumber().toUpperCase()+"%");
				
				break;
				
				
			case BOL_DATE_RANGE:
				query += " AND bolDate >= ? AND bolDate <= ?";
				
				if(filter.getBolStartDate()==null){
					throw new BolException(DEFAULT_ERROR_CODE, "From date is required","bolStartDate");
				}
				
				if(filter.getBolEndDate()==null){
					filter.setBolEndDate(new Date());
				}
				
				params.add(EstesUtil.formatDate(filter.getBolStartDate(),"yyyyMMdd"));
				params.add(EstesUtil.formatDate(filter.getBolEndDate(),"yyyyMMdd"));
				
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
		
		
		
		Page<Draft> results = pagingAndSortingDAO.getResult(countQuery, query, params, pageable, new DraftMapper());
		return results;
	}

	
	
	@Override
	public void saveDraftHeader(String username, BillOfLading billOfLading) {
		
		DraftHead draftHead = new DraftHead(billOfLading);
		LinkedHashMap<String, String> params = new LinkedHashMap<>();
		params.put("CALLED_BY_PGM","EBG10R310"); 
		params.put("BOL_DATE", EstesUtil.formatDate(draftHead.getBolDate(),EstesUtil.DATE_YYYYMMDD)); /* CHAR(10) */ 
		params.put("PICK_UP", draftHead.getPickUp()); /* CHAR(1) */ 
		params.put("PICK_UP_DATE", draftHead.getPickUpDate()); /* CHAR(10) */ 
		params.put("PRO_ORIG_TERM", draftHead.getProOrigTerm()); /* CHAR(3) */ 
		params.put("PRO_NUMBER", draftHead.getProNumber()); /* CHAR(7) */ 
		params.put("AUTO_ASIGN_PRO", draftHead.getAutoAsignPro()); /* CHAR(1) */ 
		params.put("PICKUP_REQ_USER", draftHead.getPickupReqUser()); /* CHAR(1) */ 
		params.put("USER_FIRST_NAME", draftHead.getUserFirstName()); /* CHAR(25) */ 
		params.put("USER_LAST_NAME", draftHead.getUserLastName()); /* CHAR(25) */ 
		params.put("USER_AREA_CODE", draftHead.getUserAreaCode()); /* CHAR(3) */ 
		params.put("USER_EXCHANGE", draftHead.getUserExchange()); /* CHAR(3) */ 
		params.put("USER_LAST_4", draftHead.getUserLast4()); /* CHAR(4) */ 
		params.put("USER_EXT", draftHead.getUserExt()); /* CHAR(5) */ 
		params.put("USER_EMAIL", draftHead.getUserEmail()); /* CHAR(50) */ 
		params.put("SHIP_COMPANY", draftHead.getShipCompany()); /* CHAR(30) */ 
		params.put("SHIP_ACCOUNT", draftHead.getShipAccount()); /* CHAR(7) */ 
		params.put("SHIP_FIRST_NAME", draftHead.getShipFirstName()); /* CHAR(25) */ 
		params.put("SHIP_LAST_NAME", draftHead.getShipLastName()); /* CHAR(25) */ 
		params.put("SHIP_LOCATION", draftHead.getShipLocation()); /* CHAR(10) */ 
		params.put("SHIP_AREA_CODE", draftHead.getShipAreaCode()); /* CHAR(3) */ 
		params.put("SHIP_EXCHANGE", draftHead.getShipExchange()); /* CHAR(3) */ 
		params.put("SHIP_LAST_4", draftHead.getShipLast4()); /* CHAR(4) */ 
		params.put("SHIP_EXT", draftHead.getShipExt()); /* CHAR(5) */ 
		params.put("SHIP_FAX_AREA_CODE", draftHead.getShipFaxAreaCode()); /* CHAR(3) */ 
		params.put("SHIP_FAX_EXCHANGE", draftHead.getShipFaxExchange()); /* CHAR(3) */ 
		params.put("SHIP_FAX_LAST_4", draftHead.getShipFaxLast4()); /* CHAR(4) */ 
		params.put("SHIP_COUNTRY", draftHead.getShipCountry()); /* CHAR(2) */ 
		params.put("SHIP_STR1_ADDRESS", draftHead.getShipStr1Address()); /* CHAR(30) */ 
		params.put("SHIP_STR2_ADDRESS", draftHead.getShipStr2Address()); /* CHAR(30) */ 
		params.put("SHIP_CITY", draftHead.getShipCity()); /* CHAR(20) */ 
		params.put("SHIP_STATE", draftHead.getShipState()); /* CHAR(2) */ 
		params.put("SHIP_ZIP", draftHead.getShipZip()); /* CHAR(6) */ 
		params.put("SHIP_ZIP_4", draftHead.getShipZip4()); /* CHAR(4) */ 
		params.put("CONS_COMPANY_NAME", draftHead.getConsCompanyName()); /* CHAR(30) */ 
		params.put("CONS_ACCOUNT", draftHead.getConsAccount()); /* CHAR(7) */ 
		params.put("CONS_FIRST_NAME", draftHead.getConsFirstName()); /* CHAR(25) */ 
		params.put("CONS_LAST_NAME", draftHead.getConsLastName()); /* CHAR(25) */ 
		params.put("CONS_LOCATION_NUMBER", draftHead.getConsLocationNumber()); /* CHAR(10) */ 
		params.put("CONS_PN_AREA_CODE", draftHead.getConsPnAreaCode()); /* CHAR(3) */ 
		params.put("CONS_PN_EXCHANGE", draftHead.getConsPnExchange()); /* CHAR(3) */ 
		params.put("CONS_PN_LAST_4", draftHead.getConsPnLast4()); /* CHAR(4) */ 
		params.put("CONS_PN_EXTENSION", draftHead.getConsPnExtension()); /* CHAR(5) */ 
		params.put("CONS_FN_AREA_CODE", draftHead.getConsFnAreaCode()); /* CHAR(3) */ 
		params.put("CONS_FN_EXCHANGE", draftHead.getConsFnExchange()); /* CHAR(3) */ 
		params.put("CONS_FN_LAST_4", draftHead.getConsFnLast4()); /* CHAR(4) */ 
		params.put("CONS_COUNTRY", draftHead.getConsCountry()); /* CHAR(2) */ 
		params.put("CONS_ST_ADDR_1", draftHead.getConsStAddr1()); /* CHAR(30) */ 
		params.put("CONS_ST_ADDR_2", draftHead.getConsStAddr2()); /* CHAR(30) */ 
		params.put("CONSCITY", draftHead.getConsCity()); /* CHAR(20) */ 
		params.put("CONSSTATE", draftHead.getConsState()); /* CHAR(2) */ 
		params.put("CONSZIP", draftHead.getConsZip()); /* CHAR(6) */ 
		params.put("CONS_ZIP_4", draftHead.getConsZip4()); /* CHAR(4) */ 
		params.put("PAYOR_CODE", draftHead.getPayorCode()); /* CHAR(1) */ 
		params.put("TERMS_CODE", draftHead.getTermsCode()); /* CHAR(3) */ 
		params.put("TPTY_COMPANY_NAME", draftHead.getTptyCompanyName()); /* CHAR(30) */ 
		params.put("TPTY_ACCOUNT", draftHead.getTptyAccount()); /* CHAR(7) */ 
		params.put("TPTY_FIRST_NAME", draftHead.getTptyFirstName()); /* CHAR(25) */ 
		params.put("TPTY_LAST_NAME", draftHead.getTptyLastName()); /* CHAR(25) */ 
		params.put("TPTY_LOCATION_NUMBER", draftHead.getTptyLocationNumber()); /* CHAR(10) */ 
		params.put("TPTY_PN_AREA_CODE", draftHead.getTptyPnAreaCode()); /* CHAR(3) */ 
		params.put("TPTY_PN_EXCHANGE", draftHead.getTptyPnExchange()); /* CHAR(3) */ 
		params.put("TPTY_PN_LAST_4", draftHead.getTptyPnLast4()); /* CHAR(4) */ 
		params.put("TPTY_PN_EXTENSION", draftHead.getTptyPnExtension()); /* CHAR(5) */ 
		params.put("TPTY_FN_AREA_CODE", draftHead.getTptyFnAreaCode()); /* CHAR(3) */ 
		params.put("TPTY_FN_EXCHANGE", draftHead.getTptyFnExchange()); /* CHAR(3) */ 
		params.put("TPTY_FN_LAST_4", draftHead.getTptyFnLast4()); /* CHAR(4) */ 
		params.put("TPTY_COUNTRY", draftHead.getTptyCountry()); /* CHAR(2) */ 
		params.put("TPTY_ST_ADDR_1", draftHead.getTptyStAddr1()); /* CHAR(30) */ 
		params.put("TPTY_ST_ADDR_2", draftHead.getTptyStAddr2()); /* CHAR(30) */ 
		params.put("TPTYCITY", draftHead.getTptyCity()); /* CHAR(20) */ 
		params.put("TPTYSTATE", draftHead.getTptyState()); /* CHAR(2) */ 
		params.put("TPTYZIP", draftHead.getTptyZip()); /* CHAR(6) */ 
		params.put("TPTY_ZIP_4", draftHead.getTptyZip4()); /* CHAR(4) */ 
		params.put("COD_FLG", draftHead.getCodFlg()); /* CHAR(1) */ 
		params.put("COD_AMOUNT", draftHead.getCodAmount()); /* CHAR(11) */ 
		params.put("COD_PAYMENT_TYPE", draftHead.getCodPaymentType()); /* CHAR(10) */ 
		params.put("COD_FEE_PAIDBY", draftHead.getCodFeePaidby()); /* CHAR(10) */ 
		params.put("CODNAME", draftHead.getCodname()); /* CHAR(30) */ 
		params.put("COD_FIRST_NAME", draftHead.getCodFirstName()); /* CHAR(25) */ 
		params.put("COD_LAST_NAME", draftHead.getCodLastName()); /* CHAR(25) */ 
		params.put("COD_LOCATION_NUMBER", draftHead.getCodLocationNumber()); /* CHAR(10) */ 
		params.put("COD_PN_AREA_CODE", draftHead.getCodPnAreaCode()); /* CHAR(3) */ 
		params.put("COD_PN_EXCHANGE", draftHead.getCodPnExchange()); /* CHAR(3) */ 
		params.put("COD_PN_LAST_4", draftHead.getCodPnLast4()); /* CHAR(4) */ 
		params.put("COD_PN_EXTENSION", draftHead.getCodPnExtension()); /* CHAR(5) */ 
		params.put("COD_COUNTRY", draftHead.getCodCountry()); /* CHAR(2) */ 
		params.put("COD_ST_ADDR_1", draftHead.getCodStAddr1()); /* CHAR(30) */ 
		params.put("COD_ST_ADDR_2", draftHead.getCodStAddr2()); /* CHAR(30) */ 
		params.put("CODCITY", draftHead.getCodCity()); /* CHAR(20) */ 
		params.put("CODSTATE", draftHead.getCodState()); /* CHAR(2) */ 
		params.put("CODZIP", draftHead.getCodZip()); /* CHAR(6) */ 
		params.put("COD_ZIP_4", draftHead.getCodZip4()); /* CHAR(4) */ 
		params.put("GOLD_STAR", draftHead.getGoldStar()); /* CHAR(1) */ 
		params.put("GOLD_STAR_PICKUP", draftHead.getGoldStarPickup()); /* CHAR(10) */ 
		params.put("GOLD_STAR_QUOTE", draftHead.getGoldStarQuote()); /* CHAR(7) */ 
		params.put("GOLD_SERVICE", draftHead.getGoldService()); /* CHAR(5) */ 
		params.put("VOLUME_SHIPMENT", draftHead.getVolumeShipment()); /* CHAR(1) */ 
		params.put("VOLUME_QUOTE", draftHead.getVolumeQuote()); /* CHAR(7) */ 
		params.put("SHIPPER_EMAIL", draftHead.getShipperEmail()); /* CHAR(50) */ 
		params.put("CONSIGNEE_EMAIL", draftHead.getConsigneeEmail()); /* CHAR(50) */ 
		params.put("THIRDPARTY_EMAIL", draftHead.getThirdpartyEmail()); /* CHAR(50) */ 
		params.put("COD_EMAIL", draftHead.getCodEmail()); /* CHAR(50) */ 
		params.put("EMAIL_BOL_SHIPPER", draftHead.getEmailBolShipper()); /* CHAR(1) */ 
		params.put("EMAIL_BOL_CONS", draftHead.getEmailBolCons()); /* CHAR(1) */ 
		params.put("EMAIL_BOL_TPTY", draftHead.getEmailBolTpty()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_SHIPPER", draftHead.getEmailTrkShipper()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_CONS", draftHead.getEmailTrkCons()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_TPTY", draftHead.getEmailTrkTpty()); /* CHAR(1) */ 
		params.put("TOTAL_PACKAGES", draftHead.getTotalPackages()); /* CHAR(5) */ 
		params.put("TOTAL_SHIP_UNITS", draftHead.getTotalShipUnits()); /* CHAR(5) */ 
		params.put("TOTAL_WEIGHT", draftHead.getTotalWeight()); /* CHAR(7) */ 
		params.put("TOTAL_VALUE", draftHead.getTotalValue()); /* CHAR(9) */ 
		params.put("HAZMAT_CONTACT", draftHead.getHazmatContact()); /* CHAR(30) */ 
		params.put("HAZMAT_AREA_CODE", draftHead.getHazmatAreaCode()); /* CHAR(3) */ 
		params.put("HAZMAT_EXCHANGE", draftHead.getHazmatExchange()); /* CHAR(3) */ 
		params.put("HAZMAT_LAST_4", draftHead.getHazmatLast4()); /* CHAR(4) */ 
		params.put("HAZMAT_EXTENSION", draftHead.getHazmatExtension()); /* CHAR(6) */ 
		params.put("PICKUP_INSTRUCTIONS", draftHead.getPickupInstructions()); /* CHAR(256) */ 
		params.put("SPECIAL_INSTRUCTIONS", draftHead.getSpecialInstructions()); /* CHAR(500) */ 
		params.put("CUBE", draftHead.getCube()); /* CHAR(9) */ 
		params.put("AVAILABLE_TIME", draftHead.getAvailableTime()); /* CHAR(4) */ 
		params.put("AVAILABLE_AM_PM", draftHead.getAvailableAmPm()); /* CHAR(2) */ 
		params.put("CLOSE_TIME", draftHead.getCloseTime()); /* CHAR(4) */ 
		params.put("CLOSE_AM_PM", draftHead.getCloseAmPm()); /* CHAR(2) */ 
		params.put("START_LABEL", draftHead.getStartLabel()); /* CHAR(15) */ 
		params.put("TOTAL_LABEL", draftHead.getTotalLabel()); /* CHAR(15) */ 
		params.put("FAX_TO_SHIPPER", draftHead.getFaxToShipper()); /* CHAR(1) */ 
		params.put("FAX_TO_CONSIGNEE", draftHead.getFaxToConsignee()); /* CHAR(1) */ 
		params.put("FAX_TO_THIRD_PARTY", draftHead.getFaxToThirdParty()); /* CHAR(1) */ 
		params.put("TRAILER_NUMBER", draftHead.getTrailerNumber()); /* CHAR(15) */ 
		params.put("SEAL_NUMBER", draftHead.getSealNumber()); /* CHAR(15) */ 
		params.put("S_C_A_C", draftHead.getScac()); /* CHAR(4) */ 
		params.put("MASTER_BOL", draftHead.getMasterBol()); /* CHAR(1) */ 
		params.put("MASTER_BOL_NUM", draftHead.getMasterBolNum()); /* CHAR(25) */ 
		params.put("BOL_TYPE", draftHead.getBolType()); /* CHAR(1) */ 
		params.put("PICKUP_DETAILS", draftHead.getPickupDetails()); /* CHAR(10) */ 
		params.put("SHIPMENT_DETAILS", draftHead.getShipmentDetails()); /* CHAR(10) */ 
		params.put("NOTIFY_PICKUP_RECEIPT", draftHead.getNotifyPickupReceipt()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_ACCEPT", draftHead.getNotifyPickupAccept()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_REJECT", draftHead.getNotifyPickupReject()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_BEGIN", draftHead.getNotifyPickupBegin()); /* CHAR(1) */ 
		params.put("NOTIFY_PICKUP_COMPLETE", draftHead.getNotifyPickupComplete()); /* CHAR(1) */ 
		params.put("EMAIL_BOL_OTHER", draftHead.getEmailBolOther()); /* CHAR(1) */ 
		params.put("EMAIL_TRK_OTHER", draftHead.getEmailTrkOther()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_SHIPPER", draftHead.getEmailLabelShipper()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_CONS", draftHead.getEmailLabelCons()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_TPTY", draftHead.getEmailLabelTpty()); /* CHAR(1) */ 
		params.put("EMAIL_LABEL_OTHER", draftHead.getEmailLabelOther()); /* CHAR(1) */ 
		params.put("FAX_TO_OTHER", draftHead.getFaxToOther()); /* CHAR(1) */ 
		params.put("OTHER_FAX_AREA_CODE", draftHead.getOtherFaxAreaCode()); /* CHAR(3) */ 
		params.put("OTHER_FAX_EXCHANGE", draftHead.getOtherFaxExchange()); /* CHAR(3) */ 
		params.put("OTHER_FAX_LAST_4", draftHead.getOtherFaxLast4()); /* CHAR(4) */ 
		params.put("LABEL_TYPE", draftHead.getLabelType()); /* CHAR(1) */ 
		params.put("EMAIL_OTHER_ADDR", draftHead.getEmailOtherAddr()); /* CHAR(255) */ 
		params.put("FAX_BOL_SHIPPER", draftHead.getFaxBolShipper()); /* CHAR(1) */ 
		params.put("FAX_BOL_CONSIGNEE", draftHead.getFaxBolConsignee()); /* CHAR(1) */ 
		params.put("FAX_BOL_TPTY", draftHead.getFaxBolTpty()); /* CHAR(1) */ 
		params.put("FAX_BOL_OTHER", draftHead.getFaxBolOther()); /* CHAR(1) */ 
		params.put("FAX_BOL_NUMBER", draftHead.getFaxBolNumber()); /* CHAR(10) */ 
		params.put("USER_NAME",username);
		params.put("BOL_NUMBER", draftHead.getBolNumber());
		
		
		
		LinkedHashMap<String,String> filteredParams = new LinkedHashMap<String,String>();
		
	    //will this iterator iterate the keys in the order of the map (1, 2, 3, 4)? Or just random order?
	    Iterator<String> itr = params.keySet().iterator();
	    while(itr.hasNext()) {
	        String key = itr.next();
	        if(params.get(key)!=null){
	        	filteredParams.put(key, params.get(key));
	        }
	    }
		
		
		//System.out.println(filteredParams);
		
		String query = "";
		if(isDraftExist(username, draftHead.getBolNumber())==false){			
			query = QueryUtil.prepareInsertQuery(DATALIB, DRAFT_HEADER_TABLE, filteredParams.keySet());
		}else{
			
			Set<String> whereParams = new LinkedHashSet<>();
			whereParams.add("USER_NAME");
			whereParams.add("BOL_NUMBER");
			query = QueryUtil.prepareUpdateQuery(DATALIB, DRAFT_HEADER_TABLE, filteredParams.keySet(),whereParams);
		}

		Object[] values = filteredParams.values().toArray();

		jdbcTemplate.update(query,values);		
	}
	
	@Override
	public boolean isDraftExist(String username, String bolNumber){
		int count = (int) jdbcTemplate.queryForObject(COUNT_DRAFT, new Object[]{username,bolNumber}, Integer.class);
		return count>0;
		
	}
	

	@Override
	public void deleteDraftHeader(String username, String bolNumber){
		jdbcTemplate.update(DELETE_DRAFT_HEADER, new Object[]{username,bolNumber});
	}
	
	@Override
	public void deleteDraftReference(String username, String bolNumber){
		jdbcTemplate.update(DELETE_DRAFT_REFERENCE, new Object[]{username,bolNumber});
	}
	
	@Override
	public void deleteDraftCommodity(String username, String bolNumber){
		jdbcTemplate.update(DELETE_DRAFT_COMMODITY, new Object[]{username,bolNumber});
	}
	
	@Override
	public void deleteDraftAccessorial(String username, String bolNumber){
		jdbcTemplate.update(DELETE_DRAFT_ACCESSORIAL, new Object[]{username,bolNumber});
	}
	
	@Override
	public void saveDraftReferences(String username, String bolNumber, BillOfLading billOfLading){
		
		/**
		 * Delete all the existing reference for the bolNumber
		 */
		if(billOfLading.getReferences()!=null){
			deleteDraftReference(username,bolNumber);
		}
		

		/**
		 * save all the refrences
		 */
		if(billOfLading.getReferences()!=null){
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("USER_NAME",username);
			params.put("CALLED_BY_PGM","EBG10R320"); 
			params.put("BOL_NUMBER",bolNumber);
			params.put("SHIPPER_INFO","");
			int i=1;
			
			for(Reference reference : billOfLading.getReferences()){
				
				if(reference.getReferenceType()!=null && reference.getReferenceNumber()!=null ){
					params.put("REFERENCE_NO_SEQUENCE",i); 
					params.put("REF_NO", reference.getReferenceNumber());
					params.put("TYPE", reference.getReferenceType().name());
					params.put("CARTONS",reference.getCartoon());
					params.put("WEIGHT", reference.getWeight());
					
					String query = QueryUtil.prepareInsertQuery(DATALIB, DRAFT_REFERENCE_TABLE, params.keySet());
					
					jdbcTemplate.update(query,params.values().toArray());
					i++;
				}
			}
			
		}
	}
	
	
	@Override
	public void saveDraftCommodities(String username, String bolNumber, BillOfLading billOfLading){
		
		/**
		 * Delete all the existing commodities for the bolNumber
		 */
		if(billOfLading.getCommodityInfo()!=null && billOfLading.getCommodityInfo().getCommodityList()!=null){
			deleteDraftCommodity(username, bolNumber);
		}
		
		
		/**
		 * Save all the commodities
		 */


		
		
		if(billOfLading.getCommodityInfo()!=null && billOfLading.getCommodityInfo().getCommodityList().isEmpty()==false){
			Map<String, Object> params = new LinkedHashMap<>();
			
			params.put("USER_NAME", username); 
			params.put("CALLED_BY_PGM","EBG10R330");
			params.put("BOL_NUMBER",bolNumber);
			
			int i =1;
			for(Commodity commodity : billOfLading.getCommodityInfo().getCommodityList()){
				
				params.put("SHIP_INFO_SEQUENCE", i); 
				params.put("SHIP_QUANTITY", commodity.getNumberOfPackage()); 
				params.put("SHIP_TYPE", commodity.getPackageType()); 
				params.put("WEIGHT", commodity.getGoodsWeight()); 
				
				params.put("CLASS", "");
				
				if(commodity.getShipmentClass()!=null){
					params.put("CLASS", commodity.getShipmentClass().getCode()); 
				}
				
				params.put("NMFC", commodity.getNmfc()); 
				params.put("GOODS_QUANTITY", commodity.getGoodsUnit()); 
				
				params.put("GOODS_TYPE","");
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
				
				String query = QueryUtil.prepareInsertQuery(DATALIB, DRAFT_COMMODITY_TABLE, params.keySet());
				
				jdbcTemplate.update(query, params.values().toArray());
				
				i++;
			}
			
		}
	}
	
	@Override
	public void saveDraftAccessorials(String username, String bolNumber, BillOfLading billOfLading){
		
		/**
		 * delete accessorials 
		 */
		
		if(billOfLading.getAccessorials()!=null){
			deleteDraftAccessorial(username, bolNumber);
		}
		

		
		if(billOfLading.getAccessorials().isEmpty()==false){
			Map<String, Object> params = new LinkedHashMap<>();
			params.put("USER_NAME", username); 
			params.put("BOL_NUMBER",bolNumber);
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
				
				String query = QueryUtil.prepareInsertQuery(DATALIB, DRAFT_ACCESSORIAL_TABLE, params.keySet());	
				jdbcTemplate.update(query, params.values().toArray());
				
				i++;
			}
		}
		
	}
	
	@Override
	public BillOfLading getBillOfLading(String username, String bolNumber){
		
		
		DraftHead draftHead= jdbcTemplate.queryForObject(GET_DRAFT_HEADER, new Object[]{username,bolNumber},new DraftHeadMapper());
		
		
		BillOfLading billOfLading = draftHead.getBillOfLading();
		
		
		/**
		 * Retrieve Commodity List
		 */
		
		List<Commodity> commodityList = jdbcTemplate.query(GET_DRAFT_COMMODITY, new Object[]{username,bolNumber},new CommodityMapper());
		
		billOfLading.getCommodityInfo().setCommodityList(commodityList);
		
		/**
		 * Retrieve Reference List
		 */
		List<Reference> references = jdbcTemplate.query(GET_DRAFT_REFERENCE, new Object[]{username,bolNumber},new ReferenceMapper());
		
		
		billOfLading.setReferences(references);
		
		/**
		 * Retrieve Accessorial List
		 */
		
		List<Accessorial> accessorials = jdbcTemplate.query(GET_DRAFT_ACCESSORIAL, new Object[]{username,bolNumber},new AccessorialMapper());
		
		billOfLading.setAccessorials(accessorials);
		
		return billOfLading;
		
	}
	
	
}
