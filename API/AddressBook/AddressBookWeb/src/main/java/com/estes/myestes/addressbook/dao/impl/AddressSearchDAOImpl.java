/**
 * @author: Todd Allen
 *
 * Creation date: 08/29/2018
 */

package com.estes.myestes.addressbook.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.addressbook.dao.iface.AddressSearchDAO;
import com.estes.myestes.addressbook.dto.BookAddress;
import com.estes.myestes.addressbook.dto.BookAddressSearch;
import com.estes.myestes.addressbook.exception.AddressBookException;

@Repository ("searchDAO")
public class AddressSearchDAOImpl implements AddressSearchDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class AddressMapper implements RowMapper<BookAddress>
	{
		@Override
		public BookAddress mapRow(ResultSet rs, int rowNm) throws SQLException
		{
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
		} // mapRow

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

	/**
	 * Build SQL where clause base on criteria entered
	 * 
	 * @param crit {@link BookAddress} search criteria
	 * @return Parameterized SQL where clause string
	 */
	private String buildWhereClause(BookAddressSearch criteria)
	{
		StringBuffer buffer = new StringBuffer("user_name ='" + criteria.getUser() + "'");

		if (criteria.isCompanyExact() || criteria.isCompanyContains()) {
			buffer.append(" AND ");
			if (criteria.isCompanyExact()) {
				buffer.append("user_cn = '"  + criteria.getCompany() + "'");
			}
			else {
				buffer.append("UPPER(user_cn) LIKE '%"  + criteria.getCompany().toUpperCase() + "%'");
			}
		}

		if (criteria.isStreetExact() || criteria.isStreetContains()) {
			buffer.append(" AND ");
			if (criteria.isStreetExact()) {
				buffer.append("user_str_1 = '"  + criteria.getStreetAddress() + "'");
			}
			else {
				buffer.append("UPPER(user_str_1) LIKE '%"  + criteria.getStreetAddress().toUpperCase() + "%'");
			}
		}

		if (criteria.isCityExact() || criteria.isCityContains()) {
			buffer.append(" AND ");
			if (criteria.isCityExact()) {
				buffer.append("user_city = '"  + criteria.getCity() + "'");
			}
			else {
				buffer.append("UPPER(user_city) LIKE '%"  + criteria.getCity().toUpperCase() + "%'");
			}
		}

		if (criteria.isStateExact() || criteria.isStateContains()) {
			buffer.append(" AND ");
			if (criteria.isStateExact()) {
				buffer.append("user_state = '"  + criteria.getState() + "'");
			}
			else {
				buffer.append("UPPER(user_state) LIKE '%"  + criteria.getState().toUpperCase() + "%'");
			}
		}

		if (criteria.isZipExact() || criteria.isZipContains()) {
			buffer.append(" AND ");
			if (criteria.isZipExact()) {
				buffer.append("user_zip = '"  + criteria.getZip() + "'");
			}
			else {
				buffer.append("UPPER(user_zip) LIKE '%"  + criteria.getZip().toUpperCase() + "%'");
			}
		}

		if (criteria.isLocExact() || criteria.isLocContains()) {
			buffer.append(" AND ");
			if (criteria.isLocExact()) {
				buffer.append("user_loc = '"  + criteria.getLocationNumber() + "'");
			}
			else {
				buffer.append("UPPER(user_loc) LIKE '%"  + criteria.getLocationNumber().toUpperCase() + "%'");
			}
		}

		return buffer.toString();
	} // buildWhereClause

	@Override
	public BookAddress[] searchAddresses(BookAddressSearch crit) throws AddressBookException
	{
		String sql = "SELECT user_name, addr_seq, at_ship, at_cons, at_tpty, at_cod, " +
				"user_cn, user_fn, user_ln, user_loc, user_pn_ac, user_pn_nx, user_pn_l4, " +
				"user_pn_xt, user_fn_ac, user_fn_nx, user_fn_l4, user_cntry, user_str_1, " +
				"user_str_2, user_city, user_state, user_zip, user_em " +
				"FROM " + DATA_SCHEMA + " .ebg10p115 " +
				"WHERE " + buildWhereClause(crit) + " " +
				"ORDER BY UPPER(user_cn)";

		try {
			List<BookAddress> listQuery =  jdbcMyEstes.query(sql,  new AddressMapper());
			if (listQuery != null && listQuery.size() > 0) {
				return listQuery.toArray( new BookAddress[listQuery.size()] );
			}
			else {
				return new BookAddress[0];
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, AddressSearchDAOImpl.class, "searchAddresses()", "** Error searching address book.");
			throw new AddressBookException("Search for addresses failed.");
		}
	} // searchAddresses
}
