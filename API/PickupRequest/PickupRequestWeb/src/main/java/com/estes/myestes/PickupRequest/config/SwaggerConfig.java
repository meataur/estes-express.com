/**
 * @author: Ataur Rahman
 *
 * Creation date: 06/25/2018
 */

package com.estes.myestes.PickupRequest.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger documentation
 */
@Configuration
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
        		.apiInfo(apiInfo());
    } 
    
    private SecurityScheme securityScheme() {

        return new ApiKey("Bearer", "Authorization", "header");
    }
    
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title(env.getProperty("app.title"))
			.description(env.getProperty("app.description"))
			.version(env.getProperty("app.version"))
			.contact(new Contact(env.getProperty("app.contact.name"), env.getProperty("app.contact.url"), env.getProperty("app.contact.email")))
			.build();
	}
}