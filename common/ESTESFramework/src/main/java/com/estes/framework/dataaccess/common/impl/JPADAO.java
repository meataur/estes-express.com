package com.estes.framework.dataaccess.common.impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;

import com.estes.framework.dataaccess.common.iface.GenericDAO;

/**
 * Framework DAO class to handle the basic operations.
 * 
 * @author singhpa
 */
public abstract class JPADAO implements GenericDAO<Integer, Object> {

	@PersistenceContext
	protected EntityManager entityManager;
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/** {@inheritDoc} */
	@Override
	public void save(Object entity) {
		entityManager.persist(entity);
	}

	/** {@inheritDoc} */
	@Override
	public Object update(Object entity) {
		return entityManager.merge(entity);
	}

	/** {@inheritDoc} */
	@Override
	public Object findById(Class<?> entityClass, Integer primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	/** {@inheritDoc} */
	@Override
	public List<Object> findAllByProperty(String namedQuery, Map<String, Object> paramMap) {
		List<Object> objList = null;
		if (StringUtils.isNotBlank(namedQuery)) {
			Query query = getEntityManager().createNamedQuery(namedQuery);
			if (paramMap != null && !paramMap.isEmpty()) {
				Iterator<Entry<String, Object>> it = paramMap.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, Object> params = it.next();
					String key = (String) params.getKey();
					query.setParameter(key, params.getValue());
				}
			}
			@SuppressWarnings("unchecked")
			List<Object> objListRaw = query.getResultList();
			objList = objListRaw;
		}
		return objList;
	}

	/** {@inheritDoc} */
	@Override
	public List<Object> findAll(String namedQuery) {
		List<Object> objList = null;
		if (StringUtils.isNotBlank(namedQuery)) {
			Query query = getEntityManager().createQuery(namedQuery);
			@SuppressWarnings("unchecked")
			List<Object> objListRaw = query.getResultList();
			objList = objListRaw;
		}
		return objList;
	}
	
	/** {@inheritDoc} */
	@Override
	public Object findSingleObject(String namedQuery, Map<String, Object> paramMap) {
		if (StringUtils.isNotBlank(namedQuery)) {
			try {
				Query query = getEntityManager().createNamedQuery(namedQuery);
				if (paramMap != null && !paramMap.isEmpty()) {
					Iterator<Entry<String, Object>> it = paramMap.entrySet().iterator();
					while (it.hasNext()) {
						Entry<String, Object> params = it.next();
						String key = (String) params.getKey();
						query.setParameter(key, params.getValue());
					}
				}
				@SuppressWarnings("unchecked")
				List<Object> objList = query.getResultList();
				if(objList != null && !objList.isEmpty()){
					return objList.get(0);
				}
			} catch (NoResultException noResult) {
				return null;
			}
		}
		return null;
	}
}