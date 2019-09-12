package com.estes.myestes.quickLinks.web.dao.iface;

import java.util.List;

import com.estes.myestes.quickLinks.web.dto.QuickLink;

public interface QuickLinkDAO extends BaseDAO {
	
	public static String MY_ESTES_APPLICATION_MASTER   = "ESTESRTGY2.QNP220";
	/**
	 * User Quick Links are saved in the following table
	 */
	public static String QUICK_LINKS_FOR_MY_ESTES      = "FBFILES.QUICK_LINKS_FOR_MY_ESTES";
	
	public static String ACCOUNT_NUMBER_MENU           = "ESTESRTGY2.QNP200";
	
	public static String MY_ESTES_SECURITY_FILE        = "ESTESRTGY2.QNP302";
	
	public static int ALL_OF_AN_APPLICATION_BLOCK_TYPE = 2;
	
	public static String REPLACE_WITH_USERNAME = "%REPLACE_WITH_USERNAME%";
	
	
	public static String DEFAULT_QUICK_LINKS = " SELECT"
		+ " QCKAPP AS appName,"
		+ " QCKCAT AS appCategory,"
		+ " QCKORD AS linkOrder,"
		+ " QWDSC1 AS description"
		+ " FROM FBFILES.QCK00P100 "
		+ " INNER JOIN ESTESRTGY2.QNP220 "
		+ " ON QCKAPP=QWMENU "
		+ " ORDER BY QCKORD ASC "
		+ " LIMIT 0,5";
	
	public static String USER_QUICK_LINKS = "SELECT"
			+ " QWMENU AS appName,"
			+ " QWTYPE AS appCategory,"
			+ " ORDER_LINK AS linkOrder,"
			+ " QWDSC1 AS description"
			+ " FROM " + QUICK_LINKS_FOR_MY_ESTES
			+ " INNER JOIN " + MY_ESTES_APPLICATION_MASTER
			+ " ON QWMENU=APPLI00001 "
			+ " WHERE USER_NAME=?"
			+ " ORDER BY ORDER_LINK ASC"
			+ " LIMIT 0,5";
	
	
	//there are a lot of hard coded stuff for this query
	public static String AVAILABLE_QUICK_LINKS =
			"SELECT QWMENU,QNUN,QNTYPE,QWDSC1,'' AS ADD FROM " + ACCOUNT_NUMBER_MENU + " " +
			"INNER JOIN " + MY_ESTES_APPLICATION_MASTER + " ON " + ACCOUNT_NUMBER_MENU + ".QNTYPE" +
			"=" + MY_ESTES_APPLICATION_MASTER + ".QWTYPE WHERE QNUN=? AND QWMENU NOT IN " +
			"(SELECT QZAPP FROM " + MY_ESTES_SECURITY_FILE + " WHERE QZREFC=? AND " +
			"QZBLTP=" + ALL_OF_AN_APPLICATION_BLOCK_TYPE + ") AND QNTYPE NOT IN " +
			"(SELECT APP_CAT FROM " + QUICK_LINKS_FOR_MY_ESTES + " WHERE USER_NAME=?) " +
			"AND QNTYPE<>'G04' AND QNTYPE<>'A12' AND QNTYPE<>'A10' AND QNTYPE<>'A11' " +
			"AND QNTYPE<>'K05' AND QNTYPE<>'F02' AND QNTYPE<>'F01' AND QNTYPE<>'D04' " +
			"AND QNTYPE<>'K01' AND QNTYPE<>'C01' AND QNTYPE<>'C06' AND QNTYPE<>'B03' " +
			"AND QNTYPE<>'Y02' " +
			"UNION SELECT QWMENU,'" + REPLACE_WITH_USERNAME + "' AS \"QNUN\",QWTYPE," +
			"QWDSC1, '' AS ADD FROM " + MY_ESTES_APPLICATION_MASTER + " WHERE QWTYPE " +
			"IN (SELECT QCKCAT FROM FBFILES.QCK00P100) AND QWTYPE NOT IN " +
			"(SELECT APP_CAT FROM " + QUICK_LINKS_FOR_MY_ESTES + " WHERE USER_NAME=?) " +
			"AND QWTYPE NOT IN ('D04','G04','A12') ORDER BY QWDSC1 ASC";
	
	public static String ADD_LINK = 
			" INSERT INTO "+ QUICK_LINKS_FOR_MY_ESTES
			+ " (APPLI00001,USER_NAME,ORDER_LINK,APP_CAT,DATE_ADDED,TIME_ADDED )"
			+ " VALUES ( ?, ?, ?, ?, ?, ? )";
	
	
	public static String UPDATE_QUICK_LINK_ORDER = "UPDATE "+QUICK_LINKS_FOR_MY_ESTES
			+ " SET ORDER_LINK=? "
			+ " WHERE APPLI00001=? "
			+ " AND USER_NAME=? "
			+ " AND APP_CAT=? ";
	
	public static String DELETE_LINK = " DELETE FROM " + QUICK_LINKS_FOR_MY_ESTES + ""
			+ " WHERE APPLI00001=?"
			+ " AND USER_NAME=?"
			+ " AND APP_CAT=?";
	
	public static String DELETE_USER_ALL_LINK = " DELETE FROM " + QUICK_LINKS_FOR_MY_ESTES
			+ " WHERE USER_NAME=? ";
	
	public static String DELETE_TOP_FIVE_LINKS = " DELETE FROM " + QUICK_LINKS_FOR_MY_ESTES
			+ " WHERE USER_NAME=?";
	
	public static String COUNT_LINKS_FOR_USER = " SELECT COUNT(*) AS LINKS_COUNT "
			+ " FROM " + QUICK_LINKS_FOR_MY_ESTES 
			+ " WHERE USER_NAME=? ";

	/**
	 * Default Quick Links for each user when a user is not authenticated/authorized
	 * It returns the list of 5 Quick Link
	 * @return List<QuickLink>
	 */
	public List<QuickLink> getDefaultQuickLinks();
	
	
	/**
	 * It returns the 5 customized quick Links for a authenticated user.
	 * @param username
	 * @return List<QuickLink>
	 */
	public List<QuickLink> getUserQuickLinks(String username);
	
	public boolean checkNewLinkInUserQuickLinks(QuickLink quickLink, String username);
	/**
	 * It returns all the quick links of those applications that are accessible for the authenticated user
	 * @param username
	 * @return
	 */
	
	public List<QuickLink> getAvailableQuickLinks(String username);
	
	/**
	 * Get quickLink from available quick links
	 * @param quickLink
	 * @param username
	 * @return QuickLink
	 */
	public QuickLink getQuickLinkFromAvailableQuickLinks(QuickLink quickLink,String username);
	/**
	 * It add a quick link from available quick link list to user quick link
	 * @param quickLink
	 * @param username
	 */
	public void addQuickLink(QuickLink quickLink,String username);
	
	/**
	 * It deletes a quick link from user quick link
	 * @param quickLink 
	 * @param username
	 */
	public void deleteQuickLink(QuickLink quickLink, String username);
	
	
	/**
	 * Delete all user quick Link
	 * @param username
	 */
	
	public void deleteAllQuickLink(String username);
	
	
	/**
	 * it returns the number of user quick link
	 * @param username
	 * @return int
	 */
	public int getUserQuickLinkTotal(String username);

	/**
	 * add multiple quick links to user
	 * @param quickLinks
	 * @param username
	 */
	public void addQuickLinks(List<QuickLink> quickLinks, String username);
	
}
