package com.estes.myestes.addressbook.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;
import org.springframework.security.oauth2.provider.expression.OAuth2MethodSecurityExpressionHandler;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true,proxyTargetClass=true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration
{
	@Autowired
	AuthenticationManager authenticationManager;

	@Override
	protected MethodSecurityExpressionHandler createExpressionHandler()
	{
		return new OAuth2MethodSecurityExpressionHandler();
	} // createExpressionHandler
}
