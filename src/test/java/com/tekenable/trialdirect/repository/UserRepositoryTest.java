package com.tekenable.trialdirect.repository;

import com.tekenable.repository.UserRepository;
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
public class UserRepositoryTest extends RestTestMockTemplate {

    @Autowired
    public UserRepository userRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.userRepositoryMock;
    }

    @Test
    public void getAllUsersTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from User", Integer.class);
        log.info("Overall Users found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/users")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    @Test
    public void getSingleUserTest() throws Exception {
        log.info("Reading the first user");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/users/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.pseudonym").value("Robert"));
        result.andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void getyUserSelectorQuestionnaireEntries() throws Exception {
        log.info("Reading user selectror questionnaire entires");
        log.info(" ");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from UserSelectorQuestionnaireEntry", Integer.class);
        ResultActions result = mockMvc.perform(get("/userselectorquestionnaireentries", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }
}
