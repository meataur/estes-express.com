package com.estes.myestes.PickupRequest.config;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.estes.myestes.PickupRequest.web.jpa.repository")
@EnableSpringDataWebSupport
public class JpaConfig {
	
	@Autowired
	private Environment env;

	@Bean
    public DataSource sqlServerDataSource() {
		/**
		 * DataSource Bean Creation
		 */
        return new JndiDataSourceLookup().getDataSource(env.getProperty("jndi.datasource.sqlServer"));
    }
	
	/*
     * Configuration for Hibernate JPA 
     * The following configuration is for Hibernate JPA Provider
     */
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
        return jpaVendorAdapter;
    }
    
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        // properties.put("hibernate.hbm2ddl.auto", env.getRequiredProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        properties.put("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
        return properties;
    }
    
    
    /**
     * To use openjpa the following configuration is needed.
     */
    /*
    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
    	org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter jpaVendorAdapter = new org.springframework.orm.jpa.vendor.OpenJpaVendorAdapter();
        return jpaVendorAdapter;
    }
    
    private Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put("databasePlatform", env.getProperty("databasePlatform"));
        properties.put("generateDdl", env.getRequiredProperty("generateDdl"));
        properties.put("showSql", env.getProperty("showSql"));
        return properties;
    }
    
    */
    
    /**
     * EntityManagerFactoryBean creation
     * @param sqlServerDataSource
     * @param jpaVendorAdapter
     * @return
     */

	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource sqlServerDataSource,JpaVendorAdapter jpaVendorAdapter) {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(sqlServerDataSource);
		entityManagerFactoryBean.setJpaVendorAdapter(jpaVendorAdapter);
		entityManagerFactoryBean.setPackagesToScan("com.estes.myestes.PickupRequest.web.jpa.entity");
		entityManagerFactoryBean.setJpaProperties(jpaProperties());
		return entityManagerFactoryBean;
	}


	@Bean
	public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setEntityManagerFactory(entityManagerFactory);
		return transactionManager;
	}
	
}
