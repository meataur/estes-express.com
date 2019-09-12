package com.estes.myestes.profile.web.dao.iface;

import com.estes.myestes.profile.web.dto.BasicProfile;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;

public interface ProfileDAO extends BaseDAO {
	
	/***
	 * This query returns the following profile information:
	 * firstName, lastName, accountCode, phone, companyName, username, email, email preference, created date & profile status
	 * User information is stored in basically qnp150 & qnp230 tables.
	 * User status is calculated from qnp302 table.
	 * if qnp302 contains the username with QZBLTP = 1 and QZREFT = 1, then the user is being considered disable.
	 * 
	 * this is the query formatted:
	 
	 ************************************************************************************************************
		 select qrfnam as firstName,
			qrlnam as lastName,
			QSACT# as accountCode,
			CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,
			QRCNAM as companyName,
			QRUN as username,
			qrem1 as email,
			qrwebu as preference,
			QSRDAT as createdDate,
			CASE WHEN QZREFC IS  NULL THEN 'enabled' ELSE 'disabled' END AS status
			from estesrtgy2.qnp150
			LEFT  JOIN estesrtgy2.qnp230 ON QSUN=QRUN
			LEFT JOIN ( select QZREFC from estesrtgy2.qnp302 where  QZBLTP = 1 and QZREFT = 1) as t ON QZREFC=QSUN
			WHERE QSUN=?;
	
	***************************************************************************************************************
	
	 */
	public static final String PROFILE_INFO = "select  qrfnam as firstName,"
											+ " qrlnam as lastName,"
											+ " QSACT# as accountCode,"
											+ " CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,"
											+ " QRCNAM as companyName,"
											+ " QRUN as username,"
											+ " qrem1 as email,"
											+ " qrwebu as preference,"
											+ " QSRDAT as createdDate,"
											+ " CASE WHEN QZREFC IS  NULL THEN 'ENABLED' ELSE 'DISABLED' END AS status"
											+ " from "+ALT_PGM_SCHEMA+".qnp150"
											+ " LEFT  JOIN "+ALT_PGM_SCHEMA+".qnp230 ON QSUN=QRUN"
											+ " LEFT JOIN ( select QZREFC from "+ALT_PGM_SCHEMA+".qnp302 where  QZBLTP = 1 and QZREFT = 1) as t ON QZREFC=QSUN"
											+ " WHERE QSUN=?";
	
	/**
	 * Check profile
	 */
	public static final String CHECK_PROFILE = "SELECT count(*) as found FROM "+ALT_PGM_SCHEMA+".qnp150 WHERE QRUN =?";
	
	/**
	 * Add  Profile Information Into QNP150 TABLE
	 */
	public final static String ADD_USER = "INSERT INTO "+ALT_PGM_SCHEMA+".qnp150 "
			+ " (QRFNAM, QRLNAM, QRCNAM, QRACT#, QRPNAC, QRPNFP, QRPNLP, QRUN, QRWEBU, QRAPP) "
			+ " VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, 'Y')";

	public static final String UPDATE_USER = "UPDATE "+ALT_PGM_SCHEMA+".QNP150 "
			+ " SET QRFNAM=?, QRLNAM=?, QRCNAM=?, QRPNAC=?, QRPNFP=?, QRPNLP=?, QRWEBU=? WHERE QRUN=?";

	public boolean updateUsername(String currentUsername, String newUsername);
	
	public boolean updateEmailAddress(String username,String emailAddress);
	
	public boolean changePassword(String username, Password password);
	
	public boolean updateEmailPrefence(String username,String flag);
	
    
	public Profile getUserProfile(String username);
	
	public boolean hasProfileInfo(String username);

	public void updateProfile(String username, BasicProfile profile);

	void addProfile(String username, String accountCode, BasicProfile profile);
	

}
