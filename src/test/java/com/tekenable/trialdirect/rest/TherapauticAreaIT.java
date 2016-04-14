package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by smoczyna on 30/03/16.
 */
public class TherapauticAreaIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllTherapeuticAreas() throws Exception {
        String output = this.getAllItems("therapeuticareas");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleTherapeuticArea() {
        String output = this.getSingleItemById("therapeuticareas", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetTaQuestionnaireEntries() {
        String output = this.getAllItems("therapeuticareas/" + 1 + "/questionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetTaTrials() {
        String output = this.getAllItems("therapeuticareas/" + 1 + "/trials");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetTaUserSelectorQuestionnaireEntries() {
        String output = this.getAllItems("therapeuticareas/" + 1 + "/userselectorquestionnaireentries");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testAddUpdateDeleteTherapeuticArea() {
        String output = this.createTextItem("therapeuticareas", "title", "Diabetes");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int newID = this.getNewItemId();
        if (newID>0) {
            output = this.updateItemText("therapeuticareas", newID, "answerText", "Astma");
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

            output = this.deleteItem("therapeuticareas", newID);
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        }
    }
}
