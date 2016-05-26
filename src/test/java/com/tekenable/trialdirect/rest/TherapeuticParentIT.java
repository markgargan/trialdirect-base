package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by nbarrett on 25/05/2016.
 */
public class TherapeuticParentIT extends RestTestResourceTemplate {


    @Test
    public void testGetAllTherapeuticParent() throws Exception {
        String output = this.getAllItems("therapeuticparent");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleTherapeuticParent() {
        String output = this.getSingleItemById("therapeuticparent", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetTherapeuticAreasForParent() {
        String output = this.getAllItems("therapeuticparent/" + 1 + "/therapeuticareas");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testAddUpdateDeleteTherapeuticParent() {

        String output = this.createTextItem("therapeuticparent", "title", "Diabetes");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        int newID = this.getNewItemId();
        if (newID>0) {
//            output = this.updateItemText("therapeuticparent", newID, "answerText", "Astma");
//            System.out.println(output);
//            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

            output = this.deleteItem("therapeuticparent", newID);
            System.out.println(output);
            assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        }
    }

}
