package com.estes.myestes.recentshipments.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.recentshipments.dao.iface.ShipmentDAO;
import com.estes.myestes.recentshipments.dto.ShipmentDTO;
import com.estes.myestes.recentshipments.exception.RecentShipmentsException;
import com.estes.myestes.recentshipments.util.RecentShipmentsConstant;

import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import org.springframework.stereotype.Repository;

@Repository ("shipmentDAO")
public class ShipmentDAOImpl implements ShipmentDAO {
	
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public List<ShipmentDTO> getRecentShipments(String username, String account, String party)
		throws RecentShipmentsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		Mapper mapper = new Mapper();
		//Calculate today's date and today's date for two weeks ago.
		Calendar calendar = GregorianCalendar.getInstance();
		
		Date today = calendar.getTime();
		calendar.add(Calendar.DAY_OF_MONTH, (RecentShipmentsConstant.PREVIOUS_DAYS));
		Date todayTwoWeeksAgo = calendar.getTime();
		
		//Format the dates for the stored procedure call.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String START_DATE = dateFormat.format(todayTwoWeeksAgo);
		String END_DATE = dateFormat.format(today);

		// set up stored procedure call
		sproc.withSchemaName(DATA_SCHEMA);
		sproc.withProcedureName(SP_RECENTSHIPMENTS);
		sproc.addDeclaredParameter(new SqlParameter("ACCOUNT_CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PARTY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("START_DATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("END_DATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("OT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PRO", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NUM_RECORDS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USERNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("TOTAL_RECS", Types.DECIMAL));
		sproc.addDeclaredParameter(new SqlReturnResultSet("RESULT_SET", mapper));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("ACCOUNT_CODE", account);
		inParams.put("PARTY", party);
		inParams.put("START_DATE", START_DATE);
		inParams.put("END_DATE", END_DATE);
		inParams.put("OT", "");
		inParams.put("PRO", "");
		inParams.put("NUM_RECORDS", RecentShipmentsConstant.RECENT_SHIPMENT_RECORDS);
		inParams.put("USERNAME", username);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);
	        if (outParms != null) {
	    		List<ShipmentDTO> shipments = (List<ShipmentDTO>) outParms.get("RESULT_SET");
	    		return shipments;
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "getRecentShipments()", "An error occurred getting recent shipments");
				throw new RecentShipmentsException("Failed to get recent shipments");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "getRecentShipments()", "An error occurred getting recent shipments");
			throw new RecentShipmentsException("Failed to get recent shipments");
		}
	}
	
	private class Mapper implements RowMapper<ShipmentDTO> {

		@Override
		public ShipmentDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			ShipmentDTO account = new ShipmentDTO();
			account.setOt(String.format("%03d", Integer.valueOf(rs.getString(1))));
			account.setPro(String.format("%07d", Integer.valueOf(rs.getString(2))));
			account.setPickupDate(rs.getString(3));
			account.setDeliveryDate(rs.getString(4));
			if(account.getDeliveryDate().equals("0")) {
				account.setDeliveryDate("");
			}
			account.setBol(rs.getString(5).trim());
			account.setConsigneeCity(rs.getString(10).trim());
			account.setConsigneeState(rs.getString(11));
			account.setStatus(rs.getString(12).trim());
			return account;
		}
		
	}

	@Override
	public String getDefaultParty(String username) throws RecentShipmentsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName(DATA_SCHEMA);
		sproc.withProcedureName(SP_GETDEFAULTPARTY);
		sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("VALUE", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("USRNAME", username);
		inParams.put("CODE", "RECENT");

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String value = (String) outParms.get("VALUE");
	    		return value;
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "getDefaultParty()", "An error occurred getting default party");
				throw new RecentShipmentsException("Failed to get the default party");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "getDefaultParty()", "An error occurred getting default party");
			throw new RecentShipmentsException("Failed to get the default party");
		}
	}

	@Override
	public Boolean writeDefaultParty(String username, String party) throws RecentShipmentsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName(DATA_SCHEMA);
		sproc.withProcedureName(SP_WRITEDEFAULTPARTY);
		sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("VALUE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("RCDERROR", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("USRNAME", username);
		inParams.put("CODE", "RECENT");
		inParams.put("VALUE", party);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String value = (String) outParms.get("RCDERROR");
	    		return "A".equals(value);
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "writeDefaultParty()", "An error occurred setting the default party");
				throw new RecentShipmentsException("Failed to set the default party");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "writeDefaultParty()", "An error occurred setting the default party", e);
			throw new RecentShipmentsException("Failed to set the default party");
		}
	}
	
	@Override
	public Boolean updateDefaultParty(String username, String party) throws RecentShipmentsException {
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);
		sproc.withSchemaName(DATA_SCHEMA);
		sproc.withProcedureName(SP_UPDATEDEFAULTPARTY);
		sproc.addDeclaredParameter(new SqlParameter("USRNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CODE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("VALUE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("RCDERROR", Types.CHAR));

		Map<String, Object> inParams = new HashMap<String, Object>();
		inParams.put("USRNAME", username);
		inParams.put("CODE", "RECENT");
		inParams.put("VALUE", party);

		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		String value = (String) outParms.get("RCDERROR");
	    		return "U".equals(value);
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "writeDefaultParty()", "An error occurred updating the default party");
				throw new RecentShipmentsException("Failed to update the default party");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentDAOImpl.class, "writeDefaultParty()", "An error occurred updating the default party", e);
			throw new RecentShipmentsException("Failed to update the default party");
		}
	}
}