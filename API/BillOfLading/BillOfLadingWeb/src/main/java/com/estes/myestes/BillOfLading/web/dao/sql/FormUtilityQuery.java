package com.estes.myestes.BillOfLading.web.dao.sql;

public interface FormUtilityQuery {

	// TODO - For APP_ID=8, No results in QA
	public static final String GET_ACCESSORIALS  = "SELECT T1.CODE,"
			+ "	T1.DESCRIPTION,"
			+ " T2.ON_SCR"
			+ " FROM   "+Schema.DATALIB+".ACCESSORIAL_CHARGE AS T1"
			+ " INNER JOIN  "+Schema.DATALIB+".ACCESSORIAL_TO_APP AS T2"
			+ " ON T1.CODE = T2.CODE  "
			+ " WHERE APP_ID=3 OR APP_ID=8"
			+ " ORDER BY ON_SCR DESC, DESCRIPTION ASC";

}
