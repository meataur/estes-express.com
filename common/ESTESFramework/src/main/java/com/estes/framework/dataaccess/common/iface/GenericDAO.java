package com.estes.framework.dataaccess.common.iface;

import java.util.List;
import java.util.Map;

/**
 * Generic interface
 * @author SinghPa
 *
 * @param <K>
 * @param <E>
 */
public interface GenericDAO<K, E> {
	
	/**
	 * Method to save the entity operation.
	 * @param entity
	 */
	public void save(E entity);
	
	/**
	 * Method to update the entity operation.
	 * @param entity
	 */
	public Object update(E entity);
	
	/**
	 * Method to find entity by the identifier.
	 * @param id
	 */
	public E findById(Class<?> entityClass, K primaryKey);
	
	/**
	 * Method to find list by named query and name
	 * @param queryName
	 * @param paramName
	 * @return
	 */
	public List<E> findAllByProperty(String namedQuery, Map<String, Object> paramMap);
	
	/**
	 * Method to find all the records using the named query and name
	 * @param queryName
	 * @param paramName
	 * @return
	 */
	public List<E> findAll(String namedQuery);
	
	/**
	 * Method to find a single object using the named query and parameter map.
	 * @param queryName
	 * @param paramMap
	 * @return
	 */
	public Object findSingleObject(String namedQuery, Map<String, Object> paramMap);
}