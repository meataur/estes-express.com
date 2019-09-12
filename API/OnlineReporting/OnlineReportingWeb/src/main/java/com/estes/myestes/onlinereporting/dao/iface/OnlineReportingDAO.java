/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.onlinereporting.dao.iface;

import java.util.List;

import com.estes.myestes.onlinereporting.dto.OnlineReport;
import com.estes.myestes.onlinereporting.dto.ReportType;
import com.estes.myestes.onlinereporting.exception.OnlineReportingException;

public interface OnlineReportingDAO
{
	/**
	 * Get the report types for My Estes user
	 * 
	 * @return List<ReportType>
	 * @throws OnlineReportingException
	 */
	public List<ReportType> getReportTypes() throws OnlineReportingException;
	
	/**
	 * Get the scheduled report data for My Estes user
	 * 
	 * @param scheduleId
	 * @return OnlineReport
	 * @throws OnlineReportingException
	 */
	public OnlineReport getScheduledReportData(String scheduleId) throws OnlineReportingException;
	
	/**
	 * Add new report for My Estes user
	 * 
	 * @param report, user
	 * @return  Indication of success
	 */
	public boolean addOnlineReport(OnlineReport report, String user) throws OnlineReportingException;

	/**
	 * Delete existing report for My Estes user
	 * 
	 * @param scheduleId 
	 * @return  Indication of success
	 */
	public boolean deleteOnlineReport(String scheduleId) throws OnlineReportingException;

	/**
	 * Update existing report for My Estes user
	 * 
	 * @param report 
	 * @return  Indication of success
	 */
	public boolean updateOnlineReport(OnlineReport report) throws OnlineReportingException;
	
	/**
	 * Get online reports for My Estes user
	 * 
	 * @param userName My Estes user name
	 * @return  ArrayList of {@link OnlineReport} objects
	 */
	public List<OnlineReport> getOnlineReports(String userName) throws OnlineReportingException;
	
	/**
	 * Unsubscribe the user email for the report
	 * @param scheduleId
	 * @param email
	 * @return Indication of success
	 * @throws OnlineReportingException
	 */
	public boolean unsubscribeReport(String scheduleId, String email) throws OnlineReportingException;
}
