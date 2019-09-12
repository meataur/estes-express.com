/**
 * @author: Todd Allen
 *
 * Creation date: 02/27/2018
 *
 */

package com.estes.myestes.points.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.points.dao.iface.CoverageDAO;
import com.estes.myestes.points.dao.iface.TerminalDAO;
import com.estes.myestes.points.dto.CoverageRequest;
import com.estes.myestes.points.dto.CoverageResponse;
import com.estes.myestes.points.dto.PartialTerminal;
import com.estes.myestes.points.dto.Point;
import com.estes.myestes.points.dto.Terminal;
import com.estes.myestes.points.exception.PointException;
import com.estes.myestes.points.service.iface.CoverageService;
import com.estes.myestes.points.utils.PointUtils;

/**
 * Servicing terminal lookup implementation
 */
@Service("coverageService")
@Scope("prototype")
public class CoverageServiceImpl implements CoverageService
{
	@Autowired
	private CoverageDAO coverDAO;
	@Autowired
	private TerminalDAO terminalDAO;

	/**
	 * Create a new service
	 */
	public CoverageServiceImpl()
	{
		super();
	} // Constructor

	private Terminal createTerminal(PartialTerminal part)
	{
		Terminal term = new Terminal();
		term.setId(part.getId());
		term.getAddress().setStreetAddress("");
		term.getAddress().setStreetAddress2("");
		term.getAddress().setCity(part.getCity());
		term.getAddress().setState(part.getState());
		term.getAddress().setZip(part.getZip());
		term.getAddress().setZip4("");
		term.getAddress().setCountry("");;
		return term;
	} // createTerminal

	/* (non-Javadoc)
	 * @see com.estes.myestes.points.service.iface.CoverageService#getTerminals(Point)
	 */
	@Override
	public CoverageResponse getTerminals(CoverageRequest cover) throws PointException
	{
		CoverageResponse resp = new CoverageResponse();

		/*
		 *  Perform simple error checking
		 */
		if (cover.getCity().equalsIgnoreCase("") && cover.getState().equalsIgnoreCase("") && cover.getZip().equalsIgnoreCase("")) {
		 	resp.setErrorInfo(new ServiceResponse("error", "City and state OR zip is required."));
		 	return resp;
		}
		else if (!cover.getCity().equalsIgnoreCase("") && cover.getState().equalsIgnoreCase("")) {
		 	resp.setErrorInfo(new ServiceResponse("SDE0161", "State is blank."));
		 	return resp;
		}
		else if (cover.getCity().equalsIgnoreCase("") && !cover.getState().equalsIgnoreCase("")) {
		 	resp.setErrorInfo(new ServiceResponse("SDE0163", "City is blank."));
		 	return resp;
		}

		List<PartialTerminal> servTerms = coverDAO.getServicingTerminals(cover);

		// If error returned then set ServiceResponse info
		if (PointUtils.hasError(servTerms.get(0).getErrorInfo())) {
			resp.setErrorInfo(servTerms.get(0).getErrorInfo());
		}
		// If no error then multiple partial terminals returned or 1 servicing terminal
		else {
			resp.setTerminals(new ArrayList<Terminal>());
			// Set partial terminal info in response to select the proper terminal
			if (servTerms.size() >1) {
				for (PartialTerminal trm : servTerms) {
					resp.getTerminals().add(createTerminal(trm));
				}
			}
			// If 1 terminal returned then it must be the correct servicing terminal
			else {
				resp.getTerminals().add(terminalDAO.getServicingTerminal(new Point(cover.getCountry(), cover.getCity(), cover.getState(), cover.getZip())));
			}
		}

		return resp;
	} // getTerminal
}
