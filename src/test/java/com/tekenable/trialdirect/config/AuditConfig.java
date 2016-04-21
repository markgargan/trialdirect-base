package com.tekenable.trialdirect.config;

import com.tekenable.config.AuditConfigurator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
public class AuditConfig {

    @Bean(name = "dataSource")
    public DataSource dataSource() throws SQLException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl("jdbc:mysql://localhost:3306/trialdirect?zeroDateTimeBehavior=convertToNull");
        dataSource.setUsername("trialdirect");
        dataSource.setPassword("trialdirect");
        return dataSource;
    }

    @Bean
    public AuditConfigurator auditConfigurator() { return new AuditConfigurator(); }

}
