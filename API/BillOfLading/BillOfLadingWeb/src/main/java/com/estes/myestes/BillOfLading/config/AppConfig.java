package com.estes.myestes.BillOfLading.config;

import java.util.Locale;

import javax.sql.DataSource;

//import org.apache.log4j.PropertyConfigurator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

//import com.estes.framework.logger.ESTESLogger;

/**
 * 
 * 
 * @author rahmaat
 *
 * myestes.properties, myestes.config.dir and myestes.log.dir are defined as JVM custom Properties from WebSphere Application Server as 
 * Application servers > server1 > Process definition > Java Virtual Machine > Custom properties. These are the common configurations for all application
 * Application specific configurations will be in a separate directory (application name) in ${myestes.config.dir} folder. 
 * 
 * myestes.properties  C:/apps/WebAppData/MyEstes/config/myestes.properties for windows (development) and /apps/WebAppData/MyEstes/config/myestes.properties for linux (qa/uat/production)
 * myestes.config.dir  C:/apps/WebAppData/MyEstes/config for windows (development) and  /apps/WebAppData/MyEstes/config for linux (qa/uat/production)
 * myestes.log.dir  C:/apps/WebAppLogs/MyEstes    for windows (development) and /apps/WebAppLogs/MyEstes for linux (qa/uat/production)
 * 
 * 
 * 
 */


@Configuration
@PropertySources({
	@PropertySource("file:${myestes.properties}"),
	@PropertySource("file:${myestes.config.dir}/BillOfLading/application.properties"),
	@PropertySource("file:${myestes.config.dir}/BillOfLading/error_mapping.properties")
})
@Import({ OAuth2ResourceServerConfig.class,MethodSecurityConfig.class})
public class AppConfig {

	@Autowired
	private Environment env;
	 
	// define a bean for our security dataSource
	
	@Bean
    public DataSource dataSource() {
		/**
		 * DataSource Bean Creation
		 */
        return new JndiDataSourceLookup().getDataSource(env.getProperty("jndi.datasource"));
    }
	 
	@Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		/**
		 * JdbcTemplate Bean Creation
		 */
		JdbcTemplate jdbcTemplate = new JdbcTemplate();
		jdbcTemplate.setDataSource(dataSource);
		return jdbcTemplate;
    } 
	
	@Bean
    public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource source = new ReloadableResourceBundleMessageSource();
        source.setBasename("classpath:messages");
        source.setUseCodeAsDefaultMessage(true);
        source.setDefaultEncoding("UTF-8");
        source.setFallbackToSystemLocale(false);
        return source;
    }
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
	    localeResolver.setDefaultLocale(Locale.ENGLISH);
	    return localeResolver;
	}
}