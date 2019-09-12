package com.estes.myestes.shiptrack.dao.impl;

import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.shiptrack.dao.iface.ErrorDAO;
import com.estes.myestes.shiptrack.dao.iface.ShipmentImageDAO;
import com.estes.myestes.shiptrack.dto.ShipmentImage;
import com.estes.myestes.shiptrack.dto.ShipmentImageRequest;
import com.estes.myestes.shiptrack.exception.AppException;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.utils.AppConstants;

@Repository ("shipmentImageDAO")
public class ShipmentImageDAOImpl implements ShipmentImageDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;
	
	@Autowired
	private ErrorDAO errorDAO;
	
	private synchronized String generateWRRequestNumber()
	{
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmssSS");
		return sdf.format(gc.getTime()).substring(0,14);
	}
	
	private RowMapper<ShipmentImage> getShipmentRowMapper(String requestNumber){
		
		RowMapper<ShipmentImage> shipmentImageRowMapper = (rs, rowNum) -> {

			ShipmentImage shipmentImage = new ShipmentImage();
			shipmentImage.setRequestNumber(requestNumber);
			shipmentImage.setProNumber(rs.getString("OTDIGITS") + rs.getString("PRODIGITS"));
			shipmentImage.setImagePath("/docview/" + rs.getString("OUTFILE").trim());
			shipmentImage.setFound((rs.getString("IMGFND")).equalsIgnoreCase("Y"));
			shipmentImage.setRetry(! rs.getString("IMGFND").equalsIgnoreCase("Y"));
			return shipmentImage;
		};
		return shipmentImageRowMapper;
	}
	
	private RowMapper<ShipmentImage> getWeightAndResearchImageRowMapper(String requestNumber){
		
		RowMapper<ShipmentImage> shipmentImageRowMapper = (rs, rowNum) -> {

			ShipmentImage shipmentImage = new ShipmentImage();
			shipmentImage.setRequestNumber(requestNumber);
			shipmentImage.setProNumber(rs.getString("OT") + rs.getString("PRO"));
			shipmentImage.setImagePath(rs.getString("WRLOC").trim());
			shipmentImage.setFound(true);
			shipmentImage.setRetry(false);
			return shipmentImage;
		};
		return shipmentImageRowMapper;
	}
	
	/** 
	 * 
	 * Get BOL/DR Images
	 * @param imgReq - ShipmentImageRequest
	 * @param type - BOL/DR
	 * @return List<ShipmentImage>
	 * @throws ShipTrackException
	 */
	@Override
	public List<ShipmentImage> getImagesForPRO(ShipmentImageRequest imgReq, String type)
	{
		String sql = "SELECT DIGITS(ot) AS otDigits, DIGITS(pro) AS proDigits, imgfnd, doctype, outdir, outfile FROM " + DATA_SCHEMA
				+ ".xig10p001 WHERE ot=? AND pro=? AND doctype=?";
		
		List<ShipmentImage> images = new ArrayList<>();
		
		int ot = imgReq.getOt();
		int pro = imgReq.getPro();
		
		images = jdbcMyEstes.query(sql,  new Object[] {ot,pro,type}, getShipmentRowMapper(imgReq.getRequestNumber()));
		
		if (images.isEmpty()) {
			ShipmentImage notFound = new ShipmentImage(imgReq.getRequestNumber(),imgReq.getProNumber());
			notFound.setRetry(true);
			images.add(notFound);
		}
		
		return images;
	}
	
	@Override
	public List<ShipmentImage> getWRImagesForPRO(ShipmentImageRequest imgReq)
	{
		List<ShipmentImage> images = new ArrayList<>();

		String sql = "SELECT DIGITS(wrot) AS ot, DIGITS(wrpro) AS pro, wrloc FROM " + DATA_SCHEMA
				+ ".wrp210 WHERE wrref=?";
		images =  jdbcMyEstes.query(sql,  new Object[] {imgReq.getRequestNumber()}, getWeightAndResearchImageRowMapper(imgReq.getRequestNumber()));
		
		if (images.isEmpty()) {
			ShipmentImage notFound = new ShipmentImage(imgReq.getRequestNumber(),imgReq.getProNumber());
			notFound.setRetry(true);
			images.add(notFound);
		}
		return images;
	}

	@Override
	public boolean hasWRDocuments(String pro)
	{
		String ot = pro.substring(0,3);
		String proNum = pro.substring(3);
		String sql = "SELECT count(*) FROM " + DATA_SCHEMA
				+ ".wrp002 WHERE whnamt-whoamt > 0 AND whot = ? AND whpro = ?";
		Integer cnt =  jdbcMyEstes.queryForObject(sql,  Integer.class, new Object[] {ot,proNum});
		return cnt != null && cnt > 0;
	}
	
	@Override
	public void updateWRDocumentInfo(String reqNum, String user)
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("wrq2101");
        sproc.addDeclaredParameter(new SqlParameter("ref", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("user", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("email", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("check", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("ref", reqNum);
        inParams.put("user", user);
        inParams.put("email", "");
        inParams.put("check", "");

        sproc.execute(inParams);
        
	} 
	
	@Override
	public String writeImageRequest(ShipmentImageRequest imgRequest, 
			String sessionId, 
			String docType) {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_write_image_request");
        sproc.addDeclaredParameter(new SqlParameter("random_number", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("search_criteria", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("search_type", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("doctype", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("webserver", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("error", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("request#", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("random_number", sessionId);
        inParams.put("search_criteria", imgRequest.getProNumber());
        inParams.put("search_type", "PRO");
        inParams.put("doctype", docType);
        inParams.put("webserver", ESTESConfigUtil.getProperty(AppConstants.IMAGING, AppConstants.IMAGING_SERVER));
        inParams.put("request#", "");
        Map<String, Object> outParms = sproc.execute(inParams);

    	String errorCode = (String) outParms.get("error");
    	    
	    if(ErrorDAO.isError(errorCode)){
	    	throw new AppException(errorCode, errorDAO.getErrorMessage(errorCode));
	    }
	    
	    return ((String) outParms.get("request#")).trim();
	}
	
	@Override
	public String writeWRRequest(String proNumber, String user)
	{
		String requestNo = generateWRRequestNumber();
		String sql = "INSERT INTO " + DATA_SCHEMA + ".wrp210 " + 
				"(wrot, wrpro, wrref, wruser) " + 
				"VALUES (?, ?, ?, ?)";

		Object[] values = {proNumber.substring(0,3), proNumber.substring(3), requestNo, user};
		jdbcMyEstes.update(sql,  values);
		return requestNo;
		
	}
}
