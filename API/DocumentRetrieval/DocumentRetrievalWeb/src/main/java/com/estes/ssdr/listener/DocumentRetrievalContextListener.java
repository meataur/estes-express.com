/**
 * @author: Todd Allen
 *
 * Creation date: 09/23/2016
 *
 */

package com.estes.ssdr.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.ssdr.util.DocRetrievalConstant;

/**
 * Context listener for the application
 */
public class DocumentRetrievalContextListener implements ServletContextListener {

	/**
	 * Perform actions when the application is loaded
	 * 
	 * @param ctx ServletContextEvent object
	 */
	public void contextInitialized(ServletContextEvent ctx)
	{
		try {
			ESTESConfigUtil.loadProperty(DocRetrievalConstant.LOGGING_CONFIG);
			ESTESLogger.initialize(ESTESConfigUtil.getProperty(DocRetrievalConstant.LOGGER, DocRetrievalConstant.LOCATION));
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
