package com.tekenable.trialdirect.repository;

import com.tekenable.repository.UserRepository;
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
public class UserRepositoryTest extends RestTestMockTemplate {

    @Autowired
    public UserRepository userRepositoryMock;

    private boolean isMockInitialized = false;

    public void init() {
        initDB();
        this.mockInit(userRepositoryMock);
        this.isMockInitialized = true;
    }

    @Test
    public void getAllUsersTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from User", Integer.class);
        log.info("Overall Users found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/users")).andExpect(status().is2xxSuccessful());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
        log.info("*** END OF TEST ***");
    }

    @Test
    public void getSingleUserTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first user");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/users/{id}", 1)).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        result.andExpect(jsonPath("$.pseudonym").value("Robert"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }
}
