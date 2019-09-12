/**
 * 
 */
package com.estes.framework.cache.impl;

import java.util.HashMap;
import java.util.Map;

import javax.naming.Binding;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

import com.estes.framework.cache.iface.CacheManager;
import com.estes.framework.logger.ESTESLogger;
import com.estes.framework.util.FrameworkConstant;
import com.ibm.websphere.cache.DistributedMap;
import com.ibm.websphere.cache.InvalidationListener;

/**
 * This class is used for using the websphere cache.
 * 
 */
public class WASCacheManagerImpl implements CacheManager {

	private static CacheManager cacheManager;

	private static Map<String, Boolean> cacheName2ListenerStatus = new HashMap<String, Boolean>();

	/**
	 * constructor which clears out the websphere cache before it starts.
	 */
	protected WASCacheManagerImpl(String pApplicationName) {
		destroyCache(pApplicationName);
	}

	/**
	 * Method available to create an instance of the cache manager.
	 * 
	 * @return singleton instance of the cache manager.
	 */
	static CacheManager getInstance(String pApplicationName) {
		if (cacheManager == null) {
			synchronized (WASCacheManagerImpl.class) {
				if (cacheManager == null) {
					cacheManager = new WASCacheManagerImpl(pApplicationName);
				}
			}
		}
		return cacheManager;
	}

	/**
	 * Gives the reference to the Cache Instance
	 * 
	 * @param cacheNameRef
	 * @return
	 */
	@Override
	public DistributedMap getCacheInstance(String cacheNameRef) {
		InitialContext ic;
		DistributedMap cacheInstance = null;
		try {
			ic = new InitialContext();
			cacheInstance = (DistributedMap) ic.lookup(cacheNameRef);
		} catch (NamingException e) {
			ESTESLogger.log(ESTESLogger.ERROR, WASCacheManagerImpl.class, "getCacheInstance(String cacheNameRef)",
					"Error while doing look up for the Cache Instance. Name looked up: " + cacheNameRef, e);
		}
		return cacheInstance;
	}

	@Override
	public Object get(String cacheName, String key) {
		DistributedMap dmCacheInstance = getCacheInstance(cacheName);
		if (dmCacheInstance == null) {
			return null;
		}

		return dmCacheInstance.get(key);
	}

	@Override
	public void put(String cacheName, String key, Object value) {
		DistributedMap dmCacheInstance = getCacheInstance(cacheName);
		if (dmCacheInstance != null) {
			dmCacheInstance.put(key, value);
		}

	}

	@Override
	public void addInvalidationListener(String cacheName, InvalidationListener listener) {
		DistributedMap dmCacheInstance = getCacheInstance(cacheName);
		if (dmCacheInstance != null && listener != null && !cacheName2ListenerStatus.containsKey(cacheName)) {
			dmCacheInstance.enableListener(true);
			boolean isListenerAdded = dmCacheInstance.addInvalidationListener(listener);
			if (isListenerAdded) {
				cacheName2ListenerStatus.put(cacheName, Boolean.TRUE);
				ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "addInvalidationListener()",
						"InvalidationListner added to cache: " + cacheName);
			} else {
				ESTESLogger.log(ESTESLogger.ERROR, WASCacheManagerImpl.class, "addInvalidationListener()",
						"Failed to add InvalidationListner to cache: " + cacheName);
			}
		}
	}

	@Override
	public void remove(String cacheName, String key) {
		DistributedMap dmCacheInstance = getCacheInstance(cacheName);
		if (dmCacheInstance != null) {
			dmCacheInstance.remove(key);
		}
	}

	@Override
	public void destroyCache(String pApplicationName) {
		ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "destroyCache(pApplicationName)", "Start Destroy Cache");
		InitialContext ic;
		DistributedMap cacheInstance = null;
		String cacheJndiLogicalRef = null;
		try {
			cacheJndiLogicalRef = FrameworkConstant.WAS_CACHE_JNDI_SUFFIX + FrameworkConstant.SEPARATOR + pApplicationName;
			ic = new InitialContext();

			cacheInstance = (DistributedMap) ic.lookup(cacheJndiLogicalRef);
			if (cacheInstance != null) {
				cacheInstance.clear();
				ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "destroyCache(pApplicationName)", "Destroyed Cache for JNDI Ref ::"
						+ cacheJndiLogicalRef);
			}
		} catch (NamingException e) {
			ESTESLogger.log(ESTESLogger.ERROR, WASCacheManagerImpl.class, "destroyCache(pApplicationName)",
					"Error while doing look up for the Cache Instance. Name looked up: " + cacheJndiLogicalRef + ", Exception Message ::"
							+ e.getMessage(), e);
		}
		ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "destroyCache(pApplicationName)", "End Destroy Cache");
	}

	@Override
	public void put(String cacheName, String key, Object value, int timeToLive) {
		// TODO Auto-generated method stub
	}

	/**
	 * Clears all the cache registered under the JNDI tree java:comp/env/cache in the Application Server. Should be used
	 * with caution if multiple applications are deployed in the Application server and has their cache registered under
	 * java:comp/env/cache
	 */
	public void destroyCache() {
		ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "destroyCache()", "Start Clear Cache");
		InitialContext ic;
		DistributedMap cacheInstance = null;
		try {
			ic = new InitialContext();

			NamingEnumeration<Binding> bindings = (NamingEnumeration<Binding>) ic.listBindings(FrameworkConstant.WAS_CACHE_JNDI_SUFFIX);
			Binding wsnBinding = null;
			while (bindings.hasMoreElements()) {
				wsnBinding = (Binding) bindings.nextElement();
				if (wsnBinding != null) {
					cacheInstance = (DistributedMap) wsnBinding.getObject();
					if (cacheInstance != null) {
						cacheInstance.clear();
						ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "destroyCache()",
								"Cleared Cache " + wsnBinding.toString());
					}
				}
			}
		} catch (NamingException e) {
			ESTESLogger.log(ESTESLogger.ERROR, WASCacheManagerImpl.class, "destroyCache()",
					"Error while doing look up for the Cache Instance under: " + FrameworkConstant.WAS_CACHE_JNDI_SUFFIX, e);
		}
		ESTESLogger.log(ESTESLogger.INFO, WASCacheManagerImpl.class, "destroyCache()", "End Clear Cache");
	}
}
