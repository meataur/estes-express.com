package com.estes.myestes.rating.dao.iface;

import java.util.List;

import com.estes.myestes.rating.dto.CommodityPricing;
import com.estes.myestes.rating.dto.ContactRequest;
/**
 * 
 * @author rahmaat
 *
 */
public interface RevisedQuoteDAO {

	String SAVE_REVISED_QUOTE_INFO                 = "INSERT INTO FBFILES.GSC00P150 (GSKTMST,GSKID,GSKTYPE,GSKPHONE,GSKEMAIL, GSKNAME, GSKDLDT,GSKDLTM,GSKDLCLTM,GSKCOMM) VALUES (CURRENT_TIMESTAMP, ?, ?, ?, ?, ?, CURRENT_DATE, CURRENT_TIME, CURRENT_TIME,?)";
	String UPDATE_REVISED_COMMODITY                = "UPDATE FBFILES.GSC00P130 SET  GSCPCS = ?, GSCPCTP = ?, GSCWGT = ?, GSCLEN = ?, GSCWID = ?, GSCHGT= ?, GSCDESC = ? WHERE GSCID = ? AND GSCSEQ = ?";
	String UPDATE_REVISED_SCHEDULING_AND_STACKABLE = "UPDATE FBFILES.GSC00P100 SET GSRPKDT=?, GSRPKTM=TIME(CAST(? AS CHAR(8))), GSRPKCLTM=TIME(CAST(? AS CHAR(8))),GSRSTK=? WHERE GSRID = ?";

	void saveRevisedQuoteInfo(ContactRequest contactRequest);

	void updateRevisedCommodityInfo(List<CommodityPricing> commodityList, String quoteId);

	void updateRevisedSchedulingAndStackableInfo(ContactRequest contactRequest, String quoteId);

}
