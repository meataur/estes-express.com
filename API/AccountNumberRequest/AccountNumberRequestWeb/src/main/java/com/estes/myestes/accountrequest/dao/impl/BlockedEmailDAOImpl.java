/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 *
 */

package com.estes.myestes.accountrequest.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.accountrequest.dao.iface.BlockedEmailDAO;
import com.estes.myestes.accountrequest.exception.AccountRequestException;

@Repository ("emailDAO")
public class BlockedEmailDAOImpl implements BlockedEmailDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplateExla;

	@Override
	public boolean isBlocked(String email) throws AccountRequestException
	{
		String sql = "SELECT COUNT(*) FROM fbfiles.bem10p100 WHERE UPPER(email) = UPPER(?)";
		int count = 0;

		try {
			count = jdbcTemplateExla.queryForObject(sql, new Object[] {email}, Integer.class);
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, BlockedEmailDAOImpl.class, "isBlocked()", "** Error occurred checking blocked e-mail address.", e);
			throw new AccountRequestException("An error occurred checking blocked e-mail address.", e);
		}

		return count > 0;
	} // checkAddress
}
