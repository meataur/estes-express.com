/**
 * @author: Ataur Rahman
 *
 * Creation date: 06/25/2018
 */

package com.estes.myestes.quickLinks.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger documentation
 */

@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private Environment env;
    
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
        		.select().apis(RequestHandlerSelectors.any())
        		.paths(PathSelectors.any())
        		.build()
        		.securitySchemes(Arrays.asList(securityScheme()))
        		.securityContexts(Arrays.asList(securityContext()))
        		.apiInfo(apiInfo());
    }
   
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(env.getProperty("app.title"))
			.description(env.getProperty("app.description"))
			.version(env.getProperty("app.version"))
			.contact(new Contact(env.getProperty("app.contact.name"), env.getProperty("app.contact.url"), env.getProperty("app.contact.email")))
			.build();
	}
	
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
        		.clientId(env.getProperty("app.clientId"))
        		.clientSecret(env.getProperty("app.clientSecret"))
        		.useBasicAuthenticationWithAccessCodeGrant(true).build();
    }

    private SecurityScheme securityScheme() {
 
    	GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(env.getProperty("app.tokenUrl"));

        SecurityScheme oauth = new OAuthBuilder().name("spring_oauth").grantTypes(Arrays.asList(grantType)).scopes(Arrays.asList(scopes())).build();
        return oauth;
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(Arrays.asList(new SecurityReference("spring_oauth", scopes()))).forPaths(PathSelectors.regex("/change_*")).build();
    }

    private AuthorizationScope[] scopes() {
        AuthorizationScope[] scopes = {};
        return scopes;
    }
}