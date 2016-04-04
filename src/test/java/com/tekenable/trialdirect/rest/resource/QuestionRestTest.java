/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest.resource;

import com.tekenable.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author smoczyna
 */
public class QuestionRestTest extends RestTestResourceTemplate {

    @Autowired
    private QuestionRepository questionRepositoryMock;
    private boolean isMockInitialized = false;

    public void init() {
        initDB();
        this.mockInit(questionRepositoryMock);
        this.isMockInitialized = true;
    }

    @Test
    public void getAllQuestionsTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        final Integer count = jdbc.queryForObject("select count(*) from Question", Integer.class);
        log.info("Overall Questions found: "+String.valueOf(count));
        //assertTrue("Pre populated questions are there", count==3);

        //MvcResult result = mockMvc.perform(get("/questions")).andExpect(status().is2xxSuccessful()).andReturn();
        //allQuestions.getResponse().
        //assertEquals(count, ((net.minidev.json.JSONArray) result.read("$")).size());

        ResultActions result = mockMvc.perform(get("/questions")).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        /*result.andExpect(jsonPath("$.questions.*").value(new BaseMatcher() {
            @Override
            public void describeTo(org.hamcrest.Description description) {
                // not for now
            }

            @Override
            public boolean matches(Object obj) {
                return obj instanceof JSONObject && ((JSONObject) obj).length() == count;
            }
        }));*/
        //result.andExpect(jsonPath("$", hasSize(count)));
        log.info("*** END OF TEST ***");
    }

    //@Test
    public void getSingeQuestionTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first question");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/questions/1")).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        result.andExpect(jsonPath("$.questions.questionText").value("What is the type of your cancer?"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }

    //@Test
    public void addQuestionTest() throws Exception {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        final Integer count = jdbc.queryForObject("select count(*) from Question", Integer.class);
        log.info("Overall Questions before operation: "+String.valueOf(count));

        String question = "{\"questionText\" : \"#QUESTION#\"}".replace("#QUESTION#", "New test question");
        ResultActions result = mockMvc.perform(post("/questions").contentType(MediaType.APPLICATION_JSON).content(question));
        assertNotNull(result);
        result.andExpect(status().is2xxSuccessful());

        Integer count2 = jdbc.queryForObject("select count(*) from Question", Integer.class);
        log.info("Overall Questions after operation: "+String.valueOf(count2));
        assertTrue(count==count2-1);
    }
}
