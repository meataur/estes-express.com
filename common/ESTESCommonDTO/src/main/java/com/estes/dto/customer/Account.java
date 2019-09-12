/**
 * @author  Todd Allen
 *
 * Creation date: 05/14/2008
 */

package com.estes.dto.customer;

/**
 * Estes Account information
 */
public class Account
{
	/**
     * Account code for Estes Air
     */
	public static final String ESTES_AIR = "5028824";
	/**
     * Group account type
     */
	public static final String GROUP_ACCOUNT = "G";
	/**
     * Local account type
     */
	public static final String LOCAL_ACCOUNT = "L";
	/**
     * National account type
     */
	public static final String NATIONAL_ACCOUNT = "N";
	/**
     * Web account type
     */
	public static final String WEB_ACCOUNT = "W";
	/**
     * Interline carrier account type
     */
	public static final String INTERLINE_ACCOUNT = "I";

	private String code = "";
	private String type = "";
	private String[] groupAccountsList;
	private String[] webAccountsList;
	
	private String[] groupAccountsDisplay;
	private String[] webAccountsDisplay;

	/**
	 * Constructor.
	 */
	public Account()
	{
		super();
	} // Constructor

	/**
	 * Constructor.
	 * 
	 * @param num Account code
	 */
	public Account(String num)
	{
		this();
		setCode(num);
	} // Constructor

	/**
	 * Constructor.
	 * 
	 * @param num Account code
	 * @param typ Account type
	 */
	public Account(String num, String typ)
	{
		this(num);
		setType(typ);
	} // Constructor

	/**
	 * Get the account code
	 * 
	 * @return Account code
	 */
	public String getCode()
	{
		return code;
	} // getCode()

	/**
	 * Get the list of group account codes
	 * 
	 * @return Array of group account codes
	 */
	public String[] getGroupAccountsList()
	{
		return groupAccountsList;
	} // getGroupAccountsList()

	public String[] getSubAccountsDisplay(){
		if(isGroup()){
			return this.groupAccountsDisplay;
		}else if(isWebGroup()){
			return this.webAccountsDisplay;
		}
		return null;
	}

	/**
	 * Returns sub accounts for group or web group accounts. 
	 * Will return null for all others.
	 * 
	 * @return Array of account codes associated with account
	 */
	public String[] getSubAccountsList()
	{
		if(isGroup()){
			return groupAccountsList;
		} else if(isWebGroup()){
			return webAccountsList;
		}
		return null;
	} // getSubAccountsList()
	
	/**
	 * Get the account type
	 * 
	 * @return Account type
	 */
	public String getType()
	{
		return type;
	} // getType()

	/**
	 * Get the list of web account codes
	 * 
	 * @return Array of web account codes
	 */
	public String[] getWebAccountsList()
	{
		return webAccountsList;
	} // getWebAccountsList()

	/**
	 * Determine if this is a group account
	 * 
	 * @return Boolean indicating whether account is a group account
	 */
	public boolean isGroup()
	{
		if(this.type.equalsIgnoreCase(GROUP_ACCOUNT)){
			return true;
		}
		return false;
	} // isGroup()

	/**
	 * Determine if this is a local account
	 * 
	 * @return Boolean indicating whether account is a local account
	 */
	public boolean isLocal()
	{
		if(this.type.equalsIgnoreCase(LOCAL_ACCOUNT)){
			return true;
		}
		return false;
	} // isLocal()

	/**
	 * Determine if this is a national account
	 * 
	 * @return Boolean indicating whether account is a national account
	 */
	public boolean isNational()
	{
		if(this.type.equalsIgnoreCase(NATIONAL_ACCOUNT)){
			return true;
		}
		return false;
	} // isNational()

	/**
	 * Determine if this is a web group account
	 * 
	 * @return Boolean indicating whether account is a web account
	 */
	public boolean isWebGroup()
	{
		if(this.type.equalsIgnoreCase(WEB_ACCOUNT)){
			return true;
		}
		return false;
	} // isWebGroup()

	/**
	 * Set the account code.
	 * 
	 * @param Account code
	 */
	public void setCode(String num)
	{
		this.code = num;
	} // setCode(String)

	/**
	 * Set the list of group accounts.
	 * 
	 * @param Array of group account codes
	 */

	public void setGroupAccountsDisplay(String[] accounts)
	{
		this.groupAccountsDisplay = accounts;
	}

	public void setGroupAccountsList(String[] accounts)
	{
		this.groupAccountsList = accounts;
	} // setGroupAccountsList(String[])

	/**
	 * Set the account type.
	 * 
	 * @param typ Account type
	 */
	public void setType(String typ)
	{
		this.type = typ;
	} // setType(String)
	
	public void setWebAccountsDisplay(String[] accounts)
	{
		this.webAccountsDisplay = accounts;
	}

	/**
	 * Set the list of web accounts.
	 * 
	 * @param Array of web account codes
	 */
	public void setWebAccountsList(String[] accounts)
	{
		this.webAccountsList = accounts;
	} // setWebAccountsList(String[])
}
