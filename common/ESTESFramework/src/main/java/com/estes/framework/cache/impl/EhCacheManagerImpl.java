package com.estes.framework.cache.impl;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import com.estes.framework.cache.iface.CacheManager;
import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.InvalidationListener;

/**
 * 
 * This class is used for the ehcache
 * 
 */
public class EhCacheManagerImpl implements CacheManager {

	private static CacheManager cacheManager;

	/**
	 * Method available to create an instance of the cache manager.
	 * 
	 * @return Returns singleton instance of the cache manager.
	 */
	static CacheManager getInstance() {
		if (cacheManager == null) {
			cacheManager = new EhCacheManagerImpl();
		}
		return cacheManager;
	}

	@Override
	public Object get(String cacheName, String key) {
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getInstance();
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			return null;
		}
		Element element = cache.get(key);
		if (element != null) {
			return (Object) element.getObjectValue();
		} else {
			return null;
		}
	}

	@Override
	public void remove(String cacheName, String key) {
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getInstance();
		Cache cache = cacheManager.getCache(cacheName);
		if (cache != null) {
			cache.remove(key);
		}
	}

	@Override
	public void put(String cacheName, String key, Object value) {
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getInstance();
		Cache cache = cacheManager.getCache(cacheName);
		if (cache == null) {
			cacheManager.addCache(cacheName);
		}
		Element element = new Element(key, value);
		cacheManager.getCache(cacheName).put(element);
	}

	@Override
	public void put(String cacheName, String key, Object value, int timeToLive) {
		put(cacheName, key, value);

	}

	@Override
	public void destroyCache(String pApplicationName) {
		net.sf.ehcache.CacheManager cacheManager = net.sf.ehcache.CacheManager.getInstance();
		cacheManager.shutdown();
	}

	@Override
	public void addInvalidationListener(String cacheName, InvalidationListener listener) {
		// TODO Auto-generated method stub

	}

	@Override
	public DistributedMap getCacheInstance(String cacheNameRef) {
		// TODO Auto-generated method stub
		return null;
	}

}
