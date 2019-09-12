/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.onlinereporting.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes customer online report
 */
public class OnlineReport
{
	@ApiModelProperty(notes=" Report type id ")
	int reportId;
	@ApiModelProperty(notes=" Schedule id (unique id) ")
	int scheduleId;
	@ApiModelProperty(notes=" Report name ")
	String reportName;
	@ApiModelProperty(notes=" User account number – blank for local accounts ")
	String accountNumber;
	@ApiModelProperty(notes=" Shipment type - A ")
	String shipmentType;
	@ApiModelProperty(notes=" Date type - Delivery : D, Pick Up : P ")
	String dateType;
	@ApiModelProperty(notes=" User role – Shipper : S, Consignee : C, 3rd Party : T, All : A ")
	String userRole;
	@ApiModelProperty(notes=" Report status – All : A, Open : O, Declined : D, Paid : P ")
	String reportStatus;
	@ApiModelProperty(notes=" Request status –  blank(Default), D (marked for soft deletion)")   
	String requestStatus;
	@ApiModelProperty(notes=" Frequency – Daily : D, Weekly : W, Monthly : M ")
	String frequency;
	@ApiModelProperty(notes=" User email address ")
	String email;
	@ApiModelProperty(notes=" Report format - *.xls, *.pdf, *.txt, *.csv ")
	String format;
	@ApiModelProperty(notes=" Report for the day of the week : 1-7")
	String dayToRun;
	@ApiModelProperty(notes=" Time to run - 0")
	String timeToRun;
	@ApiModelProperty(notes=" Number of days of report to be run")
	String numberOfDays;
	@ApiModelProperty(notes=" Report begin date (Next day) YYYY-MM-DD ")
	String startDate;
	@ApiModelProperty(notes=" Report expiration date - YYYY-MM-DD ")
	String expirationDate;
	@ApiModelProperty(notes=" Report to be run for the previous xx days/weeks/months ")
	String runOnPrevious;
	@ApiModelProperty(notes=" Report for the day of the month: 1-31")
	String dayOfMonth;
	@ApiModelProperty(notes=" Requested number of weeks ")
	String requestedWeeks;
	@ApiModelProperty(notes=" Requested number of months ")
	String requestedMonths;
	boolean isUntilDate = false;
	boolean isExpired = false;
	boolean isOneTime = false;
	boolean isManually = false;
	boolean isNumberOfWeeks = false;
	boolean isNumberOfMonths = false;

	public OnlineReport() {
		super();
	}

	public OnlineReport(int reportId, int scheduleId, String reportName, String accountNumber,
			String shipmentType, String dateType, String userRole, String reportStatus, String requestStatus,
			String frequency, String email, String format, String dayToRun, String timeToRun,
			String numberOfDays, String startDate, String expirationDate, String runOnPrevious, String dayOfMonth,
			String requestedWeeks, String requestedMonths) {
		super();
		this.reportId = reportId;
		this.scheduleId = scheduleId;
		this.reportName = reportName;
		this.accountNumber = accountNumber;
		this.shipmentType = shipmentType;
		this.dateType = dateType;
		this.userRole = userRole;
		this.reportStatus = reportStatus;
		this.requestStatus = requestStatus;
		this.frequency = frequency;
		this.email = email;
		this.format = format;
		this.dayToRun = dayToRun;
		this.timeToRun = timeToRun;
		this.numberOfDays = numberOfDays;
		this.startDate = startDate;
		this.expirationDate = expirationDate;
		this.runOnPrevious = runOnPrevious;
		this.dayOfMonth = dayOfMonth;
		this.requestedWeeks = requestedWeeks;
		this.requestedMonths = requestedMonths;
	}

	/**
	 * @return the reportId
	 */
	public int getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(int reportId) {
		this.reportId = reportId;
	}

	/**
	 * @return the scheduleId
	 */
	public int getScheduleId() {
		return scheduleId;
	}

	/**
	 * @param scheduleId the scheduleId to set
	 */
	public void setScheduleId(int scheduleId) {
		this.scheduleId = scheduleId;
	}

	/**
	 * @return the reportName
	 */
	public String getReportName() {
		return reportName;
	}

	/**
	 * @param reportName the reportName to set
	 */
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	/**
	 * @return the accountNumber
	 */
	public String getAccountNumber() {
		return accountNumber;
	}

	/**
	 * @param accountNumber the accountNumber to set
	 */
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	/**
	 * @return the shipmentType
	 */
	public String getShipmentType() {
		return shipmentType;
	}

	/**
	 * @param shipmentType the shipmentType to set
	 */
	public void setShipmentType(String shipmentType) {
		this.shipmentType = shipmentType;
	}

	/**
	 * @return the dateType
	 */
	public String getDateType() {
		return dateType;
	}

