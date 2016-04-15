package com.tekenable.trialdirect.repository;

import com.tekenable.repository.QuestionnaireEntryRepository;
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
public class QuestinnaireEntryTest extends RestTestMockTemplate {

    @Autowired
    public QuestionnaireEntryRepository qaEntryRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.qaEntryRepositoryMock;
    }

    @Test
    public void getAllEntriesTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from QuestionnaireEntry", Integer.class);
        log.info("Overall Entries found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/questionnaireentries")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    @Test
    public void getSingleEntry() throws Exception {
        log.info("Reading the first entry");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/questionnaireentries/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        //result.andExpect(jsonPath("$.question").value("?")); what can I verify here ?
        result.andDo(MockMvcResultHandlers.print());
    }

}
