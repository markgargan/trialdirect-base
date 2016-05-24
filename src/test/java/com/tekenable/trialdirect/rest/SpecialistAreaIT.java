package com.tekenable.trialdirect.rest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

    @Test
    public void testAddUpdateDeleteSpecialistArea() {

        //First create a Therapeutic Area
        String output = this.createTextItem("therapeuticareas", "title", "Diabetes");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int taID = this.getNewItemId();

        //Now create the Specialist Area within the Therapeutic area
        Map<String, String> paramsMap = new HashMap<String, String>();
        paramsMap.put("title", "T1 Diabetes");
        //paramsMap.put("therapeuticarea", (RestTestResourceTemplate.BASE_URL + "therapeuticareas/" + taID));

        //paramsMap.put("title", "T2 Diabetes");
        paramsMap.put("therapeuticarea", (RestTestResourceTemplate.BASE_URL + "therapeuticareas/" + taID));

        output = this.createTextItems("specialistareas", paramsMap);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int saID = this.getNewItemId();


//        if (newID>0) {
//
//            output = this.deleteItem("therapeuticareas", newID);
//            System.out.println(output);
//            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
//        }
    }
}
