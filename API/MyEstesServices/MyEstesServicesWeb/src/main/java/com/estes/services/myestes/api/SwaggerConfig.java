/**
 * @author: Todd Allen
 *
 * Creation date: 11/09/2018
 */

package com.estes.services.myestes.api;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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
 * Swagger documentation for My Estes ReST services
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
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
			.title("My Estes services")
			.description("Services used across My Estes applications")
			.version("2.0.0.0")
			.contact(new Contact("Estes IT", "http://www.estes-express.com", "eitjeesupport@estes-express.com"))
			.build();
	} // apiInfo
}
