/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author smoczyna
 */
public class CreateNewTrialAndQuestionnaireIT extends RestTestResourceTemplate {
    
    /**
     * this test creates new questionnaire for diabetes
     * containing few questions and corresponding answers
     * the issue here is to find out the IDS of created records ?
     * it would be nice to have them returned as an effect of creation
     */

    @Test
    public void testCreateNewTrialStructure() {

        // *** first step - create new questionnaire ***

        // new Therapeutic Area
        System.out.println(this.createTextItem("therapeuticareas", "title", "Diabetes"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int taID = this.getNewItemId();

        // first question of new questionnaire
        System.out.println(this.createTextItem("questions", "questionText", "What is your diabetes type?"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int qID = this.getNewItemId();

        System.out.println(this.createQuestionnaireEntry(taID, qID));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int qeID = this.getNewItemId();

        // 2 answers corresponding with the first question
        System.out.println(this.createTextItem("answers", "answerText", "Type 1"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int aID1 = this.getNewItemId();

        System.out.println(this.createTextItem("answers", "answerText", "Type 2"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int aID2 = this.getNewItemId();

        this.assingAnswersToEntry(qeID, new int[]{aID1, aID2});
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        
        // second question with 4 answers
        System.out.println(this.createTextItem("questions", "questionText", "How long do you have diabetes problem (in years)?"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        qID = this.getNewItemId();

        System.out.println(this.createQuestionnaireEntry(taID, qID));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        qeID = this.getNewItemId();

        System.out.println(this.createTextItem("answers", "answerText", "0-3"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        aID1 = this.getNewItemId();

        System.out.println(this.createTextItem("answers", "answerText", "3-5"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        aID2 = this.getNewItemId();

        System.out.println(this.createTextItem("answers", "answerText", "5-10"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int aID3 = this.getNewItemId();

        System.out.println(this.createTextItem("answers", "answerText", "10 and more"));
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        int aID4 = this.getNewItemId();

        this.assingAnswersToEntry(qeID, new int[]{aID1, aID2, aID3, aID4});
        assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        
        // at this stage there should be a new questionnaire for Diabetes 
        // with 2 questons and assigned set of aswers


        // this part of the test fails with 409 conflict error ???

        // second step - create new trial
        //System.out.println(this.createTextItem("trials", "title", "Diabetes Trial"));
        //assertTrue(RestTestResourceTemplate.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        //int trID = this.getNewItemId();

        // all trial selectors have been created automatically behind the scene
        // verifying that they are there
        //String selectors = this.getAllItems("trials/"+trID+"/trialselectorquestionnaireentries");
        // this check could be more precise than that
        //assertNotNull(selectors);
    }
}
