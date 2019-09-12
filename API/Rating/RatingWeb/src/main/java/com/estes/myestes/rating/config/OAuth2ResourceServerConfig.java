package com.estes.myestes.rating.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
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

@Configuration
@EnableResourceServer
@EnableWebSecurity
@PropertySources({
	@PropertySource("file:${myestes.properties}")
})
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter
{
	@Autowired
	private Environment env;

	@Bean
	public AccessTokenConverter accessTokenConverter()
	{
		return new CustomAccessTokenConverter();
	} // accessTokenConverter
	
	@Primary
	@Bean
	public AuthenticationManager authenticationManager() throws Exception
	{
		OAuth2AuthenticationManager authenticationManager = new OAuth2AuthenticationManager();
		authenticationManager.setTokenServices(tokenServices());
		return authenticationManager;
	} // authenticationManager

    @Override
	public void configure(final HttpSecurity http) throws Exception
	{
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
		.and().authorizeRequests()
			// Always allow OPTIONS request to all URL endpoints
			.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
			// Always allow access to Swagger docs
			.antMatchers("/swagger-ui.html", "/swagger-resources/**", "/webjars/**", "/v2/api-docs/**").permitAll()
			.antMatchers("/accessorials", "/requestQuote", "/serviceLevels").permitAll()
			.antMatchers("/quote", "/reference", "/search").authenticated()
//			.antMatchers("/**").authenticated()
		.and().authorizeRequests()
			// Authorize access for history and get quote services
			.antMatchers("/quote", "/reference", "/search").access("#oauth2.hasScope('" + env.getProperty("app.id.rating.hist") + "')");
//			.antMatchers("/**").access("#oauth2.hasScope('" + env.getProperty("app.id.rating.ltl") + "')")
//			.antMatchers("/**").access("#oauth2.hasScope('" + env.getProperty("app.id.rating.timecritical") + "')")
//			.antMatchers("/**").access("#oauth2.hasScope('" + env.getProperty("app.id.rating.vtl") + "')");
	} // configure

    @Bean
	public RemoteTokenServices tokenServices()
    {
		final RemoteTokenServices tokenService = new RemoteTokenServices();
		tokenService.setCheckTokenEndpointUrl(env.getProperty("oauth2.checkTokenUrl"));
		tokenService.setClientId(env.getProperty("oauth2.clientId"));
		tokenService.setClientSecret(env.getProperty("oauth2.clientSecret"));
		tokenService.setAccessTokenConverter(accessTokenConverter());
		return tokenService;
	} // tokenServices
}
