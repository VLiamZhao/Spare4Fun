package com.spare4fun.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class HibernateConfig {
    @Autowired
    private Environment env;

    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(env.getProperty("packagesToScan"));
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource  ds = new DriverManagerDataSource ();
        ds.setDriverClassName(env.getProperty("datasource.driver-class-name"));
        ds.setUrl(Secret.LOCAL_DB);
        ds.setUsername(Secret.USERNAME);
        ds.setPassword(Secret.PASSWORD);
        return ds;
    }

    private final Properties hibernateProperties() {
        Properties hibernate = new Properties();
        hibernate.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernate.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernate.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        return hibernate;
    }
}