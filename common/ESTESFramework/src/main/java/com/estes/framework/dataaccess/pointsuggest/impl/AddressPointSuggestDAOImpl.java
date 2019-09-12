package com.estes.framework.dataaccess.pointsuggest.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.dataaccess.pointsuggest.iface.AddressPointSuggestDAO;
import com.estes.framework.dto.AddressPoint;
import com.estes.framework.util.FrameworkConstant;


/**
 * Class to perform address lookup based on city, state and zip.
 * 
 *@author thottsr
 * 
 */
@Repository("addressPointSuggestDAO")
@Scope("prototype")
public class AddressPointSuggestDAOImpl implements AddressPointSuggestDAO
{
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/**
	 * 	 
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @return
	 */
	public List<AddressPoint> getAddress(AddressPoint inputAddressPoint, int maxRecordCnt)
	{
		String city = inputAddressPoint.getCity();
		String state = inputAddressPoint.getState();
		String zip = inputAddressPoint.getZip();
		String country = inputAddressPoint.getCountry();

		// Find the cities in the preferred city list.
		List<AddressPoint> addressPointList = getPreferedPoints(city, state, zip, country,maxRecordCnt);

		// If not found in the preferred find rest of the cities.
		if(addressPointList == null || addressPointList.isEmpty()){
			addressPointList = getNonPreferedPoints(city, state, zip, country, maxRecordCnt);
		}

		// Parse city for Mexican points
		addressPointList = parseCity(addressPointList, country);

		return addressPointList;
	} // getAddress

	/**
	 * Build the query from the preferred points
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param recordCnt
	 * @return
	 */
	private List<AddressPoint> getPreferedPoints(String city, String state, String zip,String country,int maxRecordsCnt)
	{
		StringBuffer sb = new StringBuffer();
		String query = buildQuery(city, state, zip, country, true);
		sb.append(query);
	    //append the preferred city condition
		sb.append(" AND P.AMZSTS = 'P'");
		
		//get the country query
		sb.append(getCountryQuery(country));
		
		//add the max record condition
		sb.append(getRecordCountQuery(maxRecordsCnt));
		return executeQuery(sb.toString());
	}
	
	/**
	 * Build the query from the Non preferred points
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param recordCnt
	 * @return
	 */
	private List<AddressPoint> getNonPreferedPoints(String city, String state, String zip,String country,int maxRecordsCnt)
	{
		StringBuffer sb = new StringBuffer();
		
		String query = buildQuery(city, state, zip, country, false);
		sb.append(query);

		//get the country query
		sb.append(getCountryQuery(country));
		
		//add the max record condition
		sb.append(getRecordCountQuery(maxRecordsCnt));
		return executeQuery(sb.toString());
	}
	
	/**
	 * Build the query based on conditions
	 * 
	 * @param city
	 * @param state
	 * @param zip
	 * @param country
	 * @param fromPreferredCity
	 * @return
	 */
	private String buildQuery(String city, String state, String zip, String country, boolean fromPreferredCity)
	{
		StringBuffer sb = new StringBuffer();
		boolean moreCondition = false;

		if (fromPreferredCity){
			sb.append("SELECT P.AMPNAM as city, P.AMST as state,P.AMRZIP as zip ,P.AMREF6 as country FROM FBFILES.FBP074 AS P ");
			sb.append("WHERE ");
			if(StringUtils.isNotEmpty(city))
			{
				sb.append(" P.AMPNAM like '").append(city).append( "'");
				moreCondition = true;
			}
		}
		else{
			sb.append("SELECT DISTINCT P.AMCITY as city, P.AMST as state,P.AMRZIP as zip ,P.AMREF6 as country FROM FBFILES.FBP074 AS P ");
			sb.append("WHERE ");
			if (StringUtils.isNotEmpty(city))
			{
				sb.append(" P.AMCITY like '").append( city).append( "'");
				moreCondition = true;
			}	
		}

		if (StringUtils.isNotEmpty(state)){
			if(moreCondition){
				sb.append(" AND ");
			}
			// 	Mexican state is in bytes 17-19 on city field
			if(country.equalsIgnoreCase(FrameworkConstant.COUNTRY_MX)) {
				sb.append(" SUBSTR(P.AMCITY, 17, " + state.length() + ") like '" ).append(state).append("'");
			}
			else {
				sb.append(" P.AMST like '" ).append(state).append("'");
			}
			moreCondition = true;
		}

		if (StringUtils.isNotEmpty(zip)){
			if(moreCondition){
				sb.append(" AND ");
			}
			sb.append(" P.AMRZIP like '").append(zip).append("'");
			moreCondition = true;
		}
		return sb.toString() ;
	}

	/**
	 * Restrict the number of records
	 * @param maxRecordsCnt
	 * @return
	 */
	private String getRecordCountQuery(int maxRecordsCnt)
	{
		StringBuilder sb = new StringBuilder();
		sb.append(" fetch first ").append(maxRecordsCnt).append(" rows only");
		return sb.toString();
	}

	/**
	 * execute query
	 * @param query
	 * @return
	 */
	private List<AddressPoint> executeQuery(String query)
	{
		BeanPropertyRowMapper<AddressPoint> rowMapper = new BeanPropertyRowMapper<AddressPoint>(AddressPoint.class);
		List<AddressPoint> addressPointList = jdbcTemplate.query(query, rowMapper);
		return addressPointList;
	}

	/**
	 * Add condition based on country
	 * @param country
	 * @return
	 */
	private String getCountryQuery(String country)
	{
		StringBuilder countryQuery = new StringBuilder();
		if(country.equalsIgnoreCase(FrameworkConstant.COUNTRY_CN))
		{
			countryQuery.append(" AND P.AMREF6= 'CN' ");
			countryQuery.append(" AND NOT P.AMREF3= 'MZ' ");
		}
		if(country.equalsIgnoreCase(FrameworkConstant.COUNTRY_MX))
		{
			countryQuery.append(" AND P.AMREF6= 'MX'");
		}
		if(country.equalsIgnoreCase(FrameworkConstant.COUNTRY_US))
		{
			countryQuery.append(" AND P.AMREF6= '' ");
		}
		return countryQuery.toString();
	} // getCountryQuery

	/**
	* Parse the city for Mexican points.
	* Mexican cities have state in bytes 17-19. See view FBV074A
	* 
	* @param addressPointList
	*/
	private List<AddressPoint> parseCity(List<AddressPoint> addressPointList, String country)
	{
		if(StringUtils.isNotBlank(country) && country.equalsIgnoreCase(FrameworkConstant.COUNTRY_MX))
		{
			for(AddressPoint addressPoint:addressPointList)
			{ 
				if(StringUtils.isNotEmpty(addressPoint.getCity()))
				{
					addressPoint.setState(addressPoint.getCity().substring(16, 19));
					addressPoint.setCity(addressPoint.getCity().substring(0, 16));
					addressPoint.setZip(addressPoint.getZip().substring(0, addressPoint.getZip().length()-1).trim());
					//addressPoint.setZip(addressPoint.getZip().substring(0, 5));
				}
			}
		}
		return addressPointList;
	} // parseCity
}
