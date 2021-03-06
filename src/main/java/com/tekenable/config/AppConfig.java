package com.tekenable.config;


import com.tekenable.config.prime.CancerQuestionnairePrimer;
import com.tekenable.config.prime.TrialDirectPrimer;
import com.tekenable.config.prime.trial.cancer.glaxo.GlaxoCancerPrimer;
import com.tekenable.config.prime.trial.cancer.lundbeck.LundbeckCancerPrimer;
import com.tekenable.config.prime.trial.cancer.pfizer.PfizerCancerPrimer;
import com.tekenable.model.*;
import com.tekenable.model.trial.TrialDirectImage;
import com.tekenable.model.trial.TrialInfo;
import com.tekenable.model.trial.TrialSite;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;
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
            config.exposeIdsFor(TherapeuticParent.class);
            config.exposeIdsFor(TherapeuticArea.class);
            config.exposeIdsFor(TrialSelectorQuestionnaireEntry.class);
            config.exposeIdsFor(UserSelectorQuestionnaireEntry.class);
            config.exposeIdsFor(User.class);

            config.exposeIdsFor(Trial.class);
            config.exposeIdsFor(TrialInfo.class);
            config.exposeIdsFor(TrialSite.class);
            config.exposeIdsFor(TrialDirectImage.class);
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
    public JdbcTemplate jdbcTemplate() {

        JdbcTemplate jdbcTemplate = null;
        jdbcTemplate = new JdbcTemplate(dataSource());

        return jdbcTemplate;
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
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        PropertySourcesPlaceholderConfigurer c = new PropertySourcesPlaceholderConfigurer();
        c.setLocations(new ClassPathResource("primer/lundbeck/lundbeck.properties"),
                new ClassPathResource("primer/glaxosmithklein/glaxo.properties"),
                new ClassPathResource("primer/pfizer/pfizer.properties"));
        return c;
    }

    @Bean
    public CancerQuestionnairePrimer cancerQuestionnairePrimer() { return new CancerQuestionnairePrimer(); }

    @Bean
    public GlaxoCancerPrimer glaxoCancerPrimer() { return new GlaxoCancerPrimer(); }

    @Bean
    public LundbeckCancerPrimer lundbeckCancerPrimer() { return new LundbeckCancerPrimer(); }

    @Bean
    public PfizerCancerPrimer pfizerCancerPrimer() { return new PfizerCancerPrimer(); }

    @Bean
    public TrialDirectPrimer trialDirectPrimer() {
        return new TrialDirectPrimer();
    }

    @Bean
    public AuditConfigurator auditConfigurator() { return new AuditConfigurator(); }
}
