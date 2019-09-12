package com.estes.myestes.rating.template;

import java.util.Locale;

import com.estes.framework.logger.ESTESLogger;

import freemarker.template.Configuration;
import freemarker.template.TemplateExceptionHandler;

/*
 * Singleton allowing only the creation of one instance of the FreeMakerConfiguration
 * 
 * @http://freemarker.org/docs/pgui_quickstart_all.html
 * 
 */
public class FreeMakerConfiguration
{
	private static FreeMakerConfiguration freeMakerConfiguration = new FreeMakerConfiguration();

	private Configuration cfg = null;

	private FreeMakerConfiguration()
	{
		try {
			// 1. Configure FreeMarker
			// You should do this ONLY ONCE, when your application starts,
			// then reuse the same Configuration object elsewhere.
			cfg = new Configuration();

			cfg.setClassForTemplateLoading(this.getClass(), "/fm_templates/");
			cfg.setDefaultEncoding("UTF-8");
			cfg.setLocale(Locale.US);
			cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
			// TODO: Fix this method of exception handling
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "FreeMakerConfiguration()", "** Error configuring Freemarker", e);
		}
	}

	public static FreeMakerConfiguration getInstance() {
		return freeMakerConfiguration;
	}

	public Configuration getConfiguration() {
		return cfg;
	}
}
