package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by nbarrett on 20/05/2016.
 */
public class SpecialistAreaIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllSpecialistAreas() throws Exception {
        System.out.println("Running: SpecialistAreaIT...");
        String output = this.getAllItems("specialistareas");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleSpecialistArea() {
        String output = this.getSingleItemById("specialistareas", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSaQuestionnaireEntries() {
        String output = this.getAllItems("specialistareas/" + 1 + "/questionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSaTrials() {
        String output = this.getAllItems("specialistareas/" + 1 + "/trials");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSaUserSelectorQuestionnaireEntries() {
        String output = this.getAllItems("specialistareas/" + 1 + "/userselectorquestionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }
}
