/**
 * @author: Todd Allen
 *
 * Creation date: 11/26/2018
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.invoiceinquiry.dao.iface.StatementDAO;
import com.estes.myestes.invoiceinquiry.dto.StatementDetail;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.StatementService;

@Service("stmtService")
@Scope("prototype")
public class StatementServiceImpl implements StatementService
{
	@Autowired
	private StatementDAO stmtDAO;

	@Override
	public List<StatementDetail> getStatementDetails(String stmt, String sortby) throws InvoiceException
	{
		return stmtDAO.getStatementDetails(stmt, sortby);
	} // getStatementDetails
}
