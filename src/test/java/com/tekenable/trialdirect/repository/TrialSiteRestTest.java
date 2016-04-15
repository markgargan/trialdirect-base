package com.tekenable.trialdirect.repository;

import com.tekenable.repository.TrialInfoRepository;
import com.tekenable.repository.TrialSiteRepository;
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
 * Created by smoczyna on 12/04/16.
 */
public class TrialSiteRestTest extends RestTestMockTemplate {

    @Autowired
    private TrialSiteRepository trialSiteRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.trialSiteRepositoryMock;
    }

    @Test
    public void getAllAnswersTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from TrialSite", Integer.class);
        log.info("Overall Trial Information records found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/trialsites")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    //@Test
    public void getSingleAnswerTest() throws Exception {
        log.info("Reading the first record");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/trialsites/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.siteDirector").value("Pfizer SiteDirector"));
        result.andExpect(jsonPath("$.siteSummary").value("Pfizer SiteSummary"));
        result.andExpect(jsonPath("$.siteDescription").value("Pfizer siteDescription"));
        result.andDo(MockMvcResultHandlers.print());
    }
}
