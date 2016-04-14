package com.tekenable.trialdirect.repository;

import com.tekenable.repository.TherapeuticAreaRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static junit.framework.TestCase.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by smoczyna on 04/04/16.
 */
public class TherapeuticAreaRestTest extends RestTestMockTemplate {

    @Autowired
    public TherapeuticAreaRepository therapeuticAreaRepositoryMock;

    private boolean isMockInitialized = false;

    public void init() {
        initDB();
        this.mockInit(therapeuticAreaRepositoryMock);
        this.isMockInitialized = true;
    }

    @Test
    public void getAllTAreasTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from TherapeuticArea", Integer.class);
        log.info("Overall Therapeutic Areas found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/therapeuticareas")).andExpect(status().is2xxSuccessful());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
        log.info("*** END OF TEST ***");
    }

    @Test
    public void getSingleArea() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first therapeutic area");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/therapeuticareas/{id}", 1)).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        result.andExpect(jsonPath("$.title").value("Cancer"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }
}
