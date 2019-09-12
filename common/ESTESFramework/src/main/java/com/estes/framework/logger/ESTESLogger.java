/*
 * Created on Dec 16, 2004
 *
 */
package com.estes.framework.logger;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

/**
 * Custom wrapper for Log4j Framework
 * @author Farr Shakil
 *
 * @modelguid {9619D53D-0192-448D-B3AB-211E5D5FDD8C}
 */
public class ESTESLogger  
{
	/** @modelguid {835ACBC9-1FB1-4C8F-9773-C16948110B23} */
	final static public int OFF 	= Level.OFF_INT ;
	/** @modelguid {A180A519-F08F-4194-8F18-577798922FC5} */
	final static public int FATAL 	= Level.FATAL_INT;
	/** @modelguid {B15F4CF0-89BB-4E4E-87A2-9BA0571ABCB0} */
	final static public int ERROR 	= Level.ERROR_INT;
	/** @modelguid {517BC383-367D-4833-A59B-3F2B003D4E82} */
	final static public int WARN 	= Level.WARN_INT ;
	/** @modelguid {905DBB75-9FB0-48B1-A39B-6AF16443B5DB} */
	final static public int INFO 	= Level.INFO_INT;
	/** @modelguid {87BE0F2B-B635-4378-A7E7-EAE23BD6FDA9} */
	final static public int DEBUG 	= Level.DEBUG_INT;
	/** @modelguid {2B30C376-A1C7-4759-8C49-D986D9B64732} */
	final static public int ALL 	= Level.ALL_INT;

	/** 
	 * private constructor
	 * @modelguid {D67458CA-86D4-461C-BCA0-F6DE9B2E3561} 
	 **/
	private ESTESLogger()
	{

	}
	/**
	 * Initialize logger
	 * @param fileName  log4j configuration file path
	 * @modelguid {0820FEFC-FB39-4372-8592-C1683F3D6761}
	 */
	public static void initialize(String fileName)
	{
		try
		{
			if (fileName!= null)
			{
				if (fileName.endsWith(".xml")){
					//load custom XML configuration
					DOMConfigurator.configureAndWatch(fileName);
				}else {
					PropertyConfigurator.configure(fileName);
				}
			}else {
				initialize();
			}
			Logger.getRootLogger().info("|-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||");
			Logger.getRootLogger().info("|-||-||    Logger Initialized Successful            -||-||-||-||-||");
			Logger.getRootLogger().info("|-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Initialize logger
	 * @modelguid {0820FEFC-FB39-4372-8592-C1683F3D6761}
	 * 
	 */
	public static void initialize()
	{
		try
		{
			BasicConfigurator.configure();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Shutdown logger
	 * @modelguid {0820FEFC-FB39-4372-8592-C1683F3D6761}
	 */
	public static void shutdown()
	{
		try
		{
			Logger.getRootLogger().info("|-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||");
			Logger.getRootLogger().info("|-||-||          Shut Down Logger                   -||-||-||-||-||");
			Logger.getRootLogger().info("|-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||-||");
			
			LogManager.shutdown(); 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	
	/**
	 * Logs only if debug is enabled
	 * @param clazz  Class name
	 * @param message  message to display
	 */
	public static void log(Class clazz, String message)
	{
		try
		{
			if (Logger.getLogger(clazz).isDebugEnabled()) 
			{
				Logger.getLogger(clazz).log(Level.toLevel(ESTESLogger.DEBUG), message );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Logs only if debug is enabled
	 * @param clazz  Class
	 * @param method  method name
	 * @param message  message to display
	 */
	public static void log(Class clazz, String method, String message)
	{
		try
		{
			if (Logger.getLogger(clazz).isDebugEnabled()) 
			{
				Logger.getLogger(clazz).log(Level.toLevel(ESTESLogger.DEBUG), formatLog(method, message) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param level  logger level
	 * @param clazz  class 
	 * @param method  method name
	 * @param message  message to display
	 */ 
	public static void log(int level, Class clazz, String method, String message)
	{
		try
		{
			Logger.getLogger(clazz).log(Level.toLevel(level), formatLog(method, message) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param level logger level
	 * @param clazz class
	 * @param method method name
	 * @param message message to display
	 * @param ex exception
	 */
	public static void log(int level, Class clazz, String method, String message, Throwable ex)
	{
		try
		{
			Logger.getLogger(clazz).log(Level.toLevel(level), formatLog(method, message), ex );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param clazz
	 * @param method
	 * @param message
	 */
	public static void log(String clazz, String method, String message)
	{
		try
		{
			if (Logger.getLogger(clazz).isDebugEnabled()) 
			{
				Logger.getLogger(clazz).log(Level.toLevel(ESTESLogger.DEBUG), formatLog(method, message) );
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param level
	 * @param clazz
	 * @param method
	 * @param message
	 */ 
	public static void log(int level, String clazz, String method, String message)
	{
		try
		{
			Logger.getLogger(clazz).log(Level.toLevel(level), formatLog(method, message) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param level
	 * @param clazz
	 * @param method
	 * @param message
	 * @param ex
	 */
	public static void log(int level, String clazz, String method, String message, Throwable ex)
	{
		try
		{
			Logger.getLogger(clazz).log(Level.toLevel(level), formatLog(method, message), ex );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}


	/**
	 * Private method to format log messages
	 * @param methodName
	 * @param message
	 * @return Formatted Log method Name, Message
	 */
	private static String formatLog( String methodName, String message)
	{
		return  methodName + ": " + message;
	}
	
}
