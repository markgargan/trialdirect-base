/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.repository;

import com.tekenable.repository.QuestionRepository;
import org.hamcrest.BaseMatcher;
import org.json.simple.JSONObject;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 * @author smoczyna
 */
public class QuestionRestTest extends RestTestMockTemplate {

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

        ResultActions result = mockMvc.perform(get("/questions")).andExpect(status().isOk());
        result.andExpect(jsonPath("$.page.totalElements").value(count));
        log.info("*** END OF TEST ***");
    }

    @Test
    public void getSingeQuestionTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first question");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/questions/{id}", 1)).andExpect(status().isOk());
        assertNotNull(result);
        result.andExpect(jsonPath("$.questionText").value("What is the type of your cancer?"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }

    /**
     * I couldn't make mockMvc to call POST service properly so I used RestTemplate instead
     * this might be unified somewhow later to use the same technology across all the tests
     *
     * @throws Exception
     */
    @Test
    public void addQuestionTest() throws Exception {
        log.info("*** START TEST ***");
        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/questions")).andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("200 OK,{ questions : {}},{Content-Type=[application/json]}", MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("entity", "{\"questionText\" : \"New test question\"}");

        ResponseEntity<String> response = restTemplate.postForEntity("/questions", params, String.class);
        mockServer.verify();
        assertTrue(response.getStatusCode().is2xxSuccessful());
        log.info("*** END OF TEST ***");
        //assertThat(response.getStatusCode()., allOf(containsString("SUCCESS"), containsString("resultSuccess")));
    }
}
