package com.estes.framework.cache.impl;

import com.estes.framework.cache.iface.CacheManager;
import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.InvalidationListener;

/**
 * This is the implementation class for cache manager, It calls the cache manager factory to get the right instance(WAS
 * or any other cache implementation) and returns and assigns it to the currentCacheManager. Specific(WAS or any other
 * cache implementation) cache managers calls the get, set and remove methods.
 */
public class CacheManagerImpl implements CacheManager {

	private static CacheManager cacheManagerFactory;

	private static CacheManager currentCacheManager;

	private CacheManagerImpl(String pApplicationName) {
		currentCacheManager = CacheManagerFactory.getCacheManagerInstance(pApplicationName);
	}

	/**
	 * Method available to create an instance of the cache manager.
	 * 
	 * @return singleton instance of the cache manager.
	 */
	public static CacheManager getInstance(String pApplicationName) {
		if (cacheManagerFactory == null) {
			synchronized (CacheManagerImpl.class) {
				if (cacheManagerFactory == null) {
					cacheManagerFactory = new CacheManagerImpl(pApplicationName);
				}
			}
		}
		return cacheManagerFactory;
	}

	@Override
	public Object get(String cacheName, String key) {
		return getCurrentCacheManager().get(CacheNameResolver.getCacheNameReference(cacheName), key);
	}

	@Override
	public void put(String cacheName, String key, Object value) {
		getCurrentCacheManager().put(CacheNameResolver.getCacheNameReference(cacheName), key, value);
	}

	@Override
	public void remove(String cacheName, String key) {
		getCurrentCacheManager().remove(CacheNameResolver.getCacheNameReference(cacheName), key);
	}

	@Override
	public void put(String cacheName, String key, Object value, int timeToLive) {
		getCurrentCacheManager().put(CacheNameResolver.getCacheNameReference(cacheName), key, value, timeToLive);
	}

	@Override
	public void destroyCache(String pApplicationName) {
		getCurrentCacheManager().destroyCache(pApplicationName);
	}

	/**
	 * @return the currentCacheManager
	 */
	private CacheManager getCurrentCacheManager() {
		return currentCacheManager;
	}

	@Override
	public void addInvalidationListener(String cacheName, InvalidationListener listener) {
		getCurrentCacheManager().addInvalidationListener(cacheName, listener);
	}

	@Override
	public DistributedMap getCacheInstance(String cacheNameRef) {
		return getCurrentCacheManager().getCacheInstance(CacheNameResolver.getCacheNameReference(cacheNameRef));
	}

}