package com.tekenable.trialdirect.rest;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * Created by smoczyna on 30/03/16.
 */
public class TherapauticAreaIT extends BaseRestResource {

    @Test
    public void testGetAllTherapeuticAreas() throws Exception {
        String output = this.getAllItems("therapeuticareas");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetSingleTherapeuticArea() {
        String output = this.getSingleItemById("therapeuticareas", 1);
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetTaQuestionnaireEntries() {
        String output = this.getAllItems("therapeuticareas/" + 1 + "/questionnaireentries");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetTaTrials() {
        String output = this.getAllItems("therapeuticareas/" + 1 + "/trials");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }

    @Test
    public void testGetTaUserSelectorQuestionnaireEntries() {
        String output = this.getAllItems("therapeuticareas/" + 1 + "/userselectorquestionnaireentries");
        System.out.println(output);
        assertEquals(BaseRestResource.REST_TEST_DESC, this.getResponseCode(), 200);
    }
    
}
