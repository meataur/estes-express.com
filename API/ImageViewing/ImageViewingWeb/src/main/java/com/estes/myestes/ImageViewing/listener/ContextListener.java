package com.estes.myestes.ImageViewing.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.ImageViewing.util.AppConstant;

/**
 * Context listener for the application
 */
@WebListener
public class ContextListener implements ServletContextListener
{

	/**
	 * Perform actions when the application is loaded
	 * 
	 * @param ctx ServletContextEvent object
	 */
	public void contextInitialized(ServletContextEvent ctx)
	{
		try {
            ESTESConfigUtil.loadProperty(AppConstant.APP_CONFIG);
            ESTESLogger.initialize(ESTESConfigUtil.getProperty(AppConstant.LOGGER, AppConstant.LOCATION));
            ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "contextInitialized", "Logger Initialization");
		} catch (Exception ex) {
		    ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "contextInitialized", ex.getMessage(), ex);
		            ex.printStackTrace();
		}

	} // contextInitialized

	/**
	 * Perform actions when the application is shut down
	 * 
	 * @param ce {@link ServletContextEvent} object
	 */
	public void contextDestroyed(ServletContextEvent ce)
	{
		ESTESLogger.log(ESTESLogger.DEBUG,getClass(), ce.getSource().toString()+" Context Destroyed", "");
	} // contextDestroyed
}