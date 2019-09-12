package com.estes.myestes.onlinereporting.service.iface;

import java.util.List;

import com.estes.myestes.onlinereporting.dto.OnlineReport;
import com.estes.myestes.onlinereporting.dto.ReportType;
import com.estes.myestes.onlinereporting.exception.OnlineReportingException;

/**
 * Address book maintenance service
 */
public interface OnlineReportingService
{
	
	/**
	 * Get all report types.
	 * 
	 * @return  List of {@link ReportType} objects
	 */
	public List<ReportType> getReportTypes() throws OnlineReportingException;
	
	/**
	 * Get all reports for MyEstes user.
	 * 
	 * @param  user My Estes user name
	 * @return  List of {@link OnlineReport} objects
	 */
	public List<OnlineReport> getOnlineReports(String user) throws OnlineReportingException;
	
	/**
	 * Get MyEstes user scheduled report data
	 * @param scheduleId
	 * @return {@link OnlineReport} object
	 * @throws OnlineReportingException
	 */
	public OnlineReport getScheduledReportData(String scheduleId) throws OnlineReportingException;
	
	/**
	 * Add online report for MyEstes user
	 * 
	 * @param  report {@link OnlineReport} to be added, user
	 * @return  Indicator of success
	 */
	public boolean addOnlineReport(OnlineReport report, String user) throws OnlineReportingException;
	
	/**
	 * Update online report for MyEstes user
	 * 
	 * @param  report {@link OnlineReport} to be updated
	 * @return  Indicator of success
	 */
	public boolean updateOnlineReport(OnlineReport report) throws OnlineReportingException;
	
	/**
	 * Delete online report for MyEstes user
	 * 
	 * @param  scheduleId
	 * @return  Indicator of success
	 */
	public boolean deleteOnlineReport(String scheduleId) throws OnlineReportingException;
	
	/**
	 * Unsubscribe from the report
	 * @param scheduleId
	 * @param email
	 * @return Indicator of success
	 * @throws OnlineReportingException
	 */
	public boolean unsubscribeReport(String scheduleId, String email) throws OnlineReportingException;
}
