/**
 * @author: Todd Allen
 *
 * Creation date: 01/22/2019
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.customer.Account;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.EmailDAO;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

@Repository ("emailDAO")
public class EmailDAOImpl implements EmailDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public EmailDAOImpl()
	{
	} // Constructor

	/**
	 * Get the statement details.
	 */
	@Override
	public void sendEmail(Account account, String address, String format, String age, String pros) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		if(age.equals("")){
			age = "0";
		}
		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("arg10q003");
        sproc.addDeclaredParameter(new SqlParameter("age", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("account", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("type", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("format", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("submittype", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pros", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("eaddress", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("error", Types.CHAR));
        
        String submittype = age.equals("0") ? "S" : "A";
        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("age", age);
        inParams.put("account", account.getCode());
        inParams.put("type", account.getType());
        inParams.put("format", format);
        // Set type to "A" for all PROs in age break or "S" for selected PROs
        inParams.put("submittype", submittype);
        inParams.put("pros", pros);
        inParams.put("eaddress", address);
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
        inParams.put("error", "");
        ESTESLogger.log(ESTESLogger.DEBUG,getClass(),"sendEmail()","call FBPGMS.ARG10Q003 ( '"+age+"' , '"+account.getCode()+"', '"+account.getType()+"', '"+format+"', '"+submittype+"', '"+pros+"', '"+address+"', '', ''); ");
        try {
            sproc.execute(inParams);
       }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EmailDAOImpl.class, "sendEmail()", "** Error sending aging detail e-mail.", e);
    		throw new InvoiceException("Error sending aging detail e-mail.");
        }
	} // sendEmail
}
