package com.estes.myestes.BillOfLading.util;

/**
 * Constants related to the database.
 * 
 */
public interface DBConstants {

	
	//Retrieves account type for a user given user's account number
	String QUERY_CHECK_ACCT_EXISTS_RAP001="SELECT COUNT(*) AS acctExists FROM fbfiles.rap001 WHERE CMCUST = ?";
	
	//Get count of rows having this contact type
	String QUERY_CHECK_INPUT_EXISTS_SCP002="SELECT COUNT(*) AS inputExists FROM fbfiles.scp002 WHERE TBDAK1=? AND TBDKEY=?";
	
	//check if statecode exists
	String QUERY_CHECK_STATE_EXISTS_FBP010="SELECT COUNT(spabbr) AS stateExists FROM fbfiles.fbp010 WHERE spabbr=?";
	
	//validate the zipcode to see if it is in the given state
	String QUERY_VALIDATE_ZIPCODE_STATE="SELECT coalesce(min(amst),'') AS stateCode FROM fbfiles.fbp074 AS points JOIN fbfiles.frp015 on CT1TID = amterm and CT1DIV <> 999 WHERE points.amrzip = ? ";
	
	//to check if it is a holiday 
	String QUERY_IS_HOLIDAY="SELECT wdexcp AS holiday FROM fbfiles.frp932 WHERE wddat8=?";
	
	//to check if it is a weekend
	String QUERY_IS_WEEKEND = "Select wdday AS weekend FROM fbfiles.frp932 WHERE wddat8=?";
	
	//to check if the pickup date is in FRP932 or not
	String QUERY_IS_DATE_IN_DATECONTROL="SELECT count(*) as dateExists FROM fbfiles.frp932 WHERE wddat8=?";
	
	//to check if user is in MNP098F with pgm as PKP10S000 and function as PUDATE
	String QUERY_IS_AUTH_USER_MNP098F="SELECT count(*) as userExists FROM fbfiles.MNP098F WHERE ufpgm = 'PKP10S000' "
			+ "AND uffunc = 'PUDATE' AND ufuser = ?";

	
	String VALIDATE_CITYSTATEZIP_SP_NAME = "SP_Pickup_Validate_CityStateZip"; 
	
	String QUERY_IS_GROUP_ACCOUNT_BY_ACCOUNT ="select count(*) as count from fbfiles.rav001gp where cmocc = ?";
	String QUERY_IS_ACCOUNT_BELONGS_91 ="SELECT cmcust, cmocc FROM fbfiles.rap001 WHERE cmcust = ? and cmocc = ?";
	String QUERY_WEB_GROUP_ACCOUNT ="SELECT qagrpc, qacusc FROM estesrtgy2.qnp245 WHERE qacusc = ? and qagrpc = ?";
	String QUERY_WEB_ACCOUNT ="select count(*) as count from estesrtgy2.qnp246 where qbgrpc = ?";
	String QUERY_NATIONAL_ACCOUNT ="select count(*) as count from fbfiles.rav00102 where cmna = ?";
	String QUERY_ACCOUNT_BELONGS_NATIONAL_ACCOUNT ="SELECT cmcust, cmna FROM fbfiles.rap001 WHERE cmcust = ? and cmna = ?";
	String QUERY_ACTIVE_ACCOUNT = "SELECT cmcls FROM fbfiles.rap001 WHERE cmcust = ? and cmstat = 'A'";

}