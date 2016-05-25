/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by smoczyna on 30/03/16.
 */
public class TrialIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllTrials() {
        String output = this.getAllItems("trials");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleTrial() {
        String output = this.getSingleItemById("trials", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetTrialTherapeuticAreas() {
        String output = this.getAllItems("trials/"+1+"/therapeuticarea");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetTrialSelectorQuestionnaireEntries() {
        String output = this.getAllItems("trials/"+1+"/trialselectorquestionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    public void testAddUpdateDeleteTrial() {
        String output = this.createTextItem("trials", "title", "Diabetes Trial");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int newID = this.getNewItemId();
        if (newID>0) {
            output = this.updateItemText("trials", newID, "answerText", "Astma Trial");
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

            output = this.deleteItem("trials", newID);
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        }

    }
}