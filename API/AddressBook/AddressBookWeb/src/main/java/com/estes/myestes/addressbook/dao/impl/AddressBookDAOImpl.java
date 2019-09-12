/**
 * @author: Todd Allen
 *
 * Creation date: 06/05/2018
 */

package com.estes.myestes.addressbook.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.addressbook.dao.iface.AddressBookDAO;
import com.estes.myestes.addressbook.dao.iface.ErrorDAO;
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.utils.AddressBookConstant;
import com.estes.myestes.addressbook.utils.PhoneUtil;

@Repository ("addressDAO")
public class AddressBookDAOImpl implements AddressBookDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class AddressMapper implements RowMapper<BookAddress>
	{
		@Override
		public BookAddress mapRow(ResultSet rs, int rowNm) throws SQLException {
			BookAddress elem = new BookAddress();
			elem.setUser(rs.getString("user_name"));
			elem.setSeq(rs.getInt("addr_seq"));
			elem.setShipper(getBoolFromString(rs.getString("at_ship")));
			elem.setConsignee(getBoolFromString(rs.getString("at_cons")));
			elem.setThirdParty(getBoolFromString(rs.getString("at_tpty")));
			elem.setCod(getBoolFromString(rs.getString("at_cod")));
			elem.setCompany(rs.getString("user_cn"));
			elem.setFirstName(rs.getString("user_fn"));
			elem.setLastName(rs.getString("user_ln"));
			elem.setLocationNumber(rs.getString("user_loc"));
			elem.setPhone(rs.getString("USER_PN_AC")+rs.getString("USER_PN_NX")+rs.getString("USER_PN_L4"));
			elem.setPhoneExt(rs.getString("user_pn_xt"));
			elem.setFax(rs.getString("USER_FN_AC")+rs.getString("USER_FN_NX")+rs.getString("USER_FN_L4"));
			elem.getAddress().setCountry(rs.getString("user_cntry"));
			elem.getAddress().setStreetAddress(rs.getString("user_str_1"));
			elem.getAddress().setStreetAddress2(rs.getString("user_str_2"));
			elem.getAddress().setCity(rs.getString("user_city"));
			elem.getAddress().setState(rs.getString("user_state"));
			elem.getAddress().setZip(rs.getString("user_zip"));
			elem.getAddress().setZip4("");
			elem.setEmail(rs.getString("user_em"));
			return elem;
		}

		/**
		 * Get boolean value from shipper/consignee/3rd party/COD flag
		 * 
		 * @param flag Flag value
		 * @return Boolean based on flag value
		 */
		private boolean getBoolFromString(String flag)
		{
			return flag.equalsIgnoreCase("X");
		} // getBoolFromString
} // AddressMapper

	@Override
	public boolean addAddress(BookAddress address) throws AddressBookException
	{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String ts = formatTimestamp(now);

		if (insertAddress(address, ts)) {
			processAddress(address.getUser(), ts);
			return writeAddress(address.getUser(), ts);
		}
		else {
			return false;
		}
	} // addAddress

	@Override
	public List<String> addAddress(String user, String[] address) throws AddressBookException
	{
		Timestamp now = new Timestamp(System.currentTimeMillis());
		String ts = formatTimestamp(now);

		if (insertAddress(user, address, ts)) {
			 if (processAddress(user, ts)) {
					writeAddress(user, ts);
					return Collections.emptyList();
			 }
			 else {
				 return getAddressErrors(user, ts);
			 }
		}

		return Collections.emptyList();
	} // addAddress

	@Override
	public boolean deleteAddress(BookAddress addr) throws AddressBookException
	{
		String sql = "DELETE FROM fbfiles.ebg10p115 " + 
				"WHERE user_name = ? and address_sequence = ?";

		try {
			return  jdbcMyEstes.update(sql,  new Object[] {addr.getUser(),addr.getSeq()}) > 0 ? true:false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "deleteAddress()", "** Error deleting address for user " + addr.getUser() + ".", e);
    		throw new AddressBookException("Error deleting address.");
		}
	} // deleteAddress

	@Override
	public boolean deleteAddresses(String user) throws AddressBookException
	{
		String sql = "DELETE FROM fbfiles.ebg10p115 " + 
				"WHERE user_name = ?";

		try {
			return  jdbcMyEstes.update(sql,  new Object[] {user}) > 0 ? true:false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "deleteAddresses()", "** Error deleting addresses for user " + user + ".", e);
    		throw new AddressBookException("Error deleting addresses.");
		}
	} // deleteAddresses

	/**
	 * Format timestamp for use with DB2 database.
	 * 
	 * @param ts String representation of timestamp formatted to match database format
	 * - Replace space between date and time with a dash
	 * - Replace colons in time with periods
	 */
	private String formatTimestamp(Timestamp ts)
	{
		StringBuffer time = new StringBuffer(ts.toString());
		time.replace(10, 11, "-");
		return time.toString().replace(':', '.');
	} // formatTimestamp

	private List<String> getAddressErrors(String userName, String ts) throws AddressBookException
	{
		// Select all error columns in to 1 column
		String sql = "SELECT code FROM " + DATA_SCHEMA + " .ebg10p215, " +
				"LATERAL( VALUES " +
				"( u_name_e), (user_cn_e), (u_fn_e), ( u_ln_e), ( u_loc_e), (u_pn_ac_e), " +
				"(u_pn_nx_e), (u_pn_l4_e), (u_pn_xt_e), (u_fn_ac_e), (u_fn_nx_e), (u_fn_l4_e), " +
				"(u_str_1_e), (u_str_2_e), (u_city_e), ( u_state_e), ( u_zip_e), (u_z4_e), " +
				"(u_cntry_e), (u_em_e) " +
				") AS T(code) " +
				"WHERE user_name = ? AND time_stamp = ? AND error <>  '" + NO_ERRORS + "' AND code <>  '" + NO_ERRORS + "'";

		try {
			List<String> data= jdbcMyEstes.queryForList(sql, new Object[] {userName, ts}, String.class);
			return data;
		}
		catch (Exception e) {
			throw new AddressBookException("Addresses for user " + userName + " could not be retrieved.", e);
		}
	} // getAddresses

	@Override
	public BookAddress[] getAddresses(String userName) throws AddressBookException
	{
		String sql = "SELECT user_name, addr_seq, at_ship, at_cons, at_tpty, at_cod, " +
				"user_cn, user_fn, user_ln, user_loc, user_pn_ac, user_pn_nx, user_pn_l4, " +
				"user_pn_xt, user_fn_ac, user_fn_nx, user_fn_l4, user_cntry, user_str_1, " +
				"user_str_2, user_city, user_state, user_zip, user_em " +
				"FROM " + DATA_SCHEMA + " .ebg10p115 " +
				"WHERE user_name = ? " +
				"ORDER BY user_cn";

		try {
			List<BookAddress> listQuery =  jdbcMyEstes.query(sql,  new Object[] {userName}, new AddressMapper());
			return listQuery.toArray( new BookAddress[listQuery.size()] );
		}
		catch (Exception e) {
			throw new AddressBookException("Addresses for user " + userName + " could not be retrieved.", e);
		}
	} // getAddresses

	/**
	 * Get shipper/consignee/3rd party/COD flag from boolean
	 * 
	 * @param tf Boolean value
	 * @return Flag value to be inserted in to address database row
	 */
	private String getStringFromBool(boolean tf)
	{
		return tf ? "X":" ";
	} // getStringFromBool

	private boolean insertAddress(BookAddress address, String tstamp) throws AddressBookException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + ".ebg10p215 " + 
				"(USER_NAME, TIME_STAMP, CALLED_BY, AT_SHIP, AT_CONS, AT_TPTY, " + 
				"AT_COD, USER_CN, USER_FN, USER_LN, USER_LOC, USER_PN_AC, " + 
				"USER_PN_NX, USER_PN_L4, USER_PN_XT, USER_FN_AC, USER_FN_NX, USER_FN_L4, " + 
				"USER_STR_1, USER_STR_2, USER_CITY, USER_STATE, USER_ZIP, USER_CNTRY, " +
				"USER_EM) " + 
				"VALUES (?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, " + 
				"?)";

		Object[] values = {address.getUser(), tstamp, AddressBookConstant.CALLED_BY, getStringFromBool(address.isShipper()), getStringFromBool(address.isConsignee()), getStringFromBool(address.isThirdParty()),
				getStringFromBool(address.isCod()), address.getCompany(), address.getFirstName(), address.getLastName(), address.getLocationNumber(), PhoneUtil.getAreaCode(address.getPhone()),
				PhoneUtil.getExchange(address.getPhone()), PhoneUtil.getLast4(address.getPhone()), address.getPhoneExt(), PhoneUtil.getAreaCode(address.getFax()), PhoneUtil.getExchange(address.getFax()), PhoneUtil.getLast4(address.getFax()),
				address.getAddress().getStreetAddress(), address.getAddress().getStreetAddress2(), address.getAddress().getCity(), address.getAddress().getState(), address.getAddress().getZip(),
				address.getAddress().getCountry(), address.getEmail()};

		try {
			return  jdbcMyEstes.update(sql,  values) > 0 ? true:false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "insertAddress()", "** Error inserting address.", e);
    		throw new AddressBookException("Error capturing address .");
		}
	} // insertAddress

	private boolean insertAddress(String user, String[] addr, String tstamp) throws AddressBookException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + ".ebg10p215 " + 
				"(USER_NAME, TIME_STAMP, CALLED_BY, AT_SHIP, AT_CONS, AT_TPTY, " + 
				"AT_COD, USER_CN, USER_FN, USER_LN, USER_LOC, USER_PN_AC, " + 
				"USER_PN_NX, USER_PN_L4, USER_PN_XT, USER_FN_AC, USER_FN_NX, USER_FN_L4, " + 
				"USER_STR_1, USER_STR_2, USER_CITY, USER_STATE, USER_ZIP, USER_Z4, " +
				"USER_CNTRY, USER_EM) " + 
				"VALUES (?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, " + 
				"?, ?, ?, ?, ?, ?, " + 
				"?, ?)";

		Object[] values = {user, tstamp, AddressBookConstant.CALLED_BY, addr[0], addr[1], addr[2],
				addr[3], addr[4], addr[5], addr[6], addr[7], addr[8],
				addr[9], addr[10], addr[11], addr[12], addr[13], addr[14],
				addr[15], addr[16], addr[17], addr[18], addr[19], addr[20],
				addr[21], addr[22]};

		try {
			return  jdbcMyEstes.update(sql,  values) > 0 ? true:false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "insertAddress()", "** Error inserting address.", e);
    		throw new AddressBookException("Error capturing address .");
		}
	} // insertAddress

	private boolean processAddress(String user, String ts) throws AddressBookException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("ebg10q121");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("user_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("tstamp", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("delay", "");
        inParams.put("user_name", user);
        inParams.put("tstamp", ts);

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
            	return !ErrorDAO.isError((String) outParms.get("error"));
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "processAddress()", "An error occurred processing address for user " + user);
    			return false;
    		}
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "processAddress()", "** Error processing address.", e);
    		throw new AddressBookException("Error processing address .");
        }
	} // processAddress

	@Override
	public boolean updateAddress(BookAddress address) throws AddressBookException
	{
		String sql = "UPDATE " + DATA_SCHEMA + ".ebg10p115  SET " + 
				"AT_SHIP=?, AT_CONS=?, AT_TPTY=?, " + 
				"AT_COD=?, USER_CN=?, USER_FN=?, USER_LN=?, USER_LOC=?, USER_PN_AC=?, " + 
				"USER_PN_NX=?, USER_PN_L4=?, USER_PN_XT=?, USER_FN_AC=?, USER_FN_NX=?, USER_FN_L4=?, " + 
				"USER_STR_1=?, USER_STR_2=?, USER_CITY=?, USER_STATE=?, USER_ZIP=?, " + 
				"USER_CNTRY=?, USER_EM=? " +
				"WHERE ADDRESS_SEQUENCE = ? AND USER_NAME = ?";

		Object[] values = {getStringFromBool(address.isShipper()), getStringFromBool(address.isConsignee()), getStringFromBool(address.isThirdParty()),
				getStringFromBool(address.isCod()), address.getCompany(), address.getFirstName(), address.getLastName(), address.getLocationNumber(), PhoneUtil.getAreaCode(address.getPhone()),
				PhoneUtil.getExchange(address.getPhone()), PhoneUtil.getLast4(address.getPhone()), address.getPhoneExt(), PhoneUtil.getAreaCode(address.getFax()), PhoneUtil.getExchange(address.getFax()), PhoneUtil.getLast4(address.getFax()),
				address.getAddress().getStreetAddress(), address.getAddress().getStreetAddress2(), address.getAddress().getCity(), address.getAddress().getState(), address.getAddress().getZip(),
				address.getAddress().getCountry(), address.getEmail(), address.getSeq(), address.getUser()};

		try {
			return  jdbcMyEstes.update(sql,  values) > 0 ? true:false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "updateAddress()", "** Error updating address for user " + address.getUser() + ".", e);
    		throw new AddressBookException("Error updating address .");
		}
	} // updateAddress

	public boolean writeAddress(String user, String ts) throws AddressBookException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("ebg10q141");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("user_name", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("tstamp", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("seq", Types.INTEGER));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("delay", "");
        inParams.put("user_name", user);
        inParams.put("tstamp", ts);
        inParams.put("seq", 0);
 
        try {
        	Map<String, Object> outParms = sproc.execute(inParams);
	        if (outParms != null) {
            	return !ErrorDAO.isError((String) outParms.get("error"));
			} else {
				ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "writeAddress()", "An error occurred saving address for user " + user);
				return false;
			}
	    }
	    catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressBookDAOImpl.class, "writeAddress()", "** Error saving address.", e);
			throw new AddressBookException("Error saving address .");
	    }
	} // writeAddress
}
