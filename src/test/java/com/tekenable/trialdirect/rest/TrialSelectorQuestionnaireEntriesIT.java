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
public class TrialSelectorQuestionnaireEntriesIT extends BaseRestResource {

    @Test
    public void testGetAllEntries() {
        String output = this.getAllItems("trialselectorquestionnaireentries");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetSingleEntry() {
        String output = this.getSingleItemById("trialselectorquestionnaireentries", 1);
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetEntryTrial() {
        String output = this.getAllItems("trialselectorquestionnaireentries/"+1+"/trial");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetEntryQuestion() {
        String output = this.getAllItems("trialselectorquestionnaireentries/"+1+"/question");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetEntryAnswer() {
        String output = this.getAllItems("trialselectorquestionnaireentries/"+1+"/answer");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }
}