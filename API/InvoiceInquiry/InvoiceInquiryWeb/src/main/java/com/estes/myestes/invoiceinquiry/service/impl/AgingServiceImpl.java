/**
 * @author: Todd Allen
 *
 * Creation date: 10/11/2018
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.customer.Account;
import com.estes.myestes.invoiceinquiry.dao.iface.AgingBuildStatusDAO;
import com.estes.myestes.invoiceinquiry.dao.iface.AgingDetailDAO;
import com.estes.myestes.invoiceinquiry.dao.iface.AgingSummaryDAO;
import com.estes.myestes.invoiceinquiry.dto.AgingBuildProgress;
import com.estes.myestes.invoiceinquiry.dto.AgingBuildStatus;
import com.estes.myestes.invoiceinquiry.dto.AgingDetailRequest;
import com.estes.myestes.invoiceinquiry.dto.AgingDetails;
import com.estes.myestes.invoiceinquiry.dto.AgingSummary;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.AgingService;

@Service("agingService")
@Scope("prototype")
public class AgingServiceImpl implements AgingService
{
	@Autowired
	private AgingBuildStatusDAO statusDAO;
	@Autowired
	private AgingSummaryDAO summaryDAO;
	@Autowired
	private AgingDetailDAO detailDAO;
	private AgingBuildProgress progress;

	/**
	 * Check whether aging build is busy.
	 * 
	 * @return Build progress info - Null if not busy
	 */
	private AgingBuildProgress checkBusyStatus(List<AgingBuildStatus> statuses)
	{
		// Check first row for non-null busy and bill count values
		if (statuses != null && statuses.get(0).getBusy() != null) {
			AgingBuildProgress progress = new AgingBuildProgress();
			progress.setBusy(statuses.get(0).getBusy().equalsIgnoreCase("Y"));
			progress.setBillCount(statuses.get(0).getBillCount());
		}

		return null;
	} // checkBusyStatus

	@Override
	public AgingBuildProgress getAgingBuildProgress()
	{
		return this.progress;
	} // getAgingBuildProgress

	@Override
	public AgingDetails getAgingDetail(AgingDetailRequest detReq, Account account) throws InvoiceException
	{
		return detailDAO.getAgingDetail(detReq, account);
	} // getAgingTotals

	@Override
	public AgingSummary getAgingTotals(Account account) throws InvoiceException
	{
		AgingSummary totals = new AgingSummary();

		List<AgingBuildStatus> stats = startAgingBuild(account);
		if (!stats.isEmpty()) {
			totals.setRefreshDate(getRefreshDate(stats));
		}

		stats = getBuildStatus(account);
		progress = checkBusyStatus(stats);
		if (progress != null) {
			progress.setRefreshDate(totals.getRefreshDate());
			return null;
		}

		if (account.isWebGroup()) {
			totals.setAging(summaryDAO.getAgingWebGroupSummary(account.getCode()));
		}
		else {
			totals.setAging(summaryDAO.getAgingSummary(account.getCode()));
		}

		return totals;
	} // getAgingTotals

	private List<AgingBuildStatus> getBuildStatus(Account account) throws InvoiceException
	{
		return statusDAO.getBuildStatus(account);
	} // getBuildStatus

	/**
	 * Get the last aging build date.
	 * 
	 * @return Date of last aging build
	 */
	private String getRefreshDate(List<AgingBuildStatus> statuses)
	{
		// Search for code "15" to get refresh date (YYYYMMDD) from DESC column of result set
		for (AgingBuildStatus stat : statuses) {
	      if (stat.getCode() != null && stat.getCode().equalsIgnoreCase("15")) {
			return stat.getDesc();
	      }
	    }

		return "";
	} // getRefreshDate

	private List<AgingBuildStatus> startAgingBuild(Account account) throws InvoiceException
	{
		return statusDAO.startBuildProcess(account);
	} // startAgingBuild
}
