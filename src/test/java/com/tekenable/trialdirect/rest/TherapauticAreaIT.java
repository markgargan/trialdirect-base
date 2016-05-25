package com.tekenable.trialdirect.rest;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

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

        //Create the parent: therapeuticParent
        String output = this.createTextItem("therapeuticparent", "title", "Diabetes");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int newParentID = this.getNewItemId();

        //Create the child: therapeuticArea
        Map<String, String> params = new HashMap(2);
        params.put("title", "T1 Diabetes");
        params.put("therapeuticparent", RestTestResourceTemplate.BASE_URL + "/therapeuticparent/" + newParentID);

        System.out.println(this.createTextItems("therapeuticareas", params));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int newChildID = this.getNewItemId();

        if (newChildID>0) {
//            output = this.updateItemText("therapeuticareas", newID, "answerText", "Astma");
//            System.out.println(output);
//            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

            output = this.deleteItem("therapeuticareas", newChildID);
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

            output = this.deleteItem("therapeuticparent", newParentID);
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        }
    }
}
