package com.estes.myestes.addressbook.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class CommonsUploadConfig {

	public CommonsUploadConfig() {
		// TODO Auto-generated constructor stub
	}

	@Bean
	public CommonsMultipartResolver  multipartResolver()
	{
		CommonsMultipartResolver resolver = new CommonsMultipartResolver();
		// Set max file upload size at 2 MB
		resolver.setMaxUploadSize(2097152);
		return resolver;
	}
}
