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
public class UsersIT extends RestTestResourceTemplate {

    @Test
    public void testGetAllUsers() {        
        String output = this.getAllItems("users");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetSigleUser() {
        String output = this.getSingleItemById("users", 1);
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testGetUserTherapeuticAreas() {
        String output = this.getAllItems("users/"+1+"/therapeuticArea");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    @Test
    public void testCreateUser() {
        String output = this.createTextItem("users", "pseudonym", "Jaja");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }

    //@Test
    public void testUpdateUser() {
        String output = this.updateItemText("users", 2, "pseudonym", "Jaroslaw");
        System.out.println(output);
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
    }
}
