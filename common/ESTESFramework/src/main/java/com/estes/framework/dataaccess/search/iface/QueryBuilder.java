package com.estes.framework.dataaccess.search.iface;

import com.estes.framework.dto.SearchCriteria;
/**
 * Interface to create query based on the search criteria.
 * @author singhpa
 *
 */
public interface QueryBuilder {
	
	/**
	 * Method to create the query string and return.
	 * @param searchCriteria
	 * @return
	 */
	String getQuery(SearchCriteria searchCriteria);
	
	
	/**
	 * Method to get the query for the count search.
	 * @param searchCriteria
	 * @return
	 */
	String getQueryCount(SearchCriteria searchCriteria);
	
}
