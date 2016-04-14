/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 * @author smoczyna
 */
public class TrialSelectorQuestionnaireEntriesIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllEntries() {
        String output = this.getAllItems("trialselectorquestionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleEntry() {
        String output = this.getSingleItemById("trialselectorquestionnaireentries", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryTrial() {
        String output = this.getAllItems("trialselectorquestionnaireentries/"+1+"/trial");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryQuestion() {
        String output = this.getAllItems("trialselectorquestionnaireentries/"+1+"/question");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryAnswer() {
        String output = this.getAllItems("trialselectorquestionnaireentries/"+1+"/answer");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }
}