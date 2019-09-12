package com.estes.myestes.ImageViewing.web.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.ImageViewing.exception.AppException;
import com.estes.myestes.ImageViewing.web.dao.iface.ErrorDAO;
import com.estes.myestes.ImageViewing.web.dao.iface.ImageViewingDAO;
import com.estes.myestes.ImageViewing.web.dto.DocumentType;
import com.estes.myestes.ImageViewing.web.dto.Image;
import com.estes.myestes.ImageViewing.web.dto.ImageFax;
import com.estes.myestes.ImageViewing.web.dto.ImageRequestStatus;
import com.estes.myestes.ImageViewing.web.dto.ImageResult;
import com.estes.myestes.ImageViewing.web.dto.ImageSearch;
import com.estes.myestes.ImageViewing.web.dto.ImageStatusResponse;

import static com.estes.myestes.ImageViewing.web.dao.sql.ImageQuery.*;
import static com.estes.myestes.ImageViewing.web.dao.iface.BaseDAO.*;

@Repository("imageViewingDAO")
public class ImageViewingDAOImpl implements ImageViewingDAO {
	
	@Autowired
	private Environment env;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ErrorDAO errorDAO;
	
	@Override
	public List<DocumentType> getDocumentTypes(String username, String accountNumber) {

		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(FBPGMS);
        sproc.withProcedureName("SP_GETDOCTYPES");

        sproc.addDeclaredParameter(new SqlParameter("USER", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("ACCOUNT", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
        sproc.addDeclaredParameter(new SqlReturnResultSet("DATA",new ResultSetExtractor<List<DocumentType>>() {

			@Override
			public List<DocumentType> extractData(ResultSet rs) throws SQLException, DataAccessException {
				List<DocumentType> docTypeList=new ArrayList<>();  
		        while(rs.next()){  
		        	DocumentType docType=new DocumentType();  
		        	docType.setName(rs.getString("DOCTYP").trim());
		        	docType.setDescription(rs.getString("DOCDES").trim());
		        	docType.setFaxable(rs.getString("DOCFAXYN").trim().equals("Y"));
		        	docTypeList.add(docType);  
		        }  
		        return docTypeList;  
			}
        	
		}));

        Map<String, Object> inParams = new HashMap<>();
        inParams.put("USER", username);
        inParams.put("ACCOUNT", accountNumber);
        
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
    		if (!ErrorDAO.isError((String) outParms.get("ERROR"))) {
    			@SuppressWarnings("unchecked")
				List<DocumentType> list = (List<DocumentType>) outParms.get("DATA");
				return list;
    		}
    		throw new AppException(errorDAO.getErrorMessage(((String) outParms.get("ERROR")).trim()));
    	
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "getDocumentTypes()",
					"An error occurred processing get Document Types request.");
    		throw new AppException("An error occurred processing get Document Types request.");
		}
	}

	@Override
	public Map<String, String> writeImageViewRequest(ImageSearch imageSearch,String sessionId) {
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
        
        inParams.put("SEARCH_TYPE", imageSearch.getSearchType());
        inParams.put("DOC_TYPE", imageSearch.getDocumentType());
        inParams.put("RANDOM_NUMBER", sessionId);
        inParams.put("RQSTORIG", "");
        
        inParams.put("WEBID", env.getProperty("imageWebServer"));
        
        Map<String, String> results = new HashMap<>();
        
        for(String searchCriteria : imageSearch.getSearchCriteria()){
        	
        	searchCriteria = searchCriteria.replaceAll(" ", "").replaceAll("-","").trim();
        	
        	if(!searchCriteria.equals("")){
        		
        		inParams.put("SEARCH_CRITERIA", searchCriteria);
        		
        		inParams.put("RQSTNBR", requestNumber);
        		
        	}
        	
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
        results.put("requestNumber",requestNumber);

        return results;
        
	}
	
	@Override
	public List<ImageResult> getImageSearchResults(String requestNumber, String searchType){
		List<Map<String,Object>> results = jdbcTemplate.queryForList(GET_IMAGE_SEARCH_RESULTS,new Object[]{requestNumber});
		List<ImageResult> imageResults = getImageData(results,requestNumber,searchType);
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
	public List<String>  writeImageFaxRequest(ImageFax imageFax,String sessionId) {
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
        inParams.put("SEARCH_TYPE", imageFax.getSearchType());
        inParams.put("DOC_TYPE", imageFax.getDocumentType());
        inParams.put("COMPANY", imageFax.getFaxInfo().getCompanyName()==null?"": imageFax.getFaxInfo().getCompanyName());
        inParams.put("ATTENTION",imageFax.getFaxInfo().getAttention()==null? "":imageFax.getFaxInfo().getAttention());
        inParams.put("CUSTOMER", "");
        inParams.put("AREA_CODE", imageFax.getFaxInfo().getAreaCode());
        inParams.put("EXCHANGE", imageFax.getFaxInfo().getExchange());
        inParams.put("LAST4", imageFax.getFaxInfo().getLastFour());
        
        List<String> results = new ArrayList<>();
        
        for(String proNumber : imageFax.getProNumbers()){
            inParams.put("SEARCH_CRITERIA",proNumber);
        	Map<String, Object> outParms = sproc.execute(inParams);
        	
        	if (outParms != null) {
        		
        		if (! ErrorDAO.isError((String) outParms.get("ERROR"))) {
        			results.add(proNumber);
        		}
        		
    		} else {
    			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "writeImageFaxRequest()",
    					"An error occurred processing write Image Fax Request");
    		}
        }
		return results;
	}

	@Override
	public ImageStatusResponse getImageStatus(String requestNumber,String searchData,String docType) {
		
		List<Map<String, Object>> results =  jdbcTemplate.queryForList(GET_IMAGE_STATUS,new Object[]{requestNumber,searchData,docType});
		
		ImageStatusResponse imageStatusResponse = new ImageStatusResponse(requestNumber,searchData,docType,null);
		if (results.size() > 0) {
			
			Map<String, Object> result = results.get(0);
			
    		if (ErrorDAO.isError((String) result.get("ERROR"))) {
    			imageStatusResponse.setStatus(ImageRequestStatus.NOT_AVAILABLE);
    		
    		}else{
    			
    			String imageFoundFlag = ((String) result.get("IMAGEFOUNDFLAG"));
    			
    			if(imageFoundFlag==null || imageFoundFlag.trim().equals("N")){
    				imageStatusResponse.setStatus( ImageRequestStatus.NOT_AVAILABLE);
    			
    			}else if(imageFoundFlag.trim().equals("") || imageFoundFlag.trim().equals("W")){
    				imageStatusResponse.setStatus( ImageRequestStatus.WORKING);
    			}else {
    				
    				imageStatusResponse.setStatus( ImageRequestStatus.AVAILABLE);
    			}
    		}
    		
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "getImageStatus()",
					"An error occurred processing getImageStatus()");
    		throw new AppException(HttpStatus.NOT_FOUND,"Image Not Found");
		}
		
		return imageStatusResponse;
		
	}

	@Override
	public List<Image> getImagesDetails(String key1, String key2, String key3, String key4, String key5, String docType) {
		
		List<Image> results = jdbcTemplate.query(
				GET_IMAGE_DETAILS,
				new Object[]{key1,key2,key3,key4,key5,docType},
				(resultSet, rowNum)->{
					
					Image image            = new Image();
					
					String documentId      = resultSet.getString("DOCUMENTID").trim();
					String outputDirectory = resultSet.getString("OUPUTDIRECTORY").trim();
					String outputFileName  = resultSet.getString("OUTPUTFILENAME").trim();
					String nasDirectAccess = resultSet.getString("NASDIRECTACCESS").trim();
					
					image.setDocumentId(documentId);
					image.setOutputDirectory(outputDirectory);
					image.setOutputFileName(outputFileName);
					image.setNasDirectAccess(nasDirectAccess);
					image.setImageLocation("");
					
					/**
					 * Retrieve Image Location
					 */
					
					if(nasDirectAccess.equals("Y")) {
						if(outputDirectory!="")
							image.setImageLocation(env.getProperty("serverURL")+"/"+outputDirectory);
						
					}else{
						if(outputFileName!="")
							image.setImageLocation(env.getProperty("serverURL")+"/"+env.getProperty("imageFileLocation")+"/"+outputFileName);
					}
					return image;
				});
		
		
		return results;
			
	}



}
