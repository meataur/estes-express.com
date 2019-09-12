package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.web.dao.sql.BolQuery.*;
import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.DATALIB;
import static com.estes.myestes.BillOfLading.web.dao.sql.Schema.FBPGMS;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;
import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_MESSAGE;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.util.QueryUtil;
import com.estes.myestes.BillOfLading.web.dao.iface.BolDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.BolErrorDAO;
import com.estes.myestes.BillOfLading.web.dao.iface.PagingAndSortingDAO;
import com.estes.myestes.BillOfLading.web.dao.mapper.AccessorialMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.BolErrorMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.BolHeadMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.BolMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.CommodityMapper;
import com.estes.myestes.BillOfLading.web.dao.mapper.ReferenceMapper;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Bol;
import com.estes.myestes.BillOfLading.web.dto.BolDocument;
import com.estes.myestes.BillOfLading.web.dto.BolFilter;
import com.estes.myestes.BillOfLading.web.dto.BolHead;
import com.estes.myestes.BillOfLading.web.dto.BolHeadStoreProcedure;
import com.estes.myestes.BillOfLading.web.dto.Commodity;
import com.estes.myestes.BillOfLading.web.dto.Reference;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;


@Repository("bolDAO")
public class BolDAOImpl implements BolDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	private CommonDAO commonDAO;
	
	@Autowired
	private BolErrorDAO bolErrorDAO;
	
	
	
	@Autowired
	private PagingAndSortingDAO<Bol> pagingAndSortingDAO;

	@Override
	public Bol getBolById(String username, int bolId) {

		/**
		 * Params are needed in query
		 */
		List<Object> params = new ArrayList<>();
		params.add(username);
		params.add(bolId);

		/**
		 * Prepare UserDefaultQuery
		 */

		String query = GET_BOL_LIST;

		query += " AND BOL_SEQUENCE=?";

		
		/**
		 * Retrieve Bol object
		 */
		Bol bol = jdbcTemplate.queryForObject(query, params.toArray(), new BolMapper());

		return bol;
	}

	
	
	@Override
	public Page<Bol> getBolHistory(String username, Pageable pageable, BolFilter filter) {
		/**
		 * Params are needed in query
		 */

		List<Object> params = new ArrayList<>();
		params.add(username);

		/**
		 * Filtering UserDefaultQuery Preparation
		 */

		BolFilter.FilterBy filterBy = filter.getFilterBy();

		String query = GET_BOL_LIST;

		switch (filterBy) {

		case SHOW_ALL:
			break;

		case BOL_NUMBER:
			query += " AND UPPER(BOL_NUMBER) LIKE ? ";

			if (filter.getBolNumber() == null || filter.getBolNumber().trim() == "") {
				throw new BolException("error","BOL # is required","bolNumber");
			}

			params.add("%" + filter.getBolNumber().toUpperCase() + "%");

			break;

		case BOL_DATE_RANGE:
			query += " AND BOL_DATE >= ? AND BOL_DATE <= ?";

			if (filter.getBolStartDate() == null) {
				throw new BolException("error","From date is required","bolStartDate");
			}

			if (filter.getBolEndDate() == null) {
				filter.setBolEndDate(new Date());
			}

			params.add(EstesUtil.formatDate(filter.getBolStartDate(), "yyyyMMdd"));
			params.add(EstesUtil.formatDate(filter.getBolEndDate(), "yyyyMMdd"));

			break;

		case PRO_NUMBER:
			query += " AND PRO_NUMBER LIKE ? ";

			if (filter.getProNumber() == null || filter.getProNumber().trim() == "") {
				throw new BolException("error","PRO Number is required","proNumber");
			}

			params.add("%" + filter.getProNumber() + "%");
			break;

		case SHIPPER:
			query += " AND UPPER(SHIPPER_COMPANY_NAME) LIKE ? ";

			if (filter.getShipper() == null || filter.getShipper().trim() == "") {
				throw new BolException("error","Shipper is required","shipper");
			}

			params.add("%" + filter.getShipper().toUpperCase() + "%");
			break;

		case CONSIGNEE:
			query += " AND UPPER(CONSIGNEE_COMPANY_NAME) LIKE ? ";

			if (filter.getConsignee() == null || filter.getConsignee().trim() == "") {
				throw new BolException("error","Consignee is required","consignee");
			}

			params.add("%" + filter.getConsignee().toUpperCase() + "%");

			break;

		default:
			break;
		}

		/**
		 * Pagination
		 */

		String countQuery = query.replace("*", "count(*) as total");

		/**
		 * Sorting query Generation
		 */

		String sort = pageable.getSort();

		if (sort != null) {

			String order = pageable.getOrder().name();

			switch (sort) {

			case "bolNumber":
				query += " ORDER BY UPPER(BOL_NUMBER) " + order+" , BOL_DATE DESC";
				break;

			case "bolDate":
				query += " ORDER BY BOL_DATE " + order;
				break;

			case "proNumber":
				query += " ORDER BY PRO_NUMBER " + order+" , BOL_DATE DESC";
				break;

			case "shipper":
				query += " ORDER BY UPPER(SHIPPER_COMPANY_NAME) " + order+" , BOL_DATE DESC";
				break;

			case "consignee":
				query += " ORDER BY UPPER(CONSIGNEE_COMPANY_NAME) " + order+" , BOL_DATE DESC";
				break;
			case "hasShippingLabel":
				query += " ORDER BY HAS_SHIPPING_LABEL " + order+" , BOL_DATE DESC";
				break;
				
			default:
				break;
			}
		}

		Page<Bol> results = pagingAndSortingDAO.getResult(countQuery, query, params, pageable, new BolMapper());
		return results;
	}

	
	
	@SuppressWarnings("unchecked")
	@Override
	public void saveBolHeader(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName) {

		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("EBG10Q310");
		/**
		 * UPDT_TYPE [B,T,D] . B for BOL , T for Template and D for Draft
		 */
		sproc.addDeclaredParameter(new SqlParameter("UPDT_TYPE", Types.CHAR));
		/**
		 * TEMPLATEX is Template? [Y,N] . if it is template, value should be Y
		 * else N
		 */
		sproc.addDeclaredParameter(new SqlParameter("TEMPLATEX", Types.CHAR));
		/**
		 * TP_DESC is template name.
		 */
		sproc.addDeclaredParameter(new SqlParameter("TP_DESC", Types.CHAR));

		sproc.addDeclaredParameter(new SqlParameter("ROLE", Types.CHAR));
		/**
		 * PAGE_NUM - Page Number. The Old Applications maintains the
		 */
		sproc.addDeclaredParameter(new SqlParameter("PAGE_NUM", Types.CHAR));
		/**
		 * Random Number
		 */
		sproc.addDeclaredParameter(new SqlParameter("RANDOM#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TIME_STAMP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("BOL_SEQ", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_DATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_NUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("OT_PRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("AUTOASGN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_CN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_FN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_LN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_LOCN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_PN_XT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_FAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_CNTRY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_ST1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_ST2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_CITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_STATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_ZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_CN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_FN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_LN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_LOCN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_PN_XT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_FAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_CNTRY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_STR_1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_STR_2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_CITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_STATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_ZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PAYOR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TERMS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_CN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_FN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_LN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_LOCN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_PN_XT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_FAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_CNTRY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_STR_1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_STR_2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_CITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_STATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_ZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_FLAG", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_AMT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_PAY_TP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_FEE_PB", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_FN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_LN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_LOCN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_PN_XT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_CNTRY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_STR_1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_STR_2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_CITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_STATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_ZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("QUOTE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_EMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONS_EMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TPTY_EMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COD_EMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("FAX_TO_OTH", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TOT_SU", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("HM_CNT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("HM_PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("HM_EX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SPEC_IN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CUBE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("STR_LAB", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TOT_LAB", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("LABEL_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("MSTRBOL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("MSTRBOL#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOLTYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_BOL_S", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_BOL_C", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_BOL_T", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_BOL_O", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_TRK_S", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_TRK_C", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_TRK_T", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_TRK_O", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_LAB_S", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_LAB_C", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_LAB_T", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_LAB_O", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("FAX_BOL_S", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("FAX_BOL_C", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("FAX_BOL_T", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("FAX_BOL_O", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EM_LAB_OA", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SITE_ID", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("HAZMAT", Types.CHAR));

		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new BolErrorMapper()));
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		BolHeadStoreProcedure bolHead = new BolHeadStoreProcedure(billOfLading);
		
		params.put("UPDT_TYPE", updateType);
		params.put("TEMPLATEX", (templateName == null || templateName.trim().equals("")) ? "N" : "Y");
		params.put("TP_DESC", templateName);
		// @TODO
		params.put("ROLE", "");
		params.put("PAGE_NUM", "0");
		params.put("RANDOM#", randomNumber);
		params.put("TIME_STAMP", timestamp);
		params.put("ERROR", "");
		params.put("BOL_SEQ", billOfLading.getBolId()==0? "" : billOfLading.getBolId());
		params.put("USER_NAME", username);

		params.put("BOL_SEQ", bolHead.getBolSeq()); /* CHAR(15) */ 
		params.put("BOL_DATE", bolHead.getBolDate()); /* CHAR(8) */ 
		params.put("BOL_NUM", bolHead.getBolNum()); /* CHAR(35) */ 
		params.put("OT_PRO", bolHead.getOtPro()); /* CHAR(11) */ 
		params.put("AUTOASGN", bolHead.getAutoasgn()); /* CHAR(1) */ 
		params.put("SHIP_CN", bolHead.getShipCn()); /* CHAR(30) */ 
		params.put("SHIP_FN", bolHead.getShipFn()); /* CHAR(25) */ 
		params.put("SHIP_LN", bolHead.getShipLn()); /* CHAR(25) */ 
		params.put("SHIP_LOCN", bolHead.getShipLocn()); /* CHAR(10) */ 
		params.put("SHIP_PHONE", bolHead.getShipPhone()); /* CHAR(10) */ 
		params.put("SHIP_PN_XT", bolHead.getShipPnXt()); /* CHAR(5) */ 
		params.put("SHIP_FAX", bolHead.getShipFax()); /* CHAR(10) */ 
		params.put("SHIP_CNTRY", bolHead.getShipCntry()); /* CHAR(2) */ 
		params.put("SHIP_ST1", bolHead.getShipSt1()); /* CHAR(30) */ 
		params.put("SHIP_ST2", bolHead.getShipSt2()); /* CHAR(30) */ 
		params.put("SHIP_CITY", bolHead.getShipCity()); /* CHAR(20) */ 
		params.put("SHIP_STATE", bolHead.getShipState()); /* CHAR(2) */ 
		params.put("SHIP_ZIP", bolHead.getShipZip()); /* CHAR(10) */ 
		params.put("CONS_CN", bolHead.getConsCn()); /* CHAR(30) */ 
		params.put("CONS_FN", bolHead.getConsFn()); /* CHAR(25) */ 
		params.put("CONS_LN", bolHead.getConsLn()); /* CHAR(25) */ 
		params.put("CONS_LOCN", bolHead.getConsLocn()); /* CHAR(10) */ 
		params.put("CONS_PHONE", bolHead.getConsPhone()); /* CHAR(10) */ 
		params.put("CONS_PN_XT", bolHead.getConsPnXt()); /* CHAR(5) */ 
		params.put("CONS_FAX", bolHead.getConsFax()); /* CHAR(10) */ 
		params.put("CONS_CNTRY", bolHead.getConsCntry()); /* CHAR(2) */ 
		params.put("CONS_STR_1", bolHead.getConsStr1()); /* CHAR(30) */ 
		params.put("CONS_STR_2", bolHead.getConsStr2()); /* CHAR(30) */ 
		params.put("CONS_CITY", bolHead.getConsCity()); /* CHAR(20) */ 
		params.put("CONS_STATE", bolHead.getConsState()); /* CHAR(2) */ 
		params.put("CONS_ZIP", bolHead.getConsZip()); /* CHAR(10) */ 
		params.put("PAYOR", bolHead.getPayor()); /* CHAR(1) */ 
		params.put("TERMS", bolHead.getTerms()); /* CHAR(3) */ 
		params.put("TPTY_CN", bolHead.getTptyCn()); /* CHAR(30) */ 
		params.put("TPTY_FN", bolHead.getTptyFn()); /* CHAR(25) */ 
		params.put("TPTY_LN", bolHead.getTptyLn()); /* CHAR(25) */ 
		params.put("TPTY_LOCN", bolHead.getTptyLocn()); /* CHAR(10) */ 
		params.put("TPTY_PHONE", bolHead.getTptyPhone()); /* CHAR(10) */ 
		params.put("TPTY_PN_XT", bolHead.getTptyPnXt()); /* CHAR(5) */ 
		params.put("TPTY_FAX", bolHead.getTptyFax()); /* CHAR(10) */ 
		params.put("TPTY_CNTRY", bolHead.getTptyCntry()); /* CHAR(2) */ 
		params.put("TPTY_STR_1", bolHead.getTptyStr1()); /* CHAR(30) */ 
		params.put("TPTY_STR_2", bolHead.getTptyStr2()); /* CHAR(30) */ 
		params.put("TPTY_CITY", bolHead.getTptyCity()); /* CHAR(20) */ 
		params.put("TPTY_STATE", bolHead.getTptyState()); /* CHAR(2) */ 
		params.put("TPTY_ZIP", bolHead.getTptyZip()); /* CHAR(10) */ 
		params.put("COD_FLAG", bolHead.getCodFlag()); /* CHAR(1) */ 
		params.put("COD_AMT", bolHead.getCodAmt()); /* CHAR(11) */ 
		params.put("COD_PAY_TP", bolHead.getCodPayTp()); /* CHAR(10) */ 
		params.put("COD_FEE_PB", bolHead.getCodFeePb()); /* CHAR(10) */ 
		params.put("COD_NAME", bolHead.getCodName()); /* CHAR(30) */ 
		params.put("COD_FN", bolHead.getCodFn()); /* CHAR(25) */ 
		params.put("COD_LN", bolHead.getCodLn()); /* CHAR(25) */ 
		params.put("COD_LOCN", bolHead.getCodLocn()); /* CHAR(10) */ 
		params.put("COD_PHONE", bolHead.getCodPhone()); /* CHAR(10) */ 
		params.put("COD_PN_XT", bolHead.getCodPnXt()); /* CHAR(5) */ 
		params.put("COD_CNTRY", bolHead.getCodCntry()); /* CHAR(2) */ 
		params.put("COD_STR_1", bolHead.getCodStr1()); /* CHAR(30) */ 
		params.put("COD_STR_2", bolHead.getCodStr2()); /* CHAR(30) */ 
		params.put("COD_CITY", bolHead.getCodCity()); /* CHAR(20) */ 
		params.put("COD_STATE", bolHead.getCodState()); /* CHAR(2) */ 
		params.put("COD_ZIP", bolHead.getCodZip()); /* CHAR(10) */ 
		params.put("QUOTE", bolHead.getQuote()); /* CHAR(7) */ 
		params.put("SHIP_EMAIL", bolHead.getShipEmail()); /* CHAR(50) */ 
		params.put("CONS_EMAIL", bolHead.getConsEmail()); /* CHAR(50) */ 
		params.put("TPTY_EMAIL", bolHead.getTptyEmail()); /* CHAR(50) */ 
		params.put("COD_EMAIL", bolHead.getCodEmail()); /* CHAR(50) */ 
		params.put("FAX_TO_OTH", bolHead.getFaxToOth()); /* CHAR(10) */ 
		
		// TODO
		params.put("TOT_SU","0"); /* CHAR(5) */ 
		
		params.put("HM_CNT", bolHead.getHmCnt()); /* CHAR(30) */ 
		params.put("HM_PHONE", bolHead.getHmPhone()); /* CHAR(10) */ 
		params.put("HM_EX", bolHead.getHmEx()); /* CHAR(6) */ 
		params.put("SPEC_IN", bolHead.getSpecIn()); /* CHAR(500) */ 
		params.put("CUBE", bolHead.getCube()); /* CHAR(9) */ 
		params.put("STR_LAB", bolHead.getStrLab()); /* CHAR(15) */ 
		params.put("TOT_LAB", bolHead.getTotLab()); /* CHAR(15) */ 
		params.put("LABEL_TYPE", bolHead.getLabelType()); /* CHAR(1) */ 
		params.put("MSTRBOL", bolHead.getMstrbol()); /* CHAR(1) */ 
		params.put("MSTRBOL#", bolHead.getMstrbolNumber()); /* CHAR(25) */ 
		params.put("BOLTYPE", bolHead.getBoltype()); /* CHAR(1) */ 
		params.put("EM_BOL_S", bolHead.getEmBolS()); /* CHAR(1) */ 
		params.put("EM_BOL_C", bolHead.getEmBolC()); /* CHAR(1) */ 
		params.put("EM_BOL_T", bolHead.getEmBolT()); /* CHAR(1) */ 
		params.put("EM_BOL_O", bolHead.getEmBolO()); /* CHAR(1) */ 
		params.put("EM_TRK_S", bolHead.getEmTrkS()); /* CHAR(1) */ 
		params.put("EM_TRK_C", bolHead.getEmTrkC()); /* CHAR(1) */ 
		params.put("EM_TRK_T", bolHead.getEmTrkT()); /* CHAR(1) */ 
		params.put("EM_TRK_O", bolHead.getEmTrkO()); /* CHAR(1) */ 
		params.put("EM_LAB_S", bolHead.getEmLabS()); /* CHAR(1) */ 
		params.put("EM_LAB_C", bolHead.getEmLabC()); /* CHAR(1) */ 
		params.put("EM_LAB_T", bolHead.getEmLabT()); /* CHAR(1) */ 
		params.put("EM_LAB_O", bolHead.getEmLabO()); /* CHAR(1) */ 
		params.put("FAX_BOL_S", bolHead.getFaxBolS()); /* CHAR(1) */ 
		params.put("FAX_BOL_C", bolHead.getFaxBolC()); /* CHAR(1) */ 
		params.put("FAX_BOL_T", bolHead.getFaxBolT()); /* CHAR(1) */ 
		params.put("FAX_BOL_O", bolHead.getFaxBolO()); /* CHAR(1) */ 
		params.put("EM_LAB_OA", bolHead.getEmLabOa()); /* CHAR(255) */ 
		params.put("SITE_ID", bolHead.getSiteId()); /* CHAR(30) */ 
		params.put("HAZMAT", bolHead.getHazmat()); /* CHAR(1) */ 


		
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			if (entry.getValue() == null) {
				entry.setValue("");
			}
		}
		
		
		Map<String, ?> outParms = sproc.execute(params);
		if (outParms != null) {

			if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
				if (outParms.get("DATA") != null) {
					Map<String, String> errors = (Map<String, String>) outParms.get("DATA");
					
					throw new BolException(errors);
				}
				throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
			}
		} else {

			throw new BolException(DEFAULT_ERROR_CODE,DEFAULT_ERROR_MESSAGE);
		}

		billOfLading.setBolId(new Integer(((String) outParms.get("BOL_SEQ")).trim()));
	}
	
	

	@Override
	public void saveCommodities(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("EBG10Q330");
		sproc.addDeclaredParameter(new SqlParameter("UPDT_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TEMPLATEX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TP_DESC", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("RANDOM#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TIME_STAMP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_SEQ", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_NUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOLTYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SI_SEQ#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_UNIT_Q", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_UNIT_T", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_WEIGHT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_CLASS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NMFC", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("GOOD_UNIT_Q", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("GOOD_UNIT_T", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("HAZMAT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NMFC_SUB", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("DESCRIPTION", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_HGT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIP_WTH", Types.CHAR));

		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new BolErrorMapper()));

		LinkedHashMap<String, Object> inParams = new LinkedHashMap<String, Object>();

		inParams.put("UPDT_TYPE", updateType);
		inParams.put("TEMPLATEX", templateName == null ? "N" : "Y");
		inParams.put("TP_DESC", templateName);
		inParams.put("RANDOM#", randomNumber);
		inParams.put("TIME_STAMP", timestamp);
		inParams.put("ERROR", "");
		inParams.put("BOL_SEQ", String.valueOf(billOfLading.getBolId()));
		inParams.put("BOL_NUM", billOfLading.getGeneralInfo().getBolNumber());
		inParams.put("USER_NAME", username);
		
		if(billOfLading.getDocumentType()!=null){
			inParams.put("BOLTYPE", billOfLading.getDocumentType().name());
		}else{
			inParams.put("BOLTYPE", "");
		}
		

		int i = 1;
		for (Commodity commodity : billOfLading.getCommodityInfo().getCommodityList()) {

			inParams.put("SI_SEQ#", i);
			inParams.put("SHIP_UNIT_Q", commodity.getNumberOfPackage());
			inParams.put("SHIP_UNIT_T", commodity.getPackageType());
			inParams.put("SHIP_WEIGHT", commodity.getGoodsWeight());
			
			if(commodity.getShipmentClass()!=null){
				inParams.put("SHIP_CLASS", commodity.getShipmentClass().getCode());
			}else{
				inParams.put("SHIP_CLASS", "");
			}
			
			inParams.put("NMFC", commodity.getNmfc());
			inParams.put("GOOD_UNIT_Q", commodity.getGoodsUnit());
			
			if(commodity.getGoodsType()!=null){
				inParams.put("GOOD_UNIT_T", commodity.getGoodsType().getCode());
			}else{
				inParams.put("GOOD_UNIT_T", "");
			}
			
			inParams.put("HAZMAT", commodity.isHazmat() ? "Y" : "N");
			inParams.put("NMFC_SUB", commodity.getNmfcExt());
			inParams.put("DESCRIPTION", commodity.getDescription());
			inParams.put("SHIP_HGT", commodity.getHeight());
			inParams.put("SHIP_WTH", commodity.getWeight());
			inParams.put("SHIP_LEN", commodity.getLength());
			i++;
			
			for (Map.Entry<String, Object> entry : inParams.entrySet()) {
				if (entry.getValue() == null) {
					entry.setValue("");
				}
			}
			
			Map<String, ?> outParms = sproc.execute(inParams);

			if (outParms != null) {

				if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
					if (outParms.get("DATA") != null) {
						@SuppressWarnings("unchecked")
						Map<String, String> errors = (Map<String, String>) outParms.get("DATA");
						throw new BolException(errors);
					}
					throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
				}
			} else {

				throw new BolException("error","An unexpected error occurs");
			}
		}

	}

	@Override
	public void saveReferences(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("EBG10Q320");
		sproc.addDeclaredParameter(new SqlParameter("UPDT_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TEMPLATEX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TP_DESC", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("RANDOM#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TIME_STAMP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_SEQ", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_NUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REF_SEQ#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REFERENCE#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REF_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REF_CARTONS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REF_WEIGHT", Types.CHAR));

		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new BolErrorMapper()));

		LinkedHashMap<String, Object> inParams = new LinkedHashMap<String, Object>();

		inParams.put("UPDT_TYPE", updateType);
		inParams.put("TEMPLATEX", templateName == null ? "N" : "Y");
		inParams.put("TP_DESC", templateName);
		inParams.put("RANDOM#", randomNumber);
		inParams.put("TIME_STAMP", timestamp);
		inParams.put("ERROR", "");
		
		inParams.put("BOL_SEQ", billOfLading.getBolId()==0? "" : String.valueOf(billOfLading.getBolId()));
		if(billOfLading.getGeneralInfo()!=null){
			inParams.put("BOL_NUM", billOfLading.getGeneralInfo().getBolNumber());
		}else{
			inParams.put("BOL_NUM", "");
		}
		
		inParams.put("USER_NAME", username);

		int i = 1;
		for (Reference reference : billOfLading.getReferences()) {

			inParams.put("REF_SEQ#", i);
			inParams.put("REFERENCE#", reference.getReferenceNumber());
			if(reference.getReferenceType()!=null){
				inParams.put("REF_TYPE", reference.getReferenceType().name());
			}else{
				inParams.put("REF_TYPE", "");
			}
			inParams.put("REF_CARTONS", reference.getCartoon());
			inParams.put("REF_WEIGHT", reference.getWeight());
			i++;

			for (Map.Entry<String, Object> entry : inParams.entrySet()) {
				if (entry.getValue() == null) {
					entry.setValue("");
				}
			}

			Map<String, ?> outParms = sproc.execute(inParams);

			if (outParms != null) {

				if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
					if (outParms.get("DATA") != null) {
						@SuppressWarnings("unchecked")
						Map<String, String> errors = (Map<String, String>) outParms.get("DATA");
						throw new BolException(errors);
					}
					throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
				}
			} else {

				throw new BolException("error","An unexpected error occurs");
			}
		}

	}

	@Override
	public void saveAccessorials(String username, String randomNumber, String timestamp, BillOfLading billOfLading,
			String updateType, String templateName) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("EBG10Q340");
		sproc.addDeclaredParameter(new SqlParameter("UPDT_TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TEMPLATEX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TP_DESC", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("RANDOM#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TIME_STAMP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_SEQ", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BOL_NUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ACC_SEQ#", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ACC_CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TERMS", Types.CHAR));

		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new BolErrorMapper()));

		LinkedHashMap<String, Object> inParams = new LinkedHashMap<String, Object>();

		inParams.put("UPDT_TYPE", updateType);
		inParams.put("TEMPLATEX", templateName == null ? "N" : "Y");
		inParams.put("TP_DESC", templateName);
		inParams.put("RANDOM#", randomNumber);
		inParams.put("TIME_STAMP", timestamp);
		inParams.put("ERROR", "");
		inParams.put("BOL_SEQ", String.valueOf(billOfLading.getBolId()));
		
		if(billOfLading.getGeneralInfo()!=null){
			inParams.put("BOL_NUM", billOfLading.getGeneralInfo().getBolNumber());
		}else{
			inParams.put("BOL_NUM", "");
		}
		
		inParams.put("USER_NAME", username);
		
		if(billOfLading.getBillingInfo()!=null 
				&& billOfLading.getBillingInfo().getBillTo()!=null 
				&& billOfLading.getBillingInfo().getBillTo().getPayor()!=null){
			inParams.put("TERMS", billOfLading.getBillingInfo().getBillTo().getPayor().name());
		}else{
			inParams.put("TERMS", "");
		}
		
		
		int i = 1;
		for (Accessorial accessorial : billOfLading.getAccessorials()) {

			inParams.put("ACC_SEQ#", i);
			inParams.put("ACC_CODE", accessorial.getCode());
			i++;

			for (Map.Entry<String, Object> entry : inParams.entrySet()) {
				if (entry.getValue() == null) {
					entry.setValue("");
				}
			}

			Map<String, ?> outParms = sproc.execute(inParams);

			if (outParms != null) {

				if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
					if (outParms.get("DATA") != null) {
						@SuppressWarnings("unchecked")
						Map<String, String> errors = (Map<String, String>) outParms.get("DATA");
						throw new BolException(errors);
					}
					throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
				}
			} else {

				throw new BolException(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE);
			}
		}

	}

	@Override
	public String writeFinalBol(String randomNumber, String timestamp) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("EBG10Q151");

		sproc.addDeclaredParameter(new SqlParameter("DELAY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("RANDOM_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TSTAMP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("REQUEST_NUMBER", Types.CHAR));
		LinkedHashMap<String, Object> inParams = new LinkedHashMap<String, Object>();
		inParams.put("DELAY", "N");
		inParams.put("ERROR", "");
		inParams.put("RANDOM_NUMBER", randomNumber);
		inParams.put("TSTAMP", timestamp);
		inParams.put("REQUEST_NUMBER", "");
		
		for (Map.Entry<String, Object> entry : inParams.entrySet()) {
			if (entry.getValue() == null) {
				entry.setValue("");
			}
		}
		Map<String, ?> outParms = sproc.execute(inParams);

		if (outParms != null) {

			if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
				throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
			}
		} else {
			throw new BolException(DEFAULT_ERROR_CODE, DEFAULT_ERROR_MESSAGE);
		}

		return (String) outParms.get("REQUEST_NUMBER");

	}
	
	@Override
	public boolean isBolExist(String username, int bolId) {
		int count = (int) jdbcTemplate.queryForObject(COUNT_BOL, new Object[]{username,bolId}, Integer.class);
		return count>0;
	}
	
	

	@Override
	public BillOfLading getBillOfLadingById(String username, int bolId) {
		
		BolHead bolHead= jdbcTemplate.queryForObject(GET_BOL_HEADER, new Object[]{username,bolId},new BolHeadMapper());

		BillOfLading billOfLading = bolHead.getBillOfLading();
		
		billOfLading.setBolId(bolId);
		
		/**
		 * Retrieve Commodity List
		 */
		
		List<Commodity> commodityList = jdbcTemplate.query(GET_BOL_COMMODITY, new Object[]{bolId},new CommodityMapper());
		
		billOfLading.getCommodityInfo().setCommodityList(commodityList);
		
		/**
		 * Retrieve Reference List
		 */
		List<Reference> references = jdbcTemplate.query(GET_BOL_REFERENCE, new Object[]{bolId},new ReferenceMapper());
		
		
		billOfLading.setReferences(references);
		
		/**
		 * Retrieve Accessorial List
		 */
		
		List<Accessorial> accessorials = jdbcTemplate.query(GET_BOL_ACCESSORIAL, new Object[]{bolId},new AccessorialMapper());
		
		billOfLading.setAccessorials(accessorials);
		
		return billOfLading;
	} 
 
	@Override
	public void cancelBol(String username, int id) {
		/**
		 * retrieve BOL by id
		 */
		Bol bol = getBolById(username, id);
		
		if(bol==null){
			throw new BolException(HttpStatus.NOT_FOUND, DEFAULT_ERROR_CODE, "Invalid BOL Id");
		}
		
		if(bol.getBolDate()==null){
			throw new BolException(DEFAULT_ERROR_CODE,"BOL date is not set");
		}
		
		
		Date bolDate = bol.getBolDate();
		if(bolDate.before(new Date())){
			throw new BolException(DEFAULT_ERROR_CODE, "A BOL cannot be cancelled on or after the BOL date.");
		}
		
		/**
		 * Prepare Where Params for delete query
		 */
		Set<String> whereParams = new LinkedHashSet<>();
		whereParams.add("BOL_SEQUENCE");
		
		/**
		 * Initialize query
		 */
		
		String query = "";
		
		
		
		/**
		 * Delete BOL Accessorial 
		 * Prepare delete query for accessorial
		 * execute query
		 */
		
		
		query = QueryUtil.prepareDeleteQuery(DATALIB, BOL_ACCESSORIAL_TABLE, whereParams);
		
		jdbcTemplate.update(query, new Object[]{id});
		
		
		
		/**
		 * 
		 * Delete from commodity table
		 * Prepare delete query
		 * execute delete query
		 */
		query = QueryUtil.prepareDeleteQuery(DATALIB, BOL_COMMODITY_TABLE, whereParams);
		
		jdbcTemplate.update(query, new Object[]{id});
		
		
		/**
		 * Delete from reference table 
		 * Prepare delete query for reference table
		 */
		query = QueryUtil.prepareDeleteQuery(DATALIB, BOL_REFERENCE_TABLE, whereParams);
		
		jdbcTemplate.update(query, new Object[]{id});
		
		
		/**
		 * Delete from bol email and email and fax notification table
		 * prepare delete query for email and fax notification table 
		 */
		
		query = QueryUtil.prepareDeleteQuery(DATALIB, BOL_EMAIL_AND_FAX_NOTIFICATION_TABLE, whereParams);
		
		jdbcTemplate.update(query, new Object[]{id});
		
		
		/**
		 * 
		 * Delete from bol shipping label
		 * 
		 */
		
		query = QueryUtil.prepareDeleteQuery(DATALIB, BOL_SHIPPING_LABEL_TABLE, whereParams);
		
		jdbcTemplate.update(query, new Object[]{id});
		
		/**
		 * Delete from bol header table
		 */
		
		query = QueryUtil.prepareDeleteQuery(DATALIB, BOL_HEADER_TABLE, whereParams);
		
		jdbcTemplate.update(query, new Object[]{id});
		

	}

	private BolDocument createBolLabel(Bol bol) {

		
		/**
		 * CREATE PROCEDURE FBPGMS.EBG10Q177Z ( 
			IN DELAY CHAR(1) , 
			OUT "ERROR" CHAR(7) , 
			IN BOL_SEQ CHAR(15) , 
			IN PR_SHIP_ACCT CHAR(7) , 
			IN PR_CONS_ACCT CHAR(7) , 
			IN PR_SHIP_NUM CHAR(30) , 
			IN PR_BOL_NUM CHAR(25) , 
			IN PR_PO_NUM CHAR(30) , 
			IN PR_PRO_OT DECIMAL(3, 0) , 
			IN PR_PRO_NUM DECIMAL(7, 0) , 
			IN PR_STAT_LAB DECIMAL(15, 0) , 
			IN PR_TOTAL_LAB DECIMAL(15, 0) , 
			IN PR_LABEL_TYPE CHAR(1) , 
			OUT PR_FTPPATHRTN CHAR(255) ) 
		 */
		
		

		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);
		sproc.withSchemaName(FBPGMS);
        sproc.withProcedureName("EBG10Q177Z");
        
        sproc.addDeclaredParameter(new SqlParameter("DELAY", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("BOL_SEQ", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("PR_SHIP_ACCT", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("PR_CONS_ACCT", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("PR_SHIP_NUM", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("PR_BOL_NUM", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("PR_PO_NUM", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlParameter("PR_PRO_OT", Types.DECIMAL));
        sproc.addDeclaredParameter(new SqlParameter("PR_PRO_NUM", Types.DECIMAL));
        sproc.addDeclaredParameter(new SqlParameter("PR_STAT_LAB", Types.DECIMAL));
        sproc.addDeclaredParameter(new SqlParameter("PR_TOTAL_LAB", Types.DECIMAL));
        sproc.addDeclaredParameter(new SqlParameter("PR_LABEL_TYPE", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlOutParameter("PR_FTPPATHRTN", Types.CHAR)); 
		
        
        
        Map<String, Object> inParams = new HashMap<String, Object>();
        
        inParams.put("DELAY","0");
        inParams.put("ERROR", "000000");
        inParams.put("BOL_SEQ", String.valueOf(bol.getBolId()));
        inParams.put("PR_SHIP_ACCT","");
        inParams.put("PR_CONS_ACCT","");
        inParams.put("PR_SHIP_NUM","");
        inParams.put("PR_BOL_NUM",bol.getBolNumber());
        inParams.put("PR_PO_NUM","");
        inParams.put("PR_PRO_OT", bol.getProOt()==null? 0 : new Integer(bol.getProOt()) ); 
        inParams.put("PR_PRO_NUM", bol.getProNum()==null? 0 : new Integer(bol.getProNum())); 
        inParams.put("PR_STAT_LAB", new Integer((bol.getShippingLabelStart()))); 
        inParams.put("PR_TOTAL_LAB", new Integer(bol.getShippingLabelTotal())); 
        inParams.put("PR_LABEL_TYPE", bol.getShippingLabelType());
        inParams.put("PR_FTPPATHRTN", "");
        
        Map<String, Object> outParms = sproc.execute(inParams);

		if (outParms != null) {
			if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
				throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
			}
			
		} else {
			Throwable throwable = new Throwable();
			
			ESTESLogger.log(ESTESLogger.ERROR, throwable.getStackTrace()[0].getClassName(), throwable.getStackTrace()[0].getMethodName(),
					"An unexpected error occurred at line no " +throwable.getStackTrace()[0].getLineNumber());
			throw new BolException(DEFAULT_ERROR_CODE,DEFAULT_ERROR_MESSAGE);
		}
		
		BolDocument bolDocument = new BolDocument();
		
		bolDocument.setBolId(new Integer(bol.getBolId()));
		
		bolDocument.setDocumentType("LABEL");
		
		bolDocument.setDocumentUrl(((String) outParms.get("PR_FTPPATHRTN")).trim());
		
		
		return bolDocument;
	}
	
	
	
	
	@Override
	public BolDocument createBolLabelDocument(String username, int id, ShippingLabel shippingLabel) {
		Bol bol = getBolById(username, id);
		
		if(bol==null){
			throw new BolException(HttpStatus.NOT_FOUND,DEFAULT_ERROR_CODE,"Invalid BOL Id");
		}
		
		
		bol.setShippingLabelStart(shippingLabel.getStartLabel());
		bol.setShippingLabelTotal(shippingLabel.getNumberOfLabel());
		bol.setShippingLabelType(shippingLabel.getLabelType());
		
		return createBolLabel(bol);

	}
	
	
	@Override
	public BolDocument getBolLabelDocument(String username, int id) {
		
		Bol bol = getBolById(username, id);
		
		if(bol==null){
			throw new BolException(HttpStatus.NOT_FOUND,DEFAULT_ERROR_CODE,"Invalid BOL Id");
		}
		
		return createBolLabel(bol);
	}

	@Override
	public BolDocument getBolDocument(String username, int id) {

		Bol bol = getBolById(username, id);
		
		if(bol==null){
			throw new BolException(HttpStatus.NOT_FOUND,DEFAULT_ERROR_CODE,"Invalid BOL Id");
		}
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);
		sproc.withSchemaName(FBPGMS);
        sproc.withProcedureName("EBG10Q175S");
		
        /**
         * 
         * 
         * CREATE PROCEDURE FBPGMS.EBG10Q175S ( 
			 IN DELAY CHAR(1) , 
			 OUT "ERROR" CHAR(7) , 
			 IN BOL_SEQ CHAR(15) , 
			 IN FAX CHAR(1) , 
			 OUT FTPPATHRTN CHAR(255)
		   ) 
	
         */
        
        
       
        
        sproc.addDeclaredParameter(new SqlParameter("DELAY", Types.CHAR)); 
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR)); 
		sproc.addDeclaredParameter(new SqlParameter("BOL_SEQ", Types.CHAR)); 
		sproc.addDeclaredParameter(new SqlParameter("FAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("FTPPATHRTN", Types.CHAR)); 
		
		Map<String, Object> inParams = new HashMap<>();
		
		inParams.put("DELAY","0");
        inParams.put("ERROR", "000000");
        inParams.put("BOL_SEQ", bol.getBolId());
        inParams.put("FAX","N");
        inParams.put("FTPPATHRTN","");
		
        Map<String, Object> outParms = sproc.execute(inParams);
	       

		if (outParms != null) {
			if (BolErrorDAO.isError((String) outParms.get("ERROR"))) {
				Throwable throwable = new Throwable();

				ESTESLogger.log(ESTESLogger.ERROR, throwable.getStackTrace()[0].getClassName(), throwable.getStackTrace()[0].getMethodName(), bolErrorDAO.getErrorMessage((String) outParms.get("ERROR")) );
				
				throw new BolException(bolErrorDAO.getServiceResponse(outParms.get("ERROR")));
			}
		} else {
			
			Throwable throwable = new Throwable();
			
			ESTESLogger.log(ESTESLogger.ERROR, throwable.getStackTrace()[0].getClassName(), throwable.getStackTrace()[0].getMethodName(),
					"An unexpected error occurred at line no " +throwable.getStackTrace()[0].getLineNumber());
			
			throw new BolException(DEFAULT_ERROR_CODE,DEFAULT_ERROR_MESSAGE);
		}
		
		
		BolDocument bolDocument = new BolDocument();
		
		bolDocument.setBolId(new Integer(bol.getBolId()));
		
		bolDocument.setDocumentType("BOL");
		
		bolDocument.setDocumentUrl(((String) outParms.get("FTPPATHRTN")).trim());
		
		
		return bolDocument;
		
	}



	@Override
	public void saveServiceLevel(String serviceLevel, String username, int bolId) {
		jdbcTemplate.update(SET_SERVICE_LEVEL_IN_STAGING, new Object[]{serviceLevel,username,bolId});
	}
	
	
}
