/**
 * @author: Todd Allen
 *
 * Creation date: 07/19/2018
 */

package com.estes.myestes.shiptrack.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
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
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.shiptrack.dao.iface.ImageDAO;
import com.estes.myestes.shiptrack.dto.Image;
import com.estes.myestes.shiptrack.dto.ImageRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.utils.AppConstants;

@Repository ("imageDAO")
public class ImageDAOImpl implements ImageDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private String errorCode;

	private static final class ImageMapper implements RowMapper<Image>
	{
		@Override
		public Image mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			Image elem = new Image();
			elem.setPro(rs.getString("OTDIGITS") + rs.getString("PRODIGITS"));
			elem.setType(rs.getString("DOCTYPE"));
			elem.setPath("/docview/" + rs.getString("OUTFILE"));
			//elem.setFound((rs.getString("OUTFILE")!=null && !rs.getString("OUTFILE").trim().equalsIgnoreCase("")));
			elem.setFound((rs.getString("IMGFND")));
			elem.setRetry(rs.getString("IMGFND"));
			return elem;
		}
	} // ImageMapper

	private static final class WRImageMapper implements RowMapper<Image>
	{
		@Override
		public Image mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			Image elem = new Image();
			elem.setPro(rs.getString("OT") + rs.getString("PRO"));
			elem.setFound(true);
			elem.setType("WR");
			elem.setPath(rs.getString("WRLOC"));
			return elem;
		}
	} // WRImageMapper

	private synchronized String generateWRRequestNumber()
	{
		GregorianCalendar gc = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMddhhmmssSS");
		return sdf.format(gc.getTime()).substring(0,14);
	} // generateWRRequestNumber

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.shiptrack.dao.iface.ImageDAO#getImagesForPRO
	 */
	@Override
	public List<Image> getImagesForPRO(ImageRequest imgReq, String type) throws ShipTrackException
	{
		String sql = "SELECT DIGITS(ot) AS otDigits, DIGITS(pro) AS proDigits, imgfnd, doctype, outdir, outfile FROM " + DATA_SCHEMA
				+ ".xig10p001 WHERE ot=? AND pro=? AND doctype=?";
		List<Image> images;

		try {
			Integer ot = Integer.valueOf(imgReq.getPro().substring(0,3));
			Integer proNum = Integer.valueOf(imgReq.getPro().substring(3));
			images = jdbcMyEstes.query(sql,  new Object[] {ot,proNum,type}, new ImageMapper());
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "getImagesForPRO()", "** Error getting " + type + " image info for PRO " + imgReq.getPro() + ".");
			throw new ShipTrackException("Error getting image info for PRO " + imgReq.getPro() + ".");
		}

		if (images.isEmpty()) {
			Image notFound = new Image(imgReq.getPro(), Image.BOL, true);
			images.add(notFound);
		}

		// Set request ID on all images
		for (Image img : images) {
			switch (type)
			{
			case Image.BOL:
				img.setRequestNum(imgReq.getBolRequestNum());
				break;
			case Image.DR:
				img.setRequestNum(imgReq.getDrRequestNum());
			}
		} // for

		return images;
	} // getImagesForPRO

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.shiptrack.dao.iface.ImageDAO#getWRImagesForPRO
	 */
	@Override
	public List<Image> getWRImagesForPRO(ImageRequest imgReq) throws ShipTrackException
	{
		String sql = "SELECT DIGITS(wrot) AS ot, DIGITS(wrpro) AS pro, wrloc FROM " + DATA_SCHEMA
				+ ".wrp210 WHERE wrref=?";

		try {
			return jdbcMyEstes.query(sql,  new Object[] {imgReq.getWrRequestNum()}, new WRImageMapper());
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "getWRImagesForPRO()", "** Error getting WR image info for PRO " + imgReq.getPro() + ".");
			throw new ShipTrackException("Error getting WR image info for PRO " + imgReq.getPro() + ".");
		}
	} // getWRImagesForPRO

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.shiptrack.dao.iface.ImageDAO#hasWRDocuments
	 */
	@Override
	public boolean hasWRDocuments(String pro) throws ShipTrackException
	{
		try {
			String ot = pro.substring(0,3);
			String proNum = pro.substring(3);
			String sql = "SELECT count(*) FROM " + DATA_SCHEMA
					+ ".wrp002 WHERE whnamt-whoamt > 0 AND whot = ? AND whpro = ?";
			Integer cnt =  jdbcMyEstes.queryForObject(sql,  Integer.class, new Object[] {ot,proNum});
			return cnt != null && cnt > 0;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "hasWRDocuments()", "** Error checking WR documents for PRO " + pro + ".");
			throw new ShipTrackException("Error checking WR documents for PRO.");
		}
	} // hasWRDocuments

	public String getErrorCode()
	{
		return this.errorCode.trim();
	}

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.shiptrack.dao.iface.ImageDAO#updateWRDocumentInfo
	 */
	@Override
	public void updateWRDocumentInfo(String reqNum, String user) throws ShipTrackException
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

        try {
            sproc.execute(inParams);
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipTrackDAOImpl.class, "updateWRDocumentInfo()", "** Error updating WR document info for request " + reqNum + ".");
    		throw new ShipTrackException("Update of shipment tracking WR document image failed.");
       }
	} // updateWRDocumentInfo
	
	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.shiptrack.dao.iface.ImageDAO#writeImageRequest
	 */
	@Override
	public String writeImageRequest(ImageRequest imgRequest, String docType) throws ShipTrackException
	{
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
        inParams.put("random_number", imgRequest.getSession());
        inParams.put("search_criteria", imgRequest.getPro());
        inParams.put("search_type", "PRO");
        inParams.put("doctype", docType);
        inParams.put("webserver", ESTESConfigUtil.getProperty(AppConstants.IMAGING, AppConstants.IMAGING_SERVER));
        inParams.put("request#", "");
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
    		errorCode = (String) outParms.get("error");
        	return (String) outParms.get("request#");
		}
        else {
			ESTESLogger.log(ESTESLogger.ERROR, ShipTrackDAOImpl.class, "writeImageRequest()", "** Error writing image request.");
			throw new ShipTrackException("Error writing " + docType + " request for PRO " + imgRequest.getPro() + ".");
		}
	} // writeImageRequest

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.login.shiptrack.dao.iface.ImageDAO#writeWRRequest
	 */
	@Override
	public String writeWRRequest(String pro, String user) throws ShipTrackException
	{
		String requestNo = generateWRRequestNumber();
		String sql = "INSERT INTO " + DATA_SCHEMA + ".wrp210 " + 
				"(wrot, wrpro, wrref, wruser) " + 
				"VALUES (?, ?, ?, ?)";

		try {
			Object[] values = {pro.substring(0,3), pro.substring(3), requestNo, user};
			jdbcMyEstes.update(sql,  values);
			return requestNo;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ImageDAOImpl.class, "writeWRRequest()", "** Error writing WR request for PRO " + pro + ".");
			throw new ShipTrackException("Error writing WR request.");
		}
	} // writeWRRequest
}
