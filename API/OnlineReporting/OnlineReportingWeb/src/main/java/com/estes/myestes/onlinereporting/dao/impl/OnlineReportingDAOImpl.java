/**
 * @author: Lakshman K
 *
 * Creation date: 10/29/2018
 */

package com.estes.myestes.onlinereporting.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.edps.format.StringFormat;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.onlinereporting.dao.iface.OnlineReportingDAO;
import com.estes.myestes.onlinereporting.dto.OnlineReport;
import com.estes.myestes.onlinereporting.dto.ReportType;
import com.estes.myestes.onlinereporting.exception.OnlineReportingException;
import com.estes.myestes.onlinereporting.utils.OnlineReportingConstant;
import com.estes.myestes.onlinereporting.utils.OnlineReportingUtil;

@Repository ("onlineReportingDAO")
public class OnlineReportingDAOImpl implements OnlineReportingDAO, OnlineReportingConstant
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	private static final class ReportTypeMapper implements RowMapper<ReportType>
	{
		@Override
		public ReportType mapRow(ResultSet rs, int rowNm) throws SQLException {
			ReportType elem = new ReportType();
			elem.setReportId(rs.getString("REPORT_ID"));
			elem.setReportName(rs.getString("NAME").trim());
			elem.setWebAppName(rs.getString("WEB_APPLICATION").trim());
			elem.setReportShortName(getFormattedShortName(rs.getString("SHORT_NAME")));

			return elem;
		}
		/**
		 * Format the short name to lower case without spaces and less than 18 chars
		 * @param shortName
		 * @return
		 */
		private final String getFormattedShortName(String shortName) {
			String minimizedShortName = shortName.replaceAll(" ", "").toLowerCase();
			return minimizedShortName.length() > 18 ? minimizedShortName.substring(0,17): minimizedShortName;		
		}
	} // ReportTypeMapper
	
	private static final class ScheduledReportDataMapper implements RowMapper<OnlineReport>
	{
		@Override
		public OnlineReport mapRow(ResultSet rs, int rowNm) throws SQLException {
			OnlineReport report = new OnlineReport();
			report.setScheduleId(StringFormat.toInt(rs.getString("ID")));
			//report.setUserName(rs.getString("USER_NAME").trim());
			report.setAccountNumber(rs.getString("ACCT_NUM"));
			report.setReportId(StringFormat.toInt(rs.getString("REPORT_ID")));
			report.setEmail(rs.getString("EMAILS").trim());
			report.setFormat(rs.getString("FORMAT"));
			report.setShipmentType(rs.getString("SHIP_TYPE"));
			report.setFrequency(rs.getString("FREQUENCY").trim());
			report.setDateType(rs.getString("DATE_TYPE"));
			report.setNumberOfDays(rs.getString("NUMBER_OFDAYS"));
			report.setDayToRun(rs.getString("DAY_TORUN").trim());
			report.setDayOfMonth(rs.getString("DAY_TORUN").trim());
			report.setTimeToRun(rs.getString("TIME_TORUN"));
			report.setRunOnPrevious(rs.getString("NUMBER_OFDAYS"));
			report.setUserRole(rs.getString("ROLE"));
			report.setReportName(rs.getString("REPORT_DESCRIPTION").trim());
			report.setReportStatus(rs.getString("REPORT_STATUS"));
			report.setRequestStatus(rs.getString("REQUEST_STATUS"));
			report.setStartDate(new SimpleDateFormat("MM/dd/yyyy").format(rs.getDate("REPORT_BEGDATE")));
			report.setExpirationDate(new SimpleDateFormat("MM/dd/yyyy").format(rs.getDate("REPORT_ENDDATE")));
			report.setRequestedWeeks(rs.getString("REQUESTED_WEEKS"));
			report.setRequestedMonths(rs.getString("REQUESTED_MONTHS"));
			return report;
		}	
	} // ScheduledReportDataMapper
	
	private static final class OnlineReportMapper implements RowMapper<OnlineReport>
	{
		@Override
		public OnlineReport mapRow(ResultSet rs, int rowNm) throws SQLException {
			OnlineReport report = new OnlineReport();
			report.setScheduleId(StringFormat.toInt(rs.getString("ID")));
			report.setReportId(StringFormat.toInt(rs.getString("REPORT_ID")));
			report.setFormat(rs.getString("FORMAT"));
			report.setFrequency(rs.getString("FREQUENCY").trim());
			report.setReportName(rs.getString("REPORT_DESCRIPTION").trim());
			report.setStartDate(new SimpleDateFormat("MM/dd/yyyy").format(rs.getDate("REPORT_BEGDATE")));
			report.setExpirationDate(new SimpleDateFormat("MM/dd/yyyy").format(rs.getDate("REPORT_ENDDATE")));
		//	report.setCreateDate(OnlineReportingUtil.formatDate((new java.sql.Date(Timestamp.valueOf(rs.getString("UPDATE_TIMESTAMP")).getTime()).toString())));
			report.setExpired(OnlineReportingUtil.dateExpired(OnlineReportingUtil.removeDashes(rs.getDate("REPORT_ENDDATE").toString())));
			return report;
		}
	} // OnlineReportMapper

	/**
	 * Format timestamp for use with DB2 database.
	 * 
	 * @param ts String representation of timestamp formatted to match database format
	 * - Replace space between date and time with a dash
	 * - Replace colons in time with periods
	 */
	private String formatTimestamp(Timestamp ts)
	{
		StringBuffer time = new StringBuffer(ts.toString());
		time.replace(10, 11, "-");
		return time.toString().replace(':', '.');
	} // formatTimestamp

	@Override
	public List<OnlineReport> getOnlineReports(String userName) throws OnlineReportingException
	{
		String sql = "SELECT ID,REPORT_ID,FORMAT,FREQUENCY,REPORT_DESCRIPTION," +
						"REPORT_BEGDATE,REPORT_ENDDATE,UPDATE_TIMESTAMP " +
						"FROM " + REPORT_SCHEDULE_FILE + " WHERE USER_NAME=? AND REQUEST_STATUS<>? " +
						"ORDER BY UPDATE_TIMESTAMP DESC";
		List<OnlineReport> listQuery =  jdbcMyEstes.query(sql,  new Object[] {userName, MARK_FOR_DELETION}, new OnlineReportMapper());
		if (listQuery != null && listQuery.size() > 0) {
			return listQuery;
		}

		throw new OnlineReportingException("Online reports for the user " + userName + " could not be retrieved.");
	} // getOnlineReports

	@Override
	public boolean addOnlineReport(OnlineReport onlineReport, String user) throws OnlineReportingException
	{
		String sql = "INSERT INTO " + REPORT_SCHEDULE_FILE + " (ID,USER_ID,USER_NAME,ACCT_NUM,REPORT_ID," +
						"EMAILS,FORMAT,SHIP_TYPE,FREQUENCY,DATE_TYPE,NUMBER_OFDAYS,DAY_TORUN,TIME_TORUN,ROLE," +
						"REPORT_STATUS,REQUEST_STATUS,CREATE_USER,CREATE_PGM,CREATE_JOB,REPORT_DESCRIPTION," +
						"REPORT_BEGDATE,REPORT_ENDDATE,REQUESTED_WEEKS,REQUESTED_MONTHS) " +
						"VALUES (?,?,?,?,?,"
						+ "?,?,?,?,?,?,?,?,?,"
						+ "?,?,?,?,?,?,"
						+ "?,?,?,?)";

		Object[] values = {getNextScheduledId() , user.toUpperCase(), user.toUpperCase(), onlineReport.getAccountNumber(), onlineReport.getReportId(), 
				onlineReport.getEmail().toUpperCase().replaceAll(NON_WHITESPACE, ","), onlineReport.getFormat().toUpperCase(), onlineReport.getShipmentType(), onlineReport.getFrequency(),
				onlineReport.getDateType(), onlineReport.getNumberOfDays(), onlineReport.getDayToRun(), onlineReport.getTimeToRun(), onlineReport.getUserRole(),
				onlineReport.getReportStatus(), onlineReport.getRequestStatus(), user.toUpperCase(), CREATE_PGM_JOB, CREATE_PGM_JOB, onlineReport.getReportName(),
				OnlineReportingUtil.formatDate(onlineReport.getStartDate()), OnlineReportingUtil.formatDate(onlineReport.getExpirationDate()), onlineReport.getRequestedWeeks(), onlineReport.getRequestedMonths()};
		try {
			return  jdbcMyEstes.update(sql,  values) > 0 ? true : false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, OnlineReportingDAOImpl.class, "addOnlineReport()", "** Error inserting report for user " + user + ". ", e);
    		throw new OnlineReportingException("Error adding report .");
		}
	} // insertAddress


	@Override
	public boolean updateOnlineReport(OnlineReport onlineReport) throws OnlineReportingException
	{
		String sql = "UPDATE " + REPORT_SCHEDULE_FILE + " SET EMAILS=?,FORMAT=?," +
						"SHIP_TYPE=?,FREQUENCY=?,DATE_TYPE=?,NUMBER_OFDAYS=?,DAY_TORUN=?,TIME_TORUN=?," +
						"REPORT_DESCRIPTION=?,ROLE=?,REPORT_STATUS=?,REQUEST_STATUS=?,REPORT_BEGDATE=?," +
						"REPORT_ENDDATE=?,REQUESTED_WEEKS=?,REQUESTED_MONTHS=?,ACCT_NUM=? WHERE ID=? AND REQUEST_STATUS <> 'D'";

		Object[] values = {onlineReport.getEmail().toUpperCase(), onlineReport.getFormat().toUpperCase(), onlineReport.getShipmentType(),
				onlineReport.getFrequency(), onlineReport.getDateType(), onlineReport.getNumberOfDays(), onlineReport.getDayToRun(),
				onlineReport.getTimeToRun(), onlineReport.getReportName(), onlineReport.getUserRole(), onlineReport.getReportStatus(),
				onlineReport.getRequestStatus(), OnlineReportingUtil.formatDate(onlineReport.getStartDate()), OnlineReportingUtil.formatDate(onlineReport.getExpirationDate()), onlineReport.getRequestedWeeks(),
				onlineReport.getRequestedMonths(), onlineReport.getAccountNumber(), onlineReport.getScheduleId()};

		try {
			return  jdbcMyEstes.update(sql,  values) > 0 ? true : false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, OnlineReportingDAOImpl.class, "updateOnlineReport()", "** Error updating report for myEstes user . ",e);
    		throw new OnlineReportingException("Error updating report .");
		}
	} // updateOnlineReport
	
	@Override
	public boolean deleteOnlineReport(String scheduleId) throws OnlineReportingException
	{
		String sql = "UPDATE " + REPORT_SCHEDULE_FILE + " SET REQUEST_STATUS = ? WHERE ID=?";

		try {
			return  jdbcMyEstes.update(sql,  new Object[] {MARK_FOR_DELETION ,scheduleId}) > 0 ? true : false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, OnlineReportingDAOImpl.class, "deleteOnlineReport()", "** Error deleting report. ", e);
    		throw new OnlineReportingException("Error deleting report.");
		}
	} // deleteOnlineReport

	@Override
	public List<ReportType> getReportTypes() throws OnlineReportingException {
		String sql = "SELECT REPORT_ID, NAME, WEB_APPLICATION, SHORT_NAME FROM " + REPORT_TYPE_FILE + "";
		List<ReportType> listQuery =  jdbcMyEstes.query(sql, new ReportTypeMapper());
		if (listQuery != null && listQuery.size() > 0) {
			return listQuery;
		}
		throw new OnlineReportingException("Report types could not be retrieved.");
	}

	@Override
	public OnlineReport getScheduledReportData(String scheduleId) throws OnlineReportingException {
		OnlineReport report = null;
		String sql = "SELECT ID,USER_NAME,ACCT_NUM," + REPORT_SCHEDULE_FILE + ".REPORT_ID," +
						"EMAILS,FORMAT,SHIP_TYPE,FREQUENCY,DATE_TYPE," +
						"NUMBER_OFDAYS,DAY_TORUN,TIME_TORUN,ROLE,REPORT_DESCRIPTION," +
						"REPORT_STATUS,REQUEST_STATUS,REPORT_BEGDATE,REPORT_ENDDATE,REQUESTED_WEEKS,REQUESTED_MONTHS " +
						"FROM " + REPORT_SCHEDULE_FILE + " INNER JOIN " + REPORT_TYPE_FILE + " " +
						"ON " + REPORT_SCHEDULE_FILE + ".REPORT_ID = " + REPORT_TYPE_FILE + ".REPORT_ID WHERE ID=?";
		List<OnlineReport> listQuery =  jdbcMyEstes.query(sql,  new Object[] {scheduleId}, new ScheduledReportDataMapper());
		if (listQuery != null && listQuery.size() > 0) {
			report = listQuery.get(0);
			return report;
		}

		throw new OnlineReportingException("Scheduled reports for the scheduleId " + scheduleId + " could not be retrieved.");
	}
		
	public Integer getNextScheduledId() throws OnlineReportingException {
		String sql = "SELECT (MAX(ID)+1) FROM  " + REPORT_SCHEDULE_FILE + "";
		try {
			Integer scheduleId =  jdbcMyEstes.queryForObject(sql, Integer.class, new Object[] {});
			return scheduleId;
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, OnlineReportingDAOImpl.class, "getCurrentEmail()", "** Error retrieving email. ", e);
    		throw new OnlineReportingException("Error retrieving email.");
		}		
	}

	@Override
	public boolean unsubscribeReport(String scheduleId, String email) throws OnlineReportingException {
		String sql = "UPDATE " + REPORT_SCHEDULE_FILE + " SET EMAILS=? WHERE ID=?";

		try {
			String replacedEmail = getCurrentEmail(scheduleId).replace(email, "");
			return  jdbcMyEstes.update(sql,  new Object[] {replacedEmail ,scheduleId}) > 0 ? true : false;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, OnlineReportingDAOImpl.class, "deleteOnlineReport()", "** Error deleting report. ", e);
    		throw new OnlineReportingException("Error deleting report.");
		}
	}
	
	/**
	 * Get current email for the scheduleId
	 * @param scheduleId
	 * @return
	 * @throws OnlineReportingException
	 */
	private String getCurrentEmail(String scheduleId) throws OnlineReportingException {
		String sql = "SELECT EMAILS FROM " + REPORT_SCHEDULE_FILE + " WHERE ID=?";

		try {
			String email =  jdbcMyEstes.queryForObject(sql, String.class, new Object[] {scheduleId}) ;
			return formatEmailList(email);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, OnlineReportingDAOImpl.class, "getCurrentEmail()", "** Error retrieving email. ", e);
    		throw new OnlineReportingException("Error retrieving email.");
		}
	}
	
	/**
	 * Format the email 
	 * @param emails
	 * @return
	 */
	private String formatEmailList(String emails){
		if(emails.startsWith(","))
			emails = emails.substring(1, emails.length());
		if(emails.endsWith(","))
			emails = emails.substring(0, emails.length()-1);
		return emails.replaceAll(",,", ",");
	}
}
