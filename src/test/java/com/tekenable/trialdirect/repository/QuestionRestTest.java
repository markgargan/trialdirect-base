/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.repository;

import com.tekenable.repository.QuestionRepository;
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

    @Test
    public void getSingeQuestionTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        log.info("*** START TEST ***");
        log.info("Reading the first question");
        log.info(" ");
        ResultActions result = mockMvc.perform(get("/questions/1")).andExpect(status().is2xxSuccessful());
        assertNotNull(result);
        result.andExpect(jsonPath("$.questionText").value("What is the type of your cancer?"));
        result.andDo(MockMvcResultHandlers.print());
        log.info("*** END OF TEST ***");
    }

    @Test
    public void addQuestionTest() throws Exception {
        /*JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        log.info("*** START TEST ***");
        final Integer countBefore = jdbc.queryForObject("select count(*) from Question", Integer.class);
        log.info("Overall Questions before operation: "+String.valueOf(countBefore));
*/
        /*Question question = new Question("Just another test question");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String jsonRequest = ow.writeValueAsString(question);*/

        /*String question = "{\"questionText\" : \"New test question\"}";
        RequestBuilder rb = post("/questions").contentType(MediaType.APPLICATION_JSON).header("host", "localhost").content(question);
        ResultActions result = mockMvc.perform(rb);
        result.andExpect(status().is2xxSuccessful());*/

        RestTemplate restTemplate = new RestTemplate();
        MockRestServiceServer mockServer = MockRestServiceServer.createServer(restTemplate);

        mockServer.expect(requestTo("/questions")).andExpect(method(HttpMethod.POST))
                .andRespond(withSuccess("200 OK,{ questions : {}},{Content-Type=[application/json]}", MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("entity", "{\"questionText\" : \"New test question\"}");

        ResponseEntity<String> response = restTemplate.postForEntity("/questions", params, String.class);
        mockServer.verify();

       /* Integer countAfter = jdbc.queryForObject("select count(*) from Question", Integer.class);
        log.info("Overall Questions after operation: "+String.valueOf(countAfter));
        assertTrue(countBefore==countAfter-1);*/
    }
}
