package com.tekenable.trialdirect.rest.resource;

import com.tekenable.repository.AnswerRepository;
import com.tekenable.repository.QuestionRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by smoczyna on 03/04/16.
 */
public class AnswerRestTest extends RestTestResourceTemplate {

    @Autowired
    private AnswerRepository answerRepositoryMock;
    private boolean isMockInitialized = false;

    @Before
    public void init() {
        initDB();
        this.mockInit(answerRepositoryMock);
        this.isMockInitialized = true;
    }

    /*public boolean isMockInitialized() {
        return this.isMockInitialized;
    }

    public void setIsMockInitialized(boolean isMockInitialized) {
        this.isMockInitialized = isMockInitialized;
    }*/

    @Test
    public void getAllAnswersTest() throws Exception {
        if (!this.isMockInitialized) this.init();
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        Integer count = jdbc.queryForObject("select count(*) from Answer", Integer.class);
        log.info("*** START TEST ***");
        log.info("Overall Answers found: "+String.valueOf(count));
        //assertTrue("Pre populated questions are there", count==3);

        mockMvc.perform(get("/answers")).andExpect(status().is2xxSuccessful());
        log.info("*** END OF TEST ***");
    }

}
