package com.estes.myestes.login.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


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
 * 
 */


@Configuration
@PropertySources({
	@PropertySource("file:${myestes.properties}")
})
public class AppConfig {
}