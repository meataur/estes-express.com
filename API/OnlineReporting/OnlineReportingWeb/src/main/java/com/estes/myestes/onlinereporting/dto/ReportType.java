/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.onlinereporting.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * Estes customer report type
 */
public class ReportType
{
	@ApiModelProperty(notes=" Report id ")
	String reportId;
	@ApiModelProperty(notes=" Report name")
	String reportName;
	@ApiModelProperty(notes=" Webapp name ")
	String webAppName;
	@ApiModelProperty(notes=" Report short name ")
	String reportShortName;
	
	public ReportType() {
		super();
	}

	public ReportType(String reportId, String reportName, String webAppName, String reportShortName) {
		super();
		this.reportId = reportId;
		this.reportName = reportName;
		this.webAppName = webAppName;
		this.reportShortName = reportShortName;
	}

	/**
	 * @return the reportId
	 */
	public String getReportId() {
		return reportId;
	}

	/**
	 * @param reportId the reportId to set
	 */
	public void setReportId(String reportId) {
		this.reportId = reportId;
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
	 * @return the webAppName
	 */
	public String getWebAppName() {
		return webAppName;
	}

	/**
	 * @param webAppName the webAppName to set
	 */
	public void setWebAppName(String webAppName) {
		this.webAppName = webAppName;
	}

	/**
	 * @return the reportShortName
	 */
	public String getReportShortName() {
		return reportShortName;
	}

	/**
	 * @param reportShortName the reportShortName to set
	 */
	public void setReportShortName(String reportShortName) {
		this.reportShortName = reportShortName;
	}
	
	
}
