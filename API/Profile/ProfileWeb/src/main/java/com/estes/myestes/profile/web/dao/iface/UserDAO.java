package com.estes.myestes.profile.web.dao.iface;

import java.util.List;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.profile.web.dto.App;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.User;
import com.estes.myestes.profile.web.dto.UserStatus;

public interface UserDAO extends BaseDAO {
	
	public static final String SUB_ACCOUNT_APP = "ADMINSUBS";
	
	
	public static final String CHILD_USERNAME_CHECK = "SELECT count(*) FROM "
			+ " ( select "
			+ " QRUN as username,"
			+ " QSEM2 as createdBY"
			+ " from "+ALT_PGM_SCHEMA+".qnp150"
			+ " LEFT  JOIN "+ALT_PGM_SCHEMA+".qnp230 ON QSUN=QRUN"
			+ " LEFT JOIN ( select QZREFC from "+ALT_PGM_SCHEMA+".qnp302 where  QZBLTP = 1 and QZREFT = 1) as t ON QZREFC=QSUN"
			+ " ) AS USER_INFO "
			+ " WHERE createdBY=? AND username=?";
	
	
	/**
	 * 
	 *******************Query to retrieve list of Users Created By Signed In User*****************
	 
	  select qrfnam as firstName,
			qrlnam as lastName,
			QSACT# as accountCode,
			CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,
			QRCNAM as companyName,
			QRUN as username,
			qrem1 as email,
			qrwebu as preference,
			QSUDAT as createdDate,
			CASE WHEN QZREFC IS  NULL THEN 'enabled' ELSE 'disabled' END AS status,
			QSEM2 as createdBy
			from estesrtgy2.qnp150
			LEFT  JOIN estesrtgy2.qnp230 ON QSUN=QRUN
			LEFT JOIN ( select QZREFC from estesrtgy2.qnp302 where  QZBLTP = 1 and QZREFT = 1) as t ON QZREFC=QSUN
			WHERE QSEM2=?
	 
	 
	 ********************************************************************************************
	 */

	public static final String USER_LIST_QUERY = "SELECT * FROM "
			+ " ( select  qrfnam as firstName,"
			+ " qrlnam as lastName,"
			+ " QSACT# as accountCode,"
			+ " CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,"
			+ " QRCNAM as companyName,"
			+ " QRUN as username,"
			+ " qrem1 as email,"
			+ " qrwebu as preference,"
			+ " QSUDAT as createdDate,"
			+ " CASE WHEN QZREFC IS  NULL THEN 'ENABLED' ELSE 'DISABLED' END AS status, "
			+ " QSEM2 as createdBY"
			+ " from "+ALT_PGM_SCHEMA+".qnp150"
			+ " LEFT  JOIN "+ALT_PGM_SCHEMA+".qnp230 ON QSUN=QRUN"
			+ " LEFT JOIN ( select QZREFC from "+ALT_PGM_SCHEMA+".qnp302 where  QZBLTP = 1 and QZREFT = 1) as t ON QZREFC=QSUN"
			+ " ) AS USER_INFO "
			+ " WHERE createdBY=?";
	
	/**
	 * Search by firstName, lastName, username or email 
	 */
	public static final String USER_SEARCH_QUERY = " AND (firstName LIKE ? OR lastName LIKE ? OR username LIKE ? OR email LIKE ? ) ";
	
	public static final String USER_SEARCH_BY_FIRST_NAME = " AND (firstName LIKE ?) ";
	
	
	public static final String USER_SEARCH_BY_LAST_NAME = " AND (lastName LIKE ?) ";
	
	public static final String USER_SEARCH_BY_USER_NAME = " AND (username LIKE ?) ";
	
	/**
	 * 
	 * @author rahmaat
	 *
	 */
	
	public enum OrderBy {
		FIRSTNAME,
		LASTNAME,
		ACCOUNTCODE, 
		PHONE,
		COMPANYNAME, 
		USERNAME, 
		EMAIL, 
		PREFERENCE, 
		CREATEDDATE, 
		STATUS, 
		CREATEDBY
	}
	
	public enum Order {
		ASC, 
		DESC }
	
	/**
	 * Get User Authorized Apps List.
	 * The follwing executable query for user 
	 select qwmenu, qnun, qntype, qwdsc1 
		from estesrtgy2.qnp200 
		inner join 
		estesrtgy2.qnp220 
		on estesrtgy2.qnp200.qntype=estesrtgy2.qnp220.qwtype 
		where qnun='TESTADMIN' 
		and qwmenu not in (select qzapp from estesrtgy2.qnp302 where qzrefc= and qzbltp=2) 
		and qwmenu not in (select qzapp from estesrtgy2.qnp302 where qzrefc= and qzbltp=2) 
		and qntype <> 'A12' 
		and qntype<> 'A10' 
		and qntype<> 'A11' 
		and qntype<>'K05' 
		and qntype<>'K01' 
		and qntype <>'F02' 
		and qntype<>'F01' 
		order by qwdsc1
	 */
	/**
	 * 'REQUESTINFO','DOCUMENTRETRIEVAL' are public app.
	 */
	public final static String GET_ACCESS_APPS = "select qwmenu, qntype,qwmen6, qwdsc1 "
			+ " from "+ALT_PGM_SCHEMA+".qnp200 "
			+ " inner join "+ALT_PGM_SCHEMA+".qnp220 "
			+ " on "+ALT_PGM_SCHEMA+".qnp200.qntype="+ALT_PGM_SCHEMA+".qnp220.qwtype "
			+ " where qnun=? "
			+ " and qwmenu not in (select qzapp from "+ALT_PGM_SCHEMA+".qnp302 where qzrefc=? and qzbltp=2) "
			+ " and qwmenu not in (select qzapp from "+ALT_PGM_SCHEMA+".qnp302 where qzrefc=? and qzbltp=2) "
			+ " and qntype <> 'A12' "
			+ " and qntype<> 'A10' "
			+ " and qntype<> 'A11' "
			+ " and qntype<>'K05' "
			+ " and qntype<>'K01' "
			+ " and qntype <>'F02' "
			+ " and qntype<>'F01'"
			+ " and qwmenu NOT IN ('REQUESTINFO','DOCUMENTRETRIEVAL')"
			+ " order by qwdsc1";
	
