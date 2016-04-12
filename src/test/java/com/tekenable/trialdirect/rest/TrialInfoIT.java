package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by smoczyna on 12/04/16.
 */
public class TrialInfoIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllTrialInfos() throws Exception {
        String output = this.getAllItems("trialinfos");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleTrialInfo() {
        String output = this.getSingleItemById("trialinfos", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

}
