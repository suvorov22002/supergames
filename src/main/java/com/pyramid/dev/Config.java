package com.pyramid.dev;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration(exclude = { HibernateJpaAutoConfiguration.class})
@ComponentScans(value = { @ComponentScan("boot.entry"),
		 
	      @ComponentScan("com.pyramid.dev.exception"),
	      @ComponentScan("com.pyramid.dev.enums"),
	      @ComponentScan("com.pyramid.dev.controller"),
	      @ComponentScan("com.pyramid.dev.dao"),
	      @ComponentScan("com.pyramid.dev.daoimpl"),
	      @ComponentScan("com.pyramid.dev.business"),
	      @ComponentScan("com.pyramid.dev.model"),
	      @ComponentScan("Miscallaneous"),
	      @ComponentScan("com.pyramid.dev.tools"),
	      @ComponentScan("com.pyramid.dev.service"),
		  @ComponentScan("com.pyramid.dev.serviceimpl")})
public class Config {
//	 @Autowired
  //   ApplicationContext applicationContext;
	 
	@Value("${spring.datasource.driver-class-name}")
    private String DB_DRIVER;

    @Value("${spring.datasource.password}")
    private String DB_PASSWORD;

    @Value("${spring.datasource.url}")
    private String DB_URL;

    @Value("${spring.datasource.username}")
    private String DB_USERNAME;

    @Value("${spring.jpa.properties.hibernate.dialect}")
    private String HIBERNATE_DIALECT;

    @Value("${spring.jpa.show-sql}")
    private String HIBERNATE_SHOW_SQL;

    @Value("${spring.jpa.hibernate.ddl-auto}")
    private String HIBERNATE_HBM2DDL_AUTO;

    @Value("${entitymanager.packagesToScan}")
    private String ENTITYMANAGER_PACKAGES_TO_SCAN;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(ENTITYMANAGER_PACKAGES_TO_SCAN);
      
        Properties hibernateProperties = new Properties();
        hibernateProperties.put("hibernate.dialect", HIBERNATE_DIALECT);
        hibernateProperties.put("hibernate.show_sql", HIBERNATE_SHOW_SQL);
        hibernateProperties.put("hibernate.hbm2ddl.auto", HIBERNATE_HBM2DDL_AUTO);
        hibernateProperties.put("hibernate.c3p0.min_size", 1);
        hibernateProperties.put("hibernate.c3p0.max_size", 50);
        hibernateProperties.put("hibernate.jdbc.batch_size", 20);
        hibernateProperties.put("hibernate.c3p0.acquire_increment", 5);
        hibernateProperties.put("hibernate.c3p0.timeout", 1800);
        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(DB_DRIVER);
        dataSource.setUrl(DB_URL);
        dataSource.setUsername(DB_USERNAME);
        dataSource.setPassword(DB_PASSWORD);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager /*HibernateTransactionManager*/ transactionManager() {
        HibernateTransactionManager txManager = new HibernateTransactionManager();
        txManager.setSessionFactory(sessionFactory().getObject());
        return txManager;
    }
    
    @Bean
    public InternalResourceViewResolver jspViewResolver() {
        InternalResourceViewResolver resolver= new InternalResourceViewResolver();
        resolver.setPrefix("/views/");
        resolver.setSuffix(".jsp");
        return resolver;
    } 
    
//    @Bean
//    public ApplicationContext springUtils() {
//    	ApplicationContext ctx;
//
//        /**
//         * Make Spring inject the application context
//         * and save it on a static variable,
//         * so that it can be accessed from any point in the application. 
//         */
//       
//     
//            ctx = applicationContext;       
//       return ctx;
//    }
   
}
