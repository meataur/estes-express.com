/**
 *@author thottsr
 */

package com.estes.framework.services.pointsuggest.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.framework.dataaccess.pointsuggest.iface.AddressPointSuggestDAO;
import com.estes.framework.dto.AddressPoint;
import com.estes.framework.exception.ESTESException;
import com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService;
import com.estes.framework.util.FrameworkConstant;

/**
 * Class to perform parsing based on user entry.
 */
@Service("addressPointSuggestService")
@Scope("prototype")
public class AddressPointSuggestServiceImpl implements AddressPointSuggestService
{
	@Autowired
	AddressPointSuggestDAO addressPointSuggestDAO;

	/*
	 * (non-Javadoc)
	 * @see com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService#getAddressPoint
	 */
	@Override
	public List<AddressPoint> getAddressPoint(AddressPoint point, Integer maxRecordsToFectch) throws ESTESException
	{
		AddressPoint inputPointPartial = point;

		// set the parameters
		parseInputCriteria(inputPointPartial);
		if (maxRecordsToFectch == null || maxRecordsToFectch > FrameworkConstant.POINT_SUGGEST_MAX_RECORDS) {
			maxRecordsToFectch = FrameworkConstant.POINT_SUGGEST_DEFAULT_RECORDS;
		}

		if (StringUtils.isEmpty(inputPointPartial.getCity()) && StringUtils.isEmpty(inputPointPartial.getState())
				&& StringUtils.isEmpty(inputPointPartial.getZip())) {
			List<AddressPoint> addressPointList = new ArrayList<AddressPoint>();
			addressPointList.add(new AddressPoint("", FrameworkConstant.NO_POINTS_FOUND, "", ""));
			return addressPointList;
		}
		else
		{
			return addressPointSuggestDAO.getAddress(inputPointPartial, maxRecordsToFectch);
		}
	} // getAddressPoint

	/*
	 * (non-Javadoc)
	 * @see com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService#getAddressPoint
	 */
	@Override
	public List<String> getAddressPoint(String addressPoint,String country, Integer maxRecordsToFectch) throws ESTESException
	{
		AddressPoint inputPointPartial = parsePointStr(addressPoint);

		// set the parameters
		parseInputCriteria(inputPointPartial);
		if(maxRecordsToFectch == null){
			maxRecordsToFectch = FrameworkConstant.POINT_SUGGEST_DEFAULT_RECORDS;
		}

		List<String> addList = new ArrayList<String>();
		if (StringUtils.isEmpty(inputPointPartial.getCity()) && StringUtils.isEmpty(inputPointPartial.getState())
				&& StringUtils.isEmpty(inputPointPartial.getZip())) {
			addList.add(FrameworkConstant.NO_POINTS_FOUND);
		}
		else
		{
			// default country is US
			if(StringUtils.isBlank(inputPointPartial.getCountry())){
				inputPointPartial.setCountry(country);
			}
			List<AddressPoint> addressPointList = addressPointSuggestDAO.getAddress(inputPointPartial, maxRecordsToFectch);
			if(addressPointList.size() == 0)
			{
				addList.add(FrameworkConstant.NO_POINTS_FOUND);
				return addList;
			}
			StringBuffer sb;
			for (AddressPoint ap : addressPointList) {
				sb = new StringBuffer(); 
				sb.append(ap.getCity().trim()).append(", ");
				sb.append(ap.getState().trim()).append(" ");
				sb.append(ap.getZip().trim());
				addList.add(sb.toString());
			}
		}

		return addList;
	} // getAddressPoint

	/*
	 * (non-Javadoc)
	 * @see com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService#parsePointStr(java.lang.String)
	 */
	@Override
	public AddressPoint parsePointStr(String addressPoint)
	{
		AddressPoint inputPointPartial = new AddressPoint();

		inputPointPartial.setCity("");
		inputPointPartial.setState("");
		inputPointPartial.setZip("");
		inputPointPartial.setCountry("");

		addressPoint= addressPoint.trim();
		Pattern p = Pattern.compile(",");
		String[] result = p.split(addressPoint);

		//if only one string is entered.
		if(result.length == 1)			
		{
			// If only letters and spaces it must be a city
			if (result[0].matches(FrameworkConstant.POINT_SUGGEST_CITY_REGEX)) {
				inputPointPartial.setCity(result[0]);
			}
			// If no spaces with a number present it must be a zip/postal code
			else if (result[0].indexOf(" ") == -1 && result[0].matches(FrameworkConstant.POINT_SUGGEST_ZIP)) {
				inputPointPartial.setZip(result[0]);
			}
			// Spaces and number present indicates state + zip; e.g. VA 23230
			else
			{
				inputPointPartial.setState(StringUtils.substringBeforeLast(result[0], " "));
				inputPointPartial.setZip(StringUtils.substringAfterLast(result[0], " "));
			}
		}
		else
		{
			for(int i=0;i<result.length;++i)
			{
				result[i]=result[i].trim();
				if (result[i].matches(FrameworkConstant.POINT_SUGGEST_CITY_REGEX)) {
					// City always before comma - found only in first element
					if (i==0 && StringUtils.isEmpty(inputPointPartial.getCity())){
						inputPointPartial.setCity(result[i]);
					}
					else if(StringUtils.isEmpty(inputPointPartial.getState())){ 
						inputPointPartial.setState(result[i]);
					}
				}
				// If no spaces with a number present it must be a zip/postal code
				else if (result[i].indexOf(" ") == -1 && result[i].matches(FrameworkConstant.POINT_SUGGEST_ZIP)) {
					inputPointPartial.setZip(result[i]);
				}
				else
				{
				inputPointPartial.setState(StringUtils.substringBeforeLast(result[i], " "));
				inputPointPartial.setZip(StringUtils.substringAfterLast(result[i], " "));
				}
			}
		}

		return inputPointPartial;
	} // parsePointStr

	/**
	 * This method appends '%' to city, state and zip before sending it to the dao for execution.
	 * 
	 * @param inputPointPartial
	 */
	private void parseInputCriteria(AddressPoint inputPointPartial)
	{
		if(StringUtils.isNotEmpty(inputPointPartial.getCity())){
			inputPointPartial.setCity(inputPointPartial.getCity().toUpperCase()+"%");
		}
		if(StringUtils.isNotEmpty(inputPointPartial.getState())){
			inputPointPartial.setState(inputPointPartial.getState().toUpperCase()+"%");
		}
		if(StringUtils.isNotEmpty(inputPointPartial.getZip())){
			inputPointPartial.setZip(inputPointPartial.getZip()+"%");
		}
	} // parseInputCriteria
}
