/**
 * @author: Todd Allen
 *
 * Creation date: 05/10/2018
 */

package com.estes.ssdr.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger documentation for ReST services
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
				.apis(RequestHandlerSelectors.basePackage("com.estes.ssdr"))
//				.paths(PathSelectors.regex("/api/.*"))
				.paths(PathSelectors.any())
				.build()
				.apiInfo(apiInfo());
	} // api

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Document Image Retrieval")
			.description("Request Estes shipping documents")
			.version("2.0.0.0")
			.build();
	} // apiInfo
}
