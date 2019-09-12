/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.shiptrack.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.shiptrack.dao.iface.ErrorDAO;
import com.estes.myestes.shiptrack.dao.iface.ShipTrackDAO;
import com.estes.myestes.shiptrack.dto.Shipment;
import com.estes.myestes.shiptrack.dto.TrackingRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

@Repository ("trackingDAO")
public class ShipTrackDAOImpl implements ShipTrackDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class ShipmentMapper implements RowMapper<Shipment>
	{
		@Override
		public Shipment mapRow(ResultSet rs, int rowNm) throws SQLException
		{
			Shipment elem = new Shipment();
			elem.setType(rs.getString("P_SHIP_TYPE"));
			elem.setPro(rs.getString("P_OT") + "-" + rs.getString("P_PRO"));
			elem.setError(rs.getString("P_ERROR_SP"));
			// If error blank then check 2nd error message
			if (StringUtils.isEmpty(elem.getError())) {
				elem.setError(rs.getString("P_ERROR_ST"));
			}
			
			/**
			 * Set Bad data from P_DATA
			 */
			if(ErrorDAO.isError(elem.getError())){
				elem.getErrorInfo().setErrorCode("error");
				elem.getErrorInfo().setBadData(rs.getString("P_DATA").trim());
				elem.getErrorInfo().setMessage(elem.getError());
			}
			
			
			elem.setBillTo(rs.getString("P_BILLTOACCT"));
			elem.setShipper(rs.getString("P_SHIP_ACCT"));
			elem.setCons(rs.getString("P_CONS_ACCT"));
			elem.setControl(rs.getString("P_CTRL_ACCT"));
			elem.setPayor(rs.getString("P_PAYOR_ACCT"));
			elem.setTerms(rs.getString("P_TERMS"));
			elem.setCharges(rs.getString("P_CHARGES"));
			elem.setDeliveryDate(rs.getString("P_DELIV_DATE"));
			elem.setDeliveryTime(rs.getString("P_DELIV_TIME"));
			elem.setReceivedBy(rs.getString("P_RCV_BY"));
			elem.setPickupDate(rs.getString("P_PICKUPDATE"));
			elem.setPickupTime(rs.getString("P_PICKUPTIME"));
			elem.setWeight(rs.getString("P_WEIGHT"));
			elem.setPieces(rs.getString("P_PIECES"));
			elem.setShipperName(rs.getString("P_SHIP_NAME"));
			elem.getShipperAddress().setStreetAddress(rs.getString("P_SHIP_ADDR1"));
			elem.getShipperAddress().setStreetAddress2(rs.getString("P_SHIP_ADDR2"));
			elem.getShipperAddress().setCity(rs.getString("P_SHIP_CITY"));
			elem.getShipperAddress().setState(rs.getString("P_SHIP_ST"));
			elem.getShipperAddress().setZip(rs.getString("P_SHIP_ZIP"));
			elem.getShipperAddress().setZip4(rs.getString("P_SHIP_Z4"));
			elem.getShipperAddress().setCountry(rs.getString("P_SHIP_CNRTY"));
			elem.setConsName(rs.getString("P_CONS_NAME"));
			elem.getConsAddress().setStreetAddress(rs.getString("P_CONS_ADDR1"));
			elem.getConsAddress().setStreetAddress2(rs.getString("P_CONS_ADDR2"));
			elem.getConsAddress().setCity(rs.getString("P_CONS_CITY"));
			elem.getConsAddress().setState(rs.getString("P_CONS_ST"));
			elem.getConsAddress().setZip(rs.getString("P_CONS_ZIP"));
			elem.getConsAddress().setZip4(rs.getString("P_CONS_Z4"));
			elem.getConsAddress().setCountry(rs.getString("P_CONS_CNTRY"));
//			elem.setConsRefnum(rs.getString("P_CONSREFNR"));
			elem.setThirdPartyName(rs.getString("P_TPTY_NAME"));
			elem.getThirdPartyAddress().setStreetAddress(rs.getString("P_TPTY_ADDR1"));
			elem.getThirdPartyAddress().setStreetAddress2(rs.getString("P_TPTY_ADDR2"));
			elem.getThirdPartyAddress().setCity(rs.getString("P_TPTY_CITY"));
			elem.getThirdPartyAddress().setState(rs.getString("P_TPTY_ST"));
			elem.getThirdPartyAddress().setZip(rs.getString("P_TPTY_ZIP"));
			elem.getThirdPartyAddress().setZip4(rs.getString("P_TPTY_Z4"));
			elem.getThirdPartyAddress().setCountry(rs.getString("P_TPTY_CNRTY"));
			elem.setThirdPartyRefnum(rs.getString("P_TPTY_REFNR"));
			elem.setServiceType(rs.getString("P_SERV_TYPE"));
			elem.setApptDate(rs.getString("P_APPT_DATE"));
			elem.setApptTime(rs.getString("P_APPT_TIME"));
			elem.setApptStatus(rs.getString("P_APPT_STS"));
			elem.setDestTerminalNum(rs.getString("P_DT_ALPHA"));
			elem.setDestTerminalName(rs.getString("P_DT_NAME"));
			elem.getDestTerminalAddress().setStreetAddress(rs.getString("P_DT_STREET"));
			elem.getDestTerminalAddress().setCity(rs.getString("P_DT_CITY"));
			elem.getDestTerminalAddress().setState(rs.getString("P_DT_STATE"));
			elem.getDestTerminalAddress().setZip(rs.getString("P_DT_ZIP"));
			elem.getDestTerminalAddress().setZip4(rs.getString("P_DT_Z4"));
			elem.setDestTerminalPhone(rs.getString("P_DT_PHONE"));
			elem.setDestTerminalFax(rs.getString("P_DT_FAX"));
			elem.setBol(rs.getString("P_BOL"));
			elem.setPoNum(rs.getString("P_PO"));
			elem.setLoadNum(rs.getString("P_LDN"));
			elem.setIlfb(rs.getString("P_ILFB"));
			elem.setIlName(rs.getString("P_IL_NAME"));
			elem.setIlScac(rs.getString("P_IL_SCAC"));
			elem.setIlType(rs.getString("P_IL_TYPE"));
			elem.setFirstDeliveryAttempt(rs.getString("P_FIRST_DEL"));
			elem.setStatus(rs.getString("P_STATUS_TXT"));
			elem.setStatusTime(rs.getString("P_STS_TMSTMP"));
			elem.setEstDelDate(rs.getString("P_EST_DEL_DT"));
			elem.setEstArrivalTime(rs.getString("P_ESR_ARR_TM"));
			elem.setDimWeight(rs.getString("P_DIM_WEIGHT"));
			elem.setMasterOT(rs.getString("P_MAST_OT"));
			elem.setMasterProNum(rs.getString("P_MAST_PRO"));
			elem.setFreightChargeDisc(rs.getString("P_FRT_DSC"));
			elem.setEstDelDateDisc(rs.getString("P_EST_DT_DSC"));
			elem.setDelDateDisc(rs.getString("P_DEL_DT_DSC"));
			elem.setDelTimeDisc(rs.getString("P_DEL_TM_DSC"));
			elem.setFreightChargeAudit(rs.getString("P_FRTCHRGAUD"));
			elem.setLastChanged(rs.getString("P_LCHG_TS"));
			elem.setIsPayor(rs.getString("P_IS_PAYOR"));
			elem.setIsParty(rs.getString("P_IL_PARTY"));
			elem.setSortCompare(rs.getString("P_COMP_VAL"));
			return elem;
		}
	} // ShipmentMapper

	@SuppressWarnings("unchecked")
	public List<Shipment> search(TrackingRequest crit) throws ShipTrackException
	{
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcMyEstes);

		sproc.withSchemaName(PGM_SCHEMA);
        sproc.withProcedureName("sp_get_shipment_tracking_data");
        sproc.addDeclaredParameter(new SqlParameter("random#", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("delim_data", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("criteria", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("order_by", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("direction", Types.CHAR));
        sproc.addDeclaredParameter(new SqlParameter("recs_per_page", Types.INTEGER));
        sproc.addDeclaredParameter(new SqlParameter("page_to_show", Types.INTEGER));
        sproc.addDeclaredParameter(new SqlInOutParameter("total_rows", Types.INTEGER));

        Map<String, Object> inParams = new HashMap<String, Object>();
        inParams.put("random#", crit.getSession());
        inParams.put("delim_data", crit.getSearch());
        inParams.put("criteria", crit.getType());
        inParams.put("order_by", crit.getSort());
        inParams.put("direction", crit.getDirection());
        inParams.put("recs_per_page", crit.getRowsPerPage());
        inParams.put("page_to_show", crit.getPage());
        inParams.put("total_rows", 0);
        sproc.returningResultSet("#result-set-1", new ShipmentMapper());
        Map<String, Object> outParms = sproc.execute(inParams);

        if (outParms != null) {
         	return (List<Shipment>) outParms.get("#result-set-1");
		} else {
			ESTESLogger.log(ESTESLogger.ERROR, ShipTrackDAOImpl.class, "search()", "An error occurred tracking shipments.");
    		throw new ShipTrackException("Shipment tracking search failed.");
		}
	} // search
}
