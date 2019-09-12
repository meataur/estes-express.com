package com.estes.framework.cache.impl;

import com.estes.framework.util.FrameworkConstant;

/**
 * 
 * This class resolves the name of the cache location. If the System property
 * useEHCache is set to Y then it returns the name(for EHCache) as it is; else
 * it constructs the cache name by prefixing the WAS JNDI prefix to the name(for
 * Websphere Cache).
 * 
 */
public class CacheNameResolver {

	/**
	 * It appends and returns the JNDI location for the WAS Cache and only name
	 * for EHCache.
	 * 
	 * @param name
	 * @return
	 */
	public static String getCacheNameReference(String name) {

		String useEHCache = System.getProperty(FrameworkConstant.SYSTEM_PROPERTY_USE_EH_CACHE);
		if (useEHCache != null && FrameworkConstant.YES.equalsIgnoreCase(useEHCache.trim())) {
			return name;
		} else {
			return FrameworkConstant.WAS_CACHE_JNDI_SUFFIX + FrameworkConstant.SEPARATOR + name;
		}
	}
}
