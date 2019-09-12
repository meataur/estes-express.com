package com.estes.myestes.ImageViewing.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Locale;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import com.estes.framework.logger.ESTESLogger;
/**
 * 
 * 
 * @author Ataur Rahman<ataur.me71@gmail.com>
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
	@PropertySource("file:${myestes.config.dir}/ImageViewing/application.properties")
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
	
	
	/**
	 * FTP Connection for downloading WR document
	 */
	
	  @Bean
    public DefaultFtpSessionFactory ftpSessionFactory() {
        DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
        sf.setHost(env.getProperty("wr.ftp.host"));
        sf.setUsername(env.getProperty("wr.ftp.user"));
        sf.setPassword(env.getProperty("wr.ftp.password"));
        sf.setPort(Integer.valueOf(env.getProperty("wr.ftp.port")));
        return sf;
    }
	  
	/**
	 * FtpRemoteFileTemplate  
	 * @param sf - DefaultFtpSessionFactory
	 * @return
	 */
	@Bean
    public FtpRemoteFileTemplate template(DefaultFtpSessionFactory sf) {
        return new FtpRemoteFileTemplate(sf);
    }
	
	/**
	 * SMTP Mail Connection
	 */
	
	@Bean
	public JavaMailSender getJavaMailSender() {
	    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
	    mailSender.setHost(env.getProperty("smtp.host"));
	    mailSender.setPort(Integer.parseInt(env.getProperty("smtp.port")));
//	     
//	    mailSender.setUsername(env.getProperty("mail.username"));
//	    mailSender.setPassword(env.getProperty("mail.password"));
//	     
//	    Properties props = mailSender.getJavaMailProperties();
//	    props.put("mail.transport.protocol", "smtp");
//	    props.put("mail.smtp.auth", "false");
//	    props.put("mail.smtp.starttls.enable", "true");
//	    props.put("mail.debug", "true");
	     
	    return mailSender;
	}
	
	@Bean
	public SimpleMailMessage templateSimpleMessage() {
		
	    SimpleMailMessage message = new SimpleMailMessage();
	    
	    message.setFrom(env.getProperty("mail.from"));
	    message.setSubject(env.getProperty("mail.subject"));
	    
	    String content=null;
	    try {
	    	System.out.println(env.getProperty("mail.template"));
			 content = new String(Files.readAllBytes(Paths.get(env.getProperty("mail.template"))), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "templateSimpleMessage()","email.html encoding is not utf-8: "+e.getMessage());
		} catch (IOException e) {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "templateSimpleMessage()","email.html is not found: "+e.getMessage());
		}catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "templateSimpleMessage()","email.html is not found: "+e.getMessage());
		}
	    
	    
	    if(content==null){
	    	content = "Your requested documents are attached here. Please let us know if you are not the recipient."; 
	    }
	    
	    message.setText(content);
	    return message;
	}
}