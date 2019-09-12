package com.estes.myestes.PickupRequest.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;

@EnableResourceServer
@EnableWebSecurity
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

	@Autowired
	private Environment env;
	
	@Override
	public void configure(final HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and().authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS,"/**").permitAll() //allow CORS option calls to all endpoints
				.antMatchers("/available_pickup_dates","/validate/pickup_date").permitAll()
				.antMatchers("/swagger-resources/**","/v2/api-docs/**","/swagger-ui.html","/webjars/**").permitAll()
				.antMatchers("/users","/shippers","/commodities").access("#oauth2.hasScope('"+env.getProperty("app.id.pickuprequest")+"')")
				.antMatchers(HttpMethod.GET, "/pickup").access("#oauth2.hasScope('"+env.getProperty("app.id.pickuphistory")+"')")
				.antMatchers(HttpMethod.POST, "/pickup").access("#oauth2.hasScope('"+env.getProperty("app.id.pickuprequest")+"')")
				.antMatchers("/bol/**").access("#oauth2.hasScope('"+env.getProperty("app.id.bol")+"') and #oauth2.hasScope('"+env.getProperty("app.id.pickuprequest")+"')")
		.and().authorizeRequests()
				.anyRequest().authenticated();
	}
	
	@Bean
	public AccessTokenConverter accessTokenConverter() {
		return new CustomAccessTokenConverter();
	}
	
	@Bean
	public RemoteTokenServices tokenServices(AccessTokenConverter accessTokenConverter) {
		final RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(env.getProperty("oauth2.checkTokenUrl"));
		tokenService.setClientId(env.getProperty("oauth2.clientId"));
		tokenService.setClientSecret(env.getProperty("oauth2.clientSecret"));
		tokenService.setAccessTokenConverter(accessTokenConverter);
		return tokenService;
	}

	
	@Bean
	public AuthenticationManager authenticationManager(RemoteTokenServices tokenServices) throws Exception {
		OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
		authenticationManager.setTokenServices(tokenServices);
		return authenticationManager;
	}
}