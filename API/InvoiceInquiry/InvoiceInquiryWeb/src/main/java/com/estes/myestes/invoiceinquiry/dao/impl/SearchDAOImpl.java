/**
 * @author: Todd Allen
 *
 * Creation date: 11/30/2018
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.SearchDAO;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchRequest;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchResult;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.SearchService;

@Repository ("searchDAO")
public class SearchDAOImpl implements SearchDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private final class DetailsMapper implements RowMapper<InvoiceSearchResult>
	{
		@Override
		public InvoiceSearchResult mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			InvoiceSearchResult result = new InvoiceSearchResult();
			if (!rs.getString("tmp_3").equals(GOOD_PRO)) {
				result.getError().setErrorCode(rs.getString("tmp_3"));
			}
			result.getResult().setPro(rs.getString("riot") + "-" + rs.getString("ripro"));;
			result.getResult().setPickupDate(rs.getInt("fhpda8"));
			result.getResult().setDeliveryDate(getDeliveryDate(rs.getString("riot"), rs.getString("ripro")));
			result.getResult().setInvoiceDate(rs.getInt("risd8"));
			result.getResult().setBol(rs.getString("fhbl#"));
			result.getResult().setPoNum(rs.getString("fhpo"));
			result.getResult().setStatementNum(Integer.parseInt(rs.getString("risn").trim()));;
			result.getResult().setOpenAmount(rs.getDouble("riopen"));

			return result;
		}
	} // DetailsMapper

	private static final class ValidationMapper implements RowMapper<String>
	{
		@Override
		public String mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			if (!StringUtils.isEmpty(rs.getString("error_").trim())) {

				return rs.getString("item").trim()+":"+rs.getString("error_").trim();
			}
			return null;
		}
	}

	/**
	 * Constructor
	 */
	public SearchDAOImpl()
	{
	} // Constructor

	private int getDeliveryDate(String ot, String proNum)
	{
		String sql = "SELECT fhdda8 " +
				"FROM " + DATA_SCHEMA + ".frp001 " +
				"WHERE fhot = ? AND fhpro = ?";

		try {
			return jdbcMyEstes.queryForObject(sql,  Integer.class, new Object[] { Integer.parseInt(ot), Integer.parseInt(proNum) });
		}
		catch (Exception e) {
			return 0;
		}
	} // getDeliveryDate

	@SuppressWarnings("unchecked")
	@Override
	public List<InvoiceSearchResult> getInvoiceInfo(String session, InvoiceSearchRequest search,Set<String> searchTerms) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("arg10q004");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("search_crit", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("search_type", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("random_num", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("start", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("num_rows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("total_rows", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("sort", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("gen_error", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("search_crit_e", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("search_type_e", Types.CHAR));

        
        Map<String, Object> inParams = new LinkedHashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
        inParams.put("search_crit", SearchService.createSearchString(searchTerms));
        inParams.put("search_type", search.getSearchType());
        inParams.put("random_num", session);
        inParams.put("start", START_SHOW);
        inParams.put("num_rows", ROWS_TO_SHOW);
        inParams.put("sort", "");
        sproc.returningResultSet("#result-set-1", new DetailsMapper());
        
        
        ESTESLogger.log(getClass(),"call FBPGMS.ARG10Q004('', '"+SearchService.createSearchString(searchTerms)+"', '"+search.getSearchType()+"', '"+session+"', '"+START_SHOW+"', '"+ROWS_TO_SHOW+"', '', '', '', '', '')}");
        
        
        try {
        	
            Map<String, Object> outParms = sproc.execute(inParams);
            int rows = 0;
            if (outParms != null) {
            	String totalRows = (String)outParms.get("total_rows");
            	if( totalRows.length() > 0) {
            		if(totalRows.trim().length() > 0) {
            			rows = Integer.parseInt((String) outParms.get("total_rows"));
            		}
            	}
        		// Check for error on SPROC call
    			if (rows > 0) {
                	return (List<InvoiceSearchResult>) outParms.get("#result-set-1");
    			}
    			return null;
    		}
            else {
    			ESTESLogger.log(ESTESLogger.ERROR, SearchDAOImpl.class, "getInvoiceInfo()", "** Error occurred searching invoices.");
        		throw new InvoiceException("Error searching invoices.");
    		}
       }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, SearchDAOImpl.class, "getInvoiceInfo()", "** Error occurred searching invoices.", e);
    		throw new InvoiceException("Error searching invoices.");
        }
	} // getInvoiceInfo

	@Override
	public Map<String, String> validateStatements(String session, String criteria) throws InvoiceException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		
		
		
		
		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("arg10q005");
        sproc.addDeclaredParameter(new SqlParameter("delay", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("search", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("random", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));

        
        Map<String, String> results = new HashMap<>();
        
        Map<String, Object> inParams = new HashMap<String, Object>();
		// Set delay parameter value to "1" for testing
        inParams.put("delay", "");
        inParams.put("search", criteria);
        inParams.put("random", session);
        sproc.returningResultSet("#result-set-1", new ValidationMapper());

        Map<String, Object> outParms = sproc.execute(inParams);

        
        if (outParms != null) {
        	
    		String errorCode = ((String) outParms.get("error")).trim();
    		@SuppressWarnings("unchecked")
			List<String> errors = (List<String>) outParms.get("#result-set-1");
    		if(errors!=null && !errors.isEmpty()){
    			errors.forEach((error)->{
        			if(error!=null){
        				String[] errs = error.split(":");
        				results.put(errs[0], errs[1]);
        			}
        		});
    		}
    		
//    		
//    		if(errorCode!=null && !errorCode.equals("")){
//    			results.put(null, errorCode);
//    		}
//    		
		}
        else {
    		throw new InvoiceException("Error validating statement search data.");
		}
        return results;
	} 
}
