package com.tekenable.trialdirect.repository;

import com.tekenable.repository.TrialRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by smoczyna on 04/04/16.
 */
public class TrialRepositoryRestTest extends RestTestResourceTemplate {

    @Autowired
    public TrialRepository trialRepositoryMock;

    private boolean isMockInitialized = false;

    public void init() {
        initDB();
        this.mockInit(trialRepositoryMock);
        this.isMockInitialized = true;
    }

    @Test
    public void getAllTrialsTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from Trial", Integer.class);
        log.info("Overall Trials found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/trials")).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        log.info("*** END OF TEST ***");
    }

    @Test
    public void getSingleTrialTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first trial");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/trials/1")).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        result.andExpect(jsonPath("$.title").value("Cancer Trial"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }
}
