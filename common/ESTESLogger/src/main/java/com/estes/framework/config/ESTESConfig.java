package com.estes.framework.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.naming.InitialContext;
import javax.naming.NamingException;

public class ESTESConfig {
	private static HashMap typeMap = null;

	public ESTESConfig() {
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
	public void loadProperty(String propertyName) throws Exception {
		String propertyFilePath = null;
		InputStream fi = null;
		try {
			propertyFilePath = loadFromContext(propertyName);

			if (propertyFilePath == null) {
				propertyFilePath = System.getProperty(propertyName);
			} 
			if (propertyFilePath ==null) {
				URL propertyfileURL = this.getClass().getClassLoader().getResource(propertyName);
				fi = this.getClass().getClassLoader().getResourceAsStream(propertyName);
				init(fi);
			} else {
				fi = new FileInputStream(new File(propertyFilePath));
				init(fi);
			}
		} catch (FileNotFoundException fnf) {
			throw fnf;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException("Property file path is null ");
		} finally {
			try {
				if (fi != null)
					fi.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Load property file from node context
	 * 
	 * @param propertyName
	 * @return
	 */
	private String loadFromContext(String propertyName) {
		String propertyFilePath = null;
		try {
			InitialContext ic = new InitialContext();
			propertyFilePath = (String) ic.lookup("thisNode/cell/legacyRoot/" + propertyName);
		} catch (NamingException e) {
			// e.printStackTrace();
		}
		return propertyFilePath;
	}

	/**
	 * Loads config file from a specified path
	 * 
	 * @param propertyName
	 * @throws Exception
	 */
	public void loadProperty(File propertyFilePath) throws Exception {
		FileInputStream fi = null;
		try {

			fi = new FileInputStream(propertyFilePath);
			init(fi);

		} catch (FileNotFoundException fnf) {
			throw fnf;
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			try {
				fi.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}

	public void init(InputStream is) {
		try {
			typeMap = ConfigLoader.getList(is);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** @modelguid {9C7BC29E-8F06-48FF-B47E-C9A35BEDA6E8} */
	public String getProperty(String key) {
		if (typeMap != null) {
			if (key != null) {
				LinkedHashMap valueMap = (LinkedHashMap) typeMap.get(key.trim());
				if (valueMap != null && valueMap.size() > 0) {

					return (String) valueMap.get((String) valueMap.keySet().iterator().next());
				}
			}
		}
		return null;
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
	public String getProperty(String key, String valueName) {
		if (typeMap != null) {
			// if the key is present
			if (key != null) {
				// The type contains a hash map as its value for a key.
				LinkedHashMap valueMap = (LinkedHashMap) typeMap.get(key.trim());
				// If the map has values
				if (valueMap != null && valueMap.size() > 0) {
					return (String) valueMap.get(valueName);
				}
			}
		}
		return null;
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
	public String getProperty(String key, int valueIndex) {
		if (typeMap != null) {
			// if the key is present
			if (key != null) {
				// The type contains a hash map as its value for a key.
				LinkedHashMap valueMap = (LinkedHashMap) typeMap.get(key.trim());
				// If the map has values
				if (valueMap != null && valueMap.size() > 0) {
					// Set a counter to keep a count of the loop
					int counter = 0;
					for (Iterator iter = valueMap.keySet().iterator(); iter.hasNext();) {
						// if the counter value matches the value index, return
						// the value
						if (counter == valueIndex) {
							// Return the value given the key
							return (String) valueMap.get((String) iter.next());
						} else {
							iter.next();
						}
						// increment the counter
						counter++;
					}

				}
			}
		}
		return null;
	}

	/**
	 * Overrides the config property value.
	 * 
	 * @param key
	 *            Representing the property name
	 * @param value
	 */
	@SuppressWarnings("unchecked")
	public void setProperty(String key, String valueName, String value) {
		if (typeMap != null) {
			// if the key is present
			if (key != null) {
				// The type contains a hash map as its value for a key.
				LinkedHashMap valueMap = (LinkedHashMap) typeMap.get(key.trim());
				// If the map has values
				if (valueMap != null && valueMap.size() > 0) {
					valueMap.put(valueName, value);
				}
			}
		}
	}

	/** @modelguid {FE4F1671-5809-43CE-B36D-0638DD880C49} */
	public HashMap getMap() {
		return typeMap;
	}

	/** @modelguid {8AB68955-B00C-42A9-A64C-D90C72B5C2CD} */
	public void destroy() {
		typeMap = null;
	}
}
