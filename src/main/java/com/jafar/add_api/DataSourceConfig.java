package com.jafar.add_api;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {

    @Autowired
    private DatabaseCredentialService credentialService;


    @Bean
    public DataSource dataSource() {
        String url = credentialService.getUrl();
        String username = credentialService.getUsername();
        String password = credentialService.getPassword();

        return DataSourceBuilder.create()
                .url(url)
                .username(username)
                .password(password)
                .driverClassName("org.postgresql.Driver") //  Set the driver class name
                .build();
    }
}
