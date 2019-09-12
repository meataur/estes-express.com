package com.estes.framework.cache.iface;

import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.InvalidationListener;

/**
 * This is the interface for cache manager class.
 * 
 */
public interface CacheManager {

	/**
	 * This method will return data from cache if data exists in cache otherwise it will return null
	 * 
	 * @param cacheName
	 * @param key
	 * @return Object
	 */
	Object get(String cacheName, String key);

	/**
	 * This method is to facilitate removal of a cached element from the Cache
	 * 
	 * @param cacheName
	 *            The name of the cache where you need to perform the cleanup
	 * @param key
	 *            The cache Key to be evacuated.
	 */
	void remove(String cacheName, String key);

	/**
	 * This method puts the passed Object at the specific cache area. If the cache area is not been initialized, then it
	 * create one, puts the object in a map and then puts that map at the cache area.
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	void put(String cacheName, String key, Object value);

	/**
	 * 
	 * @param cacheName
	 * @param key
	 * @param value
	 * @param timeToLive
	 */
	void put(String cacheName, String key, Object value, int timeToLive);

	/**
	 * Destroys the cache associated with the application name.
	 */
	void destroyCache(String pApplicationName);

	/**
	 * Provides the application an extension to add their own Invalidation Listener.
	 * 
	 * @param cacheName
	 * @param listener
	 */
	void addInvalidationListener(String cacheName, InvalidationListener listener);
	
	/**
	 * 
	 * @param cacheNameRef
	 * @return
	 */
	DistributedMap getCacheInstance(String cacheNameRef);
}