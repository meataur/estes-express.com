/**
 *
 */

package com.estes.myestes.recentshipments.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.recentshipments.util.RecentShipmentsConstant;

/**
 * Context listener for the application
 */
public class RecentShipmentsContextListener implements ServletContextListener {

	/**
	 * Perform actions when the application is loaded
	 * 
	 * @param ctx ServletContextEvent object
	 */
	public void contextInitialized(ServletContextEvent ctx)	{
		try {
			ESTESConfigUtil.loadProperty(RecentShipmentsConstant.LOGGING_CONFIG);
			ESTESLogger.initialize(ESTESConfigUtil.getProperty(RecentShipmentsConstant.LOGGER, RecentShipmentsConstant.LOCATION));
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "contextInitialized", "Successfully initialized the logger");
		} catch (Exception ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "contextInitialized", ex.getMessage(), ex);
			ex.printStackTrace();
		}
	} // contextInitialized

	/**
	 * Perform actions when the application is shut down
	 * 
	 * @param ce {@link }}ServletContextEvent object
	 */
	public void contextDestroyed(ServletContextEvent ce) {
		try {
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "contextDestroyed()",
					"Destroying Application Servlet Context.");

		} catch (Exception ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "contextDestroyed", ex.getMessage(), ex);
		}
	} // contextDestroyed
}