	/**
	 * @param dateType the dateType to set
	 */
	public void setDateType(String dateType) {
		this.dateType = dateType;
	}

	/**
	 * @return the userRole
	 */
	public String getUserRole() {
		return userRole;
	}

	/**
	 * @param userRole the userRole to set
	 */
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	/**
	 * @return the reportStatus
	 */
	public String getReportStatus() {
		return reportStatus;
	}

	/**
	 * @param reportStatus the reportStatus to set
	 */
	public void setReportStatus(String reportStatus) {
		this.reportStatus = reportStatus;
	}

	/**
	 * @return the requestStatus
	 */
	public String getRequestStatus() {
		return requestStatus;
	}

	/**
	 * @param requestStatus the requestStatus to set
	 */
	public void setRequestStatus(String requestStatus) {
		this.requestStatus = requestStatus;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the dayToRun
	 */
	public String getDayToRun() {
		return dayToRun;
	}

	/**
	 * @param dayToRun the dayToRun to set
	 */
	public void setDayToRun(String dayToRun) {
		this.dayToRun = dayToRun;
	}

	/**
	 * @return the timeToRun
	 */
	public String getTimeToRun() {
		return timeToRun;
	}

	/**
	 * @param timeToRun the timeToRun to set
	 */
	public void setTimeToRun(String timeToRun) {
		this.timeToRun = timeToRun;
	}

	/**
	 * @return the numberOfDays
	 */
	public String getNumberOfDays() {
		return numberOfDays;
	}

	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(String numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the expirationDate
	 */
	public String getExpirationDate() {
		return expirationDate;
	}

	/**
	 * @param expirationDate the expirationDate to set
	 */
	public void setExpirationDate(String expirationDate) {
		this.expirationDate = expirationDate;
	}

	/**
	 * @return the runOnPrevious
	 */
	public String getRunOnPrevious() {
		return runOnPrevious;
	}

	/**
	 * @param runOnPrevious the runOnPrevious to set
	 */
	public void setRunOnPrevious(String runOnPrevious) {
		this.runOnPrevious = runOnPrevious;
	}

	/**
	 * @return the dayOfMonth
	 */
	public String getDayOfMonth() {
		return dayOfMonth;
	}

	/**
	 * @param dayOfMonth the dayOfMonth to set
	 */
	public void setDayOfMonth(String dayOfMonth) {
		this.dayOfMonth = dayOfMonth;
	}

	/**
	 * @return the requestedWeeks
	 */
	public String getRequestedWeeks() {
		return requestedWeeks;
	}

	/**
	 * @param requestedWeeks the requestedWeeks to set
	 */
	public void setRequestedWeeks(String requestedWeeks) {
		this.requestedWeeks = requestedWeeks;
	}

	/**
	 * @return the requestedMonths
	 */
	public String getRequestedMonths() {
		return requestedMonths;
	}

	/**
	 * @param requestedMonths the requestedMonths to set
	 */
	public void setRequestedMonths(String requestedMonths) {
		this.requestedMonths = requestedMonths;
	}

	/**
	 * @return the isUntilDate
	 */
	public boolean isUntilDate() {
		return isUntilDate;
	}

	/**
	 * @param isUntilDate the isUntilDate to set
	 */
	public void setUntilDate(boolean isUntilDate) {
		this.isUntilDate = isUntilDate;
	}

	/**
	 * @return the isExpired
	 */
	public boolean isExpired() {
		return isExpired;
	}

	/**
	 * @param isExpired the isExpired to set
	 */
	public void setExpired(boolean isExpired) {
		this.isExpired = isExpired;
	}

	/**
	 * @return the isOneTime
	 */
	public boolean isOneTime() {
		return isOneTime;
	}

	/**
	 * @param isOneTime the isOneTime to set
	 */
	public void setOneTime(boolean isOneTime) {
		this.isOneTime = isOneTime;
	}

	/**
	 * @return the isManually
	 */
	public boolean isManually() {
		return isManually;
	}

	/**
	 * @param isManually the isManually to set
	 */
	public void setManually(boolean isManually) {
		this.isManually = isManually;
	}

	/**
	 * @return the isNumberOfWeeks
	 */
	public boolean isNumberOfWeeks() {
		return isNumberOfWeeks;
	}

	/**
	 * @param isNumberOfWeeks the isNumberOfWeeks to set
	 */
	public void setNumberOfWeeks(boolean isNumberOfWeeks) {
		this.isNumberOfWeeks = isNumberOfWeeks;
	}

	/**
	 * @return the isNumberOfMonths
	 */
	public boolean isNumberOfMonths() {
		return isNumberOfMonths;
	}

	/**
	 * @param isNumberOfMonths the isNumberOfMonths to set
	 */
	public void setNumberOfMonths(boolean isNumberOfMonths) {
		this.isNumberOfMonths = isNumberOfMonths;
	}	
}
