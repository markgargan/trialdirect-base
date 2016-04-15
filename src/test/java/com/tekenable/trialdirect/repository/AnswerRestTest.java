package com.tekenable.trialdirect.repository;

import com.tekenable.repository.AnswerRepository;
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
 * Created by smoczyna on 03/04/16.
 */
public class AnswerRestTest extends RestTestMockTemplate {

    @Autowired
    private AnswerRepository answerRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.answerRepositoryMock;
    }

    @Test
    public void getAllAnswersTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from Answer", Integer.class);
        log.info("Overall Answers found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/answers")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    @Test
    public void getSingleAnswerTest() throws Exception {
        log.info("Reading the first answer");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/answers/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.answerText").value("Stomach"));
        result.andDo(MockMvcResultHandlers.print());
    }
}
