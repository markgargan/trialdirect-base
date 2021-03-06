/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.repository;

import com.tekenable.config.WebConfig;
import com.tekenable.trialdirect.config.TestConfig;
import com.tekenable.trialdirect.config.TestRepoConfig;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;

/**
 *
 * @author smoczyna
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class, WebConfig.class, TestRepoConfig.class})
@WebAppConfiguration
public abstract class RestTestMockTemplate {

    @Value("classpath:sql/trial-direct-test-data.sql")
    private Resource createTestData;

    @Value("classpath:sql/clear-test-data.sql")
    private Resource clearTestData;

    protected boolean isMockInitialized = false;
    protected static final Logger log = LoggerFactory.getLogger(RestTestMockTemplate.class);
    protected MockMvc mockMvc;

    public abstract PagingAndSortingRepository getRepository();

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Autowired
    protected DataSource dataSource;

    private void mockInit(Object mockedObject) {
        Mockito.reset(mockedObject);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    private void initDB() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(clearTestData);
        DatabasePopulatorUtils.execute(populator, dataSource);
        populator.addScript(createTestData);
        DatabasePopulatorUtils.execute(populator, dataSource);
    }

    public void init() {
        if (!this.isMockInitialized) {
            this.initDB();
            this.mockInit(this.getRepository());
            this.isMockInitialized = true;
        }
    }

    @Before
    public void beforeTest() {
        this.init();
        log.info("*** START TEST ***");
    }

    @After
    public void afterTest() {
        log.info("*** END OF TEST ***");
    }
}
