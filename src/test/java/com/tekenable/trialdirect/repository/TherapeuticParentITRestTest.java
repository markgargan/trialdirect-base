package com.tekenable.trialdirect.repository;

import com.tekenable.repository.TherapeuticParentRepository;
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
 * Created by nbarrett on 25/05/2016.
 */
public class TherapeuticParentITRestTest extends RestTestMockTemplate {

    @Autowired
    public TherapeuticParentRepository therapeuticParentRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.therapeuticParentRepositoryMock;
    }

    @Test
    public void getAllParentAreasTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from TherapeuticParent", Integer.class);
        log.info("Overall Therapeutic Parents found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/therapeuticparent")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    @Test
    public void getSingleArea() throws Exception {
        log.info("Reading the first Therapeutic parent");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/therapeuticparent/{therapeutic_parent_id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.title").value("Cancer"));
        result.andDo(MockMvcResultHandlers.print());
    }
}
