package com.estes.framework.config;


import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;

import org.apache.commons.digester.Digester;

/**
 * Configuration loader class to load configuration file.
 * @modelguid {ADBBF1B9-5677-48E5-8FE8-36997E0ACD17}
 */
public class ConfigLoader
{

	/** @modelguid {7DBB564C-B9CE-44C0-A94C-4A5CE37A8582} */
	private static HashMap map = null;

	/** @modelguid {36571A7A-C43D-4272-98B6-CC797A8810C4} */
	public ConfigLoader ()
	{
	}


	/**
	 * A Getter method to return HashMap containing key value pair of config file properties
	 * @param InputStream is
	 * @return HashMap
	 * @throws Exception
	 * @modelguid {6932351C-45B1-44F8-A61C-5C65D03627C0}
	 */
	public static HashMap getList(InputStream  is) throws Exception
	{
	  digesterParseLoad(is);
	  return map ;
	}

	/**
	 * private method to parse the xml file using digester
	 * @modelguid {4FB081A3-9DDF-4F1C-84A4-E286ECDD8B36}
	 **/
	private static void digesterParseLoad(InputStream  is) throws Exception {
		Digester digester = new Digester();
		digester.setValidating(false);
		digester.addObjectCreate	("estes-config", ConfigLoader.class );
		digester.addObjectCreate	("estes-config/type", ConfigLoaderBean.class );

		digester.addCallMethod		("estes-config/type/property",   "setProperty"	, 	0);
		digester.addCallMethod		("estes-config/type/value",   "setValue"	, 	0);
		digester.addSetProperties	("estes-config/type/value", "name", "name");
		digester.addSetNext			("estes-config/type",               "addToList" );
	  	digester.parse(is);
	}

	/**
	 *
	 *
	 * @modelguid {861666BD-43A5-4F0F-8174-A2AF3D235C8D}
	 **/
	@SuppressWarnings("unchecked")
	public void addToList (ConfigLoaderBean bean)
	{
		if (map == null) map = new HashMap();
		map.put( bean.getProperty() , bean.getValue() );
	}



	/**
	 * Inner JavaBean class to holds properties of each config entery as desired
	 * @modelguid {D3DAAED0-7849-474D-87DB-2164723A986D}
	 */
	public static class ConfigLoaderBean
	{
		/** @modelguid {EA3F1967-D797-497A-9F11-D00A7C3EA9F2} */
		private String property ;
		/** @modelguid {2EF27BE0-B5A4-4C70-B7BD-46DF4E0FA663} */
		private LinkedHashMap valueMap    ;
		//Name identifies the attribute name for value
		private String name;


		/**
		 * @return
		 * @modelguid {034E5D81-4E85-4CBD-9EE5-82F2B9803409}
		 */
		public String getProperty()
		{
			return property;
		}

		/**
		 * @return
		 * @modelguid {30B5A9AC-AD9E-49FF-9E89-2F8398E2E6F2}
		 */
		public HashMap getValue()
		{
			return valueMap;
		}


		/**
		 * @param string
		 * @modelguid {D3A55D08-7C64-4301-89D2-0A3995F0E4FA}
		 */
		public  void setProperty(String string)
		{
			property = string;
		}

		@SuppressWarnings("unchecked")
		public  void setValue(String value)
		{
			if(valueMap == null) valueMap = new LinkedHashMap();
			String attributeValue = getName();
			if(attributeValue == null || attributeValue.trim().length() == 0)
			{
				attributeValue = "default";
			}
			valueMap.put(attributeValue, value);
		}

		/**
		 * @param string
		 * @modelguid {546FB15A-6A24-46F6-A7BF-F95A969B19C5}
		 */
		@SuppressWarnings("unchecked")
		public  void setValue(String key, String value)
		{
			if(valueMap == null) valueMap = new LinkedHashMap(16, 0.75f, false);
			if(key == null) key = value;
			valueMap.put(key, value);
		}

		/**
		 * Setter property for name
		 * @param key
		 */
		public  void setName(String key)
		{
			name = key;
		}

		/**
		 * Private getter property for name
		 * @return
		 */
		private String getName()
		{
			return name;
		}
	}
}