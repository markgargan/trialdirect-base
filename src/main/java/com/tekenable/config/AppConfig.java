package com.tekenable.config;


import com.tekenable.config.prime.CancerTrialPrimer;
import com.tekenable.config.prime.TrialDirectPrimer;
import com.tekenable.model.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.stereotype.Controller;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.HBM2DDL_AUTO;

@Configuration
@EnableJpaRepositories(basePackages = {"com.tekenable.repository"})
@ComponentScan(basePackages = "com.tekenable", excludeFilters = {
        @ComponentScan.Filter(value = Controller.class, type = FilterType.ANNOTATION),
        @ComponentScan.Filter(value = Configuration.class, type = FilterType.ANNOTATION)
})
public class AppConfig extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        super.configureRepositoryRestConfiguration(config);
        try {
            config.setBaseUri(new URI("/api"));

            config.exposeIdsFor(Answer.class);
            config.exposeIdsFor(Question.class);
            config.exposeIdsFor(QuestionnaireEntry.class);
            config.exposeIdsFor(Trial.class);
            config.exposeIdsFor(TherapeuticArea.class);
            config.exposeIdsFor(TrialSelectorQuestionnaireEntry.class);
            config.exposeIdsFor(UserSelectorQuestionnaireEntry.class);

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Bean
    public DataSource dataSource() {

        DataSource datasource = null;
        try {
            InitialContext ic = new InitialContext();
            Context initialContext = (Context) ic.lookup("java:comp/env");
            datasource = (DataSource) initialContext.lookup("jdbc/MySQLDS");

        } catch (NamingException e) {
            e.printStackTrace();
        }

        return datasource;
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
        adapter.setShowSql(true);
        adapter.setGenerateDdl(true);
        adapter.setDatabase(Database.MYSQL);
        return adapter;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws ClassNotFoundException {
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource());
        factoryBean.setPackagesToScan("com.tekenable.model");
        factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
        factoryBean.setJpaProperties(jpaProperties());

        return factoryBean;
    }

    @Bean
    public JpaTransactionManager transactionManager() throws ClassNotFoundException {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());

        return transactionManager;
    }

    @Bean
    public Properties jpaProperties() {
        Properties properties = new Properties();
        properties.put(HBM2DDL_AUTO, "create-drop");
        return properties;
    }

    @Bean
    public CancerTrialPrimer cancerTrialPrimer() { return new CancerTrialPrimer(); }

    @Bean
    public TrialDirectPrimer trialDirectPrimer() {
        return new TrialDirectPrimer();
    }

}
