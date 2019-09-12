/**
 * @author: Todd Allen
 *
 * Creation date: 10/01/2018
 */

package com.estes.myestes.invoiceinquiry.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.utils.InvoiceInquiryConstants;

/**
 * Context listener for the application
 */
public class InvoiceInquiryContextListener implements ServletContextListener
{
	/**
	 * Perform actions when the application is loaded
	 * 
	 * @param ctx ServletContextEvent object
	 */
	public void contextInitialized(ServletContextEvent ctx)
	{
		try {
			ESTESConfigUtil.loadProperty(InvoiceInquiryConstants.APP_CONFIG);
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), ESTESConfigUtil.getProperty(InvoiceInquiryConstants.LOGGER, InvoiceInquiryConstants.LOCATION), "TEST");
			ESTESLogger.initialize(ESTESConfigUtil.getProperty(InvoiceInquiryConstants.LOGGER, InvoiceInquiryConstants.LOCATION));
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
