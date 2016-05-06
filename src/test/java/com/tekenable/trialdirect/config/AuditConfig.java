package com.tekenable.trialdirect.config;

import com.tekenable.config.AuditConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
public class AuditConfig {

    public static final Logger log = LoggerFactory.getLogger(AuditConfigurator.class);

    @Value("classpath:AuditConfigDb.properties")
    private Resource localDbProps;

    @Bean(name = "dataSource")
    public DataSource dataSource() throws SQLException, IOException {
        final SimpleDriverDataSource dataSource = new SimpleDriverDataSource();

        Properties dbProps = new Properties();
        try {
            dbProps.load(localDbProps.getInputStream());
        } catch (IOException e) {

            log.error("***********Please ensure that AuditConfigDb.properties file exists " +
                    "and contains your local database connection details*********");
            throw new IOException("Cannot load local database properties, " +
                                  "please ensure that AuditConfigDb.properties file exists " +
                                  "and contains your local database connection details");
        }
        dataSource.setDriver(new com.mysql.jdbc.Driver());
        dataSource.setUrl(dbProps.getProperty("dbUrl"));
        dataSource.setUsername(dbProps.getProperty("dbUsername"));
        dataSource.setPassword(dbProps.getProperty("dbPassword"));
        return dataSource;
    }

    @Bean
    public AuditConfigurator auditConfigurator() { return new AuditConfigurator(); }

}
