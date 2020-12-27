package com.spare4fun.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
@Configuration
public class DataSourceTestConfig {
    @Autowired
    private Environment env;

    @Bean
    @Profile("test")
    public DataSource dataSource() {
        DriverManagerDataSource  ds = new DriverManagerDataSource();
        ds.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        ds.setUrl(env.getProperty("spring.datasource.test.url"));
        ds.setUsername(env.getProperty("spring.datasource.test.username"));
        ds.setPassword(env.getProperty("spring.datasource.test.password"));
        return ds;
    }
}