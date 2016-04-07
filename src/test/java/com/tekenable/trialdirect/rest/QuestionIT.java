package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class QuestionIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllQuestions() throws Exception {
        String outut = this.getAllItems("questions");
        System.out.println(outut);
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleQuestion() throws Exception {
        String outut = this.getSingleItemById("questions", 1);
        System.out.println(outut);
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetQuestionQuestionnireEntries() {
        String outut = this.getAllItems("questions/"+1+"/questionnaireentries");
        System.out.println(outut);
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testAddAndUpdateQuestion() {
        String output = this.createTextItem("questions", "questionText", "Do you take any medication?");
        System.out.println(output);
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        /*output = this.updateItemText("questions", 4, "questionText", "Do you take any medication regularly?");
        System.out.println(output);
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());*/
    }
}
