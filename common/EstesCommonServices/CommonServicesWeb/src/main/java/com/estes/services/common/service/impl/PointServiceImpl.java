/**
 * @author: Todd Allen
 *
 * Creation date: 01/31/2018
 */

package com.estes.services.common.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.framework.dto.AddressPoint;
import com.estes.framework.logger.ESTESLogger;
import com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService;
import com.estes.services.common.dao.iface.PointDAO;
import com.estes.services.common.dto.Point;
import com.estes.services.common.dto.PointLookup;
import com.estes.services.common.exception.PointException;
import com.estes.services.common.service.iface.PointService;

/**
 * Estes points lookup implementation 
 */
@Service("pointService")
@Scope("prototype")
public class PointServiceImpl implements PointService
{
	@Autowired
	AddressPointSuggestService addressPointSuggestService;
	@Autowired
	private PointDAO pointDAO;
	/**
	 * Create a new service
	 */
	public PointServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estesexpress.points.service.iface.LookupService#search()
	 */
	@Override
	public List<Point> search(PointLookup lookup) throws PointException
	{
		/*
		 *  Perform basic input data checks
		 */
		if (lookup.getPoint().getCity().equals("") && lookup.getPoint().getState().equals("") && lookup.getPoint().getZip().equals("")) {
			return new ArrayList<Point>();
		}

		try {
			/**
			 * This code for only Mexico country
			 */
			if(lookup.getPoint()!=null 
					&& lookup.getPoint().getState()!=null
					&& lookup.getPoint().getCountry()!=null
					&& lookup.getPoint().getCity() !=null
					&& lookup.getPoint().getState().equalsIgnoreCase("MX")
					&& lookup.getPoint().getCountry().equalsIgnoreCase("MX")){
				String city = lookup.getPoint().getCity();
				int indexOfLastWhiteSpace = lookup.getPoint().getCity().trim().lastIndexOf(" ");
				String state = city.substring(indexOfLastWhiteSpace);
				city = city.substring(0, indexOfLastWhiteSpace);
				lookup.getPoint().setCity(city.trim());
				lookup.getPoint().setState(state.trim());
			}
			
			
			List<AddressPoint> points = addressPointSuggestService
					.getAddressPoint(new AddressPoint(lookup.getPoint().getCountry(), lookup.getPoint().getCity(),
							lookup.getPoint().getState(), lookup.getPoint().getZip()==null? "":lookup.getPoint().getZip().toUpperCase()), lookup.getMaxRows());
			List<Point> pountsFound = new ArrayList<Point>();
			for (AddressPoint apt : points) {
				pountsFound.add(new Point(apt.getCountry(), apt.getCity().trim(), apt.getState(), apt.getZip().trim()));
			}
			return pountsFound;
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, PointServiceImpl.class, "search()", "** Error occured getting points.", e);
			throw new PointException("Error getting Estes points.", e);
		}
	} // search

	@Override
	public boolean isDirect(Point point) throws PointException {
		return pointDAO.isDirect(point);
	}
}
