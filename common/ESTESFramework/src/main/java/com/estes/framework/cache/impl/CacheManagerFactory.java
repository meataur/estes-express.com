/**
 * 
 */
package com.estes.framework.cache.impl;

import com.estes.framework.cache.iface.CacheManager;
import com.estes.framework.util.FrameworkConstant;

public class CacheManagerFactory {

	/**
	 * If the System property useEHCache is set to Y then it returns EHCache
	 * instance else it returns Websphere Cache instance.
	 * 
	 * @return
	 */
	public static CacheManager getCacheManagerInstance(String pApplicationName) {

		String useEHCache = System.getProperty(FrameworkConstant.SYSTEM_PROPERTY_USE_EH_CACHE);
		if (useEHCache != null && FrameworkConstant.YES.equalsIgnoreCase(useEHCache.trim())) {
			return EhCacheManagerImpl.getInstance();
		} else {
			return WASCacheManagerImpl.getInstance(pApplicationName);
		}
	}
}
