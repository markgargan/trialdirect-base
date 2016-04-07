/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 *
 * @author smoczyna
 */
public class QuestionnaireEntryIT extends BaseRestResource {

    @Test
    public void testGetAllEntries() {
        String output = this.getAllItems("questionnaireentries");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetSingleEntry() {
        String output = this.getSingleItemById("questionnaireentries", 1);
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetEntryQuestion() {
        String output = this.getAllItems("questionnaireentries/" + 1 + "/question");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetEntryAnswers() {
        String output = this.getAllItems("questionnaireentries/" + 1 + "/answers");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetEntryTherapeuticArea() {
        String output = this.getAllItems("questionnaireentries/" + 1 + "/therapeuticArea");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }
}

