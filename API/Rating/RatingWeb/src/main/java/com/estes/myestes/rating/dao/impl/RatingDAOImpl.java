/**
 * @author: Todd Allen
 *
 * Creation date: 02/11/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.ErrorDAO;
import com.estes.myestes.rating.dao.iface.RatingDAO;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.dto.QuoteKeys;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.dto.ServiceLevel;
import com.estes.myestes.rating.exception.RatingErrorsException;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.utils.RatingConstants;
import com.estes.myestes.rating.utils.RatingUtil;

@Repository ("ratingDAO")
public class RatingDAOImpl implements RatingDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;
	
	@Autowired
	private ErrorDAO errorDAO;

	/**
	 * Constructor
	 */
	public RatingDAOImpl()
	{
	} // Constructor

	@Override
	public boolean bookQuote(BookingRequest bookingRequest) throws RatingException
	{
		/**
		 * Update Email Address for that Quote
		 */
		if(bookingRequest.getAddresses()!=null 
				&& !bookingRequest.getAddresses().isEmpty() 
				&& bookingRequest.getAddresses().get(0)!=null){
			String sql = "UPDATE " + DATA_SCHEMA + ".gsc00p100 " +
					"SET gsrcemail = ? WHERE gsrid = ?";
			try{
				jdbcMyEstes.update(sql, new Object[] { bookingRequest.getAddresses().get(0),bookingRequest.getQuoteId() });
			}catch (Exception e) {
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "bookQuote()", "** Error occurring updating email for quote " + bookingRequest.getQuoteId() + ".", e);
			}
		}
		
		String sql = "UPDATE " + DATA_SCHEMA + ".gsc00p110 " +
				"SET gsqbook = 'Y' WHERE gsqid = ?";

		try {
			return jdbcMyEstes.update(sql, new Object[] { bookingRequest.getQuoteId() }) > 0;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "bookQuote()", "** Error occurring booking quote " + bookingRequest.getQuoteId() + ".", e);
			throw new RatingException("An error occurred booking quote.");
		}
	} // bookQuote


	private String generateQuoteID() throws RatingException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("gsc00q295");
        sproc.addDeclaredParameter(new SqlInOutParameter("gscid", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("gscerr", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("gscid", "");
        inParams.put("gscerr", "");

        try {
        	
            Map<String, Object> outParms = sproc.execute(inParams);
           	String masterQuoteId = ((String) outParms.get("gscid"));
           	
           	if(masterQuoteId==null || masterQuoteId.trim().equals("")){
           		
           		List<ServiceResponse> errors = new ArrayList<>();
        
           		String errorMessage = errorDAO.getErrorMessage(RatingConstants.MASTER_QUOTE_FAILED_ERROR);
           		
           		errors.add(new ServiceResponse(RatingConstants.MASTER_QUOTE_FAILED_ERROR, errorMessage));
           		
           		ESTESLogger.log(
        				ESTESLogger.ERROR,
        				getClass(),
        				"generateQuoteID()",
        				"Failed to create master Quote"
        			);
           		
           		throw new RatingErrorsException(HttpStatus.EXPECTATION_FAILED, errors);
           	}
           	return masterQuoteId;
        }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "generateQuoteID()", "** Error generating rate quote ID.", e);
    		throw new RatingException("Error generating rate quote ID.");
        }
	} // generateQuoteID

	@Override
	public List<RateQuote> getNewQuotes(int ref) throws RatingException
	{

		String sql = " SELECT * " +
				"FROM " + DATA_SCHEMA + ".gsc00p100 " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p110 " +
				"ON gsrid = gsqid AND gsrref = gsqref " +
				"WHERE gsrref = ?";

		try {
			return jdbcMyEstes.query(sql, new Object[] { ref }, new QuoteMapper());
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "getNewQuotes()", "** No quote found for ref# " + ref + ".", e);
			throw new RatingException("No quotes found for ref# " + ref + ".");
		}
	} // getNewQuotes

	@Override
	public RateQuote getQuoteData(String id) throws RatingException
	{
		String sql = "SELECT * " +
				"FROM " + DATA_SCHEMA + ".gsc00p100 " +
				"INNER JOIN " + DATA_SCHEMA + ".gsc00p110 ON gsrid = gsqid " +
				"WHERE gsrid = ?";

		try {
			return jdbcMyEstes.queryForObject(sql, new Object[] { id }, new QuoteMapper());
		}
		catch (EmptyResultDataAccessException erde) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "getQuoteData()", "** Quote ID " + id + " not found.");
			return null;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "getQuoteData()", "** An error occurred getting quote details.", e);
    		throw new RatingException("Error getting quote details.");
		}
	} // getQuoteData

	@Override
	public List<ServiceLevel> getServiceLevels() throws RatingException
	{
		String sql = "SELECT gsdsvc, gsdsst " +
				"FROM " + DATA_SCHEMA + ".gsc00p590 " +
				"ORDER BY gsdsvc";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql);
			ArrayList<ServiceLevel> levels = new ArrayList<ServiceLevel>();
			for (Map<String,Object> data : dataList) {
				ServiceLevel lvl = new ServiceLevel();
				lvl.setId(((BigDecimal) data.get("gsdsvc")).intValue());
				lvl.setText((String) data.get("gsdsst"));
				levels.add(lvl);
			}
			return levels;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "getServiceLevels()", "** Error occurred getting service levels.", e);
			throw new RatingException("Service level data found.");
		}
	} // getServiceLevels

	/**
	 * Get Y/N flag from boolean
	 * 
	 * @param tf Boolean value
	 * @return Flag value to be inserted in to address database column
	 */
	private static String getStringFromBool(boolean tf)
	{
		return tf ? "Y":"N";
	} // getStringFromBool

	@Override
	public void loadGMSData(String id) throws RatingException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
		sproc.withProcedureName("gsc00q600");
		sproc.addDeclaredParameter(new SqlParameter("gscid", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("gscid", id);

        try {
        	sproc.execute(inParams);
         }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "loadGMSData()", "** Error on selection of rate quote ID: " + id, e);
    		throw new RatingException("Error selecting rate quote ID: " + id);
        }
	} // loadGMSData

	@Override
	public QuoteKeys persistData(String masterId) throws RatingException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("gsc00q100");
        sproc.addDeclaredParameter(new SqlInOutParameter("gscid", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("gscref", Types.CHAR));
        sproc.addDeclaredParameter(new SqlInOutParameter("gscerr", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("gscid", masterId);
        inParams.put("gscref", "");
        inParams.put("gscerr", "");

        try {
        	ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "persistData(String masterId) ", " CALL fbpgms.gsc00q100('"+masterId+"','','')");
            Map<String, Object> outParms = sproc.execute(inParams);
            return new QuoteKeys(((String) outParms.get("gscerr")).trim(), ((String) outParms.get("gscref")).trim());
        	//throw new Exception();
         }
        catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "processRequest()", "** Error processing rate quote request: " + masterId, e);
    		throw new RatingException("Error processing rate quote request.");
        }
	} // persistData

	private final class QuoteMapper implements RowMapper<RateQuote>
	{
		@Override
		public RateQuote mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			RateQuote row = new RateQuote();
			row.setQuoteID(rs.getString("gsrid"));
			row.setQuoteRefNum(rs.getString("gsrref"));
			row.setQuoteDate(rs.getTimestamp("gsratmst").toLocalDateTime().toLocalDate());
			row.setApp(rs.getString("gsrapp").trim());
			row.setAccountCode(rs.getString("gsract"));
			row.setContactName(rs.getString("gsrcnam"));
			row.setContactPhone(RatingDAO.extractPhone(rs.getString("gsrcphone")));
			row.setContactPhoneExt(RatingDAO.extractPhoneExt(rs.getString("gsrcphone")));
			row.setContactEmail(rs.getString("gsrcemail"));
			row.setRole(rs.getString("gsrrol"));
			row.setTerms(rs.getString("gsrtrm"));
			row.setLane(rs.getInt("gsqlan"));
			row.getOrigin().setCountry(rs.getString("gsrocntry"));
			row.getOrigin().setCity(rs.getString("gsrocity"));
			row.getOrigin().setState(rs.getString("gsrost"));
			row.getOrigin().setZip(rs.getString("gsrozip"));
			row.getDest().setCountry(rs.getString("gsrdcntry"));
			row.getDest().setCity(rs.getString("gsrdcity"));
			row.getDest().setState(rs.getString("gsrdst"));
			row.getDest().setZip(rs.getString("gsrdzip"));
			row.setTransitMessage(rs.getString("gsqtmsg"));
			row.setPickupDate(rs.getDate("gsrpkdt").toLocalDate());
			row.setPickupAvail(rs.getString("gsrpktm"));
			row.setPickupClose(rs.getString("gsrpkcltm"));
			row.getDelivery().setDate(rs.getDate("gsqdat").toLocalDate());
			row.getDelivery().setTime(rs.getString("gsqtim"));
			row.setExpireDate(rs.getTimestamp("gsqexp"));
			row.setExpireTime(rs.getTimestamp("gsqexp"));
			row.getService().setId(rs.getInt("gsqsvc"));
			row.getService().setText(rs.getString("gsqsvt"));
			row.setGuaranteed(RatingUtil.isGuaranteed(rs.getInt("gsqsvc")));
			row.setDeclaredValue(rs.getDouble("gsrval"));
			row.setDeclaredValueWaived(rs.getString("gsrwave").equals("Y"));
			row.setFoodWarehouseId(rs.getInt("gsrfdid"));
			row.setStackable(rs.getString("gsrstk").equals("Y"));
			row.setLinearFeet(rs.getInt("gsrlnft"));
			row.getPricing().setDiscount(rs.getDouble("gsqdisp"));
			row.getPricing().setTotalDiscount(rs.getDouble("gsqdis"));
			row.getPricing().setTotalPrice(rs.getDouble("gsqpric"));
			row.getPricing().setDisplay(rs.getString("gsqdsp"));
			
			/**
			 *  New requirements: Added three new columns to track selected apps (LTL, TC and VTL)
			 */
			
			try{
				row.addLtlToSelectedApps(rs.getString("GSRLTLPK"));
				row.addTcToSelectedApps(rs.getString("GSRTCPK"));
				row.addVtlToSelectedApps(rs.getString("GSRVTLPK"));
			}catch(Exception ex){
				ESTESLogger.log(ESTESLogger.ERROR, getClass(), "","Rate Quote Table (FBFILES.GSC00P100) should have three new columns: GSRLTLPK  CHAR(1) GSRVTLPK  CHAR(1) GSRTCPK   CHAR(1)");
			}
			

			return row;
		}
	} // QuoteMapper

	@Override
	public String stageRateRequest(RatingRequest req) throws RatingException
	{
		String quoteId = generateQuoteID();
		
		
		String sql = "UPDATE " + DATA_SCHEMA + ".gsc00p100 SET " + 
				"gsrran=?, gsract=?, gsrrnam=UPPER(?), gsrcnam=?, gsrcphone=?, gsrcemail=?, " +
				"gsrocntry=UPPER(?), gsrocity=UPPER(?), gsrost=UPPER(?), gsrozip=?, gsrdcntry=UPPER(?), gsrdcity=UPPER(?), " +
				"gsrdst=UPPER(?), gsrdzip=?, gsrrol=?, gsrtrm=?, gsrpct=CAST(? AS FLOAT)/100, gsrpkdt=?, " +
				"gsrpktm=TIME(CAST(? AS CHAR(8))), gsrpkcltm=TIME(CAST(? AS CHAR(8))), gsrval=?, gsrwave=?, gsrstk=?, gsrlnft=?, " +
				"gsrorg='" + ORIGIN + "',gsrfdid=?, gsrapp=?" +
				"WHERE gsrid = ?";

		Object[] values = {req.getSession(), req.getAccountCode(), req.getUser(), req.getContactName(), RatingDAO.constructPhone(req.getContactPhone(), req.getContactPhoneExt()), req.getContactEmail(),
				req.getOrigin().getCountry(), req.getOrigin().getCity(), req.getOrigin().getState(), req.getOrigin().getZip(), req.getDest().getCountry(), req.getDest().getCity(),
				req.getDest().getState(), req.getDest().getZip(), req.getRole(), req.getTerms(), req.getDiscount(), req.getPickupDate()==null? "" : java.sql.Date.valueOf(req.getPickupDate()), 
				req.getPickupAvail(), 	req.getPickupClose(), req.getDeclaredValue(), getStringFromBool(req.isDeclaredValueWaived()), getStringFromBool(req.isStackable()), req.getLinearFeet(), 
				req.getFoodWarehouseId(), RatingUtil.getApp(req.getApps()), quoteId};

		
		/**
		 * New Requirement. Added 3 new columns for tracking app selection from the front-end.
		 */
		
		String sql2 = "UPDATE " + DATA_SCHEMA + ".GSC00P100 SET "
				+ "GSRLTLPK=?, GSRVTLPK=?, GSRTCPK=? "
				+ "WHERE GSRID = ?";
		
		Object[] values2 = {(req.getApps().contains("LTL")? "Y" : "N"), (req.getApps().contains("VTL")? "Y" : "N"), (req.getApps().contains("TC")? "Y" : "N"),quoteId};
		
		
		
		try {
			
			jdbcMyEstes.update(sql, values);
			/**
			 * update selected app list
			 */
			try{
				jdbcMyEstes.update(sql2, values2);
			}catch(Exception ex){
				ESTESLogger.log(ESTESLogger.ERROR, getClass(), "","Rate Quote Table (FBFILES.GSC00P100) should have three new columns: GSRLTLPK  CHAR(1) GSRVTLPK  CHAR(1) GSRTCPK   CHAR(1)");
			}
			
			
			return quoteId;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "stageRateRequest()", "** Error occurred staging rate request data for ID " + quoteId, e);
			throw new RatingException("Error staging request data for ID " + quoteId);
		}
	} // stageRateRequest
}
