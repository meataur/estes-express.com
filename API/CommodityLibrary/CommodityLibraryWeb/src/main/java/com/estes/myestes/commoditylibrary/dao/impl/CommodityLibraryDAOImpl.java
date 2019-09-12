/**
 * @author: Lakshman K
 *
 * Creation date: 10/29/2018
 */

package com.estes.myestes.commoditylibrary.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.commoditylibrary.dao.iface.CommodityLibraryDAO;
import com.estes.myestes.commoditylibrary.dto.Commodity;
import com.estes.myestes.commoditylibrary.exception.CommodityLibraryException;
import com.estes.myestes.commoditylibrary.utils.CommodityLibraryConstant;
import com.estes.myestes.commoditylibrary.utils.DB2Timestamp;

@Repository ("commodityLibraryDAO")
public class CommodityLibraryDAOImpl implements CommodityLibraryDAO, CommodityLibraryConstant
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;
	private DB2Timestamp ts = new DB2Timestamp();

	private static final class CommodityMapper implements RowMapper<Commodity>
	{
		@Override
		public Commodity mapRow(ResultSet rs, int rowNm) throws SQLException {
			Commodity elem = new Commodity();
			elem.setHazmat(rs.getString("HAZ_MAT"));
			elem.setId(rs.getString("COMMODITY_ID").trim());
			elem.setGoodsQuantity(rs.getInt("GOODS_QUANTITY"));
			elem.setGoodsType(rs.getString("GOODS_TYPE").trim());
			elem.setNmfc(rs.getString("NMFC") !=null ? rs.getString("NMFC").trim(): null);
			elem.setNmfcSub(rs.getString("NMFC_SUB") !=null ? rs.getString("NMFC_SUB").trim() : null);
			elem.setWeight(rs.getInt("WEIGHT"));
			elem.setShipClass(rs.getString("CLASS"));
			elem.setDescription(rs.getString("DESCRIPTION") !=null ? rs.getString("DESCRIPTION").trim() : null);
			elem.setDeclaredValue(rs.getDouble("DECLARED_VALUE"));
			return elem;
		}	
	} // CommodityMapper
	
	@Override
	public Commodity getCommodityById(String commodityId, String user) throws CommodityLibraryException {
		Commodity commodity = null;
		String sql = "SELECT * FROM " + DATA_SCHEMA + "." + COMMODITIES + " " +
				" WHERE user_name = ? AND " +
				" comm_id = ?";
		try {
		List<Commodity> listQuery =  jdbcMyEstes.query(sql,  new Object[] {user, commodityId}, new CommodityMapper());
			if (listQuery != null && listQuery.size() > 0) {
				commodity = listQuery.get(0);			
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "getCommodityById()", "** Error retrieving the commodity.", e);
			throw new CommodityLibraryException("Commodity for the given commodity id " + commodityId + " could not be retrieved.");
		}
		return commodity;
	}  // getCommodityById
	
	@Override
	public List<Commodity> getCommodities(String userName) throws CommodityLibraryException
	{
		List<Commodity> commodityList = null;
		String sql = "SELECT * FROM " + DATA_SCHEMA + "." + COMMODITIES + " WHERE user_name = ?  ORDER BY comm_id";
		try {
			List<Commodity> listQuery =  jdbcMyEstes.query(sql,  new Object[] {userName}, new CommodityMapper());
			if (listQuery != null && listQuery.size() > 0) {
				commodityList =  listQuery;
			}
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "getCommodities()", "** Error retrieving the commodities.", e);
			throw new CommodityLibraryException("Online reports for the user " + userName + " could not be retrieved.");
		}
		return commodityList;
	} // getCommodities

	@Override
	public List<String> getCommodityErrorCodes(String user, String timestamp) throws CommodityLibraryException {
		String sql = "SELECT * FROM " + DATA_SCHEMA + "." + TEMP_COMMODITIES + " WHERE user_name = ? AND  time_stamp = ?";
		try {
			Map<String, Object> resultMap =  jdbcMyEstes.queryForMap(sql, new Object[] {user, timestamp});
			return getErrorCodes(resultMap);
		}
		catch(Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "getCommodityByUserTimestamp()", "** Error retrieving the commodity.", e);
			throw new CommodityLibraryException("Commodity for the given user " + user + " and timestamp " + timestamp + " could not be retrieved.");
		}
	}  //  getCommodityErrorCodes
	
	private List<String> getErrorCodes(Map<String, Object> resultMap) {
		List<String> colErrCodes = new ArrayList<String>();
		for(Entry result: resultMap.entrySet()) {
			switch( result.getKey().toString()) {
			case "COMMODITY_ID_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "SHIP_WEIGHT_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "SHIP_CLASS_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "SHIP_NMFC_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "NMFC_SUB_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "GOODS_QTY_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "GOODS_TYPE_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "HAZ_MAT_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "DCL_VALUE_ERROR":
				colErrCodes.add(result.getValue().toString());
				break;
			case "DESCRIPTION_ERROR":
				colErrCodes.add(result.getValue().toString());
			}
		}
		return colErrCodes;
	}
	
	public boolean addCommodityTempTable(Commodity commodity, String user, String timestamp) throws CommodityLibraryException
	{
		String sql = "INSERT INTO " + DATA_SCHEMA + "." + TEMP_COMMODITIES + " " +
				"(user_name, time_stamp, called_by, comm_id, hazmat, gd_qty, gd_type, desc, weight, nmfc, nmfc_sub, class) " +
				"VALUES" +
				"(?, ?, '" + CALLER + "', ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		Object[] values = {user , timestamp, commodity.getId().trim(), commodity.getHazmat(), 
				commodity.getGoodsQuantity(), commodity.getGoodsType(), commodity.getDescription(), commodity.getWeight(),
				commodity.getNmfc(), commodity.getNmfcSub(), commodity.getShipClass()};
		try {
			return  jdbcMyEstes.update(sql,  values) > 0 ? true : false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "addTempCommodity()", "** Error inserting commodity for user " + user + ". ", e);
    		throw new CommodityLibraryException("Error adding commodity .");
		}
	} // addTempCommodity
	
	/**
	 * Validate commodity info.
	 * 
	 * @param dup Check for duplicate commodity ID (Y/N)
	 */
	public boolean editCommodity(String dupCheck, String user, String timestamp) throws CommodityLibraryException {
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("ebg10q122");
        sproc.addDeclaredParameter(new SqlParameter("DELAY", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("TSTAMP", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("DUPCHECK", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("DELAY", "0");
        inParams.put("USER_NAME", user);
        inParams.put("TSTAMP", timestamp);
        inParams.put("DUPCHECK", dupCheck);
 
        try {
        	Map<String, Object> outParms = sproc.execute(inParams);
	        if (outParms != null) {
            	return !isCallError((String) outParms.get("ERROR"));
			} else {
				ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "editCommodity()", "An error occurred validating the commodity for the user " + user);
				return false;
			}
	    }
	    catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "editCommodity()", "** An error occurred validating the commodity.", e);
			throw new CommodityLibraryException("Error occurred validating the commodity.");
	    }		
	}
	
	/**
	 * Save the commodity details to the original table
	 * @param commodityId
	 * @return success/failure
	 * @throws CommodityLibraryException
	 */
	public boolean finalizeCommodity(String commodityId, String user, String timestamp) throws CommodityLibraryException {
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("ebg10q142");
        sproc.addDeclaredParameter(new SqlParameter("DELAY", Types.CHAR));
        sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("USER_NAME", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("PRODUCT_ID", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("TSTAMP", Types.CHAR));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("DELAY", "0");
        inParams.put("USER_NAME", user);
        inParams.put("PRODUCT_ID", commodityId);
        inParams.put("TSTAMP", timestamp);     
 
        try {
        	Map<String, Object> outParms = sproc.execute(inParams);
	        if (outParms != null) {
            	return !isCallError((String) outParms.get("ERROR"));
			} else {
				ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "finalizeCommodity()", "An error occurred adding the commodity for the user " + user);
				return false;
			}
	    }
	    catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "finalizeCommodity()", "** An error occurred adding the commodity.", e);
			throw new CommodityLibraryException("Error occurred adding the commodity.");
	    }		
	}
	
	@Override
	public boolean deleteCommodity(String user, String commodityId) throws CommodityLibraryException
	{
		String sql = "DELETE FROM " + DATA_SCHEMA + "." + COMMODITIES + " WHERE user_name = ? AND comm_id = ?";

		try {
			return  jdbcMyEstes.update(sql,  new Object[] {user, commodityId}) > 0 ? true : false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CommodityLibraryDAOImpl.class, "deleteCommodity()", "** Error deleting commodity. ", e);
    		throw new CommodityLibraryException("Error deleting commodity.");
		}
	} // deleteCommodity


	@Override
	public boolean saveCommodity(Commodity commodity, String user, String timestamp) throws CommodityLibraryException {
		boolean success = false;
		if(addCommodityTempTable(commodity, user, timestamp)) {
			if(editCommodity("N", user, timestamp)) {
				success = finalizeCommodity(commodity.getId().trim(), user, timestamp);
			}
		}
		return success;
	}
	
	/**
	 * Check return code from SPROC call.
	 * 
	 * @param  parmValue Parameter value
	 * @return Indicator of error on SPROC call
	 */
	public boolean isCallError(String parmValue)
	{
		try {
			return !parmValue.equalsIgnoreCase("0000000");
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "isCallError()", e.getMessage(), e);
			return true;
		}
	} // isCallError

	/**
	 * Check error code for column.
	 * 
	 * @param val Column value
	 * @return Indicator of error
	 */
	public boolean isColumnError(String val)
	{
		try {
			return !val.equalsIgnoreCase("0000000");
		} catch (Exception e) {
			return true;
		}
	} // isColumnError
}
