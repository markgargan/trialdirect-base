/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author smoczyna
 */
public class QuestionnaireEntryIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllEntries() {
        String output = this.getAllItems("questionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleEntry() {
        String output = this.getSingleItemById("questionnaireentries", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryQuestion() {
        String output = this.getAllItems("questionnaireentries/" + 1 + "/question");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryAnswers() {
        String output = this.getAllItems("questionnaireentries/" + 1 + "/answers");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryTherapeuticArea() {
        String output = this.getAllItems("questionnaireentries/" + 1 + "/therapeuticArea");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }
}

