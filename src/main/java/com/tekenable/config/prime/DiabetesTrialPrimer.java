package com.tekenable.config.prime;

import com.tekenable.controller.QuestionnaireController;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by smoczyna on 22/03/16.
 * this is another way to population the questionnaire
 */
public class DiabetesTrialPrimer {

    @Autowired
    private QuestionnaireController questionnaireController;

    public void initDB() {
        this.questionnaireController.createQuestionaire("Diabetes Questionnaire");

        this.questionnaireController.addQuestionaireEntry("First Question", "diabetes",
                "What is the type of your diabetes?", new String[] {"Type 1", "Type 2"});

        this.questionnaireController.addQuestionaireEntry("Second Question", "diabetes",
                "How long do you have diabetes problem (in years)?", new String[] {"0-3", "3-5", "5-10", "10 and more"});

        this.questionnaireController.addQuestionaireEntry("Third Question", "diabetes",
                "What is your weight (kg)?", new String[] {"less than 60", "60-80", "80-100", "100 or more"});

        this.questionnaireController.addQuestionaireEntry("Fourth Question", "diabetes",
                "Do you have any other serious helth issue?", new String[] {"Yes", "No"});
    }
}
