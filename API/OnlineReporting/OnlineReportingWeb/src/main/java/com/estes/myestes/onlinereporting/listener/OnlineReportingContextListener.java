/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.onlinereporting.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.onlinereporting.utils.OnlineReportingConstant;

/**
 * Context listener for the application
 */
@WebListener
public class OnlineReportingContextListener implements ServletContextListener
{
	/**
	 * Perform actions when the application is loaded
	 * 
	 * @param ctx ServletContextEvent object
	 */
	public void contextInitialized(ServletContextEvent ctx)
	{
		try {
			ESTESConfigUtil.loadProperty(OnlineReportingConstant.APP_CONFIG);
			ESTESLogger.initialize(ESTESConfigUtil.getProperty(OnlineReportingConstant.LOGGER, OnlineReportingConstant.LOCATION));
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
		try {
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "contextDestroyed()",
					"Destroying Application Servlet Context.");

		} catch (Exception ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "contextDestroyed", ex.getMessage(), ex);
		}
	} // contextDestroyed
}
