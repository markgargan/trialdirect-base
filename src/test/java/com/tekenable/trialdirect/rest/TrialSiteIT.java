package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by smoczyna on 12/04/16.
 */
public class TrialSiteIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllTrialSites() throws Exception {
        String output = this.getAllItems("trialsites");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSingleTrialSite() {
        String output = this.getSingleItemById("trialsites", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }
}