	/**
	 * Get User Blocked Apps
	 */
	public final static String GET_BLOCKED_APPS ="select qwmenu, qntype, qwmen6, qwdsc1 "
			+ " from "+ALT_PGM_SCHEMA+".qnp200 "
			+ " inner join "+ALT_PGM_SCHEMA+".qnp220 "
			+ " on "+ALT_PGM_SCHEMA+".qnp200.qntype="+ALT_PGM_SCHEMA+".qnp220.qwtype "
			+ " where qnun=? "
			+ " and qwmenu in (select qzapp from "+ALT_PGM_SCHEMA+".qnp302 where qzrefc=? and qzbltp=2)"
			+ " and qwmenu not in (select qzapp from "+ALT_PGM_SCHEMA+".qnp302 where qzrefc=? and qzbltp=2)"
			+ " and qntype <> 'A12'"
			+ " and qntype<> 'A10'"
			+ " and qntype<> 'A11'"
			+ " and qntype<>'K05'"
			+ " and qntype<>'K01' "
			+ " and qntype <>'F02'"
			+ " and qntype<>'F01' "
			+ " order by qwdsc1";
	
	
	
	/**
	 * Delete All Blocked Apps / Clear the blocked apps list assigned to user
	 */
	
	public final static String DELETE_ALL_BLOCKED_APPS = "delete from "+ALT_PGM_SCHEMA+".qnp302 "
			+ " where qzrefc=? "
			+ " AND QZAPP NOT IN (SELECT QZAPP from "+ALT_PGM_SCHEMA+".qnp302 WHERE QZREFC = ? AND QZBLTP = 2)"
			+ " and qzapp <> '"+SUB_ACCOUNT_APP+"'"
			+ " and qzbltp=2";
	
	/**
	 * Delete  Single App From Blocked List
	 */
	
	public final static String DELETE_BLOCKED_APPS = "delete from "+ALT_PGM_SCHEMA+".qnp302 "
			+ " where qzrefc=? "
			+ " AND QZAPP NOT IN (SELECT QZAPP from "+ALT_PGM_SCHEMA+".qnp302 WHERE QZREFC = ? AND QZBLTP = 2)"
			+ " and qzapp <> '"+SUB_ACCOUNT_APP+"'"
			+ " and qzbltp=2"
			+ " AND QZAPP=?";
	
	/**
	 * Insert Blocked Apps Query
	 */
	
	public final static String INSERT_BLOCKED_APPS="insert into "
			+ " "+ALT_PGM_SCHEMA+".qnp302 "
			+ " values(?, 1, 2, ?, '', '', CURRENT_DATE, CURRENT_TIME, '', '', '', '', '', '', 0, 0)";
	
	
	
	
	public final static String IS_DISABLED  = "select count(*) as IS_BLOCKED from estesrtgy2.qnp302 where QZREFC = ? and QZBLTP = 1 and QZREFT = 1";
	
	public final static String DISABLE_USER = "insert into estesrtgy2.qnp302 values (?, 1, 1, '', '', ?, CURRENT_DATE, CURRENT_TIME, '', '', '', '', '', '', 0, 0)";
	
	public final static String ENABLE_USER  = "delete from estesrtgy2.qnp302 where qzrefc = ? and qzbltp = 1 and qzreft = 1";


	public static final String GET_USER_STATUS = "select count(*) as total from estesrtgy2.qnp302 where QZREFC = ? and QZBLTP = 1 and QZREFT = 1";
	
	public void createUser(User user, String username);
	
	 
	public void disableUser(String childUsername, String parentUsername);
	
	public void enableUser(String childUsername);
	

	public List<App> getAuthorizedApps(String parentUsername, String childUsername);
	
	public List<App> getBlockedApps(String parentUsername, String childUsername);
	

	public int getTotal(String query, Object[] objects);


	public void addToBlockedApps(List<String> appNames, String username);

	
	public void deleteBlockedApps(List<String> appNames, String parentUsername,  String username);

	public boolean checkChildUser(String parentUsername, String childUsername);


	public  UserStatus getUserStatus(String username);


	public void deleteAllBlockedApps(String parentUsername, String childUsername);


	Page<Profile> getUsers(String username, Pageable pageable, String search, String qFirstName, String qLastName,
			String qUsername);

}
