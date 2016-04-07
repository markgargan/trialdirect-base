/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by smoczyna on 30/03/16.
 */
public class TrialIT extends BaseRestResource {

    @Test
    public void testGetAllTrials() {
        String output = this.getAllItems("trials");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetSingleTrial() {
        String output = this.getSingleItemById("trials", 1);
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetTrialTherapeuticAreas() {
        String output = this.getAllItems("trials/"+1+"/therapeuticArea");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetTrialSelectorQuestionnaireEntries() {
        String output = this.getAllItems("trials/"+1+"/trialselectorquestionnaireentries");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }
}