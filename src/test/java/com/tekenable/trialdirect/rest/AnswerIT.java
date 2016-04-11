package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by smoczyna on 30/03/16.
 */
public class AnswerIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllAnswers() throws Exception {
        String output = this.getAllItems("answers");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleAnswer() {
        String output = this.getSingleItemById("answers", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetAnswerQuestionnireEntries() {
        String uri = "answers/" + 1 + "/questionnaireentries";
        String output = this.getAllItems(uri);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testAddUpdateDeleteAswer() {
        String output = this.createTextItem("answers", "answerText", "Brain");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int newID = this.getNewItemId();
        if (newID>0) {
            output = this.updateItemText("answers", 13, "answerText", "Breast");
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

            output = this.deleteItem("answers", 13);
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        }
    }
}