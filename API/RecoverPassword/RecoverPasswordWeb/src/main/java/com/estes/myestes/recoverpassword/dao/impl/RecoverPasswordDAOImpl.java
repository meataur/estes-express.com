/**
 * @author: Lakshman K
 *
 * Creation date: 10/10/2018
 */

package com.estes.myestes.recoverpassword.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.recoverpassword.dao.iface.RecoverPasswordDAO;
import com.estes.myestes.recoverpassword.dto.UserNamePassword;
import com.estes.myestes.recoverpassword.exception.RecoverPasswordException;

@Repository ("recoverPasswordDAO")
public class RecoverPasswordDAOImpl implements RecoverPasswordDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplateExla;

	private static final class RecoverPasswordMapper implements RowMapper<UserNamePassword>
	{
		@Override
		public UserNamePassword mapRow(ResultSet rs, int rowNm) throws SQLException {
			UserNamePassword elem = new UserNamePassword();		
			elem.setUserName(rs.getString("QSUN"));
			elem.setPassword(rs.getString("QSPW"));
			elem.setEmail(rs.getString("QSEM1"));

			return elem;
		}
	} // RecoverPasswordMapper

	@Override
	public UserNamePassword getUserNamePasswordWithEmail(String emailAddress) throws RecoverPasswordException {
		List<UserNamePassword> userNamePasswordList = null;
		String sql = "SELECT QSUN, QSPW, QSEM1 " +
				"FROM estesrtgy2.qnp230 " +
				"WHERE QSEM1 = '" + emailAddress + "' "  ;

		try {
			userNamePasswordList =  jdbcTemplateExla.query(sql, new RecoverPasswordMapper());		
			return userNamePasswordList.size() > 0 ? userNamePasswordList.get(0) : null;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RecoverPasswordDAOImpl.class, "getUserNamePasswordWithEmail()", "** Error occurred on getting username, password.");
			throw new RecoverPasswordException("Estes username, password could not be retrieved.", e);
		}		
	} // getUserNamePasswordWithEmail

	@Override
	public UserNamePassword getUserNamePassordWithUserName(String userName) throws RecoverPasswordException {
		List<UserNamePassword> userNamePasswordList = null;
		String sql = "SELECT QSUN, QSPW, QSEM1 " +
				"FROM estesrtgy2.qnp230 " +
				"WHERE QSUN = '" + userName + "' "  ;

		try {
			userNamePasswordList =  jdbcTemplateExla.query(sql, new RecoverPasswordMapper());
			 return userNamePasswordList.size() > 0 ? userNamePasswordList.get(0) : null;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RecoverPasswordDAOImpl.class, "getUserNamePassordWithUserName()", "** Error occurred on getting username, password.");
			throw new RecoverPasswordException("Estes username, password could not be retrieved.", e);
		}		
	} // getUserNamePassordWithUserName

}
