/**
 * @author: Todd Allen
 *
 * Creation date: 12/06/2018
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.ImageDAO;
import com.estes.myestes.invoiceinquiry.dto.ImageRequest;
import com.estes.myestes.invoiceinquiry.dto.Image;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.ImageService;
import com.estes.myestes.invoiceinquiry.utils.InvoiceInquiryConstants;

@Repository ("imageDAO")
public class ImageDAOImpl implements ImageDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public ImageDAOImpl()
	{
	} // Constructor

	private String getFileStatus(String count) throws InvoiceException
	{
		String sql = "SELECT success_ind FROM fbfiles.rag10p100 WHERE count_identifier = ?";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] {count}, String.class);
		}
		catch (EmptyResultDataAccessException ere) {
			return ImageService.WORKING;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "getFileStatus()", "** Error getting image file status.", e);
			throw new InvoiceException("File status could not be retrieved.");
		}
	} // getFileStatus

	@Override
	public Image reprintInvoice(String acct, String session, ImageRequest view) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("raq195f5");
        sproc.addDeclaredParameter(new SqlParameter("account", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("ot", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("pro", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("state", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("porf", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("fax", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("dr", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("bol", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("place", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error1", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error2", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error3", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error4", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error5", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error6", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error7", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("count", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("account", acct);
        inParams.put("ot", view.getPro().substring(0, 3));
        inParams.put("pro", view.getPro().substring(4, 11));
		// Statement is always blank
        inParams.put("state", "");
        // 'P' or 'F' parameter is always 'P'
        inParams.put("porf", P_OR_F);
		// Fax number is always blank
        inParams.put("fax", "");
        inParams.put("dr", view.isShowDr() ? "Y":"N");
        inParams.put("bol", view.isShowBol() ? "Y":"N");
        inParams.put("place", ESTESConfigUtil.getProperty(InvoiceInquiryConstants.DOCS, InvoiceInquiryConstants.MOUNT_LOC));
        inParams.put("random_number", session);
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "0");
        //These are needed for RPG
        inParams.put("error1", " ");
        inParams.put("error2", " ");
        inParams.put("error3", " ");
        inParams.put("error4", " ");
        inParams.put("error5", " ");
        inParams.put("error6", " ");
        inParams.put("error7", " ");
        inParams.put("count", " ");

        try {
            Map<String, Object> outParms = sproc.execute(inParams);
            if (outParms != null) {
    			Image img = new Image();
    			img.setPro(view.getPro());
    			String stat = getFileStatus((String) outParms.get("count"));
    			img.setStatus(stat);
    			if (stat.equals(ImageService.FOUND)) {
        			String server = ESTESConfigUtil.getProperty(InvoiceInquiryConstants.DOCS, InvoiceInquiryConstants.DOCVIEW_LOC);
        			String path = ESTESConfigUtil.getProperty(InvoiceInquiryConstants.DOCS, InvoiceInquiryConstants.FILE_LOC);
                	String fileName = ((String) outParms.get("count")).trim();
        			img.setLocation(server + "/" + path + "/" + fileName + ".pdf");
    			}
   				return img;
    		}
            else {
    			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "reprintInvoice()", "** Error occurred reprinting invoice for PRO " + view.getPro() + ".");
        		throw new InvoiceException("Error reprinting invoice.");
    		}
       }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "reprintInvoice()", "** Error occurred reprinting invoice for PRO " + view.getPro() + ".", e);
    		throw new InvoiceException("Error reprinting invoice.");
        }
	} // reprintInvoice

	@Override
	public String getBillToAccountCode(String proNumber) {
		
		List<String> accountCode = null;
		
		try {
			accountCode = jdbcMyEstes.queryForList(QUERY_BILLTO_ACCOUNT, new Object[]{proNumber.substring(0, 3),proNumber.substring(4, 11)}, String.class);
		}catch(Exception e){
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "getBillToAccountCode()", "** Error occurred retriving account code for PRO "+proNumber+".");
		}
		
		return accountCode.get(0);
	}
}
