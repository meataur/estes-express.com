package com.estes.framework.dataaccess.search.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.dataaccess.search.iface.SearchDao;
import com.estes.framework.dto.SearchCriteria;
import com.estes.framework.dto.SearchResults;


/**
 * Dao class for search this class will use spring NamedParameterJdbcDaoSupport.
 * @author SinghPa
 *
 */
@SuppressWarnings("rawtypes")
@Repository("searchDao")
@Scope("prototype")
public class SearchDaoImpl implements SearchDao{
	
	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public NamedParameterJdbcTemplate getNamedParameterJdbcTemplate() {
		return namedParameterJdbcTemplate;
	}

	public void setNamedParameterJdbcTemplate(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	/**
	 * This is a generic method which executes the search for any given search criteria. 
	 * @param query
	 * @param searchCriteria
	 * @param resultClass
	 * @param currentIndexNumber
	 * @param maxRowsToFetch
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List executeSearch(String query, SearchCriteria searchCriteria, Class resultClass, int maxRowsToFetch)  {
		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(searchCriteria);
		BeanPropertyRowMapper bprm = new BeanPropertyRowMapper(resultClass);
		
		// Calculate start and end element row for the selected page.
		int start = ((searchCriteria.getPageNumber() - 1)* searchCriteria.getPageSize())+1;
		int end = start + searchCriteria.getPageSize()-1;
		
		StringBuilder queryEndString = new StringBuilder(" as temp where ROW_ID BETWEEN " + start + " and " + end );
		StringBuilder queryStringBuilder = new StringBuilder("Select * from (" + query  + ")" + queryEndString.toString());
		
		List resultList = (List) namedParameterJdbcTemplate.query(queryStringBuilder.toString(), namedParameters, bprm);
		return resultList;
	}
	
	/**
	 * This method returns the result count for the given search criteria. 
	 * @param query
	 * @param searchCriteria
	 * @return
	 */
	protected int executeCountSearch(String query, SearchCriteria searchCriteria)  {
		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(searchCriteria);
		int count = namedParameterJdbcTemplate.queryForObject(query, namedParameters, Integer.class);

		return count;
	}

	/*
	 * This method will be overriden by the specific searches.
	 * (non-Javadoc)
	 * @see com.estes.framework.dataaccess.search.iface.SearchDao#getSearchResults(com.estes.framework.domain.SearchCriteria)
	 */
	public SearchResults getSearchResults(SearchCriteria searchCriteria) {
		return null;
	}

	/*
	 * This method will be overriden by the specific searches.
	 * (non-Javadoc)
	 * @see com.estes.framework.dataaccess.search.iface.SearchDao#getCount(com.estes.framework.domain.SearchCriteria)
	 */
	public int getCount(SearchCriteria searchCriteria) {
		return 0;
	}

	
	/*
	 * (non-Javadoc)
	 * @see com.estes.framework.dataaccess.search.iface.SearchDao#executeFullSearch(java.lang.String, com.estes.framework.dto.SearchCriteria, java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public List executeFullSearch(String query, SearchCriteria searchCriteria, Class resultClass) {
		BeanPropertySqlParameterSource namedParameters = new BeanPropertySqlParameterSource(searchCriteria);
		StringBuilder queryStringBuilder = new StringBuilder(query);
		BeanPropertyRowMapper bprm = new BeanPropertyRowMapper(resultClass);
		List resultList = (List) namedParameterJdbcTemplate.query(queryStringBuilder.toString(), namedParameters, bprm);
		return resultList;
	}
}
