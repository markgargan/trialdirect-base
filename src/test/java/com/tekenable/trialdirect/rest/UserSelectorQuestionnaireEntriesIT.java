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
public class UserSelectorQuestionnaireEntriesIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllEntries() {
        String output = this.getAllItems("userselectorquestionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleEntry() {
        String output = this.getSingleItemById("userselectorquestionnaireentries", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryUser() {
        String output = this.getAllItems("userselectorquestionnaireentries/"+1+"/user");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryQuestion() {
        String output = this.getAllItems("userselectorquestionnaireentries/"+1+"/question");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryAnswer() {
        String output = this.getAllItems("userselectorquestionnaireentries/"+1+"/answer");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetEntryTherapeuticArea() {
        String output = this.getAllItems("userselectorquestionnaireentries/"+1+"/therapeuticArea");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }
}

