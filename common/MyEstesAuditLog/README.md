## My Estes Audit Log Integration for all  MyEstes Applications

1. Add the following dependency to MyEstes App Web Module

	```xml
		<!-- My Estes Audit Logs -->
		<dependency>
				<groupId>com.estes.myestes.MyEstesAuditLog</groupId>
				<artifactId>MyEstesAuditLog</artifactId>
				<version>1.0.0.0</version>
		</dependency>
		<!-- Add the following AspectJ version -->
		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>1.8.9</version>
		</dependency>
	```
2. Modify or replace the existing class __CustomAccessTokenConverter.java__ with the following code to get authenticated username in proper format.

	```java
	import java.util.HashMap;
	import java.util.Map;
	
	import org.springframework.security.oauth2.provider.OAuth2Authentication;
	import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
	
	public class CustomAccessTokenConverter extends DefaultAccessTokenConverter
	{
		@Override
		public OAuth2Authentication extractAuthentication(Map<String, ?> claims)
		{
			Map<String, Object> newClaims = new HashMap<>(claims);
			String username = (String) claims.get("username");
			newClaims.put("user_name", username);
			OAuth2Authentication authentication = super.extractAuthentication(newClaims);
			
			authentication.setDetails(newClaims);
			
			return authentication;
		}
		
	}
	```

3. Add the following class __AuditLogConfig.java__ in __config__ folder for java based configuration

	```java
	import org.springframework.context.annotation.Bean;
	import org.springframework.context.annotation.Configuration;
	import org.springframework.context.annotation.EnableAspectJAutoProxy;
	import org.springframework.jdbc.core.JdbcTemplate;
	
	import com.estes.myestes.MyEstesAuditLog.MyEstesAuditLogger;
	
	@Configuration
	@EnableAspectJAutoProxy(proxyTargetClass=false)
	public class AuditLogConfig {
		@Bean
		public MyEstesAuditLogger getMyEstesAuditLogger(JdbcTemplate jdbcTemplate){
			return new MyEstesAuditLogger(jdbcTemplate);
		}
	}
	```

4.  Update the __myestes_auditLogs.properties__ file located at (__/apps/WebAppData/MyEstes/config/myestes_auditLogs.properties__). Add your custom property for your MyEstes App Rest Controller Method as the below:
	```
	# My Estes Audit Logs
	# Sample Name
	#AuditLogs.AppName.ControllerName.ControllerMethodName=App Name 
	# AppName= App Name in context path
	# ControllerName=  Controller Class name
	# ControllerMethodName = Controller Public method name
	
	# Audit Logs for Rating application
	AuditLogs.Rating.RatingController.generateQuotes=Request Quote
	AuditLogs.Rating.RatingController.bookQuote=Book Quote
	AuditLogs.Rating.RatingController.selectQuote=Select Quote
	AuditLogs.Rating.RateQuotePdfController.printRateQuote=Download Rate Quote PDF
	
	#Audit Logs for Shipment Tracking
	AuditLogs.ShipmentTracking.TrackingController.trackShipments=Search Shipment Tracking
	```
5. Add the JVM Custom property:

	__Name__  = __myestes_auditLogs.properties__ 
	 
	__Value__ = __/apps/WebAppData/MyEstes/config/myestes_auditLogs.properties__ 
   