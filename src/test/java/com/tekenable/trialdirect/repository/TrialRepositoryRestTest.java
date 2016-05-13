package com.tekenable.trialdirect.repository;

import com.tekenable.repository.TrialRepository;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
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
public class TrialRepositoryRestTest extends RestTestMockTemplate {

    @Autowired
    public TrialRepository trialRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.trialRepositoryMock;
    }

    @Test
    public void getAllTrialsTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from Trial", Integer.class);
        log.info("Overall Trials found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/trials")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    @Test
    public void getSingleTrialTest() throws Exception {
        log.info("Reading the first trial");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/trials/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.title").value("Cancer Trial"));
        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getTrialSelectorQuestionnaireEntries() throws Exception {
        log.info("Reading coresponding trial selector quetionnaire entries");
        log.info(" ");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from TrialSelectorQuestionnaireEntry", Integer.class);
        ResultActions result = mockMvc.perform(get("/trials/{id}/trialselectorquestionnaireentries", 1)).andExpect(status().isOk());
        assertNotNull(result);
//        result.andExpect(jsonPath("$._embedded.trialselectorquestionnaireentries.*", hasSize(12)));
    }

}
