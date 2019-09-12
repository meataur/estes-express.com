package com.estes.myestes.ImageViewing.web.dao.sql;

import static com.estes.myestes.ImageViewing.web.dao.iface.BaseDAO.*;

public interface ImageQuery {
	
	public static final String GET_IMAGE_SEARCH_RESULTS = "SELECT DISTINCT rpsrch as searchData,"
			+ " rpsearch1,"
			+ " rpsearch2,"
			+ " rpsearch3,"
			+ " rpsearch4,"
			+ " rpsearch5,"
			+ " RPDOCTY AS docType,"
			+ " trim(rperror) as error,"
			+ " imgfnd as imageFoundFlag"
			+ " FROM "+FBFILES+".XIG10P526"
			+ " LEFT OUTER JOIN "+FBFILES+".XIG10P020"
			+ " ON RPSEARCH1 = KEYVAL1"
			+ " AND RPSEARCH2 = KEYVAL2"
			+ " AND RPSEARCH3 = KEYVAL3 "
			+ " AND RPSEARCH4 = KEYVAL4"
			+ " AND RPSEARCH5 = KEYVAL5"
			+ " AND RPRQST# = REQUEST#"
			+ " AND DOCTYPE = RPDOCTY"
			+ " WHERE RPRQST# = ? ";
	
	public static final String GET_IMAGE_STATUS = "SELECT DISTINCT rpsrch as searchData,"
			+ " trim(rperror) as error,"
			+ " imgfnd as imageFoundFlag"
			+ " FROM "+FBFILES+".XIG10P526"
			+ " LEFT OUTER JOIN "+FBFILES+".XIG10P020"
			+ " ON RPSEARCH1 = KEYVAL1"
			+ " AND RPSEARCH2 = KEYVAL2"
			+ " AND RPSEARCH3 = KEYVAL3 "
			+ " AND RPSEARCH4 = KEYVAL4"
			+ " AND RPSEARCH5 = KEYVAL5"
			+ " AND RPRQST# = REQUEST#"
			+ " AND DOCTYPE = RPDOCTY"
			+ " WHERE RPRQST# = ? "
			+ " AND RPSRCH= ? "
			+ " AND RPDOCTY= ? ";
	
	
	public static final String GET_IMAGE_DETAILS = " SELECT "
			+ " DOC_ID as documentId, "
			+ " OUTPUT_DIRECTORY as ouputDirectory,"
			+ " OUTPUT_FILENAME as outputFileName,"
			+ " NAS_DIRECT_ACCESS as nasDirectAccess "+
			  " FROM "+FBFILES+".xig10p021 "+
			  " WHERE SEARCH_KEY_VALUE_1 = ? "+
			  " AND SEARCH_KEY_VALUE_2 = ? "+
			  " AND SEARCH_KEY_VALUE_3 = ? "+
			  " AND SEARCH_KEY_VALUE_4 = ? "+
			  " AND SEARCH_KEY_VALUE_5 = ? "+
			  " AND doctype = ?";
}
