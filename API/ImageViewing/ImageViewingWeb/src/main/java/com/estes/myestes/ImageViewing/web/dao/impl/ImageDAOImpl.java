package com.estes.myestes.ImageViewing.web.dao.impl;

import static com.estes.myestes.ImageViewing.web.dao.iface.BaseDAO.FBPGMS;
import static com.estes.myestes.ImageViewing.web.dao.sql.ImageQuery.GET_IMAGE_SEARCH_RESULTS;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.ImageViewing.exception.AppException;
import com.estes.myestes.ImageViewing.web.dao.iface.ErrorDAO;
import com.estes.myestes.ImageViewing.web.dao.iface.ImageDAO;
import com.estes.myestes.ImageViewing.web.dto.ImageRequest;
import com.estes.myestes.ImageViewing.web.dto.ImageRequestStatus;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;

@Repository("imageDAO")
public class ImageDAOImpl implements ImageDAO {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ErrorDAO errorDAO;
	
	

	@Override
	public Map<String, String> writeImageViewRequest(ImageRequest imageRequest,String sessionId) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
        sproc.withProcedureName("SP_XIG10C525");

        sproc.addDeclaredParameter(new SqlParameter("SEARCH_CRITERIA", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("SEARCH_TYPE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("DOC_TYPE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("RANDOM_NUMBER", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("RQSTORIG", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("WEBID", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("RQSTNBR", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		
        Map<String, Object> inParams = new HashMap<>();
        
        String requestNumber = "";
        
        inParams.put("SEARCH_TYPE", imageRequest.getSearchType());
        inParams.put("DOC_TYPE", imageRequest.getDocumentType());
        inParams.put("RANDOM_NUMBER", sessionId);
        inParams.put("RQSTORIG", "");
        
        inParams.put("WEBID", env.getProperty("imageWebServer"));
        
        Map<String, String> results = new HashMap<>();
        
        for(String searchCriteria : imageRequest.getSearchCriteria()){
        	
        	searchCriteria = searchCriteria.replaceAll(" ", "").replaceAll("-","").trim();
        	
        	if(searchCriteria!=null && !searchCriteria.trim().equals("")  && searchCriteria.trim().length()<=30){
        		
        		inParams.put("SEARCH_CRITERIA", searchCriteria);
        		
        		inParams.put("RQSTNBR", requestNumber);

            	Map<String, Object> outParms = sproc.execute(inParams);
            	
            	if (outParms != null) {
            		String errorCode = (String) outParms.get("ERROR");
            		
            		if (ErrorDAO.isError(errorCode)) {
            			 results.put(searchCriteria,errorDAO.getErrorMessage(errorCode));
            		}
            		requestNumber = (String) outParms.get("RQSTNBR");
            		
            	
        		} else {
        			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "writeImageViewRequest()",
        					"An error occurred processing write Image View  request.");
            		throw new AppException("An error occurred processing write Image View Request.");
        		}
        	}
        }
        
        if(requestNumber.equals("")){
        	throw new AppException("Invalid search term.","searchTerm", imageRequest.getSearchTerm());
        }
        results.put("requestNumber",requestNumber);

        return results;
        
	}
	
	@Override
	public List<ImageResult> getImageSearchResults(String requestNumber, String searchType){
		List<Map<String,Object>> results = jdbcTemplate.queryForList(GET_IMAGE_SEARCH_RESULTS,new Object[]{requestNumber});
		List<ImageResult> imageResults = getImageData(results,requestNumber, searchType);
		return imageResults;
	}
	
	private List<ImageResult> getImageData(List<Map<String,Object>> results, String requestNumber, String searchType){
		List<ImageResult> imageResults = new ArrayList<>();
		for(Map<String,Object> result : results){
			ImageResult imageResult = new ImageResult();
			imageResult.setRequestNumber(requestNumber);
			imageResult.setSearchType(searchType);
			imageResult.setDocType(((String) result.get("docType")).trim());
			imageResult.setKey1(((String) result.get("rpsearch1")).trim());
			imageResult.setKey2(((String) result.get("rpsearch2")).trim());
			imageResult.setKey3(((String) result.get("rpsearch3")).trim());
			imageResult.setKey4(((String) result.get("rpsearch4")).trim());
			imageResult.setKey5(((String) result.get("rpsearch5")).trim());
			imageResult.setSearchData(((String) result.get("searchData")).trim());
		
			String imageFoundFlag = (String) result.get("imageFoundFlag");
			String errorCode = (String) result.get("error");
			
    		if (ErrorDAO.isError(errorCode)) {
				imageResult.setStatus(ImageRequestStatus.ERROR);
				imageResult.setErrorMessage(errorDAO.getErrorMessage(errorCode));
			}else{
				if(imageFoundFlag.trim().equals("N")){
					imageResult.setStatus(ImageRequestStatus.NOT_AVAILABLE);
				}else if(imageFoundFlag.trim().equals("") || imageFoundFlag.trim().equals("W")){
					imageResult.setStatus(ImageRequestStatus.WORKING);
				} else {
					imageResult.setStatus(ImageRequestStatus.AVAILABLE);
				}
			}
			
			
			imageResults.add(imageResult);
		}
		return imageResults;
	}

	@Override
	public void  writeImageFaxRequest(List<ImageResult> imageResults, ImageRequest imageRequest,String sessionId) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
        sproc.withProcedureName("SP_WRITEFAXREQUEST");
        
        sproc.addDeclaredParameter(new SqlParameter("RANDOM_NUMBER", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("SEARCH_CRITERIA", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("SEARCH_TYPE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("DOC_TYPE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("COMPANY", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("ATTENTION", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("CUSTOMER", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("AREA_CODE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("EXCHANGE", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("LAST4", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		
        Map<String, Object> inParams = new HashMap<>();
        
        
        inParams.put("RANDOM_NUMBER", sessionId);
        inParams.put("SEARCH_TYPE", imageRequest.getSearchType());
        inParams.put("DOC_TYPE", imageRequest.getDocumentType());
        inParams.put("COMPANY", imageRequest.getFaxInfo().getCompanyName()==null?"": imageRequest.getFaxInfo().getCompanyName());
        inParams.put("ATTENTION",imageRequest.getFaxInfo().getAttention()==null? "":imageRequest.getFaxInfo().getAttention());
        inParams.put("CUSTOMER", "");
        inParams.put("AREA_CODE", imageRequest.getFaxInfo().getAreaCode());
        inParams.put("EXCHANGE", imageRequest.getFaxInfo().getExchange());
        inParams.put("LAST4", imageRequest.getFaxInfo().getLastFour());

        for(ImageResult imageResult : imageResults){
            inParams.put("SEARCH_CRITERIA",imageResult.getKey1()+"-"+imageResult.getKey2());
        	Map<String, Object> outParms = sproc.execute(inParams);
        	
        	if (outParms != null) {
        		if (ErrorDAO.isError((String) outParms.get("ERROR"))) {
        			imageResult.setStatus(ImageRequestStatus.FAX_ERROR);
        		}else{
        			imageResult.setStatus(ImageRequestStatus.FAX_SENT);
        		}
        		
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "writeImageFaxRequest()",
    					"An error occurred processing write Image Fax Request");
        		throw new AppException("An error occurred processing write Image Fax Request");
    		}
        }
	}


}
