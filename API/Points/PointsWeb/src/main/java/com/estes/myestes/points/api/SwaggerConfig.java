/**
 * @author: Todd Allen
 *
 * Creation date: 09/18/2018
 */

package com.estes.myestes.points.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger documentation for points inquiry ReST services
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig
{
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.estes.myestes.points"))
//			.paths(PathSelectors.regex("/api/myestes/Points/.*"))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo());
	} // api

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Estes Points services")
			.description("Search for Estes delivery points and find terminals servicing these points")
			.version("2.0.0.0")
			.contact(new Contact("Estes IT", "http://www.estes-express.com", "eitjeesupport@estes-express.com"))
			.build();
	} // apiInfo
}
