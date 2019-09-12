package com.estes.myestes.PickupRequest.web.dao.sql;

import static com.estes.myestes.PickupRequest.web.dao.sql.Schema.DATALIB;
import static com.estes.myestes.PickupRequest.web.dao.sql.Schema.FBPGMS;


public interface PickupRequestQuery {

	
	public static String PREVIOUS_USER_QUERY = "SELECT "
			+ " SEQUENCENUMBER AS ID,"
			+ " TRIM(PTNAME) AS NAME,"
			+ " PTPHONE AS PHONE,"
			+ " PTEXT AS PHONEEXT,"
			+ " TRIM(PTEMAIL) AS EMAIL"
			+ " FROM "+DATALIB+".PKP10P107 "
			+ " INNER JOIN "+DATALIB+".PKP10P101"
			+ " ON PTRQST=PHRQST "
			+ " WHERE PHWEBUSR=? ORDER BY PTRQST DESC  LIMIT 0, ?";
	
	
	public static String PREVIOUS_COMMOTITY_QUERY = " SELECT "
			+ " TRIM(PAZIP) AS DESTINATIONZIPCODE,"
			+ " TRIM(PAZIP4) AS DESTINATIONZIPCODEEXT,"
			+ " PHTPCS AS TOTALPIECES,"
			+ " PHTWGT AS TOTALWEIGHT,"
			+ " PHHNDLUNTS AS TOTALSKIDS,"
			+ " PHSHAZ AS HAZMAT,"
			+ " (SELECT TRIM(MIN(PCRCOM)) "
			+ " FROM "+DATALIB+".PKP10P104"
			+ " WHERE PHRQST=PCRQST AND PCRTYP = 'SSI') AS SPECIALINSTRUCTIONS, "
			+ " TRIM('PHREF#') AS REFERENCENUMBER "
			+ " FROM "+DATALIB+".PKP10P101 "
			+ " LEFT OUTER JOIN "+DATALIB+".PKP10P105 "
			+ " ON PHRQST=PARQST AND PAADRTYP='C' "
			+ " WHERE  PHWEBUSR= ?	ORDER BY PHRQST DESC  LIMIT 0, ?";
	
	public static String PREVIOUS_SHIPPER_QUERY = " SELECT "
			+ " TRIM(PTNAME)  AS NAME,"
			+ " TRIM(PSSNAM)  AS COMPANY,"
			+ " TRIM(PSSAD1)  AS ADDRESSLINE1,"
			+ " TRIM(PSSAD2)  AS ADDRESSLINE2,"
			+ " TRIM(PSSCTY)  AS CITY,"
			+ " TRIM(PSSST)   AS STATE, "
			+ " TRIM(PSSZIP)  AS ZIP,"
			+ " TRIM(PSSZP4)  AS ZIPEXT,"
			+ " TRIM(PTPHONE) AS PHONE,"
			+ " TRIM(PTEXT)   AS PHONEEXT,"
			+ " TRIM(PTEMAIL) AS EMAIL "
			+ " FROM   "+DATALIB+".PKP10P100"
			+ " INNER JOIN "+DATALIB+".PKP10P101"
			+ " ON PSSNUM = PHSNUM"
			+ " INNER JOIN "+DATALIB+".PKP10P107"
			+ " ON PHRQST = PTRQST"
			+ " WHERE PHWEBUSR=? ORDER  BY PHSNUM DESC  LIMIT 0, ?";
	
	public static String PICKUP_SUMMARY_QUERY="SELECT * FROM ("
			+ " SELECT "
			+ " t.requestNumber,"
			+ " t.proNumber,"
			+ " t.shipperCompany,"
			+ " t.shipperCity,"
			+ " t.shipperState,"
			+ " t.submittedDate,"
			+ " t.pickupDate,"
			+ " t.totalPieces,"
			+ " t.totalWeight,"
			+ " t.pstid,"
			+ " t.status,"
			+ " t.username,"
			+ " t1.tiname AS terminalName,"
			+ " t1.tipho AS terminalPhone,"
			+ " t1.tifax AS terminalFax"
			+ " FROM (SELECT"
			+ " TRIM(PHRQST) AS requestNumber, "
			+ " CASE (mxot + mxpro) "
			+ "	WHEN 0 THEN '' "
			+ " ELSE CONCAT(DIGITS(mxot), CONCAT('-', DIGITS(mxpro)))"
			+ " END AS proNumber,"
			+ " TRIM(pssnam) AS shipperCompany,"
			+ " TRIM(psscty) AS shipperCity,"
			+ " TRIM(pssst) AS shipperState,"
			+ " date(phcdte) AS submittedDate,"
			+ " CASE WHEN FHPUD8>0 THEN DATE(TO_DATE(VARCHAR(FHPUD8),'YYYYMMDD')) ELSE NULL END AS pickupDate,"
			+ " phtpcs AS totalPieces ,"
			+ " phtwgt AS totalWeight,"
			+ " pstid,"
			+ " TRIM("+FBPGMS+".sf_pickupstatus(phstat)) AS status,"
			+ " phwebusr AS username"
			+ " FROM "+DATALIB+".pkp10p101 "
			+ " INNER JOIN "+DATALIB+".pkp10p100"
			+ " ON phsnum = pssnum "
			+ " LEFT OUTER JOIN "+DATALIB+".frp103 "
			+ " ON phrqst=mxrqst"
			+ " AND mxtype='PUR'"
			+ " LEFT JOIN "+DATALIB+".frp001"
			+ " ON  mxpro=fhpro"
			+ " AND mxot=fhot) AS t"
			+ " LEFT OUTER JOIN "+DATALIB+".eep015 as t1"
			+ " ON t.pstid=t1.titid ) tt"
			+ " WHERE tt.username = ? ";
	
	/**
	 * To update pickup notification for shipper & consignee only
	 */
	public static String UPDATE_PICKUP_NOTIFICATION = " UPDATE "+DATALIB+".PKP10P107"
			+ " SET SENDNOTIFICATION='Y',"
			+ " METHOD='E' "
			+ " WHERE  REQUESTNUMBER= ?"
			+ " AND TYPE = ?"
			+ " AND EMAIL = ?";
	

	/*
	 * Get the list of available pickup dates
	 */
	public static final String GET_AVAILABLE_PICKUP_DATE="SELECT  WDDAT8, WDDAY,WDEXCP FROM FBFILES.FRP932 "
			+ " WHERE WDDAT8  >=  (SELECT INT(VARCHAR_FORMAT(CURRENT TIMESTAMP, 'YYYYMMDD'))"
			+ " FROM SYSIBM.SYSDUMMY1)  "
			+ " AND WDDAT8 <= (SELECT INT(VARCHAR_FORMAT(CURRENT TIMESTAMP + 30 DAY, 'YYYYMMDD'))"
			+ " FROM SYSIBM.SYSDUMMY1)";
	
	public static final  String VALID_PICKUP_DATE_QUERY="SELECT  COUNT(*) AS VALID FROM FBFILES.FRP932 "
			+ " WHERE WDDAT8  = ? "
			+ " AND WDDAY <6 "
			+ " AND WDEXCP!='H'"
			+ " AND WDDAT8 >= (SELECT INT(VARCHAR_FORMAT(CURRENT TIMESTAMP, 'YYYYMMDD'))"
			+ " FROM SYSIBM.SYSDUMMY1)"
			+ " AND WDDAT8 <= (SELECT INT(VARCHAR_FORMAT(CURRENT TIMESTAMP + 30 DAY, 'YYYYMMDD'))"
			+ " FROM SYSIBM.SYSDUMMY1) ";
	
}
