/**
 * @author: Todd Allen
 *
 * Creation date: 05/17/2018
 */

package com.estes.myestes.addressbook.api;

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
			.apis(RequestHandlerSelectors.basePackage("com.estes.myestes.addressbook"))
// TODO: replace .any with .regex if regex works
//			.paths(PathSelectors.regex("/api/myestes/AddressBook/.*"))
//			.paths(PathSelectors.ant("/api/myestes/AddressBook/**"))
			.paths(PathSelectors.any())
			.build()
			.apiInfo(apiInfo());
	} // api

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("My Estes Customer Address Book")
			.description("Customer address book maintenance")
			.version("1.0")
			.contact(new Contact("Estes IT", "http://www.estes-express.com", "eitjeesupport@estes-express.com"))
			.build();
	} // apiInfo
}
