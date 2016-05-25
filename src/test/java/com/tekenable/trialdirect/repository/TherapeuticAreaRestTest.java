package com.tekenable.trialdirect.repository;

import com.tekenable.repository.SpecialistAreaRepository;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.PagingAndSortingRepository;
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
    public SpecialistAreaRepository specialistAreaRepositoryMock;

    @Override
    public PagingAndSortingRepository getRepository() {
        return this.specialistAreaRepositoryMock;
    }

    @Test
    public void getAllSAreasTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from SpecialistArea", Integer.class);
        log.info("Overall Specialist Areas found: "+String.valueOf(count));

        ResultActions result = mockMvc.perform(get("/childareas")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
    }

    @Test
    public void getSingleArea() throws Exception {
        log.info("Reading the first Specialist area");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/childareas/{specialist_area_id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.title").value("Lung Cancer"));
        result.andDo(MockMvcResultHandlers.print());
    }
}
