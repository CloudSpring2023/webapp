package com.example.assignment1.config;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.url}")
    private String datasourceUrl;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
//		System.out.println(datasourceUrl+" "+username+" "+password);
        return DataSourceBuilder.create().driverClassName("com.mysql.cj.jdbc.Driver").url(datasourceUrl)
                .username(username).password(password).build();
    }
}
