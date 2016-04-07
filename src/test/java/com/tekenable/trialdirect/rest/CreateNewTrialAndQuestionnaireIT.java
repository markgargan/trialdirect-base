/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tekenable.trialdirect.rest;

import static org.junit.Assert.assertTrue;
import org.junit.Test;

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
    public void testCreateNewQuestionaire() {
        // new Therapeutic Area
        System.out.println(this.createTextItem("therapeuticareas", "title", "Diabetes"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        
        // first question of new questionnaire
        System.out.println(this.createTextItem("questions", "questionText", "What is your diabetes type?"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        
        System.out.println(this.createQuestionnaireEntry(2, 4)); // how to find out those ids programmatically ?
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
                
        // 2 answers corresponding with the first question
        System.out.println(this.createTextItem("answers", "answerText", "Type 1"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        System.out.println(this.createTextItem("answers", "answerText", "Type 2"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        this.assingAnswersToEntry(4, new int[]{13, 14});
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        
        // second question with answers
        System.out.println(this.createTextItem("questions", "questionText", "How long do you have diabetes problem (in years)?"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        System.out.println(this.createQuestionnaireEntry(2, 5));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        System.out.println(this.createTextItem("answers", "answerText", "0-3"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        System.out.println(this.createTextItem("answers", "answerText", "3-5"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        System.out.println(this.createTextItem("answers", "answerText", "5-10"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        System.out.println(this.createTextItem("answers", "answerText", "10 and more"));
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());

        this.assingAnswersToEntry(5, new int[]{15, 16, 17, 18});
        assertTrue(BaseRestResource.REST_TEST_DESC, this.getStatus().is2xxSuccessful());
        
        // at this stage there should be a new questionnaire for Diabetes 
        // with 2 questons and assigned set of aswers
    }
}
