package com.estes.framework.config;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;

/**
 * A utility class to load Configuration files. It is called from Plugin or
 * startup classes
 * 
 * @modelguid {755147CE-75AA-4556-8FAD-7E74EB9BC438}
 */
public class ESTESConfigUtil {
	
	private static ESTESConfig estesConfig = new ESTESConfig();

	private ESTESConfigUtil() {
	}

	/**
	 * 
	 * Method to Load XML config File. This method requires a <br>
	 * property either defined in the jndi tree or as a system property
	 * 
	 * @param propertyName
	 *            (property name defined in the websphere
	 *            "thisNode/cell/legacyRoot/" or System property
	 * @throws Exception
	 */
	public static void loadProperty(String propertyName) throws Exception {
		estesConfig.loadProperty(propertyName);
	}

	/**
	 * Loads config file from a specified path
	 * 
	 * @param propertyName
	 * @throws Exception
	 */
	public static void loadProperty(File propertyFilePath) throws Exception {
		estesConfig.loadProperty(propertyFilePath);
	}

	public static void init(InputStream is) {
		try {
			estesConfig = new ESTESConfig();
			estesConfig.init(is);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** @modelguid {9C7BC29E-8F06-48FF-B47E-C9A35BEDA6E8} */
	public static String getProperty(String key) {
		return estesConfig.getProperty(key);
	}

	/**
	 * Gets the property value of its children for a property given the name of
	 * the attribute.
	 * 
	 * @param key
	 *            Representing the property name
	 * @param valueIndex
	 *            Representing the child's index
	 * @return Value matching the property name and its child(value)'s attribute
	 *         name
	 */
	public static String getProperty(String key, String valueName) {
		return estesConfig.getProperty(key, valueName);
	}

	/**
	 * Gets the property value of its children(specified by valueIndex) for a
	 * property.
	 * 
	 * @param key
	 *            Representing the property name
	 * @param valueIndex
	 *            Representing the child's index
	 * @return Value matching the property name and its child(value)'s index
	 *         number
	 */
	public static String getProperty(String key, int valueIndex) {
		return estesConfig.getProperty(key, valueIndex);
	}

	/**
	 * Overrides the config property value.
	 * 
	 * @param key
	 *            Representing the property name
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public static void setProperty(String key, String valueName, String value) {
		estesConfig.setProperty(key, valueName, value);
	}

	public static HashMap getMap() {
		return estesConfig.getMap();
	}

	public static void destroy() {
		estesConfig.destroy();
	}
}
