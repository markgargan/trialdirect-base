package com.tekenable.trialdirect.repository;

import com.tekenable.repository.TrialInfoRepository;
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
 * Created by smoczyna on 12/04/16.
 */
public class TrialInfoRestTest extends RestTestMockTemplate {

    @Autowired
    private TrialInfoRepository trialInfoRepositoryMock;

    private boolean isMockInitialized = false;

    public void init() {
        initDB();
        this.mockInit(trialInfoRepositoryMock);
        this.isMockInitialized = true;
    }

    @Test
    public void getAllAnswersTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from TrialInfo", Integer.class);
        log.info("Overall Trial Information records found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/trialinfos")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
        log.info("*** END OF TEST ***");
    }

    public void getSingleAnswerTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first record");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/trialinfostrialinfos/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.description").value("Pfizer Info1"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }
}
