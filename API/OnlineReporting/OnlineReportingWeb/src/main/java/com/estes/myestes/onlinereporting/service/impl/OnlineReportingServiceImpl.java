/**
 * @author: Lakshman K
 *
 * Creation date: 11/12/2018
 */

package com.estes.myestes.onlinereporting.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.edps.format.DateFormat;
import com.estes.myestes.onlinereporting.dao.iface.OnlineReportingDAO;
import com.estes.myestes.onlinereporting.dto.OnlineReport;
import com.estes.myestes.onlinereporting.dto.ReportType;
import com.estes.myestes.onlinereporting.exception.OnlineReportingException;
import com.estes.myestes.onlinereporting.service.iface.OnlineReportingService;
import com.estes.myestes.onlinereporting.utils.OnlineReportingConstant;
import com.estes.myestes.onlinereporting.utils.OnlineReportingUtil;

@Service("onlineReportingService")
@Scope("prototype")
public class OnlineReportingServiceImpl implements OnlineReportingService, OnlineReportingConstant
{
	@Autowired
	private OnlineReportingDAO onlineReportingDAO;

	@Override
	public List<ReportType> getReportTypes() throws OnlineReportingException {
		return onlineReportingDAO.getReportTypes();
	}

	@Override
	public List<OnlineReport> getOnlineReports(String user) throws OnlineReportingException {
		List<OnlineReport> reports = onlineReportingDAO.getOnlineReports(user);
		for(OnlineReport report : reports) {
			report.setExpired(OnlineReportingUtil.dateExpired(new SimpleDateFormat("yyyyMMdd").format(new Date(report.getExpirationDate()))));
		}
		return reports;
	}

	@Override
	public OnlineReport getScheduledReportData(String scheduleId) throws OnlineReportingException {
		return onlineReportingDAO.getScheduledReportData(scheduleId);
	}

	@Override
	public boolean addOnlineReport(OnlineReport report, String user) throws OnlineReportingException {
		setReportData(report);
		return onlineReportingDAO.addOnlineReport(report, user);
	}

	@Override
	public boolean updateOnlineReport(OnlineReport report) throws OnlineReportingException {
		setReportData(report);
		return onlineReportingDAO.updateOnlineReport(report);
	}

	@Override
	public boolean deleteOnlineReport(String scheduleId) throws OnlineReportingException {
		return onlineReportingDAO.deleteOnlineReport(scheduleId);
	}

	@Override
	public boolean unsubscribeReport(String scheduleId, String email) throws OnlineReportingException {
		return onlineReportingDAO.unsubscribeReport(scheduleId, email);
	}
	
	private void setReportData(OnlineReport report) {
		//set Frequency
		if(report.getFrequency().equals("D")){
			report.setNumberOfDays(report.getRunOnPrevious());
			report.setDayToRun(DEFAULT_NUMBER_VALUE);
		}
		if(report.getFrequency().equals("W")){
			report.setNumberOfDays(DEFAULT_NUMBER_VALUE);
			report.setDayToRun(report.getDayToRun());
		}
		if(report.getFrequency().equals("M")){
			report.setNumberOfDays(DEFAULT_NUMBER_VALUE);
			report.setDayToRun(report.getDayOfMonth());
		}
		//set Dates
		if(report.isOneTime()){
			report.setExpirationDate(report.getStartDate());	
		}
		if(report.isManually()){
			report.setExpirationDate(OnlineReportingUtil.convert8digitNumericToDateString(FUTURE_END_DATE.replaceAll("/", ""), "-"));
		}
		if(report.isNumberOfWeeks()){
			report.setExpirationDate(OnlineReportingUtil.getDateWeeksFromToday(report.getRequestedWeeks()));
		}
		if(report.isNumberOfMonths()){
			report.setExpirationDate(OnlineReportingUtil.getDateWeeksFromToday(report.getRequestedMonths()));
		}	
	}	
}
